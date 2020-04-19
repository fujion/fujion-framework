/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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

/**
 * Allows the implementer to receive notifications of session lifecycle events.
 */
public interface ISessionLifecycle {

    /**
     * Invoked after a session is created and its page has been initialized but not yet
     * materialized.
     *
     * @param session The session.
     */
    void onSessionCreate(Session session);

    /**
     * Invoked after a session is destroyed (e.g., after its associated web socket has been closed).
     *
     * @param session The session.
     */
    void onSessionDestroy(Session session);
}
