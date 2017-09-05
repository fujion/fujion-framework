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

/**
 * A component representing a drop down menu.
 */
@Component(tag = "menu", widgetClass = "Menu", parentTag = "*", childTag = { @ChildTag("menuitem"),
        @ChildTag("menuheader"), @ChildTag("menuseparator") })
public class Menu extends BaseMenuComponent {
    
    private boolean open;
    
    public void open() {
        setOpen(true);
    }
    
    public void close() {
        setOpen(false);
    }
    
    @PropertyGetter("open")
    public boolean isOpen() {
        return open;
    }
    
    @PropertySetter("open")
    public void setOpen(boolean open) {
        if (open != this.open) {
            invoke((this.open = open) ? "open" : "close");
        }
    }
    
    @EventHandler(value = { "open", "close" }, syncToClient = false)
    private void onOpenOrClose(Event event) {
        open = event instanceof OpenEvent;
    }
    
    @EventHandler("open")
    private void _onOpen() {
        this.open = true;
    }
    
    @EventHandler("close")
    private void _onClose() {
        this.open = false;
    }
}
