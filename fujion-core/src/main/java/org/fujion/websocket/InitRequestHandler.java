/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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

import org.fujion.ancillary.OptionMap;
import org.fujion.client.ClientOptions;
import org.fujion.client.ClientRequest;
import org.fujion.client.Synchronizer;
import org.fujion.component.Page;
import org.fujion.page.PageDefinition;
import org.fujion.page.PageDefinitionCache;

/**
 * Handler for an initialization request. The client sends this request when the bootstrap page is
 * first loaded in order to complete the initialization of the page.
 */
public class InitRequestHandler implements IRequestHandler {

    private final OptionMap clientOptionMap = ClientOptions.getInstance().toMap();
    
    @Override
    public void handleRequest(ClientRequest request) {
        Page page = request.getPage();
        PageDefinition pageDefinition = PageDefinitionCache.getInstance().get(page.getSrc());
        Synchronizer synchronizer = request.getSession().getSynchronizer();
        synchronizer.startQueueing();
        Page._init(page, request, synchronizer);
        Sessions.getInstance().notifyLifecycleListeners(request.getSession(), true);

        try {
            page.invoke("beforeInitialize", clientOptionMap);
            pageDefinition.materialize(page);
            page.invoke("afterInitialize");
            page.fireEvent("afterInitialize");
        } catch (Exception e) {
            synchronizer.clear();
            throw new SessionInitException(e);
        } finally {
            synchronizer.stopQueueing();
        }
    }

    @Override
    public String getRequestType() {
        return "init";
    }

}
