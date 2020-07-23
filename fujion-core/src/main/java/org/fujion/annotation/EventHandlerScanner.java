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
package org.fujion.annotation;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.fujion.ancillary.ComponentException;
import org.fujion.ancillary.ConvertUtil;
import org.fujion.common.CollectionUtil;
import org.fujion.common.MiscUtil;
import org.fujion.component.BaseComponent;
import org.fujion.event.Event;
import org.fujion.event.EventUtil;
import org.fujion.event.IEventListener;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Wires {@literal @EventHandler}-annotated methods.
 */
public class EventHandlerScanner {

    /**
     * Event listener implementation that invokes an {@literal @EventHandler}-annotated method on a
     * target object.
     */
    private static class EventListener implements IEventListener {

        private final Object target;

        private final Method method;

        EventListener(Object target, Method method) {
            this.target = target;
            this.method = method;
        }

        /**
         * Pass an event to the handler method on the target.
         */
        @Override
        public void onEvent(Event event) {
            try {
                if (method.getParameterTypes().length == 0) {
                    method.invoke(target);
                } else {
                    method.invoke(target, event);
                }
            } catch (Throwable e) {
                throw MiscUtil.toUnchecked(e);
            }
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            
            if (!(object instanceof EventListener)) {
                return false;
            }
            
            EventListener el = (EventListener) object;
            return target == el.target && method.equals(el.method);
        }

        @Override
        public int hashCode() {
            return target.hashCode() ^ method.hashCode();
        }
    }

    /**
     * Wires onEvent style event handlers.
     *
     * @param instance Controller to be wired.
     * @param component The component that triggers the event.
     * @param onHandlers The map of onEvent style handlers.
     */
    public static void wire(Object instance, BaseComponent component, Map<String, String> onHandlers) {
        for (Entry<String, String> entry : onHandlers.entrySet()) {
            String eventType = entry.getKey();
            String methodName = entry.getValue();
            Class<? extends Event> eventClass = EventUtil.getEventClass(eventType);
            Method handler = ReflectionUtils.findMethod(instance.getClass(), methodName, eventClass);
            handler = handler == null ? ReflectionUtils.findMethod(instance.getClass(), methodName) : handler;
            ComponentException.assertTrue(handler != null, "A suitable event handler named \"%s\" could not be found",
                    methodName);
            EventListener eventListener = new EventListener(instance, handler);
            component.addEventListener(eventType, eventListener);
        }
    }

    private static final String[] DEFAULT_MODE = { "" };

    /**
     * Scans the specified class for {@literal @EventHandler}-annotated methods.
     *
     * @param instance Controller to be wired.
     * @param root The root component used to resolve component names.
     * @param mode The wiring mode(s).
     */
    public static void wire(Object instance, BaseComponent root, String... mode) {
        Class<?> clazz = instance.getClass();
        String[] activeModes = mode == null || mode.length == 0 ? DEFAULT_MODE : mode;

        MethodScanner.scan(clazz, method -> {
            EventHandler[] annotations = method.getAnnotationsByType(EventHandler.class);

            for (EventHandler annotation : annotations) {
                if (!CollectionUtil.intersects(annotation.mode(), activeModes)) {
                    continue;
                }
                
                OnFailure onFailure = annotation.onFailure();
                
                if (!validateSignature(method)) {
                    onFailure.doAction("Signature for method \"%s\" does not conform to that required of an event handler",
                        method.getName());
                    break;
                }

                Set<String> targets = ConvertUtil.convertToSet(annotation.target(), true);
                Set<String> types = ConvertUtil.convertToSet(annotation.value(), true);
                BaseComponent component = null;

                if (types.isEmpty()) {
                    onFailure.doAction("At least one event type must be specified");
                }

                if (targets.isEmpty()) {
                    targets.add("self");
                }

                for (String target : targets) {
                    if ("self".equals(target)) {
                        component = isComponent(clazz) ? (BaseComponent) instance : root;
                    } else if (target.startsWith("@")) {
                        String[] fld = target.substring(1).split("\\.", 2);
                        Field field = findField(clazz, fld[0]);

                        if (field != null) {
                            try {
                                field.setAccessible(true);
                                Object value = field.get(instance);
                                
                                if (value != null && fld.length == 2) {
                                    value = PropertyUtils.getProperty(value, fld[1]);
                                }
                                
                                component = (BaseComponent) value;
                            } catch (Exception e) {
                                onFailure.doAction(e);
                            }
                        }
                    } else {
                        component = root == null ? null : root.findByName(target);
                    }

                    if (component == null) {
                        onFailure.doAction("No suitable event handler target found for \"%s\"", target);
                    } else {
                        for (String type : types) {
                            component.addEventListener(type, new EventListener(instance, method), annotation.syncToClient());
                        }
                    }
                }
            }
        });
    }
    
    /**
     * Validates if the method signature is suitable for an event handler.
     *
     * @param method The method to validate.
     * @return True if the method is suitable as an event handler.
     */
    private static boolean validateSignature(Method method) {
        Class<?>[] params = method.getParameterTypes();
        return params.length == 0 || (params.length == 1 && Event.class.isAssignableFrom(params[0]));
    }

    /**
     * Returns true if the class is a component class.
     *
     * @param clazz Class to be examined.
     * @return True if the class is a component class.
     */
    private static boolean isComponent(Class<?> clazz) {
        return BaseComponent.class.isAssignableFrom(clazz);
    }

    /**
     * Returns the field definition for the named field. Field must be a component type.
     *
     * @param clazz Class to be searched.
     * @param name Name of the field.
     * @return The corresponding field definition, or null if no matching field was found.
     */
    private static Field findField(Class<?> clazz, String name) {
        try {
            Field result = FieldUtils.getField(clazz, name, true);
            return isComponent(result.getType()) ? result : null;
        } catch (Exception e) {
            return null;
        }
    }

    private EventHandlerScanner() {
    }

}
