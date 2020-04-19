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
package org.fujion.react;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.BaseUIComponent;

/**
 * Container for a React component.
 */
@Component(
        tag = "react",
        widgetModule = "fujion-react-widget",
        widgetClass = "ReactWidget",
        parentTag = "*",
        description = "Container for a React component.")
public class ReactComponent extends BaseUIComponent {
    
    private String src;

    /**
     * Returns the name of the module containing the React component.
     *
     * @return Name of module containing React component.
     */
    @PropertyGetter(value = "src", description = "Name of module containing React component.")
    public String getSrc() {
        return src;
    }

    /**
     * Sets the module containing the React component.
     *
     * @param src Name of module containing React component.
     */
    @PropertySetter(value = "src", description = "Name of module containing React component.")
    public void setSrc(String src) {
        propertyChange("src", this.src, this.src = trimify(src), true);
    }

    /**
     * Invokes a published method on the module containing the React component.
     *
     * @param functionName Name of function to invoke.
     * @param args Arguments to pass to function.
     */
    public void rxInvoke(String functionName, Object... args) {
        invoke("rxInvoke", functionName, args);
    }

}
