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
package org.fujion.event;

import org.fujion.annotation.EventType;
import org.fujion.component.BaseComponent;

/**
 * A property change event. This event is sent by a component when one of its property values has
 * changed.
 */
@EventType(PropertychangeEvent.TYPE)
public class PropertychangeEvent extends Event {

    /**
     * The event type.
     */
    public static final String TYPE = "propertychange";

    private final String propertyName;

    private final Object oldValue;

    private final Object newValue;
    
    public PropertychangeEvent(BaseComponent target, String propertyName, Object oldValue, Object newValue) {
        super(TYPE, target);
        this.propertyName = propertyName.startsWith("_") ? propertyName.substring(1) : propertyName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * Returns the name of the property that changed.
     *
     * @return The name of the property that changed.
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Returns the new value of the property.
     *
     * @return The new value of the property.
     */
    public Object getNewValue() {
        return newValue;
    }
    
    /**
     * Returns the old value of the property.
     *
     * @return The old value of the property.
     */
    public Object getOldValue() {
        return oldValue;
    }
}
