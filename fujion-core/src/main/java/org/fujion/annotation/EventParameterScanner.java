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
package org.fujion.annotation;

import java.lang.reflect.Field;

import org.fujion.annotation.EventType.EventParameter;
import org.fujion.client.ClientRequest;
import org.fujion.common.MiscUtil;
import org.fujion.event.Event;

/**
 * Scans an object's class and superclasses for fields marked for wiring. Only fields that extend
 * BaseComponent are eligible for wiring.
 */
public class EventParameterScanner {

    private EventParameterScanner() {
    }

    /**
     * Wire an event object with parameters in a client request.
     * 
     * @param instance The event object to be wired..
     * @param request The client request from which parameter values will be derived.
     */
    public static void wire(Event instance, ClientRequest request) {
        Class<?> clazz = instance.getClass();

        while (clazz != Object.class) {
            wire(instance, request, clazz);
            clazz = clazz.getSuperclass();
        }
    }

    private static void wire(Event instance, ClientRequest request, Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                EventParameter annot = field.getAnnotation(EventParameter.class);

                if (annot == null) {
                    continue;
                }

                String name = annot.value();
                OnFailure onFailure = annot.onFailure();

                name = name.isEmpty() ? field.getName() : name;
                Object value = request.getParam(name, field.getType());

                if (value == null) {
                    onFailure.doAction("Request contains no valid value for field  \"%s\"", name);
                } else {
                    field.set(instance, value);
                }
            } catch (Exception e) {
                throw MiscUtil.toUnchecked(e);
            }
        }
    }

}
