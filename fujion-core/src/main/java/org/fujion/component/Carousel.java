/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
import org.springframework.util.Assert;

/**
 * A component supporting a carousel view.
 */
@Component(
        tag = "carousel",
        widgetModule = "fujion-carousel",
        widgetClass = "Carousel",
        parentTag = "*",
        childTag = @ChildTag("carouselitem"),
        description = "A component supporting a carousel view.")
public class Carousel extends BaseUIComponent implements ISupportsModel<Carouselitem> {

    private final ModelAndView<Carouselitem, Object> modelAndView = new ModelAndView<>(this);

    private Carouselitem selectedItem;

    private int interval;

    private boolean keyboard = true;

    private boolean wrap = true;

    /**
     * Returns the amount of time to delay between automatically cycling an item. If 0, carousel will not automatically cycle.
     *
     * @return The amount of time to delay between automatically cycling an item.
     */
    @PropertyGetter(value = "interval", bindable = false, description = "The amount of time to delay between automatically cycling an item. If 0, carousel will not automatically cycle.")
    public int getInterval() {
        return interval;
    }

    /**
     * Sets the amount of time to delay between automatically cycling an item. If 0, carousel will not automatically cycle.
     *
     * @param interval The amount of time to delay between automatically cycling an item.
     */
    @PropertySetter(value = "interval", bindable = false, defaultValue = "0", description = "The amount of time to delay between automatically cycling an item. If 0, carousel will not automatically cycle.")
    public void setInterval(int interval) {
        Assert.isTrue(interval >= 0, "Interval value must not be negative.");
        propertyChange("interval", this.interval, this.interval = interval, true);
    }

    /**
     * Returns whether or not the carousel will respond to keyboard events.
     *
     * @return True if the carousel will respond to keyboard events.
     */
    @PropertyGetter(value = "keyboard", bindable = false, description = "If true, the carousel will respond to keyboard events.")
    public boolean getKeyboard() {
        return keyboard;
    }

    /**
     * Sets whether or not the carousel will respond to keyboard events.
     *
     * @param keyboard True if the carousel is to respond to keyboard events.
     */
    @PropertySetter(value = "keyboard", bindable = false, defaultValue = "true", description = "If true, the carousel will respond to keyboard events.")
    public void setKeyboard(boolean keyboard) {
        propertyChange("keyboard", this.keyboard, this.keyboard = keyboard, true);
    }

    /**
     * Returns Whether the carousel should cycle continuously (wrap = true) or have hard stops (wrap = false).
     *
     * @return True if the carousel should cycle continously.
     */
    @PropertyGetter(value = "wrap", bindable = false, description = "If true, the carousel will cycle continously.")
    public boolean getWrap() {
        return wrap;
    }

    /**
     * Sets whether or not the carousel will respond to keyboard events.
     *
     * @param wrap True if the carousel is to respond to keyboard events.
     */
    @PropertySetter(value = "wrap", bindable = false, defaultValue = "true", description = "If true, the carousel will cycle continously.")
    public void setWrap(boolean wrap) {
        propertyChange("wrap", this.wrap, this.wrap = wrap, true);
    }

    /**
     * Returns the currently selected item, if any.
     *
     * @return The currently selected item (may be null).
     */
    public Carouselitem getSelectedItem() {
        return selectedItem;
    }

    /**
     * Sets the currently selected item.
     *
     * @param selectedItem The item to select (may be null).
     */
    public void setSelectedItem(Carouselitem selectedItem) {
        validateIsChild(selectedItem);

        if (this.selectedItem != null) {
            this.selectedItem._setSelected(false, false);
        }

        this.selectedItem = selectedItem;

        if (selectedItem != null) {
            selectedItem._setSelected(true, false);
        }
    }

    /**
     * If the added item is marked as selected, update the selected item.
     *
     * @see BaseUIComponent#afterAddChild(BaseComponent)
     */
    @Override
    protected void afterAddChild(BaseComponent child) {
        if (((Carouselitem) child).isSelected()) {
            setSelectedItem((Carouselitem) child);
        }
    }

    /**
     * If the removed item is selected, clear the selection.
     *
     * @see BaseUIComponent#afterRemoveChild(BaseComponent)
     */
    @Override
    protected void afterRemoveChild(BaseComponent child) {
        if (child == selectedItem) {
            selectedItem = null;
        }
    }

    @Override
    public IModelAndView<Carouselitem, ?> getModelAndView() {
        return modelAndView;
    }

}
