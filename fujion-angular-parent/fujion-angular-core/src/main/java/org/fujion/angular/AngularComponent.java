/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2016 Regenstrief Institute, Inc.
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
package org.fujion.angular;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.BaseUIComponent;

/**
 * Container for an angular component.
 */
@Component(tag = "angular", widgetModule = "fujion-angular-widget", widgetClass = "AngularWidget", parentTag = "*")
public class AngularComponent extends BaseUIComponent {

    private String src;
    
    @PropertyGetter("src")
    public String getSrc() {
        return src;
    }
    
    @PropertySetter("src")
    public void setSrc(String src) {
        if (!areEqual(src = trimify(src), this.src)) {
            sync("src", this.src = src);
        }
    }
    
    public void ngInvoke(String functionName, Object... args) {
        invoke("ngInvoke", functionName, args);
    }
    
}