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
package org.fujion.annotation;

import org.fujion.common.RegistryMap;
import org.fujion.common.RegistryMap.DuplicateAction;
import org.fujion.event.Event;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Builds a map of event types to implementation classes by scanning class annotations.
 */
public class EventTypeScanner extends AbstractClassScanner<Event, EventType> {

    private static final EventTypeScanner instance = new EventTypeScanner();

    private final Map<String, Class<? extends Event>> typeToClass = new RegistryMap<>(DuplicateAction.ERROR);

    /**
     * Returns a singleton instance of the event type scanner.
     *
     * @return Singleton instance of the event type scanner.
     */
    public static EventTypeScanner getInstance() {
        return instance;
    }

    private EventTypeScanner() {
        super(Event.class, EventType.class);
    }

    /**
     * Creates mapping between event type and its implementation class.
     *
     * @param eventClass Class containing EventType annotations.
     */
    @Override
    protected void doScanClass(Class<Event> eventClass) {
        typeToClass.put(getEventType(eventClass), eventClass);
    }

    /**
     * Returns an implementation class given an event type. Will never return null; for any event
     * types that do not have an explicit implementation class this method will return a generic
     * Event class.
     *
     * @param eventType The event type whose implementation class is sought.
     * @return The implementation class.
     */
    public Class<? extends Event> getEventClass(String eventType) {
        Class<? extends Event> eventClass = typeToClass.get(eventType);
        return eventClass == null ? Event.class : eventClass;
    }

    /**
     * Given an event class, returns the event type implemented by that class.
     *
     * @param eventClass An event class.
     * @return The event type, or null if none is associated with the class.
     */
    public String getEventType(Class<? extends Event> eventClass) {
        EventType eventType = eventClass.getAnnotation(EventType.class);
        return eventType == null ? null : eventType.value();
    }
    
    /**
     * Returns the set of registered event types.
     *
     * @return The set of registered event types.
     */
    public Set<String> getEventTypes() {
        return Collections.unmodifiableSet(typeToClass.keySet());
    }
}
