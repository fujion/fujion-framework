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
package org.fujion.component;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A component containing visually separate panes with optional splitter controls.
 */
@Component(tag = "paneview", widgetModule = "fujion-paneview", widgetClass = "Paneview", parentTag = "*", childTag = @ChildTag("pane"), description = "A container that visually separate panes with optional splitter controls.")
public class Paneview extends BaseUIComponent {

    /**
     * Orientation of panes within a pane view.
     */
    public enum Orientation {
        /**
         * Panes are oriented horizontally in a single row.
         */
        HORIZONTAL,
        /**
         * Panes are orientated vertically in a single column.
         */
        VERTICAL
    }

    private Orientation orientation = Orientation.HORIZONTAL;

    /**
     * Returns the {@link Orientation orientation} of child panes.
     *
     * @return The {@link Orientation orientation} of child panes.
     */
    @PropertyGetter(value = "orientation", description = "The orientation of child panes.")
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Sets the {@link Orientation orientation} of child panes.
     *
     * @param orientation The {@link Orientation orientation} of child panes.
     */
    @PropertySetter(value = "orientation", defaultValue = "horizontal", description = "The orientation of child panes.")
    public void setOrientation(Orientation orientation) {
        propertyChange("orientation", this.orientation, this.orientation = defaultify(orientation, Orientation.HORIZONTAL),
            true);
    }

}
