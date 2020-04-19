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

import org.fujion.websocket.IRequestHandler;

/**
 * Handler for servicing ping requests from the client. These requests have no associated execution
 * logic and serve only to verify client-server connectivity and reset inactivity timeouts.
 */
public class PingRequestHandler implements IRequestHandler {
    
    @Override
    public void handleRequest(ClientRequest request) {
    }
    
    /**
     * Returns a request type of "ping".
     */
    @Override
    public String getRequestType() {
        return "ping";
    }
    
}
