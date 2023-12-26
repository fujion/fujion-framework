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

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fujion.ancillary.IAutoWired;
import org.fujion.annotation.EventHandler;
import org.fujion.annotation.WiredComponent;
import org.fujion.client.ExecutionContext;
import org.fujion.component.*;
import org.fujion.page.PageUtil;

import java.util.Map;

/**
 * Implements a simple dialog for prompting for user input.
 */
public class InputDialog implements IAutoWired {

    public interface IInputCallback {

        void onComplete(String value);

    }

    protected static final Log log = LogFactory.getLog(InputDialog.class);

    /******************** Controller *******************/

    @WiredComponent
    private Textbox textbox;

    @WiredComponent
    private Cell prompt;

    @WiredComponent
    private Button btnOK;

    private Window root;

    /**
     * Prompt user for input.
     *
     * @param args     The argument map.
     * @param callback The callback to receive the text input. If the dialog was cancelled, the text
     *                 input will be returned as null.
     */
    public static void show(
            Map<String, Object> args,
            IInputCallback callback) {
        Window dialog = (Window) PageUtil
                .createPage(org.fujion.dialog.DialogConstants.RESOURCE_PREFIX + "inputDialog.fsp", ExecutionContext.getPage(), args).get(0);

        dialog.modal(callback == null ? null : event ->
                callback.onComplete(dialog.getAttribute("result", String.class))
        );
    }

    @Override
    public void afterInitialized(BaseComponent comp) {
        this.root = (Window) comp;
        root.setAttribute("controller", this);
        root.setTitle(root.getAttribute("title", ""));
        root.addClass("flavor:" + root.getAttribute("panelClass", "alert-primary"));
        prompt.setLabel(root.getAttribute("prompt", ""));
        textbox.setValue(root.getAttribute("oldValue", null));
        textbox.selectAll();
        updateState();
    }

    private void updateState() {
        btnOK.setDisabled(ObjectUtils.isEmpty(textbox.getValue()));
    }

    @EventHandler(value = "change", target = "@textbox")
    @SuppressWarnings("unused")
    private void _onChange() {
        updateState();
    }

    @EventHandler(value = "enter", target = "@textbox")
    @EventHandler(value = "click", target = "@btnOK")
    @SuppressWarnings("unused")
    private void _onCommit() {
        root.setAttribute("result", textbox.getValue());
        root.close();
    }

    @EventHandler(value = "click", target = "btnCancel")
    @SuppressWarnings("unused")
    private void _onCancel() {
        root.setAttribute("result", null);
        root.close();
    }

}
