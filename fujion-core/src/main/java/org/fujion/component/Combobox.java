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

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.model.IModelAndView;
import org.fujion.model.ISupportsModel;
import org.fujion.model.ModelAndView;

/**
 * A component representing a combo box control.
 */
@Component(tag = "combobox", widgetClass = "Combobox", parentTag = "*", childTag = @ChildTag("comboitem"), description = "A combo box control.")
public class Combobox extends BaseInputboxComponent<String> implements ISupportsModel<Comboitem> {

    private Comboitem selected;

    private boolean autoFilter;

    private final ModelAndView<Comboitem, Object> modelAndView = new ModelAndView<>(this);

    /**
     * Returns the currently selected item, if any.
     *
     * @return The currently selected item, or null if none.
     */
    public Comboitem getSelectedItem() {
        return selected;
    }

    /**
     * Sets the currently selected item.
     *
     * @param item The combo item to select, or null for no selection.
     */
    public void setSelectedItem(Comboitem item) {
        validateIsChild(item);

        if (item == null) {
            _updateSelected(item);
        } else {
            item.setSelected(true);
        }
    }

    /**
     * Returns the index of the currently selected item.
     *
     * @return The index of the currently selected item, or -1 if no item is selected.
     */
    public int getSelectedIndex() {
        Comboitem item = getSelectedItem();
        return item == null ? -1 : item.getIndex();
    }

    /**
     * Sets the item at the specified index as selected.
     *
     * @param index Index of the item to select, or -1 to clear the selection.
     */
    public void setSelectedIndex(int index) {
        setSelectedItem((Comboitem) getChildAt(index));
    }

    /**
     * Update the selected item to the specified value.
     *
     * @param item Item to be selected.
     */
    protected void _updateSelected(Comboitem item) {
        if (selected != null) {
            selected._setSelected(false, true, false);
        }

        selected = item;
        setValue(selected == null ? null : selected.getLabel());
    }

    /**
     * If the added item is marked as selected, set it as the selected item.
     *
     * @see org.fujion.component.BaseComponent#afterAddChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterAddChild(BaseComponent child) {
        Comboitem item = (Comboitem) child;

        if (item.isSelected()) {
            _updateSelected(item);
        }
    }

    /**
     * If the removed item is selected, set the selected item to null.
     *
     * @see org.fujion.component.BaseUIComponent#afterRemoveChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterRemoveChild(BaseComponent child) {
        if (child == selected) {
            selected = null;
        }
    }

    /**
     * If true, any choices not matching the current input will be hidden.
     *
     * @return True if any choices not matching the current input should be hidden.
     */
    @PropertyGetter(value = "autoFilter", description = "True if any choices not matching the current input should be hidden.")
    public boolean getAutoFilter() {
        return autoFilter;
    }

    /**
     * Set to true if any choices not matching the current input should be hidden.
     *
     * @param autoFilter True if any choices not matching the current input should be hidden.
     */
    @PropertySetter(value = "autoFilter", defaultValue = "false", description = "True if any choices not matching the current input should be hidden.")
    public void setAutoFilter(boolean autoFilter) {
        propertyChange("autoFilter", this.autoFilter, this.autoFilter = autoFilter, true);
    }

    @Override
    protected String _toValue(String value) {
        return value;
    }

    @Override
    protected String _toString(String value) {
        return value;
    }

    @Override
    public void destroy() {
        super.destroy();
        modelAndView.destroy();
    }

    @Override
    public IModelAndView<Comboitem, ?> getModelAndView() {
        return modelAndView;
    }

}
