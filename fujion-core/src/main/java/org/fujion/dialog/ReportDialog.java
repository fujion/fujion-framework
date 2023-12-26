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
package org.fujion.dialog;

import org.fujion.ancillary.IAutoWired;
import org.fujion.ancillary.PrintOptions;
import org.fujion.annotation.EventHandler;
import org.fujion.annotation.WiredComponent;
import org.fujion.component.*;
import org.fujion.event.IEventListener;

import java.util.HashMap;
import java.util.Map;

import static org.fujion.dialog.DialogConstants.RESOURCE_PREFIX;

/**
 * A simple dialog for displaying text information modally or amodally.
 */
public class ReportDialog implements IAutoWired {

    private Window window;

    @WiredComponent
    private Cell cmpText;

    @WiredComponent
    private Html cmpHtml;

    @WiredComponent
    private Button btnPrint;

    @WiredComponent
    private BaseUIComponent printRoot;

    /**
     * Displays the dialog.
     *
     * @param text       The text or HTML content. HTML content is indicated by prefixing with the html
     *                   tag.
     * @param title      Dialog title.
     * @param allowPrint If true, a print button is provided.
     * @param asModal    If true, open as modal; otherwise, as popup.
     * @param callback   Callback when dialog is closed.
     * @return The created dialog.
     */
    public static Window show(
            String text,
            String title,
            boolean allowPrint,
            boolean asModal,
            IEventListener callback) {
        Map<String, Object> args = new HashMap<>();
        args.put("text", text);
        args.put("title", title);
        args.put("allowPrint", allowPrint);
        Window dialog = org.fujion.dialog.PopupDialog.show(RESOURCE_PREFIX + "reportDialog.fsp", args, true, true, false,
                null);

        if (asModal) {
            dialog.modal(callback);
        } else {
            dialog.popup(callback);
        }

        return dialog;
    }

    @Override
    public void afterInitialized(BaseComponent root) {
        window = (Window) root;
        window.setTitle(root.getAttribute("title", ""));
        btnPrint.setVisible(root.getAttribute("allowPrint", false));
        String text = root.getAttribute("text", "");

        if (text.startsWith("<html>")) {
            cmpHtml.setContent(text);
        } else {
            cmpText.setLabel(text);
        }
    }

    @EventHandler(value = "click", target = "btnClose")
    @SuppressWarnings("unused")
    private void _btnClose$click() {
        window.close();
    }

    @EventHandler(value = "click", target = "@btnPrint")
    @SuppressWarnings("unused")
    private void _btnPrint$click() {
        PrintOptions options = new PrintOptions();
        options.title = window.getTitle();
        printRoot.print(options);
    }

}
