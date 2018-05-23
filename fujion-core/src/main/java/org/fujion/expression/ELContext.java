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
package org.fujion.expression;

import java.util.Map;

import org.fujion.component.BaseComponent;
import org.fujion.page.PageElement;

/**
 * This serves as the context root for an EL expression evaluation.
 */
public class ELContext {
    
    private final BaseComponent component;
    
    private final BaseComponent parent;
    
    private final PageElement element;

    private final Map<String, Object> args;
    
    /**
     * Create an EL context.
     *
     * @param component The current component.
     * @param parent The component that will become the parent.
     * @param element The current page element.
     * @param args The argument map passed to the materializer.
     */
    public ELContext(BaseComponent component, BaseComponent parent, PageElement element, Map<String, Object> args) {
        this.component = component;
        this.parent = parent;
        this.element = element;
        this.args = args;
    }
    
    /**
     * Returns a value for the named object. If the name is "self", the component itself is
     * returned. If the name is "parent" the component's parent is returned. Otherwise, name
     * resolution is attempted from several sources in the following order:
     * <ol>
     * <li>Tag library prefixes</li>
     * <li>The argument map passed to the materializer</li>
     * <li>The component's attribute map</li>
     * <li>The component's namespace</li>
     * <li>All ancestor attribute maps</li>
     * </ol>
     *
     * @param name The object name.
     * @return The value of the named object, or null if not found.
     */
    public Object getValue(String name) {
        Object result = "self".equals(name) ? component : null;
        result = result != null ? result : "parent".equals(name) ? parent : null;
        result = result != null ? result : element.getTagLibrary(name);
        result = result != null ? result : args == null ? null : args.get(name);
        result = result != null ? result : component.getAttribute(name);
        result = result != null ? result : parent == null ? null : parent.findByName(name);
        result = result != null ? result : parent == null ? null : parent.findAttribute(name);
        return result;
    }
}
