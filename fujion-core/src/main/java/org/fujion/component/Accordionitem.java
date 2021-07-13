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
 * A single item in a accordion.
 */
@Component(
        tag = "accordionitem",
        widgetModule = "fujion-accordion",
        widgetClass = "Accordionitem",
        content = ContentHandling.AS_CHILD,
        parentTag = "accordion",
        childTag = @ChildTag("*"),
        description = "A single item in an accordion.")
public class Accordionitem extends BaseUIComponent {

    private boolean expanded;

    private String title;

    /**
     * Returns the expansion state of the item.
     *
     * @return The expansion state of the item.
     */
    @PropertyGetter(value = "expanded", description = "True if the item is expanded.")
    public boolean isExpanded() {
        return expanded;
    }

    /**
     * Sets the expansion state of the item.
     *
     * @param expanded The expansion state of the item.
     */
    @PropertySetter(value = "expanded", defaultValue = "false", description = "True if the item is expanded.")
    public void setExpanded(boolean expanded) {
        _setExpanded(expanded, true, true);
    }

    /**
     * Returns the title text.
     *
     * @return The title text.
     */
    @PropertyGetter(value = "title", description = "The title text.")
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title text.
     *
     * @param title The title text.
     */
    @PropertySetter(value = "title", description = "The title text.")
    public void setTitle(String title) {
        propertyChange("title", this.title, this.title = nullify(title), true);
    }

    /**
     * Sets the item's expanded status.
     *
     * @param expanded The new expanded status.
     * @param notifyParent If true, notify the parent item view of the status change.
     * @param notifyClient If true, notify the client of the status change.
     */
    protected void _setExpanded(boolean expanded, boolean notifyParent, boolean notifyClient) {
        if (propertyChange("expanded", this.expanded, this.expanded = expanded, notifyClient)) {
            if (expanded && notifyParent && getParent() != null) {
                getAccordion()._setExpandedItem(this);
            }
        }
    }

    /**
     * Returns the parent Accordion component, or null if there is no parent.
     *
     * @return The parent Accordion component.
     */
    public Accordion getAccordion() {
        return (Accordion) getParent();
    }

    @Override
    public void bringToFront() {
        setExpanded(true);
        super.bringToFront();
    }

    /**
     * Handles change events from the client.
     *
     * @param event A change event.
     */
    @EventHandler(value = "change", syncToClient = false, mode = "init")
    private void _onChange(ChangeEvent event) {
        _setExpanded(defaultify(event.getValue(Boolean.class), true), true, false);
        event = new ChangeEvent(this.getParent(), event.getData(), this);
        EventUtil.send(event);
    }

}
