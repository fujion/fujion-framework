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
import org.fujion.annotation.EventHandler;
import org.fujion.event.CloseEvent;
import org.fujion.event.Event;
import org.fujion.event.EventUtil;
import org.fujion.event.OpenEvent;

/**
 * A popup box component is a text box with a drop down button that triggers the appearance of a
 * popup component. A popup component may be specified as a property or inline as a child.
 */
@Component(
        tag = "popupbox",
        widgetClass = "Popupbox",
        parentTag = "*",
        childTag = @ChildTag(value = "popup", maximum = 1),
        description = "A text box with a drop down button that triggers the appearance of a popup component.")
public class Popupbox extends Textbox {
    
    private boolean open;
    
    /**
     * Opens the popup box. Shortcut for <code>setOpen(true)</code>
     */
    public void open() {
        setOpen(true);
    }
    
    /**
     * Closes the popup box. Shortcut for <code>setOpen(false)</code>
     */
    public void close() {
        setOpen(false);
    }
    
    /**
     * If popup specified as a child, set it as the associated popup component.
     *
     * @see org.fujion.component.BaseComponent#afterAddChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterAddChild(BaseComponent child) {
        super.afterAddChild(child);
        
        if (getPage() != null) {
            setPopup((Popup) child);
        }
    }
    
    /**
     * If popup specified as a child, set it as the associated popup component.
     *
     * @see org.fujion.component.BaseComponent#onAttach(org.fujion.component.Page)
     */
    @Override
    protected void onAttach(Page page) {
        super.onAttach(page);
        
        if (getChildCount() > 0) {
            setPopup((Popup) getFirstChild());
        }
    }
    
    /**
     * Sets the popup associated with the popup box.
     */
    @Override
    public void setPopup(Popup popup) {
        BaseComponent child = this.getFirstChild();
        
        if (child != null && child != popup) {
            throw new IllegalArgumentException("You may not set a popup reference when a child popup is present");
        }
        
        if (popup != getPopup()) {
            open = false;
        }
        
        super.setPopup(popup);
    }
    
    /**
     * Returns true if the popup box is open.
     *
     * @return True if the popup box is open.
     */
    @PropertyGetter(value = "open", description = "True if the popup box is open.")
    public boolean isOpen() {
        return open;
    }
    
    /**
     * Sets the open state of the popup box.
     *
     * @param open The open state of the popup box.
     */
    @PropertySetter(value = "open", defaultValue = "false", description = "True if the popup box is open.")
    public void setOpen(boolean open) {
        _setOpen(open, true);
    }

    /**
     * Sets the open state of the popup box, optionally notifying the client.
     *
     * @param open The open state of the popup box.
     * @param notifyClient If true, notify the client.
     */
    private void _setOpen(boolean open, boolean notifyClient) {
        if (propertyChange("open", this.open, this.open = open, false) && notifyClient) {
            invoke(open ? "open" : "close");
        }
    }
    
    /**
     * Handles popup open and close events from the client.
     *
     * @param event A popup open or close event.
     */
    @EventHandler(value = { "popupopen", "popupclose" }, mode = "init")
    private void _onOpen(Event event) {
        boolean open = "popupopen".equals(event.getType());

        if (open != this.open) {
            _setOpen(open, false);
            event = open ? new OpenEvent(this, event.getData()) : new CloseEvent(this, event.getData());
            EventUtil.send(event);
        }
    }
    
}
