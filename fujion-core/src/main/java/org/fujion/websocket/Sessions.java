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

import org.fujion.common.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.ServletContext;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Keeps track of active sessions.
 */
public class Sessions implements BeanPostProcessor, ServletContextAware, ApplicationContextAware {
    
    private static final Logger log = Logger.create(Sessions.class);

    private static final Sessions instance = new Sessions();
    
    private ApplicationContext applicationContext;
    
    private ServletContext servletContext;
    
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();
    
    private final Set<ISessionLifecycle> lifecycleListeners = new HashSet<>();
    
    public static Sessions getInstance() {
        return instance;
    }
    
    private Sessions() {
    }

    /**
     * Returns an immutable list of all active sessions.
     *
     * @return List of all active sessions.
     */
    public Collection<Session> getActiveSessions() {
        return Collections.unmodifiableCollection(sessions.values());
    }
    
    /**
     * Registers a lifecycle listener.
     *
     * @param listener A lifecycle listener.
     */
    public void addLifecycleListener(ISessionLifecycle listener) {
        lifecycleListeners.add(listener);
    }
    
    /**
     * Unregisters a lifecycle listener.
     *
     * @param listener A lifecycle listener.
     */
    public void removeLifecycleListener(ISessionLifecycle listener) {
        lifecycleListeners.remove(listener);
    }
    
    /**
     * Notify lifecycle listeners of a lifecycle event.
     *
     * @param session Session triggering the event.
     * @param created If true, it is a create event; if false, a destroy event.
     */
    protected void notifyLifecycleListeners(Session session, boolean created) {
        if (!lifecycleListeners.isEmpty()) {
            for (ISessionLifecycle listener : new ArrayList<>(lifecycleListeners)) {
                try {
                    if (created) {
                        listener.onSessionCreate(session);
                    } else {
                        listener.onSessionDestroy(session);
                    }
                } catch (Exception e) {
                    if (created && e instanceof SessionInitException) {
                        throw e;
                    } else {
                        log.error("A session lifecycle listener threw an exception.", e);
                    }
                }
            }
        }
    }
    
    /**
     * Looks up a session by its unique id.
     *
     * @param id The session id.
     * @return The associated session, or null if none found.
     */
    public Session getSession(String id) {
        return sessions.get(id);
    }
    
    /**
     * Creates and registers a new session.
     *
     * @param socket The web socket session.
     * @return The newly created session.
     */
    protected Session createSession(WebSocketSession socket) {
        Session session = new Session(applicationContext, servletContext, socket);
        sessions.put(session.getId(), session);
        
        if (log.isDebugEnabled()) {
            logSessionEvent(session, "established");
        }

        return session;
    }
    
    /**
     * Destroys and unregisters the session associated with the specified web socket.
     *
     * @param socket The web socket session.
     * @param status The close status.
     */
    protected void destroySession(WebSocketSession socket, CloseStatus status) {
        Session session = sessions.remove(socket.getId());
        
        if (session != null) {
            if (log.isDebugEnabled()) {
                logSessionEvent(session, "closed, " + status);
            }
            
            notifyLifecycleListeners(session, false);
            session.destroy();
        }
    }
    
    /**
     * Logs a session event.
     *
     * @param session The session.
     * @param event The text describing the event.
     */
    private void logSessionEvent(Session session, String event) {
        log.debug(() -> "Session #" + session.getId() + " " + event + ".");
    }
    
    /**
     * NOP
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    
    /**
     * Detects and registers lifecycle listeners.
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ISessionLifecycle) {
            addLifecycleListener((ISessionLifecycle) bean);
        }
        
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    
}
