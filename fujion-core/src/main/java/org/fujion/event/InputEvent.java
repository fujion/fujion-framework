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
import org.fujion.annotation.EventType.EventParameter;
import org.fujion.component.BaseComponent;

/**
 * An input event. This event is similar to the {@link ChangeEvent change} event. The difference is
 * that the input event occurs immediately after the value of an element has changed, while change
 * occurs when the element loses focus.
 */
@EventType(InputEvent.TYPE)
public class InputEvent extends Event {
    
    /**
     * The event type.
     */
    public static final String TYPE = "input";
    
    @EventParameter
    private String value;
    
    public InputEvent() {
        super(TYPE);
    }
    
    public InputEvent(BaseComponent target, Object data) {
        super(TYPE, target, data);
    }
    
    /**
     * Returns the value of the input data.
     *
     * @return Value of the input data.
     */
    public String getValue() {
        return value;
    }
}
