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
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.event.Event;
import org.fujion.event.OpenEvent;
import org.fujion.model.IModelAndView;
import org.fujion.model.ISupportsModel;
import org.fujion.model.ModelAndView;

/**
 * A component representing a drop down menu.
 */
@Component(tag = "menu", widgetClass = "Menu", parentTag = "*", childTag = { @ChildTag("menuitem"), @ChildTag("menuheader"),
        @ChildTag("menuseparator") }, description = "A drop down menu.")
public class Menu extends BaseMenuComponent implements ISupportsModel<BaseMenuComponent> {

    private final ModelAndView<BaseMenuComponent, Object> modelAndView = new ModelAndView<>(this);
    
    private boolean open;

    /**
     * Opens the drop down menu. Shortcut for <code>setOpen(true)</code>
     */
    public void open() {
        setOpen(true);
    }

    /**
     * Closes the drop down menu. Shortcut for <code>setOpen(false)</code>
     */
    public void close() {
        setOpen(false);
    }

    /**
     * Returns the open state.
     *
     * @return The open state.
     */
    @PropertyGetter(value = "open", description = "The open state.")
    public boolean isOpen() {
        return open;
    }

    /**
     * Sets the open state.
     *
     * @param open The open state.
     */
    @PropertySetter(value = "open", defaultValue = "false", description = "The open state.")
    public void setOpen(boolean open) {
        if (open != this.open) {
            invoke((this.open = open) ? "open" : "close");
        }
    }

    /**
     * Handles open and close events from the client.
     *
     * @param event An open or close event.
     */
    @EventHandler(value = { "open", "close" }, syncToClient = false)
    private void onOpenOrClose(Event event) {
        open = event instanceof OpenEvent;
    }

    @Override
    public IModelAndView<BaseMenuComponent, ?> getModelAndView() {
        return modelAndView;
    }
    
}
