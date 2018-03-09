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

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.model.IModelAndView;
import org.fujion.model.ISupportsModel;
import org.fujion.model.ModelAndView;

/**
 * A component representing a simple list box control.
 */
@Component(tag = "listbox", widgetClass = "Listbox", parentTag = "*", childTag = @ChildTag("listitem"), description = "A simple list box control.")
public class Listbox extends BaseUIComponent implements ISupportsModel<Listitem> {
    
    private boolean multiple;
    
    private int size;
    
    private final ModelAndView<Listitem, ?> modelAndView = new ModelAndView<>(this);
    
    private final Set<Listitem> selected = new LinkedHashSet<>();
    
    /**
     * Returns the multiple selection flag. If true, multiple list items may be selected at once.
     *
     * @return The multiple selection flag.
     */
    @PropertyGetter(value = "multiple", description = "If true, multiple list items may be selected at once.")
    public boolean isMultiple() {
        return multiple;
    }
    
    /**
     * Sets the multiple selection flag. If true, multiple list items may be selected at once.
     *
     * @param multiple The multiple selection flag.
     */
    @PropertySetter(value = "multiple", defaultValue = "false", description = "If true, multiple list items may be selected at once.")
    public void setMultiple(boolean multiple) {
        if (propertyChange("multiple", this.multiple, this.multiple = multiple, true)) {
            if (!multiple && selected.size() > 1) {
                unselect(null);
            }
        }
    }
    
    /**
     * Returns the number of visible list items.
     *
     * @return The number of visible list items.
     */
    @PropertyGetter(value = "size", description = "The number of visible list items.")
    public int getSize() {
        return size;
    }
    
    /**
     * Sets the number of visible list items.
     *
     * @param size The number of visible list items
     */
    @PropertySetter(value = "size", description = "The number of visible list items.")
    public void setSize(int size) {
        propertyChange("size", this.size, this.size = size, true);
    }
    
    /**
     * Returns the set of selected list items.
     *
     * @return The set of selected list items (never null).
     */
    public Set<Listitem> getSelected() {
        return Collections.unmodifiableSet(selected);
    }
    
    /**
     * Returns the number of selected list items.
     *
     * @return The number of selected list items.
     */
    public int getSelectedCount() {
        return selected.size();
    }
    
    /**
     * Returns the selected list item, if any. If there are multiple selections, only the first will
     * be returned.
     *
     * @return The selected list item (may be null).
     */
    public Listitem getSelectedItem() {
        return selected.isEmpty() ? null : selected.iterator().next();
    }
    
    /**
     * Sets the selected list item. Any existing selections are cleared.
     *
     * @param item The selected list item (may be null).
     */
    public void setSelectedItem(Listitem item) {
        validateIsChild(item);
        unselect(item);
        
        if (item != null) {
            item.setSelected(true);
        }
    }
    
    /**
     * Returns the index of the selected list item. If there are multiple selections, the index of
     * the first selected item will be returned.
     *
     * @return The index of the selected list item. If there is no current selection, returns -1.
     */
    public int getSelectedIndex() {
        Listitem item = getSelectedItem();
        return item == null ? -1 : item.getIndex();
    }
    
    /**
     * Sets the selected list item by its index. Any existing selections are cleared.
     *
     * @param index The index of the list item to be selected.
     */
    public void setSelectedIndex(int index) {
        setSelectedItem((Listitem) getChildAt(index));
    }
    
    /**
     * Updates the selection status of a list item.
     *
     * @param item A list item.
     */
    protected void _updateSelected(Listitem item) {
        if (item.isSelected() != selected.contains(item)) {
            if (!multiple) {
                unselect(null);
            }
            
            if (item.isSelected()) {
                selected.add(item);
            } else {
                selected.remove(item);
            }
        }
    }
    
    /**
     * Clears any existing selection.
     */
    public void clearSelected() {
        unselect(null);
    }
    
    /**
     * Unselect all selected list items.
     *
     * @param skip Optional list item to skip.
     */
    private void unselect(Listitem skip) {
        for (Listitem item : selected) {
            if (item != skip) {
                item._setSelected(false, true, false);
            }
        }
        
        selected.clear();
    }
    
    /**
     * If the added list item is marked as selected, add it to the set of selected items.
     *
     * @see org.fujion.component.BaseComponent#afterAddChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterAddChild(BaseComponent child) {
        _updateSelected((Listitem) child);
    }
    
    /**
     * If the removed list item is marked as selected, remove it from the set of selected items.
     *
     * @see org.fujion.component.BaseComponent#afterRemoveChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterRemoveChild(BaseComponent child) {
        if (selected.remove(child)) {
            ((Listitem) child)._setSelected(false, true, false);
        }
    }
    
    @Override
    public void destroy() {
        super.destroy();
        modelAndView.destroy();
    }
    
    @Override
    public IModelAndView<Listitem, ?> getModelAndView() {
        return modelAndView;
    }
    
}
