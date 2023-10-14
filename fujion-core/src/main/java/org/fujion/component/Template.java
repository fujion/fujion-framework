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

import org.fujion.ancillary.INamespace;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.page.PageUtil;

/**
 * A component that merges a source page with zero or more snippets.
 *
 * @see Snippet
 */
@Component(
        tag = "template",
        widgetClass = "Span",
        parentTag = "*",
        childTag = @ChildTag("snippet"),
        description = "A component that merges a source page with zero or more snippets.")
public class Template extends BaseComponent implements INamespace {

    public Template() {
    }

    public Template(String src) {
        setSrc(src);
    }

    /**
     * We override this because the schema constrains children to snippets only, but we want to be
     * able to dynamically add children of any type.
     */
    @Override
    protected void validateChild(BaseComponent child) {
        child.getDefinition().validateParent(getDefinition());
    }

    /**
     * Sets the URL of the FSP for this template.
     *
     * @param src The URL of the FSP for this template.
     */
    @PropertySetter(value = "src", description = "The URL of the FSP for this template.")
    private void setSrc(String src) {
        src = nullify(src);

        if (src != null) {
            PageUtil.createPage(src, this);
        }
    }
}
