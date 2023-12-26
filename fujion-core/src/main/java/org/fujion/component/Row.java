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
import org.fujion.annotation.EventHandler;
import org.fujion.event.ChangeEvent;
import org.fujion.event.EventUtil;

/**
 * A component representing a single row within a grid.
 */
@Component(
        tag = "row",
        widgetModule = "fujion-grid",
        widgetClass = "Row",
        content = ContentHandling.AS_CHILD,
        parentTag = "rows",
        childTag = @ChildTag("*"),
        description = "A single row within a grid.")
public class Row extends BaseUIComponent {

    private boolean selected;

    /**
     * Returns the selected state of this row.
     *
     * @return The selected state of this row.
     */
    @PropertyGetter(value = "selected", description = "The selected state of this row.")
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selected state of this row.
     *
     * @param selected The selected state of this row.
     */
    @PropertySetter(value = "selected", defaultValue = "false", description = "The selected state of this row.")
    public void setSelected(boolean selected) {
        _setSelected(selected, true, true);
    }

    protected void _setSelected(boolean selected, boolean notifyClient, boolean notifyParent) {
        if (propertyChange("selected", this.selected, this.selected = selected, notifyClient)) {
            if (notifyParent && getParent() != null) {
                ((Rows) getParent())._updateSelected(this);
            }
        }
    }

    /**
     * Handles change events from the client.
     *
     * @param event A change event.
     */
    @EventHandler(value = "change", syncToClient = false, mode = "init")
    @SuppressWarnings("unused")
    private void _onChange(ChangeEvent event) {
        _setSelected(defaultify(event.getValue(Boolean.class), true), false, true);
        event = new ChangeEvent(this.getParent(), event.getData(), this);
        EventUtil.send(event);
    }
}
