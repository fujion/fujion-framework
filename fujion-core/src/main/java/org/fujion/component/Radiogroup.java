/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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
 * A component that logically groups together multiple radio buttons. Selection of radio buttons
 * under the same radio group is mutually exclusive. Note that radio buttons do not have to be a
 * direct child of their group.
 */
@Component(tag = "radiogroup", widgetClass = "Radiogroup", content = ContentHandling.AS_CHILD, parentTag = "*", childTag = @ChildTag("*"), description = "Logically groups together multiple radio buttons.")
public class Radiogroup extends BaseUIComponent {

    /**
     * Orientation of radio buttons within this group.
     */
    public enum Orientation {
        /**
         * Radio buttons are displayed in a row.
         */
        HORIZONTAL,
        /**
         * Radio buttons are display in a column.
         */
        VERTICAL
    }

    private Orientation orientation = Orientation.HORIZONTAL;

    /**
     * Returns the currently selected radio button in this group, if any.
     *
     * @return The currently selected radio button in this group, possibly null.
     */
    public Radiobutton getSelected() {
        return getSelected(this);
    }

    private Radiobutton getSelected(BaseComponent parent) {
        for (BaseComponent child : parent._getChildren()) {
            if (child instanceof Radiobutton && ((Radiobutton) child).isChecked()) {
                return (Radiobutton) child;
            }
        }

        for (BaseComponent child : parent._getChildren()) {
            Radiobutton rb = child instanceof Radiogroup ? null : getSelected(child);

            if (rb != null) {
                return rb;
            }
        }

        return null;
    }

    /**
     * Returns the {@link Orientation orientation} of radio buttons belonging to this group.
     *
     * @return The {@link Orientation orientation} of radio buttons belonging to this group.
     */
    @PropertyGetter(value = "orientation", description = "The orientation of radio buttons belonging to this group.")
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Sets the {@link Orientation orientation} of radio buttons belonging to this group.
     *
     * @param orientation The {@link Orientation orientation} of radio buttons belonging to this
     *            group.
     */
    @PropertySetter(value = "orientation", defaultValue = "horizontal", description = "The orientation of radio buttons belonging to this group.")
    public void setOrientation(Orientation orientation) {
        propertyChange("orientation", this.orientation, this.orientation = defaultify(orientation, Orientation.HORIZONTAL),
                true);
    }
}
