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
package org.fujion.event;

/**
 * Typed callback interface for an event listener.
 *
 * @param <T> The event type.
 */
public interface ITypedEventListener<T extends Event> extends IEventListener {

    /**
     * Callback for an event.
     *
     * @param event The event.
     */
    void onTypedEvent(T event);

    @Override
    @SuppressWarnings("unchecked")
    default void onEvent(Event event) {
        event = EventUtil.getOriginalEvent(event);
        onTypedEvent((T) event);
    }
}
