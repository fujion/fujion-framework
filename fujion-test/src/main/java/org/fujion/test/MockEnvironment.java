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
package org.fujion.test;

import java.util.HashMap;
import java.util.Map;

import org.fujion.client.ExecutionContext;
import org.fujion.component.Page;
import org.fujion.event.EventQueue;
import org.fujion.spring.ClasspathMessageSource;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * This class creates a mock Fujion environment suitable for certain kinds of unit tests. It creates a
 * web app instance with a single page, a mock session and execution context and a single
 * application context.
 */
public class MockEnvironment {
    
    private MockSession session;
    
    private MockClientRequest clientRequest;
    
    private MockServletContext servletContext;
    
    private XmlWebApplicationContext rootContext;
    
    private XmlWebApplicationContext childContext;
    
    private final Map<String, Object> browserInfo = new HashMap<>();
    
    private final Map<String, Object> clientRequestMap = new HashMap<>();
    
    /**
     * Creates a mock environment for unit testing.
     */
    public MockEnvironment() {
    }
    
    /**
     * Initializes the mock environment.
     *
     * @param rootConfig Root container configuration.
     * @param childConfig Child container configuration.
     * @throws Exception Unspecified exception.
     */
    public void init(MockConfig rootConfig, MockConfig childConfig) throws Exception {
        // Set up web app
        servletContext = initServletContext(new MockServletContext());
        // Create root Spring context
        rootContext = initAppContext(rootConfig, null);
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, rootContext);
        rootContext.refresh();
        // Create mock session
        MockWebSocketSession socket = new MockWebSocketSession();
        session = new MockSession(servletContext, socket);
        // Create the mock request
        initBrowserInfoMap(browserInfo);
        clientRequestMap.put("data", browserInfo);
        initClientRequestMap(clientRequestMap);
        clientRequest = new MockClientRequest(session, clientRequestMap);
        // Create the mock execution
        initExecutionContext();
        // Initialize the page
        Page page = session.getPage();
        Page._init(page, clientRequest, session.getSynchronizer());
        page = initPage(page);
        // Create child Spring context
        if (childConfig != null) {
            childContext = initAppContext(childConfig, rootContext);
            childContext.refresh();
        }
    }
    
    /**
     * Cleans up all application contexts and invalidates the session.
     */
    public void close() {
        session.destroy();
        rootContext.close();
    }
    
    protected XmlWebApplicationContext createApplicationContext() {
        return new XmlWebApplicationContext();
    }
    
    /**
     * Initialize the mock servlet context.
     *
     * @param servletContext The mock servlet context.
     * @return The initialized mock servlet context.
     */
    protected MockServletContext initServletContext(MockServletContext servletContext) {
        return servletContext;
    }
    
    protected void initExecutionContext() {
        ExecutionContext.put(ExecutionContext.ATTR_REQUEST, clientRequest);
    }
    
    protected void initClientRequestMap(Map<String, Object> map) {
        map.put("pid", session.getPage().getId());
        map.put("type", "mock");
    }
    
    /**
     * Initialize the app context.
     *
     * @param config Configuration data.
     * @param parent If creating a child context, this will be its parent. Otherwise, will be null.
     * @return The initialized app context.
     */
    protected XmlWebApplicationContext initAppContext(MockConfig config, ApplicationContext parent) {
        XmlWebApplicationContext appContext = createApplicationContext();
        appContext.setServletContext(servletContext);

        if (parent == null) {
            ClasspathMessageSource.getInstance().setResourceLoader(appContext);
        } else {
            appContext.setParent(parent);
        }
        
        if (config.configLocations != null) {
            appContext.setConfigLocations(config.configLocations);
        }
        
        if (config.profiles != null && config.profiles.length > 0) {
            ConfigurableEnvironment env = appContext.getEnvironment();
            env.setDefaultProfiles(parent == null ? config.profiles[0] : "dummy");
            env.setActiveProfiles(config.profiles);
        }
        
        return appContext;
    }
    
    /**
     * Initialize browserInfo map.
     *
     * @param browserInfo The browser info map.
     */
    protected void initBrowserInfoMap(Map<String, Object> browserInfo) {
        browserInfo.put("requestURL", "http://mock.org/mock.fsp");
    }
    
    /**
     * Initialize the page.
     *
     * @param page The page.
     * @return The initialized page.
     */
    protected Page initPage(Page page) {
        return page;
    }
    
    public ApplicationContext getRootContext() {
        return rootContext;
    }

    public ApplicationContext getChildContext() {
        return childContext;
    }
    
    public MockSession getSession() {
        return session;
    }
    
    /**
     * Flushes and processes any event on the event queue.
     *
     * @return True if events were flushed.
     */
    public boolean flushEvents() {
        EventQueue queue = session.getPage().getEventQueue();
        boolean flushed = !queue.isEmpty();
        queue.processAll();
        return flushed;
    }
    
}
