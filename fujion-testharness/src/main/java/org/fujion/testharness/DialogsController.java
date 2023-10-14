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
import org.fujion.client.ClientUtil;
import org.fujion.component.BaseComponent;
import org.fujion.dialog.DialogUtil;

/**
 * Model binding demonstration
 */
public class DialogsController extends BaseController {

    @Override
    public void afterInitialized(BaseComponent comp) {
        super.afterInitialized(comp);
    }

    @EventHandler(value = "click", target = "btnInputDialog")
    private void btnInputDialogHandler() {
        DialogUtil.input("Enter something here: ", "Input Dialog", null);
    }

    @EventHandler(value = "click", target = "btnConfirmDialog")
    private void btnConfirmDialogHandler() {
        DialogUtil.confirm("Confirmation Dialog", null);
    }

    @EventHandler(value = "click", target = "btnShowInfoDialog")
    private void btnInfoDialogHandler() {
        DialogUtil.showInfo("Informational Dialog");
    }

    @EventHandler(value = "click", target = "btnShowWarningDialog")
    private void btnWarningDialogHandler() {
        DialogUtil.showWarning("Warning Dialog");
    }

    @EventHandler(value = "click", target = "btnShowErrorDialog")
    private void btnErrorDialogHandler() {
        DialogUtil.showError("Error Dialog");
    }

    @EventHandler(value = "click", target = "btnPopupDialog")
    private void btnPopupDialogHandler() {
        DialogUtil.popup("pages/@intro.fsp");
    }

    @EventHandler(value = "click", target = "btnReportDialog")
    private void btnReportDialogHandler() {
        DialogUtil.showReport("This is a report dialog.", "Report Dialog", true);
    }

    @EventHandler(value = "click", target = "btnErrorMessage")
    private void btnErrorMessageHandler() {
        ClientUtil.error("This is an error message.");
    }

    @EventHandler(value = "click", target = "btnWarnMessage")
    private void btnWarnMessageHandler() {
        ClientUtil.warn("This is warning message.");
    }

    @EventHandler(value = "click", target = "btnInfoMessage")
    private void btnInfoMessageHandler() {
        ClientUtil.info("This is an informational message.", "Information");
    }

}
