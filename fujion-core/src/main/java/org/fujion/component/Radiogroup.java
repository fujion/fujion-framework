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
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A component that logically groups together multiple radio buttons. Selection of radio buttons
 * under the same radio group is mutually exclusive. Note that radio buttons do not have to be a
 * direct child of their group.
 */
@Component(tag = "radiogroup", widgetClass = "Radiogroup", content = ContentHandling.AS_CHILD, parentTag = "*", childTag = @ChildTag("*"))
public class Radiogroup extends BaseUIComponent {

    /**
     * Orientation of radio buttons within this group.
     */
    public enum Orientation {
        HORIZONTAL, VERTICAL
    }

    private Orientation orientation = Orientation.HORIZONTAL;

    public Radiobutton getSelected() {
        return getSelected(this);
    }

    private Radiobutton getSelected(BaseComponent parent) {
        for (BaseComponent child : parent.getChildren()) {
            if (child instanceof Radiobutton && ((Radiobutton) child).isChecked()) {
                return (Radiobutton) child;
            }
        }

        for (BaseComponent child : parent.getChildren()) {
            Radiobutton rb = child instanceof Radiogroup ? null : getSelected(child);

            if (rb != null) {
                return rb;
            }
        }

        return null;
    }

    @PropertyGetter("orientation")
    public Orientation getOrientation() {
        return orientation;
    }

    @PropertySetter("orientation")
    public void setOrientation(Orientation orientation) {
        orientation = orientation == null ? Orientation.HORIZONTAL : orientation;

        if (orientation != this.orientation) {
            sync("orientation", this.orientation = orientation);
        }
    }
}
