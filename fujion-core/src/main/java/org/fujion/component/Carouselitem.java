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

    private String caption;

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
        _setSelected(selected, true, true);
    }

    /**
     * Returns the caption associated with the item.
     *
     * @return The item's caption.
     */
    @PropertyGetter(value = "caption", description = "The caption associated with the item.")
    public String getCaption() {
        return caption;
    }

    /**
     * Sets the caption associated with the item.
     *
     * @param caption The item's caption.
     */
    @PropertySetter(value = "caption", description = "The caption associated with the item.")
    public void setCaption(String caption) {
        propertyChange("caption", this.caption, this.caption = caption, true);
    }

    /**
     * Handles change events from the client.
     *
     * @param event A change event.
     */
    @EventHandler(value = "change", syncToClient = false, mode = "init")
    private void _onChange(ChangeEvent event) {
        _setSelected(defaultify(event.getValue(Boolean.class), true), true, false);
        event = new ChangeEvent(this.getParent(), event.getData(), this);
        EventUtil.send(event);
    }

    /**
     * Sets the item's selected status.
     *
     * @param selected The new selected status.
     * @param notifyParent If true, notify the parent item view of the status change.
     * @param syncToClient If true, sync the status with the client.
     */
    protected void _setSelected(boolean selected, boolean notifyParent, boolean syncToClient) {
        if (propertyChange("selected", this.selected, this.selected = selected, syncToClient)) {
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
