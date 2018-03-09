/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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
package org.fujion.testharness;

import org.fujion.annotation.EventHandler;
import org.fujion.annotation.WiredComponent;
import org.fujion.component.Menu;
import org.fujion.component.Menuitem;
import org.fujion.event.ClickEvent;

/**
 * Menus Demonstration
 */
public class MenusController extends BaseController {
    
    @WiredComponent
    private Menu mainMenu;
    
    @EventHandler(value = "click", target = { "menu1", "menu2", "menu3", "menu5_1", "menu5_2" })
    private void menuClickHandler(ClickEvent event) {
        log(event.getTarget().getName() + " clicked.");
    }
    
    @EventHandler(value = "click", target = "menu2")
    private void menuClickHandler2(ClickEvent event) {
        Menuitem item = (Menuitem) event.getTarget();
        item.setChecked(!item.isChecked());
    }
    
    @EventHandler(value = "click", target = "btnToggleMenu")
    private void btnToggleMenuHandler() {
        mainMenu.setOpen(!mainMenu.isOpen());
    }
    
}
