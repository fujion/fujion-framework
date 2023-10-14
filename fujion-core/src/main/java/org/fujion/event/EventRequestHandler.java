/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2023 Fujion Framework
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
package org.fujion.event;

import org.fujion.client.ClientRequest;
import org.fujion.websocket.IRequestHandler;

/**
 * This is the handler for client requests that contain an event. It simply dispatches the event to
 * its target component.
 */
public class EventRequestHandler implements IRequestHandler {
    
    @Override
    public void handleRequest(ClientRequest request) {
        if (EventUtil.hasEvent(request)) {
            EventUtil.send(EventUtil.toEvent(request));
        }
        
    }
    
    @Override
    public String getRequestType() {
        return "event";
    }
    
}
