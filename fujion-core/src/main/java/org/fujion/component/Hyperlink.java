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
package org.fujion.component;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A simple hyperlink (anchor) component.
 */
@Component(
        tag = "link",
        widgetClass = "Hyperlink",
        parentTag = "*",
        description = "A simple hyperlink (anchor) component.")
public class Hyperlink extends Button {
    
    private String href;
    
    private String target;
    
    public Hyperlink() {
        this(null);
    }
    
    public Hyperlink(String label) {
        super(label, "flavor:btn-link size:btn-sm");
    }
    
    /**
     * Returns the URL of the link destination.
     *
     * @return URL of the link destination.
     */
    @PropertyGetter(value = "href", description = "The URL of the link destination.")
    public String getHref() {
        return href;
    }
    
    /**
     * Sets the URL of the link destination.
     *
     * @param href URL of the link destination.
     */
    @PropertySetter(value = "href", description = "The URL of the link destination.")
    public void setHref(String href) {
        propertyChange("href", this.href, this.href = nullify(href), true);
    }
    
    /**
     * Returns the target where the linked document will be opened.
     *
     * @return The target where the linked document will be opened.
     */
    @PropertyGetter(value = "target", description = "The target where the linked document will be opened.")
    public String getTarget() {
        return target;
    }
    
    /**
     * Sets the target where the linked document will be opened.
     *
     * @param target The target where the linked document will be opened.
     */
    @PropertySetter(value = "target", description = "The target where the linked document will be opened.")
    public void setTarget(String target) {
        propertyChange("target", this.target, this.target = nullify(target), true);
    }
    
}
