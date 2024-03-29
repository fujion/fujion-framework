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
package org.fujion.spring;

import org.fujion.common.Logger;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.lang.ref.Cleaner;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Abstract base class for implementing custom scopes.
 */
public abstract class AbstractScope implements Scope {

    private static final Logger log = Logger.create(AbstractScope.class);

    private static final Cleaner cleaner = Cleaner.create();

    /**
     * IOC container for the custom scopes.
     */
    public static class ScopeContainer implements Scope {

        private static class CleanableState {

            private final Map<String, Object> beans;

            private final Map<String, Runnable> destructionCallbacks = new HashMap<>();

            private CleanableState(Map<String, Object> beans) {
                this.beans = beans == null ? new HashMap<>() : beans;
            }

            public void clean() {
                for (Entry<String, Runnable> entry : destructionCallbacks.entrySet()) {
                    try {
                        entry.getValue().run();
                    } catch (Throwable t) {
                        log.error(() -> "Error during destruction callback for bean " + entry.getKey(), t);
                    }
                }

                beans.clear();
                destructionCallbacks.clear();
            }
        }

        private final String conversationId;

        private final CleanableState cleanableState;

        public ScopeContainer(String conversationId) {
            this(conversationId, null);
        }

        public ScopeContainer(String conversationId, Map<String, Object> beans) {
            this.conversationId = conversationId;
            CleanableState cleanableState = new CleanableState(beans);
            this.cleanableState = cleanableState;
            cleaner.register(this, cleanableState::clean);
        }

        @Override
        public synchronized Object remove(String key) {
            cleanableState.destructionCallbacks.remove(key);
            return cleanableState.beans.remove(key);
        }

        @Override
        public synchronized Object get(String name, ObjectFactory<?> objectFactory) {
            Object bean = cleanableState.beans.get(name);

            if (bean == null) {
                registerBean(name, bean = objectFactory.getObject());
            }

            return bean;
        }

        /**
         * Register a bean in this scope.
         *
         * @param name The name of the bean.
         * @param bean The bean instance to register. If null, any existing registration is removed.
         * @return The previous bean instance associated with this name, or null if none.
         */
        public synchronized Object registerBean(String name, Object bean) {
            if (bean == null) {
                return cleanableState.beans.remove(name);
            } else {
                return cleanableState.beans.put(name, bean);
            }
        }

        /**
         * Register a bean destruction callback.
         *
         * @param name     Bean name.
         * @param callback Callback.
         */
        @Override
        public synchronized void registerDestructionCallback(String name, Runnable callback) {
            cleanableState.destructionCallbacks.put(name, callback);
        }

        /**
         * Calls all registered destruction callbacks and removes all bean references from the
         * container.
         */
        public void destroy() {
            cleanableState.clean();
        }

        @Override
        public Object resolveContextualObject(String key) {
            return null;
        }

        @Override
        public String getConversationId() {
            return conversationId;
        }
    }

    /**
     * Implement to retrieve the container for this scope.
     *
     * @return Container for this scope.
     */
    public abstract ScopeContainer getContainer();

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        return getContainer().get(name, objectFactory);
    }

    @Override
    public Object remove(String name) {
        return getContainer().remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        getContainer().registerDestructionCallback(name, callback);
    }

    @Override
    public Object resolveContextualObject(String key) {
        return getContainer().resolveContextualObject(key);
    }

    @Override
    public String getConversationId() {
        ScopeContainer container = getContainer();
        return container == null ? null : container.getConversationId();
    }

}
