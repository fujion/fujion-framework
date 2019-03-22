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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.ConstructorUtils;
import org.fujion.annotation.EventParameterScanner;
import org.fujion.annotation.EventTypeScanner;
import org.fujion.client.ClientRequest;
import org.fujion.client.ExecutionContext;
import org.fujion.common.MiscUtil;
import org.fujion.component.BaseComponent;
import org.fujion.component.Page;
import org.springframework.util.Assert;

/**
 * Static methods for manipulating events.
 */
public class EventUtil {

    private static final Class<?>[] CTOR_PARAM_TYPES = { BaseComponent.class, Object.class };

    /**
     * Sends an event to its designated target.
     *
     * @param event The event to send. If the event's designated target is null, the event is sent
     *            to the page in the current execution context.
     */
    public static void send(Event event) {
        BaseComponent target = event.getCurrentTarget();
        send(event, target);
    }

    /**
     * Sends an event to the specified target.
     *
     * @param event The event to send.
     * @param target The target for the event. If the target is null, the event is sent to the page
     *            in the current execution context.
     */
    public static void send(Event event, BaseComponent target) {
        if (target != null) {
            target.fireEvent(event);
        }
    }

    /**
     * Creates and sends an event to the specified target.
     *
     * @param eventName The event name.
     * @param target The event target.
     * @param data Optional event data.
     * @return The newly created event.
     */
    public static Event send(String eventName, BaseComponent target, Object data) {
        Event event = new Event(eventName, target, data);
        send(event);
        return event;
    }

    /**
     * Echo an event. This is similar to a post, but defers event delivery until the client responds
     * to a dummy invocation. It simulates an event roundtrip from server to client and back, though
     * the event itself never leaves the server.
     *
     * @param event The event to send. If the event's designated target is null, the event is sent
     *            to the page in the current execution context.
     */
    public static void echo(Event event) {
        event.getPage().invoke("id", (response) -> send(event));
    }

    /**
     * Queues an event for later processing.
     *
     * @param event The event to queue.
     */
    public static void post(Event event) {
        Page page = event.getPage();
        post(page != null ? page : ExecutionContext.getPage(), event);
    }

    /**
     * Queues an event for later processing.
     *
     * @param page Page owning the target queue.
     * @param event The event to queue.
     */
    public static void post(Page page, Event event) {
        page.getEventQueue().queue(event);
    }

    /**
     * Creates and posts an event for later delivery.
     *
     * @param eventName The event name.
     * @param target The event target.
     * @param data Optional event data.
     * @return The newly created event.
     */
    public static Event post(String eventName, BaseComponent target, Object data) {
        Event event = new Event(eventName, target, data);
        post(event);
        return event;
    }

    /**
     * Creates and posts an event for later delivery.
     *
     * @param page Page owning the target queue.
     * @param eventName The event name.
     * @param target The event target.
     * @param data Optional event data.
     * @return The newly created event.
     */
    public static Event post(Page page, String eventName, BaseComponent target, Object data) {
        Event event = new Event(eventName, target, data);
        post(page, event);
        return event;
    }

    /**
     * Returns the implementation class for the specified event type. If there is no implementation
     * specific to the event type, the Event class will be returned.
     *
     * @param eventType The event type (with or without the "on" prefix).
     * @return The implementation class for the event type (never null).
     */
    public static Class<? extends Event> getEventClass(String eventType) {
        return EventTypeScanner.getInstance().getEventClass(stripOn(eventType));
    }

    /**
     * Returns the event type given the implementation class.
     *
     * @param eventClass The event class.
     * @return The corresponding event type, or null if none.
     */
    public static String getEventType(Class<? extends Event> eventClass) {
        return EventTypeScanner.getInstance().getEventType(eventClass);
    }

    /**
     * Strips the "on" prefix from an event type, if one is present.
     *
     * @param eventType The event type.
     * @return The event type stripped of the "on" prefix.
     */
    public static String stripOn(String eventType) {
        return eventType.startsWith("on") ? StringUtils.uncapitalize(eventType.substring(2)) : eventType;
    }

    /**
     * Invokes an event handler.
     *
     * @param handlerName The name of the handler method.
     * @param instance The instance implementing the handler method.
     * @param event The event.
     * @return True if the event was handled.
     */
    public static boolean invokeHandler(String handlerName, Object instance, Event event) {
        Method method = getHandler(handlerName, instance, event);

        if (method != null) {
            try {
                if (method.getParameterCount() == 1) {
                    method.invoke(instance, event);
                } else {
                    method.invoke(instance);
                }

                return true;
            } catch (Exception e) {
                throw MiscUtil.toUnchecked(e);
            }
        }

        return false;
    }

