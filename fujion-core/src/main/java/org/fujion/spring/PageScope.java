/*
 * #%L
 * carewebframework
 * %%
 * Copyright (C) 2008 - 2016 Regenstrief Institute, Inc.
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
 * This Source Code Form is also subject to the terms of the Health-Related
 * Additional Disclaimer of Warranty and Limitation of Liability available at
 *
 *      http://www.carewebframework.org/licensing/disclaimer.
 *
 * #L%
 */
package org.fujion.spring;

import org.fujion.client.ExecutionContext;
import org.fujion.websocket.ISessionLifecycle;
import org.fujion.websocket.Session;
import org.fujion.websocket.Sessions;

/**
 * Implements a custom Spring scope bound to Fujion's execution context.
 */
public class PageScope extends AbstractScope {

    private static final String SCOPE_ATTR = PageScope.class.getName();
    
    /**
     * Manages creating and destroying scope containers.
     */
    private final ISessionLifecycle sessionTracker = new ISessionLifecycle() {

        /**
         * Create a new scope container and bind it to the newly created session.
         *
         * @see org.fujion.websocket.ISessionLifecycle#onSessionCreate(org.fujion.websocket.Session)
         */
        @Override
        public void onSessionCreate(Session session) {
            ScopeContainer scopeContainer = new ScopeContainer(session.getId());
            session.getAttributes().put(SCOPE_ATTR, scopeContainer);
        }

        /**
         * Destroy the scope container bound to the session.
         *
         * @see org.fujion.websocket.ISessionLifecycle#onSessionDestroy(org.fujion.websocket.Session)
         */
        @Override
        public void onSessionDestroy(Session session) {
            ScopeContainer container = (ScopeContainer) session.getAttributes().remove(SCOPE_ATTR);

            if (container != null) {
                container.destroy();
            }
        }

    };

    public PageScope() {
        Sessions.getInstance().addLifecycleListener(sessionTracker);
    }

    /**
     * Return container for current execution context.
     */
    @Override
    public ScopeContainer getContainer() {
        Session session = ExecutionContext.getSession();
        
        if (session == null) {
            throw new IllegalStateException("Cannot access Page scope outside of an execution context");
        }
        
        return (ScopeContainer) session.getAttributes().get(SCOPE_ATTR);
    }

}
