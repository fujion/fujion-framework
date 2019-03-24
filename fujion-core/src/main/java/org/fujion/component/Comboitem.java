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
package org.fujion.component;

import org.apache.commons.lang.ObjectUtils;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.event.ChangeEvent;
import org.fujion.event.EventUtil;

/**
 * A single item within a combo box.
 */
@Component(tag = "comboitem", widgetClass = "Comboitem", parentTag = "combobox", description = "A single item within a combo box.")
public class Comboitem extends BaseLabeledImageComponent<BaseLabeledComponent.LabelPositionNone> implements Comparable<Comboitem> {

    private boolean selected;

    private String value;

    public Comboitem() {
        super();
    }

    public Comboitem(String label) {
        super(label);
    }

    public Comboitem(String label, String value, Object data) {
        super(label);
        setValue(value);
        setData(data);
    }

    public Comboitem(String label, String image) {
        super(label, image);
    }

    /**
     * Returns the selection state.
     *
     * @return The selection state.
     */
    @PropertyGetter(value = "selected", description = "The selection state.")
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selection state.
     *
     * @param selected The selection state.
     */
    @PropertySetter(value = "selected", defaultValue = "false", description = "The selection state.")
    public void setSelected(boolean selected) {
        _setSelected(selected, true, true);
    }

    /**
     * Returns the value associated with the combo item.
     *
     * @return The value associated with the combo item.
     */
    @PropertyGetter(value = "value", description = "The value associated with the combo item.")
    public String getValue() {
        return value;
    }

    /**
     * Sets the value associated with the combo item.
     *
     * @param value The value associated with the combo item.
     */
    @PropertySetter(value = "value", description = "The value associated with the combo item.")
    public void setValue(String value) {
        propertyChange("value", this.value, this.value = value, true);
    }

    /**
     * Sets the selection state.
     *
     * @param selected The selection state.
     * @param notifyClient If true, notify the client of the state change.
     * @param notifyParent If true, notify the parent of the state change.
     */
    protected void _setSelected(boolean selected, boolean notifyClient, boolean notifyParent) {
        if (propertyChange("selected", this.selected, this.selected = selected, notifyClient)) {
            if (notifyParent && getParent() != null) {
                getCombobox()._updateSelected(selected ? this : null);
            }
        }
    }

    /**
     * Returns the combo box that is the parent of this combo item.
     *
     * @return The parent combo box (may be null).
     */
    public Combobox getCombobox() {
        return (Combobox) getParent();
    }

    /**
     * Handles change events from the client.
     *
     * @param event A change event.
     */
    @EventHandler(value = "change", syncToClient = false, mode = "init")
    private void _onChange(ChangeEvent event) {
        _setSelected(defaultify(event.getValue(Boolean.class), true), false, true);
        event = new ChangeEvent(this.getParent(), this, event.getData(), getLabel());
        EventUtil.send(event);
    }

    /**
     * Compares two combo items by comparing the label and value properties, respectively.
     *
     * @param o Item to compare.
     * @return Result of the comparison.
     */
    @Override
    public int compareTo(Comboitem o) {
        int cmp = ObjectUtils.compare(getLabel(), o.getLabel());
        return cmp != 0 ? cmp : ObjectUtils.compare(value, o.value);
    }
    
}
