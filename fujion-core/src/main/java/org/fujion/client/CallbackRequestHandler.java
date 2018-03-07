/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
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
 * Handler for dispatching client callbacks.
 */
public class CallbackRequestHandler implements IRequestHandler {

    @Override
    public void handleRequest(ClientRequest request) {
        Integer handle = request.getParam("handle", Integer.class);
        Object response = request.getParam("response", Object.class);

        if (handle != null) {
            CallbackRegistry.invokeCallback(handle, response);
        }
    }

    /**
     * Returns a request type of "callback".
     */
    @Override
    public String getRequestType() {
        return "callback";
    }

}
