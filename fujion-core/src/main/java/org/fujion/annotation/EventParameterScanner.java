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

import org.fujion.annotation.EventType.EventParameter;
import org.fujion.client.ClientRequest;
import org.fujion.common.MiscUtil;
import org.fujion.event.Event;

/**
 * Scans an event object's class and superclasses for fields annotated for wiring.
 */
public class EventParameterScanner extends AbstractFieldScanner<Event, EventParameter> {

    private static final EventParameterScanner instance = new EventParameterScanner();
    
    /**
     * Wire an event object with parameters in a client request.
     *
     * @param event The event object to be wired..
     * @param request The client request from which parameter values will be derived.
     */
    public static void wire(Event event, ClientRequest request) {
        instance.scan(event, (annotation, field) -> {
            try {
                String name = annotation.value();
                OnFailure onFailure = annotation.onFailure();

                name = name.isEmpty() ? field.getName() : name;
                Object value = request.getParam(name, field.getType());

                if (value == null) {
                    onFailure.doAction("Request contains no valid value for field  \"%s\"", name);
                } else {
                    field.set(event, value);
                }
            } catch (Exception e) {
                throw MiscUtil.toUnchecked(e);
            }
            return true;
        });
    }

    private EventParameterScanner() {
        super(Event.class, EventParameter.class);
    }

}
