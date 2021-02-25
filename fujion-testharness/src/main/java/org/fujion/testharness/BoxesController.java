/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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
import org.fujion.component.*;
import org.fujion.event.Event;
import org.fujion.event.KeycaptureEvent;
import org.fujion.model.IComponentRenderer;
import org.fujion.model.ListModel;

/**
 * Input boxes demonstration
 */
public class BoxesController extends BaseController {
    
    @WiredComponent
    private Textbox txtSelect;
    
    @WiredComponent
    private Textbox txtInput;
    
    @WiredComponent
    private Listbox lboxRender;
    
    @WiredComponent
    private Combobox cboxRender;
    
    @WiredComponent
    private Popupbox popupbox1;
    
    @WiredComponent
    private Popupbox popupbox2;
    
    @Override
    public void afterInitialized(BaseComponent root) {
        super.afterInitialized(root);
        ListModel<String> model = new ListModel<>();
        
        for (int i = 1; i < 6; i++) {
            model.add("Rendered item #" + i);
        }
        
        lboxRender.setModel(model);
        cboxRender.setModel(model);
        lboxRender.setRenderer((IComponentRenderer<Listitem, String>) Listitem::new);
        cboxRender.setRenderer((IComponentRenderer<Comboitem, String>) Comboitem::new);
    }
    
    @EventHandler(value = "change", target = "tabInputBoxes", onFailure = OnFailure.LOG)
    private void InputBoxTabChangeHandler() {
        txtInput.setValue("Value set programmatically");
        txtSelect.selectRange(2, 5);
        txtSelect.focus();
    }
    
    @EventHandler(value = "close", target = "popupboxpopup1")
    private void popupbox1CloseHandler(Event event) {
        popupbox1.setValue("Drop down closed!");
    }
    
    @EventHandler(value = "open", target = "popupboxpopup1")
    private void popupbox1OpenHandler(Event event) {
        popupbox1.setValue("Drop down opened!");
    }
    
    @EventHandler(value = "close", target = "popupboxpopup2")
    private void popupbox2CloseHandler(Event event) {
        popupbox2.setValue("Drop down closed!");
    }
    
    @EventHandler(value = "open", target = "popupboxpopup2")
    private void popupbox2OpenHandler(Event event) {
        popupbox2.setValue("Drop down opened!");
    }
    
    @EventHandler(value = "keycapture", target = "memobox")
    private void memoboxKeyPressHandler(KeycaptureEvent event) {
        log("Captured keypress: " + event.getKeycapture());
    }
    
}
