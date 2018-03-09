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
package org.fujion.component;

import org.fujion.ancillary.MimeContent;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.event.LoadEvent;

/**
 * An iframe component.
 */
@Component(tag = "iframe", widgetClass = "Iframe", parentTag = "*", description = "An iframe component.")
public class Iframe extends BaseUIComponent {
    
    private String src;
    
    private String sandbox;
    
    /**
     * Returns the URL of the loaded document.
     *
     * @return URL of the loaded document.
     */
    @PropertyGetter(value = "src", description = "The URL of the loaded document.")
    public String getSrc() {
        return src;
    }
    
    /**
     * Sets the URL of the document to be loaded.
     *
     * @param src URL of the document to be loaded.
     */
    @PropertySetter(value = "src", description = "The URL of the loaded document.")
    public void setSrc(String src) {
        _setSrc(src, true);
    }
    
    /**
     * Sets the URL of the document to be loaded, optionally notifying the client.
     *
     * @param src URL of the document to be loaded.
     * @param notifyClient If true, notify the client.
     */
    private void _setSrc(String src, boolean notifyClient) {
        propertyChange("src", this.src, this.src = nullify(src), notifyClient);
    }

    /**
     * Directly sets the iframe content.
     *
     * @param content The iframe content.
     */
    public void setContent(MimeContent content) {
        setSrc(content == null ? null : content.getSrc());
    }
    
    /**
     * Directly sets the iframe content.
     *
     * @param content The iframe content as HTML.
     */
    @Override
    public void setContent(String content) {
        setContent(content == null ? null : new MimeContent("text/html", content.getBytes()));
    }
    
    /**
     * Returns the sandbox setting for the iframe.
     *
     * @return The sandbox setting for the iframe.
     * @see <a href="https://www.w3schools.com/tags/att_iframe_sandbox.asp">HTML iframe sandbox
     *      Attribute</a>
     */
    @PropertyGetter(value = "sandbox", description = "The sandbox setting for the iframe.")
    public String getSandbox() {
        return sandbox;
    }
    
    /**
     * Sets the sandbox setting for the iframe.
     *
     * @param sandbox The sandbox setting for the iframe.
     * @see <a href="https://www.w3schools.com/tags/att_iframe_sandbox.asp">HTML iframe sandbox
     *      Attribute</a>
     */
    @PropertySetter(value = "sandbox", description = "The sandbox setting for the iframe.")
    public void setSandbox(String sandbox) {
        propertyChange("sandbox", this.sandbox, this.sandbox = sandbox, true);
    }
    
    /**
     * Handles a load event from the client.
     *
     * @param event A load event.
     */
    @EventHandler(value = "load", syncToClient = false)
    private void _onLoad(LoadEvent event) {
        String src = nullify(event.getSrc());
        
        if (src != null) {
            _setSrc(src, false);
        }
    }
}
