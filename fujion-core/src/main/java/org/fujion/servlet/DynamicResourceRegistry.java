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
package org.fujion.servlet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.fujion.client.ExecutionContext;
import org.fujion.websocket.ISessionLifecycle;
import org.fujion.websocket.Session;
import org.fujion.websocket.Sessions;
import org.springframework.core.io.Resource;

/**
 * Allows registration of resources that may be retrieved via url path '.../dynamic/**'.
 */
public class DynamicResourceRegistry implements ISessionLifecycle {
    
    private static class ResourceRegistry extends ConcurrentHashMap<String, Resource> {

        private static final long serialVersionUID = 1L;
    }
    
    private static DynamicResourceRegistry instance = new DynamicResourceRegistry();

    private final Map<String, ResourceRegistry> registryMap = new ConcurrentHashMap<>();

    public static DynamicResourceRegistry getInstance() {
        return instance;
    }

    private DynamicResourceRegistry() {
        getRegistry(true, true);
        Sessions.getInstance().addLifecycleListener(this);
    }

    public void registerGlobal(String name, Resource resource) {
        doRegister(name, resource, true);
    }
    
    public void registerLocal(String name, Resource resource) {
        doRegister(name, resource, false);
    }

    public Resource getResource(String name, boolean global) {
        ResourceRegistry registry = getRegistry(global, false);
        return registry == null ? null : registry.get(name);
    }

    private void doRegister(String name, Resource resource, boolean global) {
        ResourceRegistry registry = getRegistry(global, resource != null);
        resource = resource == null ? null : resource.getFilename() == null ? new DynamicResource(name, resource) : resource;

        if (registry != null) {
            if (resource == null) {
                registry.remove(name);
            } else {
                registry.put(name, resource);
            }
        }
    }
    
    private ResourceRegistry getRegistry(boolean global, boolean autoCreate) {
        synchronized (registryMap) {
            String key = global ? "" : ExecutionContext.isEmpty() ? null : ExecutionContext.getSession().getId();
            ResourceRegistry registry = key == null ? null : registryMap.get(key);

            if (registry == null && key != null && autoCreate) {
                registry = new ResourceRegistry();
                registryMap.put(key, registry);
            }

            return registry;
        }
    }

    @Override
    public void onSessionCreate(Session session) {
    }

    @Override
    public void onSessionDestroy(Session session) {
        ResourceRegistry registry = registryMap.get(session.getId());
        
        if (registry != null) {
            registryMap.remove(session.getId());
            registry.clear();
        }
    }
}
