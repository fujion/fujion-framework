/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
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
 * A resize event.
 */
@EventType(ResizeEvent.TYPE)
public class ResizeEvent extends Event {

    /**
     * The event type.
     */
    public static final String TYPE = "resize";

    @EventParameter
    private double height;

    @EventParameter
    private double width;

    @EventParameter
    private double top;

    @EventParameter
    private double left;

    public ResizeEvent() {
        super(TYPE);
    }

    public ResizeEvent(BaseComponent target, Object data) {
        super(TYPE, target, data);
    }

    /**
     * Returns the new height in pixels.
     *
     * @return The new height in pixels.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the new width in pixels.
     *
     * @return The new width in pixels.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the new top relative position in pixels.
     *
     * @return The new top relative position in pixels.
     */
    public double getTop() {
        return top;
    }

    /**
     * Returns the new left relative position in pixels.
     *
     * @return The new left relative position in pixels.
     */
    public double getLeft() {
        return left;
    }

    /**
     * Returns the new bottom relative position in pixels.
     *
     * @return The new bottom relative position in pixels.
     */
    public double getBottom() {
        return top + height;
    }

    /**
     * Returns the new right relative position in pixels.
     *
     * @return The new right relative position in pixels.
     */
    public double getRight() {
        return left + width;
    }

}
