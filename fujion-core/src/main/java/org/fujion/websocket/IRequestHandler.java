/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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

import org.fujion.client.ClientRequest;

/**
 * Interface for handling a client request. A request handler must be registered for each expected
 * request type.
 */
public interface IRequestHandler {

    /**
     * Handle a client request.
     *
     * @param request The client request.
     */
    void handleRequest(ClientRequest request);

    /**
     * @return The type of request handled.
     */
    String getRequestType();
}