    /**
     * Returns a suitable handler method for an event. First, it searches for a method with a single
     * argument that assignment-compatible with the event. Failing that, it searches for a method
     * with no arguments. Failing that, it returns null.
     *
     * @param handlerName The name of the handler method (defaults to a method with the same name as
     *            the event type).
     * @param instance The instance implementing the handler method.
     * @param event The event.
     * @return The handler method, or null if a suitable method could not be found.
     */
    public static Method getHandler(String handlerName, Object instance, Event event) {
        handlerName = handlerName != null ? handlerName : event.getType();
        Method method = MethodUtils.getMatchingAccessibleMethod(instance.getClass(), handlerName,
            new Class<?>[] { event.getClass() });
        return method != null ? method
                : MethodUtils.getAccessibleMethod(instance.getClass(), handlerName, ArrayUtils.EMPTY_CLASS_ARRAY);

    }

    /**
     * Returns true if the client request contains an event.
     *
     * @param request The client request.
     * @return True if the request contains an event.
     */
    public static boolean hasEvent(ClientRequest request) {
        return "event".equals(request.getType()) && request.getData() instanceof Map;
    }

    /**
     * Returns true if the client request contains an event of the specified type.
     *
     * @param request The client request.
     * @param eventType The event type.
     * @return True if the request contains an event of the specified type.
     */
    public static boolean hasEvent(ClientRequest request, String eventType) {
        return eventType.equals(getEventType(request));
    }

    /**
     * Extracts an event from the client request.
     *
     * @param request The client request.
     * @return The extracted event.
     * @exception IllegalArgumentException If the request does not contain an event.
     */
    public static Event toEvent(ClientRequest request) {
        String type = getEventType(request);
        
        if (type == null) {
            throw new IllegalArgumentException("Request does not contain an event");
        }
        
        return toEvent(getEventClass(type), request);
    }
    
    /**
     * Extracts the event type from the client request.
     *
     * @param request The client request.
     * @return The event type.
     */
    public static String getEventType(ClientRequest request) {
        return hasEvent(request) ? (String) request.getData(Map.class).get("type") : null;
    }

    /**
     * Creates an event from the specified event type. If an event class exists for the specified
     * type, will create an event of that class. Otherwise, will create a generic event.
     *
     * @param eventType The event type.
     * @return The newly created event.
     */
    public static Event toEvent(String eventType) {
        return toEvent(eventType, null, null);
    }

    /**
     * Creates an event from the specified event type. If an event class exists for the specified
     * type, will create an event of that class. Otherwise, will create a generic event.
     *
     * @param eventType The event type.
     * @param target The target of the event (may be null).
     * @param data Data to be associated with the event (may be null).
     * @return The newly created event.
     */
    public static Event toEvent(String eventType, BaseComponent target, Object data) {
        String type = stripOn(eventType);
        Class<? extends Event> eventClass = getEventClass(type);

        try {
            if (eventClass == Event.class) {
                return new Event(type, target, data);
            }

            if (target == null && data == null) {
                return eventClass.newInstance();
            }
            
            Constructor<?> ctor = ConstructorUtils.getMatchingAccessibleConstructor(eventClass, CTOR_PARAM_TYPES);
            Assert.notNull(ctor, () -> "Cannot find compatible constructor for event type " + type);
            return (Event) ctor.newInstance(target, data);
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * Extracts an event of the expected class from the client request.
     *
     * @param clazz The expected class.
     * @param request The client request.
     * @return An event of the expected class, or null if the class is not a subclass of Event, or
     *         if a suitable constructor could not be found.
     */
    private static Event toEvent(Class<?> clazz, ClientRequest request) {
        try {
            if (Event.class.isAssignableFrom(clazz)) {
                Event event = (Event) clazz.newInstance();
                EventParameterScanner.wire(event, request);
                return event;
            }
        } catch (InstantiationException | IllegalAccessException e) {
            return toEvent(clazz.getSuperclass(), request);
        }

        return null;
    }

    /**
     * Returns original event in a chain of forwarded events.
     *
     * @param event The event to inspect.
     * @return The original event in the forwarded event chain.
     */
    public static Event getOriginalEvent(Event event) {
        while (event instanceof ForwardedEvent) {
            event = ((ForwardedEvent) event).getOriginalEvent();
        }

        return event;
    }
    
    private EventUtil() {
    }

}
