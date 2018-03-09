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
package org.fujion.servlet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.fujion.component.Page;
import org.fujion.page.PageRegistry;
import org.fujion.websocket.ISessionLifecycle;
import org.fujion.websocket.Session;
import org.fujion.websocket.Sessions;
import org.springframework.core.io.Resource;

/**
 * Allows registration of resources that may be retrieved via the url path '.../dynamic/**'. Paths
 * of the pattern '.../dynamic/_fujion_NNN/**' are assumed to be session-specific, with NNN
 * representing the page id of the session. Session-specific resources are removed when the
 * associated session is destroyed.
 */
public class DynamicResourceRegistry implements ISessionLifecycle {
    
    private static class ResourceRegistry extends ConcurrentHashMap<String, Resource> {

        private static final long serialVersionUID = 1L;
    }
    
    private static DynamicResourceRegistry instance = new DynamicResourceRegistry();

    private final Map<String, ResourceRegistry> registryMap = new ConcurrentHashMap<>();

    private final Pattern sessionPattern = Pattern.compile("^_fujion_\\d+\\/.*$");

    /**
     * Returns the singleton instance.
     *
     * @return Singleton instance of the dynamic resource registry.
     */
    public static DynamicResourceRegistry getInstance() {
        return instance;
    }

    /**
     * Force creation of global registry and subscribe to session lifecycle events.
     */
    private DynamicResourceRegistry() {
        getRegistry("", true);
        Sessions.getInstance().addLifecycleListener(this);
    }

    /**
     * Retrieves the resource registered to the specified path.
     *
     * @param path The resource path.
     * @return The named resource, or null if not found.
     */
    public Resource getResource(String path) {
        ResourceRegistry registry = getRegistry(path, false);
        String name = registry == null ? null : extractFromPath(path, false);
        return registry == null || name == null ? null : registry.get(name);
    }

    /**
     * Registers or unregisters a resource.
     *
     * @param path The resource path.
     * @param resource The resource to register or, if null, any resource registered under this name
     *            will be unregistered.
     */
    public void registerResource(String path, Resource resource) {
        ResourceRegistry registry = getRegistry(path, resource != null);
        String name = extractFromPath(path, false);
        resource = resource == null || name == null ? null
                : resource.getFilename() == null ? new DynamicResource(name, resource) : resource;

        if (registry != null && name != null) {
            if (resource == null) {
                registry.remove(name);
            } else {
                registry.put(name, resource);
            }
        }
    }
    
    /**
     * Extracts either the page id or the resource name from a path.
     *
     * @param path The resource path.
     * @param pageId If true, extract the page id. Otherwise, the resource name. If the page id is
     *            requested and none is present in the path, will attempt to infer the page id from
     *            the execution context.
     * @return The requested fragment, or null if not present.
     */
    private String extractFromPath(String path, boolean pageId) {
        String part;

        if (sessionPattern.matcher(path).matches()) {
            int i = path.indexOf("/");
            part = pageId ? path.substring(0, i) : StringUtils.substring(path, i + 1);
        } else {
            part = pageId ? null : path;
        }

        return StringUtils.trimToNull(part);
    }

    /**
     * Returns the resource registry as determined from the resource path.
     *
     * @param path The resource path. Paths prefixed with "_fujion_NNN/" designate a session-based
     *            resource where NNN is the session's page id.
     * @param autoCreate If true and no registry currently exists, one will be created.
     * @return A resource registry, or null if none exists.
     */
    private ResourceRegistry getRegistry(String path, boolean autoCreate) {
        synchronized (registryMap) {
            String key = extractFromPath(path, true);
            
            if (key != null) {
                autoCreate &= PageRegistry.hasPage(key);
            } else {
                key = "";
            }

            ResourceRegistry registry = registryMap.get(key);

            if (registry == null && autoCreate) {
                registry = new ResourceRegistry();
                registryMap.put(key, registry);
            }

            return registry;
        }
    }

    @Override
    public void onSessionCreate(Session session) {
    }

    /**
     * Remove the session's associated resource registry, if any.
     *
     * @see org.fujion.websocket.ISessionLifecycle#onSessionDestroy(org.fujion.websocket.Session)
     */
    @Override
    public void onSessionDestroy(Session session) {
        Page page = session.getPage();
        String pid = page == null ? "?" : page.getId();
        ResourceRegistry registry = registryMap.get(pid);
        
        if (registry != null) {
            registryMap.remove(pid);
            registry.clear();
        }
    }
}
