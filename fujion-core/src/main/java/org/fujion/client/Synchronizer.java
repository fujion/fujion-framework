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
package org.fujion.client;

import org.fujion.ancillary.IElementIdentifier;
import org.fujion.ancillary.IResponseCallback;
import org.fujion.ancillary.OptionMap;
import org.fujion.component.BaseComponent;
import org.fujion.websocket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;

/**
 * Synchronizes state changes from the server to the client.
 */
public class Synchronizer {
    
    private final ClientInvocationQueue queue;
    
    private final WebSocketSession session;
    
    private boolean queueing;
    
    /**
     * Create a synchronizer for the specific web socket session.
     *
     * @param session The web socket session.
     */
    public Synchronizer(WebSocketSession session) {
        this.session = session;
        queue = new ClientInvocationQueue();
    }
    
    /**
     * Activate queueing. All client invocations will be queued until queueing is deactivated.
     */
    public void startQueueing() {
        queueing = true;
    }
    
    /**
     * Deactivate queueing. Any queued invocations will be immediately sent to the client.
     */
    public void stopQueueing() {
        queueing = false;
        sendToClient(queue.flush());
    }
    
    /**
     * Clear the client invocation queue.
     */
    public void clear() {
        queue.clear();
    }
    
    /**
     * Creates and sends a client invocation to create a widget.
     *
     * @param parent Parent of the new widget.
     * @param props Property values used to initialize the new widget.
     * @param state State values used to initialize the new widget.
     * @return This synchronizer instance (for chaining).
     */
    public Synchronizer createWidget(BaseComponent parent, OptionMap props, OptionMap state) {
        return sendToClient(new ClientInvocation("fujion.widget.create", null, parent, props, state));
    }
    
    /**
     * Process queued client invocations.
     *
     * @param queue A client invocation queue.
     * @return This synchronizer instance (for chaining).
     */
    public Synchronizer processQueue(ClientInvocationQueue queue) {
        return sendToClient(queue.flush());
    }
    
    /**
     * Creates and sends a client invocation to invoke a specified function on a component.
     *
     * @param component Identifier of component whose function is to be invoked.
     * @param function Name of the function to be invoked.
     * @param args Arguments to pass to the functions.
     * @return This synchronizer instance (for chaining).
     */
    public Synchronizer invokeClient(IElementIdentifier component, String function, Object... args) {
        ClientInvocation invocation = new ClientInvocation(component, function, null, args);
        return sendToClient(invocation);
    }
    
    /**
     * Creates and sends a client invocation to invoke a specified function on a component.
     *
     * @param component Identifier of component whose function is to be invoked.
     * @param function Name of the function to be invoked.
     * @param callback Optional callback.
     * @param args Arguments to pass to the functions.
     * @return This synchronizer instance (for chaining).
     */
    public Synchronizer invokeClient(IElementIdentifier component, String function, IResponseCallback<?> callback,
                                     Object... args) {
        ClientInvocation invocation = new ClientInvocation(component, function, callback, args);
        return sendToClient(invocation);
    }
    
    /**
     * Sends (or queues) a client invocation.
     *
     * @param invocation Client invocation.
     * @return This synchronizer instance (for chaining).
     */
    public Synchronizer sendToClient(ClientInvocation invocation) {
        if (queueing) {
            queue.queue(invocation);
        } else {
            WebSocketHandler.send(session, invocation);
        }
        
        return this;
    }
    
    /**
     * Sends (or queues) multiple client invocations.
     *
     * @param invocations List of client invocations.
     * @return This synchronizer instance (for chaining).
     */
    public Synchronizer sendToClient(Collection<ClientInvocation> invocations) {
        if (queueing) {
            queue.queue(invocations);
        } else {
            WebSocketHandler.send(session, invocations);
        }
        
        return this;
    }
}
