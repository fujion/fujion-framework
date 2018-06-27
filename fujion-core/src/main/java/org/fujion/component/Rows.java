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

import java.util.Collections;
import java.util.Iterator;
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
 * A component serving as a container for a grid's rows.
 */
@Component(tag = "rows", widgetModule = "fujion-grid", widgetClass = "Rows", parentTag = "grid", childTag = @ChildTag("row"), description = "A container for a grid's rows.")
public class Rows extends BaseUIComponent implements ISupportsModel<Row> {
    
    /**
     * Specifies the selection mode for rows.
     */
    public enum Selectable {
        /**
         * Rows are not selectable (the default).
         */
        NO,
        /**
         * Only a single row may be selected at one time.
         */
        SINGLE,
        /**
         * Multiple rows may be selected at one time.
         */
        MULTIPLE
    }
    
    private Selectable selectable = Selectable.NO;
    
    private final Set<Row> selected = new LinkedHashSet<>();
    
    private final ModelAndView<Row, Object> modelAndView = new ModelAndView<>(this);
    
    @Override
    public void destroy() {
        super.destroy();
        modelAndView.destroy();
    }
    
    @Override
    public IModelAndView<Row, ?> getModelAndView() {
        return modelAndView;
    }
    
    /**
     * Returns the {@link Selectable selectable} setting.
     *
     * @return The {@link Selectable selectable} setting.
     */
    @PropertyGetter(value = "selectable", description = "The selectability setting.")
    public Selectable getSelectable() {
        return selectable;
    }
    
    /**
     * Sets the {@link Selectable selectable} setting.
     *
     * @param selectable The {@link Selectable selectable} setting.
     */
    @PropertySetter(value = "selectable", defaultValue = "no", description = "The selectability setting.")
    public void setSelectable(Selectable selectable) {
        if (propertyChange("selectable", this.selectable, this.selectable = defaultify(selectable, Selectable.NO), true)) {
            if (selectable != Selectable.MULTIPLE && !selected.isEmpty()) {
                unselect(selectable == Selectable.NO ? null : getSelectedRow());
            }
        }
    }
    
    /**
     * Returns the currently selected row, or null if none. If multiple rows are selected, only the
     * first will be returned.
     *
     * @return The currently selected row, or null if none.
     */
    public Row getSelectedRow() {
        return selected.isEmpty() ? null : selected.iterator().next();
    }
    
    /**
     * Returns an immutable set of currently selected rows.
     *
     * @return An immutable set of currently selected rows.
     */
    public Set<Row> getSelected() {
        return Collections.unmodifiableSet(selected);
    }
    
    /**
     * Clears all selections.
     */
    public void clearSelected() {
        unselect(null);
    }
    
    private void unselect(Row excluded) {
        Iterator<Row> iter = selected.iterator();
        
        while (iter.hasNext()) {
            Row row = iter.next();
            
            if (row != excluded) {
                row._setSelected(false, true, false);
                iter.remove();
            }
        }
    }

    /**
     * Returns the number of selected rows.
     *
     * @return The number of selected rows.
     */
    public int getSelectedCount() {
        return selected.size();
    }
    
    /**
     * Updates the selection state of a row.
     *
     * @param row A row.
     */
    protected void _updateSelected(Row row) {
        if (row.isSelected()) {
            selected.add(row);
            
            if (selectable != Selectable.MULTIPLE) {
                unselect(selectable == Selectable.NO ? null : row);
            }
        } else {
            selected.remove(row);
        }
    }
    
    /**
     * If the removed child row was selected, remove it from the set.
     *
     * @see org.fujion.component.BaseUIComponent#afterRemoveChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterRemoveChild(BaseComponent child) {
        super.afterRemoveChild(child);
        selected.remove(child);
    }
    
    /**
     * if the added child row is selected, add it to the set.
     *
     * @see org.fujion.component.BaseComponent#afterAddChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterAddChild(BaseComponent child) {
        super.afterAddChild(child);
        Row row = (Row) child;
        
        if (row.isSelected()) {
            _updateSelected(row);
        }
    }
}
