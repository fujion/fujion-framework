/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * #L%
 */
package org.fujion.websocket;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.fujion.client.ClientInvocation;
import org.fujion.client.ClientRequest;
import org.fujion.client.ExecutionContext;
import org.fujion.common.Logger;
import org.fujion.common.MiscUtil;
import org.fujion.component.Page;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.Assert;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Handler for all web socket communications.
 */
public class WebSocketHandler extends AbstractWebSocketHandler implements BeanPostProcessor {

    private static final Logger log = Logger.create(WebSocketHandler.class);

    private static final String ATTR_BUFFER = "message_buffer";

    private static final Map<String, IRequestHandler> handlers = new HashMap<>();

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final ObjectReader reader = mapper.readerFor(Map.class);

    private static final ObjectWriter writer = mapper.writerFor(Map.class);

    private static final Sessions sessions = Sessions.getInstance();

    /**
     * Register a request handler.
     *
     * @param handler The request handler.
     */
    public static void registerRequestHandler(IRequestHandler handler) {
        String type = handler.getRequestType();
        Assert.isTrue(!handlers.containsKey(type),
            () -> "Attempt to register a duplicate request handler for request type: " + type);
        handlers.put(type, handler);
    }

    /**
     * Sends a json payload to the client via the web socket session.
     *
     * @param socket The web socket session. If null, the session is derived from the current
     *            execution context.
     * @param json The json payload.
     */
    private static void sendData(WebSocketSession socket, String json) {
        try {
            Session session = resolveSession(socket);
            socket = socket == null ? session.getSocket() : socket;
            socket.sendMessage(new TextMessage(json));
            session.updateLastActivity();
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Sends a client invocation request to the client via the web socket session derived from the
     * current execution context.
     *
     * @param invocation The client invocation request.
     */
    public static void send(ClientInvocation invocation) {
        send(null, invocation);
    }

    /**
     * Sends a client invocation request to the client via the web socket session.
     *
     * @param socket The web socket session. If null, the session is derived from the current
     *            execution context.
     * @param invocation The client invocation request.
     */
    public static void send(WebSocketSession socket, ClientInvocation invocation) {
        try {
            Session session = resolveSession(socket);
            session.notifySessionListeners(invocation);
            String json = writer.writeValueAsString(invocation.toMap(session.getPage()));
            sendData(socket, json);
        } catch (Exception e) {
            log.error(e);
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * Sends multiple client invocation requests to the client via the web socket session derived
     * from the current execution context.
     *
     * @param invocations The client invocation requests.
     */
    public static void send(Collection<ClientInvocation> invocations) {
        send(null, invocations);
    }

    /**
     * Sends multiple client invocation requests to the client via the web socket session.
     *
     * @param socket The web socket session. If null, the session is derived from the current
     *            execution context.
     * @param invocations The client invocation requests.
     */
    public static void send(WebSocketSession socket, Iterable<ClientInvocation> invocations) {
        StringBuilder sb = null;
        Session session = resolveSession(socket);
        Page page = session.getPage();
        
        try {
            for (ClientInvocation invocation : invocations) {
                sb = sb == null ? new StringBuilder() : sb;
                String json = writer.writeValueAsString(invocation.toMap(page));
                sb.append(sb.length() == 0 ? "[" : ",").append(json);
                session.notifySessionListeners(invocation);
            }

            if (sb != null) {
                sb.append("]");
                sendData(socket, sb.toString());
            }
        } catch (Exception e) {
            log.error("Unexpected exception", e);
        }
    }

    /**
     * Sends an exception to the client for display via the web socket session.
     *
     * @param socket The web socket session. If null, the session is derived from the current
     *            execution context.
     * @param exception The exception.
     */
    public static void sendError(WebSocketSession socket, Throwable exception) {
        log.error("Uncaught exception", exception);
        Throwable cause = ExceptionUtils.getRootCause(exception);
        cause = cause == null ? exception : cause;
        
        try (StringWriter writer = new StringWriter(); PrintWriter print = new PrintWriter(writer);) {
            cause.printStackTrace(print);
            ClientInvocation invocation = new ClientInvocation("fujion.alert", null, writer.toString(), "Error", "danger");
            send(socket, invocation);
        } catch (Exception e) {
            log.error("Could not send exception to client.", exception);
        }
    }

    /**
     * Returns a session given a web socket.
     *
     * @param socket A web socket (may be null).
     * @return The session corresponding to the specified socket. If the socket was null, determines
     *         the session from the execution context.
     * @throws IllegalStateException If no session corresponds to the specified socket.
     */
    private static Session resolveSession(WebSocketSession socket) {
        Session session = socket == null ? ExecutionContext.getSession()
                : (Session) socket.getAttributes().get(Session.ATTR_SESSION);
        Assert.state(session != null, () -> "Request received on unknown socket");
        return session;
    }

    /**
     * Processes a client request sent via the web socket session. Extracts the client request from
     * the message, creates a new execution context, and invokes registered request handlers. If no
     * registered request handler is capable of processing the request, an exception will be sent to
     * the client.
     *
     * @param socket The web socket session transmitting the request.
     * @param message The message containing the client request.
     */
    @Override
    protected void handleTextMessage(WebSocketSession socket, TextMessage message) {
        Session session = resolveSession(socket);
        Map<String, Object> attribs = socket.getAttributes();

        try {
            StringBuilder buffer = (StringBuilder) attribs.get(ATTR_BUFFER);
            String payload = message.getPayload();

            if (!message.isLast()) {
                if (buffer == null) {
                    attribs.put(ATTR_BUFFER, buffer = new StringBuilder(payload));
                } else {
                    buffer.append(payload);
                }

                return;
            }

            if (buffer != null) {
                payload = buffer.append(payload).toString();
                int len = payload.length();
                buffer = null;
                attribs.remove(ATTR_BUFFER);
                log.warn(() -> "Large payload received from client (" + len + " bytes).");
            }

            Map<String, Object> map = reader.readValue(payload);
            processRequest(session, map);

        } catch (Exception e) {
            attribs.remove(ATTR_BUFFER);
            log.error("Error processing client request.", e);
            sendError(socket, e);
        }
    }

    /**
     * Processes a client request containing a BLOB payload sent via the web socket session.
     * Extracts the client request from the message, creates a new execution context, and invokes
     * registered request handlers. If no registered request handler is capable of processing the
     * request, an exception will be sent to the client.
     *
     * @param socket The web socket session transmitting the request.
     * @param message The message containing the client request.
     */
    @Override
    protected void handleBinaryMessage(WebSocketSession socket, BinaryMessage message) throws Exception {
        Session session = resolveSession(socket);
        Map<String, Object> attribs = socket.getAttributes();

        try {
            byte[] buffer = (byte[]) attribs.get(ATTR_BUFFER);
            byte[] payload = new byte[message.getPayloadLength()];
            message.getPayload().get(payload);
            buffer = buffer == null ? payload : ArrayUtils.addAll(buffer, payload);

            if (!message.isLast()) {
                attribs.put(ATTR_BUFFER, buffer);
                return;
            }

            if (attribs.remove(ATTR_BUFFER) != null) {
                int len = buffer.length;
                log.warn(() -> "Large payload received from client (" + len + " bytes).");
            }

            ByteArrayInputStream is = new ByteArrayInputStream(buffer);
            byte[] preamble = new byte[100];
            int i = 0;

            while (true) {
                int b = is.read();

                if (b == 10 || b == -1) {
                    break;
                }

                if (i >= preamble.length) {
                    preamble = Arrays.copyOf(preamble, i + 100);
                }

                preamble[i++] = (byte) b;
            }

            Map<String, Object> map = reader.readValue(preamble, 0, i);
            @SuppressWarnings("unchecked")
            Map<String, Object> data = (Map<String, Object>) map.get("data");
            data.put("blob", is);
            processRequest(session, map);

        } catch (Exception e) {
            attribs.remove(ATTR_BUFFER);
            log.error("Error processing client request.", e);
            sendError(socket, e);
        }
    }

    private void processRequest(Session session, Map<String, Object> map) throws Exception {
        session._init((String) map.get("pid"));
        session.updateLastActivity();
        ClientRequest request = new ClientRequest(session, map);
        IRequestHandler handler = handlers.get(request.getType());

        if (handler == null) {
            throw new IllegalArgumentException("No registered handler for request type: " + request.getType());
        }

        ExecutionContext.clear();
        ExecutionContext.put(ExecutionContext.ATTR_REQUEST, request);
        ExecutionContext.put(ExecutionContext.ATTR_PROCESSING, true);
        
        try {
            handler.handleRequest(request);
            session.notifySessionListeners(request);
            request.getPage().getEventQueue().processAll();
        } catch (Exception e) {
            request.getPage().getEventQueue().clearAll();
            log.error("Unexpected exception", e);
            sendError(session.getSocket(), e);
        } finally {
            ExecutionContext.clear();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession socket) throws Exception {
        sessions.createSession(socket);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession socket, CloseStatus status) throws Exception {
        sessions.destroySession(socket, status);
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }

    /**
     * NOP
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * Detects and registers request handlers.
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof IRequestHandler) {
            registerRequestHandler((IRequestHandler) bean);
        }

        return bean;
    }

}
