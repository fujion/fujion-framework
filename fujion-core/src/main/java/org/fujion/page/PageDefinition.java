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
package org.fujion.page;

import org.fujion.ancillary.ComponentException;
import org.fujion.ancillary.DeferredInvocation;
import org.fujion.annotation.ComponentDefinition;
import org.fujion.component.BaseComponent;
import org.fujion.component.Page;
import org.fujion.expression.ELContext;
import org.fujion.expression.ELEvaluator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This represents the compiled form of a single Fujion server page. It is a simple wrapper of a
 * tree of page elements, rooted at the root element.
 */
public class PageDefinition {

    private final PageElement root = new PageElement(null, null);

    private String source;

    /**
     * The root of all page elements in this definition.
     *
     * @return The root page element.
     */
    public PageElement getRootElement() {
        return root;
    }

    /**
     * Returns the source from which this page definition was derived.
     *
     * @return The source of this page definition (possibly null).
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the source from which this page definition was derived.
     *
     * @param source The source of this page definition.
     */
    /*package*/ void setSource(String source) {
        this.source = source;
    }

    /**
     * Materializes this page definition under the given parent component.
     *
     * @param parent The parent component for all top level components produced. This may be null.
     * @return A list of all top level components produced.
     */
    public List<BaseComponent> materialize(BaseComponent parent) {
        return materialize(parent, null);
    }

    /**
     * Materializes this page definition under the given parent component.
     *
     * @param parent The parent component for all top level components produced. This may be null.
     * @param args A map of arguments that will be copied into the attribute maps of all top level
     *            components. This may be null.
     * @return A list of all top level components produced.
     */
    public List<BaseComponent> materialize(BaseComponent parent, Map<String, Object> args) {
        try {
            List<DeferredInvocation<?>> deferrals = new ArrayList<>();
            List<BaseComponent> created = new ArrayList<>();
            List<PageElement> children = root.getChildren();
            
            if (!(parent instanceof Page) && children.size() == 1
                    && children.get(0).getDefinition().getComponentClass() == Page.class) {
                children = children.get(0).getChildren();
            }

            materialize(children, parent, deferrals, args, created);
            
            for (DeferredInvocation<?> deferral : deferrals) {
                deferral.invoke();
            }
            
            return created;
        } catch (Exception e) {
            throw new ComponentException(e, "Exception materializing page definition \"%s\"", source);
        }
    }

    private void materialize(Iterable<PageElement> children, BaseComponent parent, List<DeferredInvocation<?>> deferrals,
                             Map<String, Object> args, List<BaseComponent> created) {
        if (children != null) {
            for (PageElement child : children) {
                List<BaseComponent> components = materialize(child, parent, deferrals, args);

                if (created != null) {
                    for (BaseComponent component : components) {
                        if (args != null && !args.isEmpty()) {
                            component.getAttributes().putAll(args);
                        }
                        
                        created.add(component);
                    }
                    
                }
            }
        }
    }

    private List<BaseComponent> materialize(PageElement element, BaseComponent parent, List<DeferredInvocation<?>> deferrals,
                                            Map<String, Object> args) {
        ComponentDefinition def = element.getDefinition();
        boolean merge = parent instanceof Page && def.getComponentClass() == Page.class;
        Map<String, String> attributes = element.getAttributes();
        List<BaseComponent> components;

        if (merge) {
            components = Collections.singletonList(parent);
            parent = null;
        } else {
            ELContext elContext = attributes.isEmpty() ? null : new ELContext(null, parent, element, args);
            components = def.getFactory().create(attributes, elContext, element.getConstructorArgs());
        }

        for (BaseComponent component : components) {
            if (!attributes.isEmpty()) {
                ELContext elContext = new ELContext(component, parent, element, args);

                for (Entry<String, String> attribute : attributes.entrySet()) {
                    Object value = ELEvaluator.getInstance().evaluate(attribute.getValue(), elContext);
                    DeferredInvocation<?> deferral = def.setProperty(component, attribute.getKey(), value);

                    if (deferral != null) {
                        deferrals.add(deferral);
                    }
                }
            }

            if (parent != null) {
                parent.addChild(component);
            }

            materialize(element.getChildren(), component, deferrals, args, null);
        }

        return components;
    }
    
}
