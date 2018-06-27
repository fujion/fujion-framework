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

import org.fujion.annotation.EventHandler;
import org.fujion.annotation.OnFailure;
import org.fujion.annotation.WiredComponent;
import org.fujion.client.ClientUtil;
import org.fujion.component.BaseComponent;
import org.fujion.component.BaseLabeledComponent.LabelPositionAll;
import org.fujion.component.Button;
import org.fujion.component.Caption;
import org.fujion.component.Caption.LabelAlignment;
import org.fujion.component.Checkbox;
import org.fujion.component.Detail;
import org.fujion.component.Div;
import org.fujion.component.Popup;
import org.fujion.component.Radiogroup;
import org.fujion.component.Tab;
import org.fujion.event.ChangeEvent;
import org.fujion.event.Event;
import org.fujion.event.EventUtil;
import org.fujion.page.PageUtil;
import org.fujion.servlet.DynamicResourceRegistry;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

/**
 * Demonstration of miscellaneous capabilities.
 */
public class MiscController extends BaseController {

    private static final String DYNAMIC_CONTENT_1 = "<button label='Dynamic Content' class='flavor:btn-danger'/>";
    
    private static final String DYNAMIC_CONTENT_2 = "<button label='Dynamic Resource' class='flavor:btn-success'/>";
    
    static {
        DynamicResourceRegistry drr = DynamicResourceRegistry.getInstance();
        Resource resource = new ByteArrayResource(DYNAMIC_CONTENT_2.getBytes());
        drr.registerResource("dynamic_resource.fsp", resource);
    }
    
    @WiredComponent(onFailure = OnFailure.IGNORE)
    private Div nomatch;

    @WiredComponent
    private BaseComponent dynamicContent;

    @WiredComponent
    private Popup contextMenu;

    @Override
    public void afterInitialized(BaseComponent root) {
        super.afterInitialized(root);
        log(nomatch == null, "Component 'nomatch' was correctly not wired.", "Component 'nomatch' as erroneously wired.");
        PageUtil.createPageFromContent(DYNAMIC_CONTENT_1, dynamicContent);
    }

    /**
     * Controls whether or not application closure is challenged.
     *
     * @param event The checkbox change event.
     */
    @EventHandler(value = "change", target = "chkPreventClosure")
    public void chkPreventClosureHandler(ChangeEvent event) {
        page.setClosable(!((Checkbox) event.getTarget()).isChecked());
    }

    @EventHandler(value = "click", target = "btnSaveAsFile")
    public void btnSaveAsFileHandler() {
        ClientUtil.saveToFile("This is test content", "text/plain", "testFile.txt");
    }

    @EventHandler(value = "click", target = "btnPrint")
    public void btnPrintHandler() {
        this.root.getAncestor(Tab.class).print();
    }

    @EventHandler(value = "click", target = "btnTestCallback")
    public void btnTestCallbackHandler() {
        log("You should see \"The document url is:...\"");
        
        ClientUtil.invoke("window.location.href", (response) -> {
            log("The document url is: " + response);
        });
    }

    @WiredComponent
    private Div divMaskTest;

    private boolean masked;

    @EventHandler(value = "click", target = "btnMaskTest")
    private void btnMaskTestClickHandler() {
        if (masked = !masked) {
            divMaskTest.addMask("Mask Test", contextMenu);
        } else {
            divMaskTest.removeMask();
        }
    }

    @WiredComponent
    private Button btnToggleBalloon;

    @EventHandler(value = "click", target = "@btnToggleBalloon")
    private void btnToggleBalloonClickHandler() {
        if (btnToggleBalloon.getBalloon() == null) {
            btnToggleBalloon.setBalloon("Balloon Text");
        } else {
            btnToggleBalloon.setBalloon(null);
        }
    }

    @WiredComponent
    private Caption caption;
    
    @WiredComponent
    private Radiogroup rgPosition;
    
    @EventHandler(value = "change", target = "@rgPosition")
    private void positionChangeHandler() {
        String value = rgPosition.getSelected().getLabel();
        LabelPositionAll position = LabelPositionAll.valueOf(value.toUpperCase());
        caption.setPosition(position);
    }
    
    @WiredComponent
    private Radiogroup rgAlignment;

    @EventHandler(value = "change", target = "@rgAlignment")
    private void alignmentChangeHandler() {
        String value = rgAlignment.getSelected().getLabel();
        LabelAlignment alignment = LabelAlignment.valueOf(value.toUpperCase());
        caption.setAlignment(alignment);
    }

    @WiredComponent
    private Detail detail;
    
    @EventHandler(value = "click", target = "btnToggleDetail")
    private void toggleDetailHandler(Event event) {
        detail.setOpen(!detail.isOpen());
    }
    
    @WiredComponent
    private BaseComponent eventTestParent;
    
    @EventHandler(value = "click", target = "btnEventTest")
    private void eventTestHandler() {
        String message = " event test successful.";
        Event event;
        
        for (Checkbox chk : eventTestParent.getChildren(Checkbox.class)) {
            if (chk.isChecked()) {
                switch ((Integer) chk.getData()) {
                    case 0: // Send
                        event = new Event("log", root, "Send" + message);
                        EventUtil.send(event);
                        break;

                    case 1: // Post
                        event = new Event("log", root, "Post" + message);
                        EventUtil.post(event);
                        break;

                    case 2: // Echo
                        event = new Event("log", root, "Echo" + message);
                        EventUtil.echo(event);
                        break;
                }
            }
        }
    }
}
