/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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

/**
 * A component supporting a tab-based view.
 */
@Component(tag = "tabview", widgetModule = "fujion-tabview", widgetClass = "Tabview", parentTag = "*", childTag = @ChildTag("tab"), description = "A component supporting a tab-based view.")
public class Tabview extends BaseUIComponent implements ISupportsModel<Tab> {

    /**
     * Placement of tabs in a tab view. Default is top.
     */
    public enum TabPosition {
        /**
         * Position tabs at the top of the tab view.
         */
        TOP,
        /**
         * Position tabs at the bottom of the tab view.
         */
        BOTTOM,
        /**
         * Position tabs on the left side of the tab view.
         */
        LEFT,
        /**
         * Position tabs on the right side of the tab view.
         */
        RIGHT
    }

    private final ModelAndView<Tab, Object> modelAndView = new ModelAndView<>(this);

    private Tab selectedTab;

    private TabPosition tabPosition = TabPosition.TOP;

    /**
     * Returns the currently selected tab, if any.
     *
     * @return The currently selected tab (may be null).
     */
    public Tab getSelectedTab() {
        return selectedTab;
    }

    /**
     * Sets the currently selected tab.
     *
     * @param selectedTab The tab to select (may be null).
     */
    public void setSelectedTab(Tab selectedTab) {
        validateIsChild(selectedTab);

        if (this.selectedTab != null) {
            this.selectedTab._setSelected(false, false);
        }

        this.selectedTab = selectedTab;

        if (selectedTab != null) {
            selectedTab._setSelected(true, false);
        }
    }

    /**
     * If the added tab is marked as selected, update the selected tab.
     *
     * @see org.fujion.component.BaseUIComponent#afterAddChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterAddChild(BaseComponent child) {
        if (((Tab) child).isSelected()) {
            setSelectedTab((Tab) child);
        }
    }

    /**
     * If the removed tab is selected, clear the selection.
     *
     * @see org.fujion.component.BaseUIComponent#afterRemoveChild(org.fujion.component.BaseComponent)
     */
    @Override
    protected void afterRemoveChild(BaseComponent child) {
        if (child == selectedTab) {
            selectedTab = null;
        }
    }

    /**
     * Returns the tab {@link TabPosition position}.
     *
     * @return The tab {@link TabPosition position}.
     */
    @PropertyGetter(value = "tabPosition", description = "The position of the tabs.")
    public TabPosition getTabPosition() {
        return tabPosition;
    }

    /**
     * Sets the tab {@link TabPosition position}.
     *
     * @param tabPosition The tab {@link TabPosition position}.
     */
    @PropertySetter(value = "tabPosition", defaultValue = "top", description = "The position of the tabs.")
    public void setTabPosition(TabPosition tabPosition) {
        propertyChange("tabPosition", this.tabPosition, this.tabPosition = defaultify(tabPosition, TabPosition.TOP), true);
    }

    @Override
    public IModelAndView<Tab, ?> getModelAndView() {
        return modelAndView;
    }

}
