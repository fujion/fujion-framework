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
package org.fujion.testharness;

import org.apache.commons.lang3.math.NumberUtils;
import org.fujion.ancillary.IAutoWired;
import org.fujion.annotation.EventHandler;
import org.fujion.annotation.WiredComponent;
import org.fujion.client.ClientUtil;
import org.fujion.component.*;
import org.fujion.event.ChangeEvent;
import org.fujion.event.Event;
import org.fujion.websocket.ISessionListener;

/**
 * Controller for main page.
 */
public class MainController implements IAutoWired {

    @WiredComponent
    private Tabview tabview;

    @Override
    public void afterInitialized(BaseComponent root) {
        Page page = root.getPage();
        int tabIndex = NumberUtils.toInt(page.getQueryParam("tab"));
        tabIndex = tabIndex < 0 ? tabview.getChildCount() + tabIndex : tabIndex;
        tabview.setSelectedTab((Tab) tabview.getChildAt(tabIndex));
        page.setAttribute("mainController", this);
        page.getSession().addSessionListener(new ISessionListener() {
            @Override
            public void onDestroy() {
                ClientUtil.warn("Your session has timed out.");
            }
        });
    }

    /*********************** Status Log ***********************/

    @WiredComponent
    private Memobox statusLog;

    private int logCount;

    @EventHandler(value = "click", target = "btnClearLog")
    public void btnClearLogHandler() {
        statusLog.clear();
        logCount = 0;
    }

    @EventHandler(value = "change", target = "chkScrollLock")
    public void chkScrollLockHandler(ChangeEvent event) {
        statusLog.setAutoScroll(((Checkbox) event.getTarget()).isChecked());
    }

    /**
     * Handler for custom log events sent from client.
     *
     * @param event The log event.
     */
    @EventHandler(value = "log", target = "^/page")
    private void onInfo(Event event) {
        log((String) event.getData());
    }

    public void log(String message) {
        if (message != null && !message.isEmpty()) {
            String value = statusLog.getValue();
            statusLog.setValue((value == null ? "" : value) + ++logCount + ". " + message + "\n\n");
        }
    }

}
