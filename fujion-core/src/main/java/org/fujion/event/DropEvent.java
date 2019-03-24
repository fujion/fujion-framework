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
package org.fujion.event;

import org.fujion.annotation.EventType;
import org.fujion.annotation.EventType.EventParameter;
import org.fujion.component.BaseComponent;

/**
 * A drop event.
 */
@EventType(DropEvent.TYPE)
public class DropEvent extends Event {

    /**
     * The event type.
     */
    public static final String TYPE = "drop";

    @EventParameter
    private BaseComponent draggable;

    public DropEvent() {
        super(TYPE);
    }

    public DropEvent(BaseComponent target, Object data) {
        super(TYPE, target, data);
    }

    public DropEvent(BaseComponent target, BaseComponent draggable, Object data) {
        this(target, data);
        this.draggable = draggable;
    }
    
    /**
     * Returns the component that was dragged.
     *
     * @return The component that was dragged.
     */
    public BaseComponent getDraggable() {
        return draggable;
    }
    
}
