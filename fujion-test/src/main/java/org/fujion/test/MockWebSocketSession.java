/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
package org.fujion.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketExtension;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * A mock web socket connection.
 */
public class MockWebSocketSession implements WebSocketSession {

    private URI uri;

    private final Map<String, Object> attributes = new HashMap<>();

    private Principal principal;

    int messageSizeLimitText = 5000;

    int messageSizeLimitBinary = 5000;

    boolean open = true;

    public MockWebSocketSession() throws Exception {
        this("http://mock.domain.org");
    }

    public MockWebSocketSession(String uri) throws Exception {
        this.uri = new URI(uri);
        principal = () -> "mock-principal";
    }

    @Override
    public String getId() {
        return "mock-id";
    }

    @Override
    public URI getUri() {
        return uri;
    }

    @Override
    public HttpHeaders getHandshakeHeaders() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Principal getPrincipal() {
        return principal;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }

    @Override
    public String getAcceptedProtocol() {
        return null;
    }

    @Override
    public void setTextMessageSizeLimit(int messageSizeLimit) {
        messageSizeLimitText = messageSizeLimit;
    }

    @Override
    public int getTextMessageSizeLimit() {
        return messageSizeLimitText;
    }

    @Override
    public void setBinaryMessageSizeLimit(int messageSizeLimit) {
        messageSizeLimitBinary = messageSizeLimit;
    }

    @Override
    public int getBinaryMessageSizeLimit() {
        return messageSizeLimitBinary;
    }

    @Override
    public List<WebSocketExtension> getExtensions() {
        return null;
    }

    @Override
    public void sendMessage(WebSocketMessage<?> message) {
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public void close() {
        open = false;
    }

    @Override
    public void close(CloseStatus status) {
        open = false;
    }

}
