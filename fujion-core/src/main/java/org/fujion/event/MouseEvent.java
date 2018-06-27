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

import org.fujion.annotation.EventType.EventParameter;
import org.fujion.component.BaseComponent;

/**
 * Base class for mouse-related events.
 */
public abstract class MouseEvent extends Event {
    
    /**
     * Mouse button specifier.
     */
    public enum MouseButton {
        /**
         * The mouse button could not be determined.
         */
        UNSPECIFIED,
        /**
         * The left mouse button was depressed.
         */
        LEFT,
        /**
         * The middle mouse button was depressed.
         */
        MIDDLE,
        /**
         * The right mouse button was depressed.
         */
        RIGHT
    }
    
    @EventParameter
    private int pageX;
    
    @EventParameter
    private int pageY;
    
    @EventParameter
    private int which;
    
    protected MouseEvent(String type) {
        super(type);
    }
    
    protected MouseEvent(String type, BaseComponent target, Object data) {
        super(type, target, data);
    }
    
    /**
     * Returns the X (horizontal) coordinate in pixels of the event relative to the whole viewport.
     *
     * @return The X (horizontal) coordinate in pixels of the event relative to the whole viewport.
     */
    public int getPageX() {
        return pageX;
    }
    
    /**
     * Returns the Y (vertical) coordinate in pixels of the event relative to the whole viewport.
     *
     * @return The Y (vertical) coordinate in pixels of the event relative to the whole viewport.
     */
    public int getPageY() {
        return pageY;
    }
    
    /**
     * Returns which {@link MouseButton mouse button} was depressed at the time of the event.
     *
     * @return Which {@link MouseButton mouse button} was depressed at the time of the event.
     */
    public MouseButton getMouseButton() {
        return MouseButton.values()[which];
    }
    
}
