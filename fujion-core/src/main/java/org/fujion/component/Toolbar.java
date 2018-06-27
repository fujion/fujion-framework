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
package org.fujion.component;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A toolbar component.
 */
@Component(tag = "toolbar", widgetClass = "Toolbar", content = ContentHandling.AS_CHILD, parentTag = "*", childTag = @ChildTag("*"), description = "A toolbar component.")
public class Toolbar extends BaseUIComponent {
    
    /**
     * Alignment of children within the tool bar. Default is START.
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
     * Orientation of the tool bar. Default is HORIZONTAL.
     */
    public enum Orientation {
        /**
         * Tool bar is oriented horizontally.
         */
        HORIZONTAL,
        /**
         * Tool bar is oriented vertically.
         */
        VERTICAL
    }
    
    private Alignment alignment = Alignment.START;
    
    private Orientation orientation = Orientation.HORIZONTAL;
    
    /**
     * Returns the {@link Alignment alignment} of children within the tool bar.
     *
     * @return The {@link Alignment alignment} of children within the tool bar.
     */
    @PropertyGetter(value = "alignment", description = "The alignment of children within the tool bar.")
    public Alignment getAlignment() {
        return alignment;
    }
    
    /**
     * Sets the {@link Alignment alignment} of children within the tool bar.
     *
     * @param alignment The {@link Alignment alignment} of children within the tool bar.
     */
    @PropertySetter(value = "alignment", defaultValue = "start", description = "The alignment of children within the tool bar.")
    public void setAlignment(Alignment alignment) {
        propertyChange("alignment", this.alignment, this.alignment = defaultify(alignment, Alignment.START), true);
    }
    
    /**
     * Returns the {@link Orientation orientation} of the tool bar.
     *
     * @return The {@link Orientation orientation} of the tool bar.
     */
    @PropertyGetter(value = "orientation", description = "The orientation of the tool bar.")
    public Orientation getOrientation() {
        return orientation;
    }
    
    /**
     * Sets the {@link Orientation orientation} of the tool bar.
     *
     * @param orientation The {@link Orientation orientation} of the tool bar.
     */
    @PropertySetter(value = "orientation", defaultValue = "horizontal", description = "The orientation of the tool bar.")
    public void setOrientation(Orientation orientation) {
        propertyChange("orientation", this.orientation, this.orientation = defaultify(orientation, Orientation.HORIZONTAL),
            true);
    }
}
