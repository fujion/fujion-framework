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
package org.fujion.testharness;

import org.fujion.ancillary.Badge;
import org.fujion.annotation.EventHandler;
import org.fujion.annotation.WiredComponent;
import org.fujion.component.BaseComponent;
import org.fujion.component.Tab;

/**
 * Demonstration of tab view control.
 */
public class TabsController extends BaseController {

    @WiredComponent
    private Tab tabNoClose;

    @WiredComponent
    private Tab tabWithBadge;

    private Badge badge;

    @Override
    public void afterInitialized(BaseComponent root) {
        super.afterInitialized(root);
        tabNoClose.setOnCanClose(this::canCloseTab);
        badge = new Badge(tabWithBadge);
    }

    /**
     * Prevent closure of a tab.
     *
     * @return Always false.
     */
    public boolean canCloseTab() {
        log("Preventing tab from closing...");
        return false;
    }

    @EventHandler(value = "click", target = "btnIncBadge")
    private void incBadgeHandler() {
        badge.incCount(1);
    }

    @EventHandler(value = "click", target = "btnDecBadge")
    private void decBadgeHandler() {
        badge.incCount(-1);
    }

    @EventHandler(value = "click", target = "btnClrBadge")
    private void clickHandler() {
        badge.setCount(0);
    }

}
