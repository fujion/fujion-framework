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

/**
 * A component representing a single menu item.
 */
@Component(
        tag = "menuitem",
        widgetClass = "Menuitem",
        parentTag = { "menu", "menupopup", "menuitem" },
        childTag = { @ChildTag("menuitem"), @ChildTag("menuheader"), @ChildTag("menuseparator") },
        description = "A single menu item.")
public class Menuitem extends BaseMenuComponent {

    private boolean checkable;

    private boolean checked;

    /**
     * Returns true if the menu item has an associated check box.
     *
     * @return True if the menu item has an associated check box.
     */
    @PropertyGetter(value = "checkable", description = "True if the menu item has an associated check box.")
    public boolean isCheckable() {
        return checkable;
    }

    /**
     * Set to true to associate a check box with the menu item.
     *
     * @param checkable True to associate a check box with the menu item.
     */
    @PropertySetter(value = "checkable", defaultValue = "false", description = "True if the menu item has an associated check box.")
    public void setCheckable(boolean checkable) {
        propertyChange("checkable", this.checkable, this.checkable = checkable, true);
    }

    /**
     * Returns the checked state of the menu item. If the checkable property is set to true, this
     * state will be reflected in the associated check box.
     *
     * @return The checked state of the menu item.
     */
    @PropertyGetter(value = "checked", description = "The checked state of the menu item.")
    public boolean isChecked() {
        return checked;
    }

    /**
     * Sets the checked state of the menu item. If the checkable property is set to true, this state
     * will be reflected in the associated check box.
     *
     * @param checked The checked state of the menu item.
     */
    @PropertySetter(value = "checked", defaultValue = "false", description = "The checked state of the menu item.")
    public void setChecked(boolean checked) {
        propertyChange("checked", this.checked, this.checked = checked, true);
    }

}
