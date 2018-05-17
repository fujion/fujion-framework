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

import org.fujion.ancillary.ISnippet;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A component representing a Fujion resource that can be inserted into a template.
 *
 * @see Template
 */
@Component(tag = "snippet", widgetClass = "MetaWidget", parentTag = "template", description = "A Fujion resource that can be inserted into a template.")
public class Snippet extends BaseComponent implements ISnippet {

    private String src;

    private String anchor;

    private SnippetPosition position = SnippetPosition.LAST;

    public Snippet() {
    }

    @PropertyGetter(value = "src", bindable = false, description = "The URL of the source FSP for this snippet.")
    @Override
    public String getSnippetSource() {
        return src;
    }
    
    /**
     * Sets the URL of the source FSP for this snippet.
     *
     * @param src The URL of the source FSP for this snippet.
     */
    @PropertySetter(value = "src", bindable = false, description = "The URL of the source FSP for this snippet.")
    private void setSnippetSource(String src) {
        this.src = trimify(src);
    }

    @PropertyGetter(value = "anchor", bindable = false, description = "The name of the anchor component within the template.")
    @Override
    public String getSnippetAnchor() {
        return anchor;
    }
    
    /**
     * Sets the name of the anchor component within the template.
     *
     * @param anchor The name of the anchor component within the template.
     */
    @PropertySetter(value = "anchor", bindable = false, description = "The name of the anchor component within the template.")
    private void setSnippetAnchor(String anchor) {
        this.anchor = trimify(anchor);
    }

    @Override
    @PropertyGetter(value = "position", bindable = false, description = "The insertion point of the snippet relative to its anchor.")
    public SnippetPosition getSnippetPosition() {
        return position;
    }
    
    /**
     * Sets the insertion point of the snippet relative to its anchor.
     *
     * @param position The insertion point of the snippet relative to its anchor.
     */
    @PropertySetter(value = "position", defaultValue = "last", bindable = false, description = "The insertion point of the snippet relative to its anchor.")
    private void setSnippetPosition(SnippetPosition position) {
        this.position = position == null ? SnippetPosition.LAST : position;
    }
    
}
