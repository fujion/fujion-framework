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
package org.fujion.component;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ChildTag;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A container component with a frame and title for visual grouping of related elements.
 */
@Component(
        tag = "groupbox",
        widgetClass = "Groupbox",
        content = ContentHandling.AS_CHILD,
        parentTag = "*",
        childTag = @ChildTag("*"),
        description = "A container component with a frame and title for visual grouping of related elements.")
public class Groupbox extends BaseUIComponent {
    
    private String title;
    
    public Groupbox() {
    }

    public Groupbox(String title) {
        setTitle(title);
    }

    /**
     * Returns the title text.
     *
     * @return The title text.
     */
    @PropertyGetter(value = "title", description = "The title text.")
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title text.
     *
     * @param title The title text.
     */
    @PropertySetter(value = "title", description = "The title text.")
    public void setTitle(String title) {
        propertyChange("title", this.title, this.title = nullify(title), true);
    }
    
}
