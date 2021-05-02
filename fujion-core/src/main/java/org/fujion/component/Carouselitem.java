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
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.event.ChangeEvent;
import org.fujion.event.EventUtil;

/**
 * A single item in a carousel.
 */
@Component(
        tag = "carouselitem",
        widgetModule = "fujion-carousel",
        widgetClass = "Carouselitem",
        content = ContentHandling.AS_CHILD,
        parentTag = "carousel",
        childTag = @ChildTag("*"),
        description = "A single item in a carousel.")
public class Carouselitem extends BaseUIComponent {

    private boolean selected;

    /**
     * Returns the selection state of the item.
     *
     * @return The selection state of the item.
     */
    @PropertyGetter(value = "selected", description = "The selection state of the item.")
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selection state of the item.
     *
     * @param selected The selection state of the item.
     */
    @PropertySetter(value = "selected", defaultValue = "false", description = "The selection state of the item.")
    public void setSelected(boolean selected) {
        _setSelected(selected, true);
    }

    /**
     * Handles change events from the client.
     *
     * @param event A change event.
     */
    @EventHandler(value = "change", syncToClient = false, mode = "init")
    private void _onChange(ChangeEvent event) {
        _setSelected(defaultify(event.getValue(Boolean.class), true), true);
        event = new ChangeEvent(this.getParent(), event.getData(), this);
        EventUtil.send(event);
    }

    /**
     * Sets the item's selected status.
     *
     * @param selected The new selected status.
     * @param notifyParent If true, notify the parent item view of the status change.
     */
    protected void _setSelected(boolean selected, boolean notifyParent) {
        if (propertyChange("selected", this.selected, this.selected = selected, true)) {
            if (notifyParent && getParent() != null) {
                getCarousel().setSelectedItem(selected ? this : null);
            }
        }
    }

    public Carousel getCarousel() {
        return (Carousel) getParent();
    }

    @Override
    public void bringToFront() {
        setSelected(true);
        super.bringToFront();
    }

}
