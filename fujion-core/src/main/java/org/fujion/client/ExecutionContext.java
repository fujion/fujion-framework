/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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

import jakarta.servlet.ServletContext;
import org.fujion.common.Assert;
import org.fujion.component.Page;
import org.fujion.page.PageRegistry;
import org.fujion.websocket.Session;
import org.springframework.context.ApplicationContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Static helper class for threads to determine the current execution context. An execution context
 * is created each time a thread services a client request. It contains information about the target
 * page and the session servicing the request, as well as the request itself.
 */
public class ExecutionContext {

    public static final String ATTR_REQUEST = "fujion_request";

    public static final String ATTR_PROCESSING = "fujion_processing";

    private static final ThreadLocal<Map<String, Object>> context = ThreadLocal.withInitial(HashMap::new);

    /**
     * Put a key/value pair in the thread-local map.
     *
     * @param key The key.
     * @param value The value.
     * @return The previous value.
     */
    public static Object put(String key, Object value) {
        return context.get().put(key, value);
    }

    /**
     * Returns the value associated with a key, or null if not found.
     *
     * @param key The key.
     * @return The associated value, or null if there is none.
     */
    public static Object get(String key) {
        return context.get().get(key);
    }

    /**
     * Removes a value given its key.
     *
     * @param key The key.
     * @return The value associated with the key, or null if none.
     */
    public static Object remove(String key) {
        return context.get().remove(key);
    }

    /**
     * Clears the thread-local map.
     */
    public static void clear() {
        context.get().clear();
    }

    /**
     * Destroys the thread-local map.
     */
    public static void destroy() {
        context.remove();
    }

    /**
     * Returns true if the thread-local map is empty.
     *
     * @return True if the thread-local map is empty.
     */
    public static boolean isEmpty() {
        return context.get().isEmpty();
    }

    /**
     * Returns true if execution is processing a web socket request.
     *
     * @return True if execution is processing a web socket request.
     */
    public static boolean isProcessing() {
        return context.get().containsKey(ATTR_PROCESSING);
    }

    /**
     * Returns the active client request.
     *
     * @return The active client request.
     */
    public static ClientRequest getRequest() {
        return (ClientRequest) context.get().get(ATTR_REQUEST);
    }

    /**
     * Returns the active client session.
     *
     * @return The active client session.
     */
    public static Session getSession() {
        ClientRequest request = getRequest();
        return request == null ? null : request.getSession();
    }

    /**
     * Returns the active client page.
     *
     * @return The active client page.
     */
    public static Page getPage() {
        ClientRequest request = getRequest();
        return request == null ? null : request.getPage();
    }

    /**
     * Returns the servlet context.
     *
     * @return The servlet context.
     */
    public static ServletContext getServletContext() {
        Session session = getSession();
        return session == null ? null : session.getServletContext();
    }

    /**
     * Invoke a callback in the execution context of the specified page.
     *
     * @param pid The id of the page.
     * @param callback The callback to invoke.
     */
    public static void invoke(String pid, Runnable callback) {
        Page page = PageRegistry.getPage(pid);
        Page current = getPage();
        Assert.isTrue(current == null || current == page, () -> "Cannot switch current page execution context");

        try {
            if (current == null) {
                clear();
                put(ExecutionContext.ATTR_REQUEST, new ClientRequest(page.getSession(), Collections.emptyMap()));
            }
            
            callback.run();
        } finally {
            if (current == null) {
                destroy();
            }
        }
    }

    /**
     * Returns the application context associated with the session.
     *
     * @return The application context associated with the session.
     */
    public static ApplicationContext getApplicationContext() {
        Session session = getSession();
        return session == null ? null : session.getApplicationContext();
    }
    
    private ExecutionContext() {
    }
}
