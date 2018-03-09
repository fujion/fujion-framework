/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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

import java.io.FileOutputStream;

import org.apache.commons.io.IOUtils;
import org.fujion.annotation.EventHandler;
import org.fujion.annotation.WiredComponent;
import org.fujion.component.Button;
import org.fujion.component.Checkbox;
import org.fujion.component.Radiobutton;
import org.fujion.component.Upload;
import org.fujion.event.ChangeEvent;
import org.fujion.event.ClickEvent;
import org.fujion.event.UploadEvent;

/**
 * Buttons demonstration.
 */
public class ButtonsController extends BaseController {
    
    @WiredComponent
    private Button btnWithEvent;
    
    @WiredComponent
    private Checkbox chkMultiple;
    
    @WiredComponent
    private Upload upload;
    
    /**
     * Sample button event handler.
     */
    @EventHandler(value = "click", target = "@btnWithEvent")
    private void btnEventHandler() {
        log("Button event handler was invoked");
    }
    
    /**
     * This gets invoked from an onEvent style attribute.
     *
     * @param event The click event.
     */
    public void btnEventHandler(ClickEvent event) {
        log("Attribute bound event handler was invoked");
    }
    
    @EventHandler(value = "change", target = { "rg1", "rg2" })
    private void radiobuttonChangeHandler(ChangeEvent event) {
        Radiobutton rb = event.getValue(Radiobutton.class);
        log("Radiobutton '" + (rb.getLabel() + "' was " + (rb.isChecked() ? "selected." : "deselected.")));
    }
    
    @EventHandler(value = "upload", target = "@upload")
    private void uploadHandler(UploadEvent event) throws Exception {
        String file = event.getFile();
        
        switch (event.getState()) {
            case DONE:
                String tmpdir = System.getProperty("java.io.tmpdir");
                file = tmpdir + file;
                FileOutputStream out = new FileOutputStream(file);
                IOUtils.copy(event.getBlob(), out);
                out.close();
                log("Uploaded contents to " + file);
                break;
            
            case MAXSIZE:
                log("File too large: " + file);
                break;
            
            case ABORTED:
                log("Upload aborted for " + file);
                break;
            
            case LOADING:
                double pct = event.getLoaded() * 100.0 / event.getTotal();
                log("Upload " + pct + "% completed for " + file);
                break;
        }
    }
    
    @EventHandler(value = "change", target = "@chkMultiple")
    private void chkMultipleChangeHandler(ChangeEvent event) {
        upload.setMultiple(chkMultiple.isChecked());
    }
}
