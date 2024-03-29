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
package org.fujion.websocket;

import org.fujion.client.ClientInvocation;
import org.fujion.client.ClientRequest;

/**
 * Allows the implementer to receive notifications about session activity.
 */
public interface ISessionListener {
    
    /**
     * Called when a client request is received.
     *
     * @param request A client request.
     */
    default void onClientRequest(ClientRequest request) {
    }

    /**
     * Called when a client invocation is sent.
     *
     * @param invocation A client invocation.
     */
    default void onClientInvocation(ClientInvocation invocation) {
    }

    /**
     * Called when the session is destroyed.
     */
    default void onDestroy() {
    }

}
