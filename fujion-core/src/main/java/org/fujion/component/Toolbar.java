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
package org.fujion.component;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A toolbar component.
 */
@Component(
        tag = "toolbar",
        widgetClass = "Toolbar",
        content = ContentHandling.AS_CHILD,
        parentTag = "*",
        childTag = @ChildTag("*"),
        description = "A toolbar component.")
public class Toolbar extends BaseUIComponent {
    
    /**
     * Alignment of children within the toolbar. Default is START.
     */
    public enum Alignment {
        /**
         * Children are aligned to the left (if horizontal) or top (if vertical).
         */
        START,
        /**
         * Children are centered.
         */
        CENTER,
        /**
         * Children are aligned to the right (if horizontal) or bottom (if vertical).
         */
        END
    }
    
    /**
     * Orientation of the toolbar. Default is HORIZONTAL.
     */
    public enum Orientation {
        /**
         * Toolbar is oriented horizontally.
         */
        HORIZONTAL,
        /**
         * Toolbar is oriented vertically.
         */
        VERTICAL
    }
    
    private Alignment alignment = Alignment.START;
    
    private Orientation orientation = Orientation.HORIZONTAL;
    
    /**
     * Returns the {@link Alignment alignment} of children within the toolbar.
     *
     * @return The {@link Alignment alignment} of children within the toolbar.
     */
    @PropertyGetter(value = "alignment", description = "The alignment of children within the toolbar.")
    public Alignment getAlignment() {
        return alignment;
    }
    
    /**
     * Sets the {@link Alignment alignment} of children within the toolbar.
     *
     * @param alignment The {@link Alignment alignment} of children within the toolbar.
     */
    @PropertySetter(value = "alignment", defaultValue = "start", description = "The alignment of children within the toolbar.")
    public void setAlignment(Alignment alignment) {
        propertyChange("alignment", this.alignment, this.alignment = defaultify(alignment, Alignment.START), true);
    }
    
    /**
     * Returns the {@link Orientation orientation} of the toolbar.
     *
     * @return The {@link Orientation orientation} of the toolbar.
     */
    @PropertyGetter(value = "orientation", description = "The orientation of the toolbar.")
    public Orientation getOrientation() {
        return orientation;
    }
    
    /**
     * Sets the {@link Orientation orientation} of the toolbar.
     *
     * @param orientation The {@link Orientation orientation} of the toolbar.
     */
    @PropertySetter(value = "orientation", defaultValue = "horizontal", description = "The orientation of the toolbar.")
    public void setOrientation(Orientation orientation) {
        propertyChange("orientation", this.orientation, this.orientation = defaultify(orientation, Orientation.HORIZONTAL),
            true);
    }
}
