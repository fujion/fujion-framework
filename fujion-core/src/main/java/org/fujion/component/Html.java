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

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A component that allows embedding native HTML within a page.
 */
@Component(tag = "html", widgetClass = "Html", content = ContentHandling.AS_ATTRIBUTE, parentTag = "*", description = "A component that allows embedding native HTML within a page.")
public class Html extends BaseUIComponent {
    
    private String src;

    public Html() {
        super();
    }
    
    public Html(String content) {
        super();
        setContent(content);
    }
    
    /**
     * Sets the HTML content.
     *
     * @see org.fujion.component.BaseComponent#setContent(java.lang.String)
     */
    @PropertySetter(value = "content", description = "The text content associated with this component.")
    @Override
    public void setContent(String content) {
        content = nullify(content);

        if (content != null) {
            setSrc(null);
        }

        super.setContent(content);
    }
    
    /**
     * Returns the URL of external HTML content.
     *
     * @return URL of external HTML content.
     */
    @PropertyGetter(value = "src", description = "The URL of external HTML content.")
    public String getSrc() {
        return src;
    }

    /**
     * Sets the URL of external HTML content.
     *
     * @param src URL of external HTML content.
     */
    @PropertySetter(value = "src", description = "The URL of external HTML content.")
    public void setSrc(String src) {
        src = nullify(src);

        if (src != null) {
            super.setContent(null);
        }
        
        propertyChange("src", this.src, this.src = src, isContentSynced());
    }
    
}
