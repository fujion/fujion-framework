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
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.event.ChangeEvent;
import org.fujion.event.EventUtil;

/**
 * A single item within a list box.
 */
@Component(tag = "listitem", widgetClass = "Listitem", parentTag = "listbox", description = "A single item within a list box.")
public class Listitem extends BaseLabeledComponent<BaseLabeledComponent.LabelPositionNone> {
    
    private boolean selected;
    
    private String value;
    
    public Listitem() {
        super();
    }
    
    public Listitem(String label) {
        super(label);
    }
    
    public Listitem(String label, String value, Object data) {
        super(label);
        setValue(value);
        setData(data);
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
                getListbox()._updateSelected(this);
            }
        }
    }
    
    /**
     * Returns the list box that is the parent of this list item.
     *
     * @return The parent list box (may be null).
     */
    public Listbox getListbox() {
        return (Listbox) getParent();
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
    @PropertySetter(value = "selected", description = "The selection state.")
    public void setSelected(boolean selected) {
        _setSelected(selected, true, true);
    }
    
    /**
     * Returns the value associated with the list item.
     *
     * @return The value associated with the list item.
     */
    @PropertyGetter(value = "value", description = "The value associated with the list item.")
    public String getValue() {
        return value;
    }
    
    /**
     * Sets the value associated with the list item.
     *
     * @param value The value associated with the list item.
     */
    @PropertySetter(value = "value", description = "The value associated with the list item.")
    public void setValue(String value) {
        propertyChange("value", this.value, this.value = value, true);
    }
    
    /**
     * Handles change events from the client.
     *
     * @param event A change event.
     */
    @EventHandler(value = "change", syncToClient = false, mode = "init")
    private void _onChange(ChangeEvent event) {
        _setSelected(defaultify(event.getValue(Boolean.class), true), false, true);
        event = new ChangeEvent(this.getParent(), this, event.getData(), this);
        EventUtil.send(event);
    }
    
}
