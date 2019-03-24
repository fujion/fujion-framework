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

import org.fujion.ancillary.INamespace;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A component representing a FSP that can be inserted into a template.
 *
 * @see Template
 */
@Component(tag = "snippet", widgetClass = "MetaWidget", parentTag = { "template",
        "snippet" }, childTag = @ChildTag("snippet"), description = "A Fujion resource that can be inserted into a template.")
public class Snippet extends BaseCompositeComponent implements INamespace {

    public Snippet() {
    }

    public Snippet(String src, String anchor, CompositePosition position) {
        super(src, anchor, position);
    }
    
    @Override
    @PropertySetter(value = "src", bindable = false, description = "The URL of the source FSP for this snippet.")
    protected void setCompositeSource(String src) {
        super.setCompositeSource(src);
    }

    @PropertyGetter(value = "anchor", bindable = false, description = "The name of the anchor component within the template.")
    @Override
    public String getCompositeAnchor() {
        return super.getCompositeAnchor();
    }
    
    @Override
    @PropertySetter(value = "anchor", bindable = false, description = "The name of the anchor component within the template.")
    protected void setCompositeAnchor(String anchor) {
        super.setCompositeAnchor(anchor);
    }

    @PropertyGetter(value = "position", bindable = false, description = "The insertion point of the snippet relative to its anchor.")
    @Override
    public CompositePosition getCompositePosition() {
        return super.getCompositePosition();
    }
    
    @PropertySetter(value = "position", defaultValue = "last", bindable = false, description = "The insertion point of the snippet relative to its anchor.")
    protected void setCompositionPosition(CompositePosition position) {
        super.setCompositePosition(position);
    }
    
}
