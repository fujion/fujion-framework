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

/**
 * Component serving as a container for a grid's columns.
 */
@Component(tag = "columns", widgetModule = "fujion-grid", widgetClass = "Columns", parentTag = "grid", childTag = @ChildTag("column"), description = "A container for a grid's columns.")
public class Columns extends BaseUIComponent {
    
    private Column sortColumn;
    
    /**
     * Returns the currently sorted column.
     *
     * @return The currently sorted column.
     */
    public Column getSortColumn() {
        return sortColumn;
    }
    
    /**
     * Sets the currently sorted column.
     *
     * @param sortColumn The column to sort.
     */
    public void setSortColumn(Column sortColumn) {
        if (sortColumn != this.sortColumn) {
            validateIsChild(sortColumn);
            
            if (this.sortColumn != null) {
                this.sortColumn._setSortColumn(false, false);
            }
            
            this.sortColumn = sortColumn;
            
            if (sortColumn != null) {
                sortColumn._setSortColumn(true, false);
            }
        }
    }

    /**
     * If the added column is marked as sorted, set it as the sort column.
     *
     * @see org.fujion.component.BaseComponent#afterAddChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterAddChild(BaseComponent child) {
        super.afterAddChild(child);
        
        if (((Column) child).isSortColumn()) {
            setSortColumn((Column) child);
        }
    }
    
    /**
     * If the removed column is the sort column, set the sort column to null.
     *
     * @see org.fujion.component.BaseUIComponent#afterRemoveChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterRemoveChild(BaseComponent child) {
        super.afterRemoveChild(child);
        
        if (child == sortColumn) {
            sortColumn = null;
        }
    }
}
