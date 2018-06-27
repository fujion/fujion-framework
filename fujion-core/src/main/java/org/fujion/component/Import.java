/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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

import java.util.Map;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.page.PageUtil;

/**
 * A component that permits importing another page into the referencing page. Note that while it
 * prohibits child tags, it does allow the addition of child components either programmatically or
 * via the specified source page.
 */
@Component(tag = "import", widgetClass = "Span", parentTag = "*", description = "A component that permits importing another page into the referencing page.")
public class Import extends BaseUIComponent {
    
    private String src;
    
    @Override
    protected void _initProps(Map<String, Object> props) {
        super._initProps(props);
        props.put("wclazz", "fujion_import");
    }

    @Override
    public boolean isContainer() {
        return true;
    }
    
    @Override
    protected void validateChild(BaseComponent child) {
        child.getDefinition().validateParent(getDefinition());
    }
    
    /**
     * Returns the URL of the imported FSP.
     *
     * @return URL of the imported FSP.
     */
    @PropertyGetter(value = "src", description = "The URL of the imported FSP.")
    public String getSrc() {
        return src;
    }
    
    /**
     * Sets the URL of the FSP to import.
     *
     * @param src URL of the FSP to import.
     */
    @PropertySetter(value = "src", defer = true, description = "The URL of the imported FSP.")
    public void setSrc(String src) {
        if (propertyChange("src", this.src, this.src = nullify(src), false)) {
            this.destroyChildren();
            
            if (this.src != null) {
                PageUtil.createPage(src, this);
            }
        }
    }
}
