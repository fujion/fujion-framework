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

import org.fujion.annotation.EventHandler;
import org.fujion.annotation.OnFailure;
import org.fujion.annotation.WiredComponent;
import org.fujion.client.ClientUtil;
import org.fujion.component.*;
import org.fujion.component.Window.Mode;
import org.fujion.event.ResizeEvent;

/**
 * Demonstrates various window modes.
 */
public class WindowsController extends BaseController {
    
    @WiredComponent(value = "^/messagewindow", onFailure = OnFailure.LOG)
    private MessageWindow messagewindow;
    
    @WiredComponent("window1/window_div")
    private Div windowdiv1;
    
    @WiredComponent("window2/window_div")
    private Div windowdiv2;
    
    @WiredComponent("window3")
    private Window window3;
    
    @WiredComponent("window3/rgMode")
    private Radiogroup rgMode;

    @WiredComponent
    private Button btnMessage;
    
    private int messageClass = -1;
    
    @Override
    public void afterInitialized(BaseComponent root) {
        super.afterInitialized(root);
        btnMessage.setVisible(messagewindow != null);
        log(windowdiv1 == null, "Component window1/window_div was NOT autowired.",
                "Component window1/window_div was autowired.");
        log(windowdiv2 == null, "Component window2/window_div was NOT autowired.",
                "Component window2/window_div was autowired.");
        log(windowdiv1 == windowdiv2, "window1/window_div and window2/window_div should not be the same.", null);
    }
    
    @EventHandler(value = "change", target = "window3/rgMode")
    private void rgModeHandler() {
        Mode newMode = Mode.valueOf(rgMode.getSelected().getLabel().toUpperCase());
        window3.setMode(newMode);
    }
    
    @EventHandler(value = "click", target = "btnAlert")
    private void btnAlertHandler() {
        ClientUtil.invoke("fujion.alert", "This is a test alert", "TEST!", "danger");
    }
    
    private static final String[] MSG_CLASS = { "success", "warning", "danger", "info" };
    
    @EventHandler(value = "click", target = "@btnMessage")
    private void btnMessageHandler() {
        MessagePane pane = new MessagePane("Message Title", "category", 8000, false);
        messageClass++;
        messageClass = messageClass >= MSG_CLASS.length ? 0 : messageClass;
        pane.addClass("flavor: alert-" + MSG_CLASS[messageClass]);
        pane.addChild(new Label("This is a test " + MSG_CLASS[messageClass] + " message"));
        messagewindow.addChild(pane);
    }
    
    @EventHandler(value = "resize", target = "window3")
    private void resizeHandler(ResizeEvent event) {
        log("Resize event!!!");
    }
    
}
