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

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A component for loading a module via the SystemJS module loader.
 */
@Component(
        tag = "module",
        widgetClass = "NullWidget",
        parentTag = "*",
        description = "A component for loading a module via the SystemJS module loader.")
public class Module extends BaseComponent {
    
    private String src;

    /**
     * Returns the module's path.
     *
     * @return The module's path.
     */
    @PropertyGetter(value = "src", bindable = false, description = "The path of the module to load.")
    public String getSrc() {
        return src;
    }
    
    /**
     * Sets the module's path.
     *
     * @param src The module's path.
     */
    @PropertySetter(value = "src", bindable = false, description = "The path of the module to load.")
    public void setSrc(String src) {
        if (!areEqual(src = trimify(src), this.src)) {
            this.src = src;
            
            if (src != null) {
                loadModule(src);
            }
        }
    }
    
}
