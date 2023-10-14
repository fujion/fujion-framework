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

/**
 * A component representing a single cell within a grid row.
 */
@Component(
        tag = "rowcell",
        widgetModule = "fujion-grid",
        widgetClass = "Rowcell",
        content = ContentHandling.AS_CHILD,
        parentTag = "row",
        childTag = @ChildTag("*"),
        description = "A single cell within a grid row.")
public class Rowcell extends BaseLabeledComponent<BaseLabeledComponent.LabelPositionNone> {
    
    private int colspan = 1;
    
    private int rowspan = 1;
    
    public Rowcell() {
        super();
    }

    public Rowcell(String label) {
        super(label);
    }

    /**
     * Returns how many columns this cell will span. Default is 1.
     *
     * @return How many columns this cell will span. Default is 1.
     */
    @PropertyGetter(value = "colspan", description = "How many columns this cell will span.")
    public int getColspan() {
        return colspan;
    }
    
    /**
     * Sets how many columns this cell will span.
     *
     * @param colspan How many columns this cell will span.
     */
    @PropertySetter(value = "colspan", defaultValue = "1", description = "How many columns this cell will span.")
    public void setColspan(int colspan) {
        propertyChange("colspan", this.colspan, this.colspan = colspan, true);
    }
    
    /**
     * Returns how many rows this cell will span. Default is 1.
     *
     * @return How many rows this cell will span. Default is 1.
     */
    @PropertyGetter(value = "rowspan", description = "How many rows this cell will span.")
    public int getRowspan() {
        return rowspan;
    }
    
    /**
     * Sets how many rows this cell will span.
     *
     * @param rowspan How many rows this cell will span.
     */
    @PropertySetter(value = "rowspan", defaultValue = "1", description = "How many rows this cell will span.")
    public void setRowspan(int rowspan) {
        propertyChange("rowspan", this.rowspan, this.rowspan = rowspan, true);
    }
    
}
