/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
package org.fujion.markdown;

import org.fujion.annotation.Component;
import org.fujion.component.BaseSourcedComponent;

/**
 * A component that allows embedding Markdown within a page.
 */
@Component(
        tag = "markdown",
        widgetModule = "fujion-markdown",
        widgetClass = "Markdown",
        content = Component.ContentHandling.AS_ATTRIBUTE,
        parentTag = "*",
        description = "A component that allows embedding Markdown within a page.")
public class Markdown extends BaseSourcedComponent {

    public Markdown() {
        super(true);
    }

    public Markdown(String content) {
        super(content, true);
    }

}