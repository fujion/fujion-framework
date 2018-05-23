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
package org.fujion.ancillary;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.fujion.annotation.Component.FactoryParameter;
import org.fujion.annotation.ComponentDefinition;
import org.fujion.common.MiscUtil;
import org.fujion.component.BaseComponent;
import org.fujion.expression.ELEvaluator;

/**
 * Factory used during component deserialization. Factory parameters may be modified during
 * deserialization to provide control over component creation.
 */
public class ComponentFactory {
    
    private final ComponentDefinition def;
    
    private Class<? extends BaseComponent> clazz;
    
    private boolean active = true;
    
    private Iterable<?> forEach;
    
    private String forVar = "each";

    public ComponentFactory(ComponentDefinition def) {
        this.def = def;
        this.clazz = def.getComponentClass();
    }
    
    /**
     * A special processor may modify the component's implementation class, as long as the
     * substituted class is a subclass of the original.
     *
     * @param clazz Component implementation class to substitute.
     */
    @FactoryParameter(value = "impl", description = "Component implementation class to substitute.")
    protected void setImplementationClass(Class<? extends BaseComponent> clazz) {
        Class<? extends BaseComponent> originalClazz = def.getComponentClass();

        if (clazz != null && !originalClazz.isAssignableFrom(clazz)) {
            throw new ComponentException("Implementation class must extend class " + originalClazz.getName());
        }
        
        this.clazz = clazz;
    }
    
    /**
     * Conditionally prevents the factory from creating a component.
     *
     * @param condition If false, prevent factory from creating a component.
     */
    @FactoryParameter(value = "if", description = "If false, prevent component creation.")
    protected void setIf(boolean condition) {
        active &= condition;
    }
    
    /**
     * Conditionally prevents the factory from creating a component.
     *
     * @param condition If true, prevent factory from creating a component.
     */
    @FactoryParameter(value = "unless", description = "If true, prevent component creation.")
    protected void setUnless(boolean condition) {
        active &= !condition;
    }
    
    /**
     * Sets an iterable which will be used to produce one component for each value returned by the
     * iterable. Components produced by an iterable will contain an attribute named "each" (unless
     * overridden by forvar) that may be referenced to retrieve the value of the associated iterable
     * element.
     *
     * @param forEach An object that will be converted to an iterable. See
     *            {@link org.fujion.ancillary.ConvertUtil#convertToIterable convertToITerable} for
     *            supported types.
     */
    @FactoryParameter(value = "foreach", description = "Specifies a collection for iterative component creation.")
    protected void setForEach(Object forEach) {
        this.forEach = ConvertUtil.convertToIterable(forEach);
    }

    /**
     * Sets the attribute name to use in foreach iteration.
     *
     * @param forVar Name of attribute to use in foreach iteration.
     */
    @FactoryParameter(value = "forvar", defaultValue = "each", description = "Specifies the attribute name used in foreach.")
    protected void setForVar(String forVar) {
        forVar = StringUtils.trimToNull(forVar);
        forVar = forVar == null ? "each" : forVar;
        
        if (!BaseComponent.validateName(forVar)) {
            throw new ComponentException("Name specified in 'forvar' is not valid: " + forVar);
        }
        
        this.forVar = forVar;
    }

    /**
     * Creates one or more component instances from the component definition using a factory
     * context.
     *
     * @param attributes Attribute map for initializing.
     * @return A list of newly created components (never null).
     */
    public List<BaseComponent> create(Map<String, String> attributes) {
        if (attributes != null) {
            for (Entry<String, Method> entry : def.getFactoryParameters().entrySet()) {
                String name = entry.getKey();
                
                if (attributes.containsKey(name)) {
                    Object value = ELEvaluator.getInstance().evaluate(attributes.remove(name));
                    ConvertUtil.invokeMethod(this, entry.getValue(), value);
                }
            }
        }
        
        if (!active) {
            return Collections.emptyList();
        }

        if (forEach == null) {
            return Collections.singletonList(create());
        }

        List<BaseComponent> components = new ArrayList<>();

        for (Object each : forEach) {
            BaseComponent comp = create();
            comp.setAttribute(forVar, each);
            components.add(comp);
        }

        return components;
    }

    private BaseComponent create() {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
    
}
