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
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.model.IModelAndView;
import org.fujion.model.ISupportsModel;
import org.fujion.model.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A component supporting a accordion view.
 */
@Component(
        tag = "accordion",
        widgetModule = "fujion-accordion",
        widgetClass = "Accordion",
        parentTag = "*",
        childTag = @ChildTag("accordionitem"),
        description = "A component supporting a accordion view.")
public class Accordion extends BaseUIComponent implements ISupportsModel<Accordionitem> {

    private final ModelAndView<Accordionitem, Object> modelAndView = new ModelAndView<>(this);

    private boolean multiple;

    /**
     * Returns the currently expanded item, if any.  If multiple items are expanded, returns the first only.
     *
     * @return The currently expanded item (possibly null).
     */
    public Accordionitem getExpandedItem() {
        return this.getChildren().stream()
                .map(Accordionitem.class::cast)
                .filter(Accordionitem::isExpanded)
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns the currently expanded items, if any.
     *
     * @return A list of the currently expanded items (never null).
     */
    public List<Accordionitem> getExpandedItems() {
        return this.getChildren().stream()
                .map(Accordionitem.class::cast)
                .filter(Accordionitem::isExpanded)
                .collect(Collectors.toList());
    }

    /**
     * Sets the currently expanded item.  All other expanded items will be collapsed.
     *
     * @param expandedItem The item to expand (possibly null).
     */
    public void setExpandedItem(Accordionitem expandedItem) {
        validateIsChild(expandedItem);
        getExpandedItems().forEach(item -> item._setExpanded(item == expandedItem, false, true));
    }

    /**
     * Used to notify parent of a change in a child's expansion state.
     *
     * @param item The expanded item.
     */
    protected void _setExpandedItem(Accordionitem item) {
        if (!multiple && item.isExpanded()) {
            setExpandedItem(item);
        }
    }

    /**
     * If the added item is marked as expanded, update the expanded item.
     *
     * @see BaseUIComponent#afterAddChild(BaseComponent)
     */
    @Override
    protected void afterAddChild(BaseComponent child) {
        _setExpandedItem((Accordionitem) child);
    }

    @Override
    public IModelAndView<Accordionitem, ?> getModelAndView() {
        return modelAndView;
    }

    @PropertyGetter(value = "multiple", description = "True if multiple items may be expanded at once.")
    public boolean isMultiple() {
        return multiple;
    }

    @PropertySetter(value = "multiple", description = "True if multiple items may be expanded at once.", defaultValue = "false")
    public void setMultiple(boolean multiple) {
        if (propertyChange("multiple", this.multiple, this.multiple = multiple, false) && !multiple) {
            setExpandedItem(getExpandedItem());
        }
    }

}
