/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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
import org.fujion.model.IModelAndView;
import org.fujion.model.ISupportsModel;
import org.fujion.model.ModelAndView;

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

    private Accordionitem expandedItem;
    
    /**
     * Returns the currently expanded item, if any.
     *
     * @return The currently expanded item (may be null).
     */
    public Accordionitem getExpandedItem() {
        return expandedItem;
    }

    /**
     * Sets the currently expanded item.
     *
     * @param expandedItem The item to select (may be null).
     */
    public void setExpandedItem(Accordionitem expandedItem) {
        validateIsChild(expandedItem);

        if (this.expandedItem != null) {
            this.expandedItem._setExpanded(false, false, true);
        }

        this.expandedItem = expandedItem;

        if (expandedItem != null) {
            expandedItem._setExpanded(true, false, true);
        }
    }

    /**
     * If the added item is marked as expanded, update the expanded item.
     *
     * @see BaseUIComponent#afterAddChild(BaseComponent)
     */
    @Override
    protected void afterAddChild(BaseComponent child) {
        if (((Accordionitem) child).isExpanded()) {
            setExpandedItem((Accordionitem) child);
        }
    }

    /**
     * If the removed item is expanded, clear the selection.
     *
     * @see BaseUIComponent#afterRemoveChild(BaseComponent)
     */
    @Override
    protected void afterRemoveChild(BaseComponent child) {
        if (child == expandedItem) {
            expandedItem = null;
        }
    }

    @Override
    public IModelAndView<Accordionitem, ?> getModelAndView() {
        return modelAndView;
    }

}
