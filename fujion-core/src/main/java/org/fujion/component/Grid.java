/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2016 Regenstrief Institute, Inc.
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

/**
 * A grid component.
 */
@Component(tag = "grid", widgetModule = "fujion-grid", widgetClass = "Grid", parentTag = "*", childTag = {
        @ChildTag(value = "rows", maximum = 1), @ChildTag(value = "columns", maximum = 1) })
public class Grid extends BaseUIComponent {

    private Columns columns;

    private Rows rows;

    private String title;

    public Grid() {
        addClass("table");
    }

    @Override
    protected void afterAddChild(BaseComponent child) {
        super.afterAddChild(child);

        if (child instanceof Rows) {
            rows = (Rows) child;
        }

        if (child instanceof Columns) {
            columns = (Columns) child;
        }
    }

    @Override
    protected void afterRemoveChild(BaseComponent child) {
        super.afterRemoveChild(child);

        if (child == rows) {
            rows = null;
        } else if (child == columns) {
            columns = null;
        }
    }

    public Columns getColumns() {
        return columns;
    }

    public Rows getRows() {
        return rows;
    }
    
    @PropertyGetter("title")
    public String getTitle() {
        return title;
    }
    
    @PropertySetter("title")
    public void setTitle(String title) {
        if (!areEqual(title = nullify(title), this.title)) {
            sync("title", this.title = title);
        }
    }
}