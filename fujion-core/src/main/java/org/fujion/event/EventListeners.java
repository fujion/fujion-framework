/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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

import java.util.HashMap;
import java.util.Map;

/**
 * Manages event listeners for a single component.
 */
public class EventListeners {
    
    private final Map<String, EventListenerSet> allListeners = new HashMap<>();
    
    /**
     * Add a listener for the given event type.
     *
     * @param eventType The event type.
     * @param eventListener The event listener.
     */
    public void add(String eventType, IEventListener eventListener) {
        getListeners(eventType, true).add(eventListener);
    }
    
    /**
     * Remove a listener for the given event type.
     *
     * @param eventType The event type.
     * @param eventListener The event listener.
     */
    public void remove(String eventType, IEventListener eventListener) {
        EventListenerSet listeners = getListeners(eventType, false);
        
        if (listeners != null) {
            listeners.remove(eventListener);
            
            if (listeners.isEmpty()) {
                allListeners.remove(eventType);
            }
        }
    }
    
    /**
     * Remove all listeners for all event types.
     */
    public void removeAll() {
        allListeners.clear();
    }
    
    /**
     * Remove all listeners for the specified event type only.
     *
     * @param eventType The event type.
     */
    public void removeAll(String eventType) {
        allListeners.remove(eventType);
    }
    
    /**
     * Invoke all listeners for this type of event.
     *
     * @param event An event.
     */
    public void invoke(Event event) {
        EventListenerSet listeners = getListeners(event.getType(), false);
        
        if (listeners != null) {
            listeners.onEvent(event);
        }
    }
    
    /**
     * Returns true if this event type has any listeners.
     *
     * @param eventType The event type.
     * @return True if there is at least one listener for the event type.
     */
    public boolean hasListeners(String eventType) {
        EventListenerSet listeners = getListeners(eventType, false);
        return listeners != null && !listeners.isEmpty();
    }
    
    private EventListenerSet getListeners(String eventType, boolean forceCreate) {
        EventListenerSet listeners = allListeners.get(eventType);
        
        if (listeners == null && forceCreate) {
            allListeners.put(eventType, listeners = new EventListenerSet());
        }
        
        return listeners;
    }
    
}
