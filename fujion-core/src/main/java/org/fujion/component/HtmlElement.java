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
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.common.Assert;

/**
 * A component that allows embedding native HTML within a page.
 */
@Component(
        tag = "html:",
        widgetClass = "HtmlElement",
        content = ContentHandling.AS_CHILD,
        parentTag = "*",
        childTag = @ChildTag("*"),
        description = "A native HTML element.")
public class HtmlElement extends BaseComponent {
    
    private String tag = "span";

    public HtmlElement() {
        super();
    }

    public HtmlElement(String tag) {
        super();
        setTag(tag);
    }

    @PropertySetter(value = "html:", description = "Sets an attribute value on a native HTML element.")
    public void setElementAttribute(String attribute, Object value) {
        invoke("attribute", attribute, value);
    }
    
    public String getTag() {
        return tag;
    }
    
    public void setTag(String tag) {
        String tagName = defaultify(nullify(tag), "span");
        Assert.isTrue(validateName(tagName), () -> "Invalid tag: " + tagName);
        propertyChange("tag", this.tag, this.tag = tagName, true);
    }
    
}
