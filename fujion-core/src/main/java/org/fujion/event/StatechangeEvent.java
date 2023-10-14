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

import org.fujion.annotation.EventType;
import org.fujion.annotation.EventType.EventParameter;
import org.fujion.annotation.OnFailure;

/**
 * A state change event. This event is sent by the client when a monitored property has been
 * changed.
 */
@EventType(StatechangeEvent.TYPE)
public class StatechangeEvent extends Event {
    
    /**
     * The event type.
     */
    public static final String TYPE = "statechange";
    
    @EventParameter
    private String state;
    
    @EventParameter(onFailure = OnFailure.IGNORE)
    private Object value;
    
    public StatechangeEvent() {
        super(TYPE);
    }
    
    /**
     * Returns the name of the client state that changed.
     *
     * @return The name of the client state that changed.
     */
    public String getState() {
        return state;
    }
    
    /**
     * Returns the new value of the state.
     *
     * @return The new value of the state.
     */
    public Object getValue() {
        return value;
    }
}
