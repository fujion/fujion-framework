/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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

import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * Base class for non-UI components that allow content to be expressed inline or imported from an
 * external source.
 */
public abstract class BaseSourcedComponent extends BaseComponent {

    private String src;

    protected BaseSourcedComponent(boolean contentSynced) {
        this(null, contentSynced);
    }

    protected BaseSourcedComponent(String content, boolean contentSynced) {
        setContentSynced(contentSynced);
        setContent(content);
    }

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
     * Returns the URL of the external content source.
     *
     * @return URL of the external content source.
     */
    @PropertyGetter(value = "src", description = "The URL of the external content source.")
    public String getSrc() {
        return src;
    }

    /**
     * Sets the URL of the external content source.
     *
     * @param src URL of the external content source.
     */
    @PropertySetter(value = "src", description = "The URL of the external content source.")
    public void setSrc(String src) {
        src = nullify(src);

        if (src != null) {
            super.setContent(null);
        }

        propertyChange("src", this.src, this.src = src, isContentSynced());
    }
    
}
