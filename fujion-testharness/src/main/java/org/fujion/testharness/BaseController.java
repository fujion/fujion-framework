/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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

import org.fujion.ancillary.IAutoWired;
import org.fujion.annotation.EventHandler;
import org.fujion.component.BaseComponent;
import org.fujion.component.Page;
import org.fujion.event.Event;

/**
 * Base controller for all demo controllers. Provides convenience methods for accessing logging
 * capability on the main controller.
 */
public class BaseController implements IAutoWired {
    
    protected Page page;

    protected BaseComponent root;
    
    /**
     * Generates a log entry as each controller is initialized.
     */
    @Override
    public void afterInitialized(BaseComponent root) {
        page = root.getPage();
        log(getClass().getName() + " initialized.");
    }

    @Override
    public void beforeInitialized(BaseComponent root) {
        this.root = root;
    }
    
    /**
     * Log the message to the UI.
     *
     * @param message Message to log.
     */
    public void log(String message) {
        MainController mainController = (MainController) page.getAttribute("mainController");
        
        if (mainController != null) {
            mainController.log(message);
        }
    }
    
    /**
     * Logs one of two messages, depending on the condition.
     *
     * @param condition Determines which message to log.
     * @param messageIfTrue Message to log if condition is true.
     * @param messageIfFalse Message to log if condition is false.
     */
    public void log(boolean condition, String messageIfTrue, String messageIfFalse) {
        log(condition ? messageIfTrue : messageIfFalse);
    }
    
    @EventHandler("log")
    private void onLog(Event event) {
        Object data = event.getData();
        
        if (data != null) {
            log(data.toString());
        }
    }
}
