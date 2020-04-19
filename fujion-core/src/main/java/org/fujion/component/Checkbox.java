/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.event.ChangeEvent;

/**
 * A component representing a simple check box with an associated label.
 */
@Component(
        tag = "checkbox",
        widgetClass = "Checkbox",
        parentTag = "*",
        description = "A simple check box with an associated label.")
public class Checkbox extends BaseLabeledComponent<BaseLabeledComponent.LabelPositionAll> {

    private boolean checked;

    public Checkbox() {
        this(null);
    }

    public Checkbox(String label) {
        super(label);
        setPosition(LabelPositionAll.RIGHT);
    }

    /**
     * Returns the checked state of the check box.
     *
     * @return True if the check box is checked, false if not.
     */
    @PropertyGetter(value = "checked", description = "The checked state of the check box.")
    public boolean isChecked() {
        return checked;
    }

    /**
     * Sets the checked state of the check box.
     *
     * @param checked if the check box is checked, false if not.
     */
    @PropertySetter(value = "checked", defaultValue = "false", description = "The checked state of the check box.")
    public void setChecked(boolean checked) {
        _setChecked(checked, true);
    }

    /**
     * Update the checked state and optionally notify the client.
     *
     * @param checked The new checked state.
     * @param notifyClient If true, notify the client.
     */
    public void _setChecked(boolean checked, boolean notifyClient) {
        propertyChange("checked", this.checked, this.checked = checked, notifyClient);
    }

    /**
     * Returns the position of the label relative to the contained elements. Defaults to 'right'.
     *
     * @return May be one of: left, right, top, bottom.
     */
    @Override
    @PropertyGetter(value = "position", description = "The position of the label relative to the contained elements.")
    public LabelPositionAll getPosition() {
        return super.getPosition();
    }

    /**
     * Sets the position of the label relative to the contained elements.
     *
     * @param position May be one of: left, right, top, bottom.
     */
    @Override
    @PropertySetter(value = "position", defaultValue = "right", description = "The position of the label relative to the contained elements.")
    public void setPosition(LabelPositionAll position) {
        super.setPosition(position);
    }

    /**
     * Handler for change events sent from the client.
     *
     * @param event A change event.
     */
    @EventHandler(value = "change", syncToClient = false, mode = "init")
    protected void _onChange(ChangeEvent event) {
        _setChecked(defaultify(event.getValue(Boolean.class), true), false);
    }

}
