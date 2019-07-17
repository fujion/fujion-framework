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
package org.fujion.annotation;

import org.apache.commons.beanutils.ConstructorUtils;
import org.fujion.ancillary.*;
import org.fujion.annotation.Component.*;
import org.fujion.common.MiscUtil;
import org.fujion.component.BaseComponent;
import org.fujion.model.IBinding;
import org.fujion.model.IBinding.IReadBinding;
import org.fujion.model.IBinding.IWriteBinding;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.IntSupplier;

/**
 * Stores metadata about a component, as derived from component annotations.
 */
public class ComponentDefinition {
    
    /**
     * Represents the cardinality of a child tag.
     */
    public static class Cardinality {
        
        private final int minimum;
        
        private final int maximum;
        
        Cardinality(int minimum, int maximum) {
            this.minimum = minimum;
            this.maximum = maximum;
        }
        
        /**
         * Returns the minimum cardinality.
         *
         * @return The minimum cardinality.
         */
        public int getMinimum() {
            return minimum;
        }
        
        /**
         * Returns the maximum cardinality.
         *
         * @return The maximum cardinality.
         */
        public int getMaximum() {
            return maximum;
        }
        
        /**
         * Returns true if there is a minimum cardinality.
         *
         * @return True if there is a minimum cardinality.
         */
        public boolean hasMinimum() {
            return minimum > 0;
        }
        
        /**
         * Returns true if there is a maximum cardinality.
         *
         * @return True if there is a maximum cardinality.
         */
        public boolean hasMaximum() {
            return maximum != Integer.MAX_VALUE;
        }
        
        /**
         * Returns true if the count falls within the cardinality constraints.
         *
         * @param count The count to test.
         * @return True if the count falls within the cardinality constraints.
         */
        public boolean isValid(int count) {
            return count >= minimum && count <= maximum;
        }
    }

    private final ContentHandling contentHandling;
    
    private final String tag;
    
    private final Class<? extends BaseComponent> componentClass;
    
    private final Class<? extends ComponentFactory> factoryClass;

    private final String widgetModule;
    
    private final String widgetClass;
    
    private final String description;
    
    private final Set<String> parentTags = new HashSet<>();
    
    private final Map<String, Cardinality> childTags = new HashMap<>();
    
    private final Map<String, Method> getters = new HashMap<>();
    
    private final Map<String, Method> setters = new HashMap<>();
    
    private final Map<String, Method> parameters = new HashMap<>();
    
    /**
     * Creates a component definition derived from annotation information within the specified
     * class.
     *
     * @param componentClass A component class.
     */
    public ComponentDefinition(Class<? extends BaseComponent> componentClass) {
        Component annot = componentClass.getAnnotation(Component.class);
        this.componentClass = componentClass;
        this.factoryClass = annot.factoryClass();
        this.widgetModule = annot.widgetModule();
        this.widgetClass = annot.widgetClass();
        this.tag = annot.tag();
        this.contentHandling = annot.content();
        this.description = annot.description();
        
        for (String tag : annot.parentTag()) {
            addParentTag(tag);
        }
        
        for (ChildTag tag : annot.childTag()) {
            addChildTag(tag);
        }
        
    }
    
    /**
     * Returns The value of the named property.
     *
     * @param instance Instance to retrieve property from.
     * @param name Name of property.
     * @return The property value.
     */
    public Object getProperty(BaseComponent instance, String name) {
        QualifiedName pname = new QualifiedName(name);
        Method setter = setters.get(pname.getName());
        Method getter = getters.get(pname.getName());
        assertTrue(getter != null, setter != null ? "Property \"%s\" is write-only" : "Property \"%s\" is not recognized",
                name);
        
        try {
            return getter.invoke(instance, getter.getParameterCount() == 1 ? new Object[] { pname.getQualifier() } : null);
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
    
    /**
     * Returns true if attribute name is valid for this component definition.
     *
     * @param name The attribute name.
     * @return True if valid.
     */
    public boolean validateAttribute(String name) {
        QualifiedName pname = new QualifiedName(name);
        return setters.get(pname.getName()) != null || getters.get(pname.getName()) != null || parameters.containsKey(name);
    }
    
    /**
     * Sets a property value or defers that operation if the property is marked as such.
     *
     * @param instance Instance containing the property.
     * @param name Name of property.
     * @param value The value to set.
     * @return Null if the operation occurred, or a DeferredInvocation object if deferred.
     */
    public DeferredInvocation<?> setProperty(BaseComponent instance, String name, Object value) {
        QualifiedName pname = new QualifiedName(name);
        Method setter = setters.get(pname.getName());
        Method getter = getters.get(pname.getName());
        
        if (value instanceof IBinding) {
            assertTrue(
                getter == null || !(value instanceof IWriteBinding) || getter.getAnnotation(PropertyGetter.class).bindable(),
                "Property \"%s\" does not support a write binding", name);
            assertTrue(
                setter == null || !(value instanceof IReadBinding) || setter.getAnnotation(PropertySetter.class).bindable(),
                "Property \"%s\" does not support a read binding", name);
            ((IBinding) value).init(instance, name, getter, setter);
            return null;
        }

        if (setter == null) {
            assertTrue(parameters.containsKey(name),
                getter != null ? "Property \"%s\" is read-only" : "Property \"%s\" is not recognized", name);
            return null;
        }
        
        Object[] args = setter.getParameterCount() == 1 ? new Object[] { value }
                : new Object[] { pname.getQualifier(), value };
        
        if (setter.getAnnotation(PropertySetter.class).defer()) {
            return new DeferredInvocation<>(instance, setter, args);
        }

        ConvertUtil.invokeMethod(instance, setter, args);
        return null;
    }
    
    /**
     * Validates a condition, throwing an exception if not met.
     *
     * @param condition The evaluated condition.
     * @param message The exception message.
     * @param args Replaceable arguments for message.
     * @exception ComponentException Thrown if the condition is not met.
     */
    private void assertTrue(boolean condition, String message, Object... args) {
        ComponentException.assertTrue(condition, componentClass, message, args);
    }

    /**
     * Returns the XML tag for this component type.
     *
     * @return An XML tag.
     */
    public String getTag() {
        return tag;
    }
    
    /**
     * Returns the implementation class for this component type.
     *
     * @return Implementation class.
     */
    public Class<? extends BaseComponent> getComponentClass() {
        return componentClass;
    }
    
    /**
     * Returns the factory class for this component type.
     *
     * @return Factory class.
     */
    public Class<? extends ComponentFactory> getFactoryClass() {
        return factoryClass;
    }
    
    /**
     * Returns the description of this component.
     *
     * @return The component's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a factory instance for this component.
     *
     * @return The component factory.
     */
    public ComponentFactory getFactory() {
        try {
            return ConstructorUtils.invokeConstructor(factoryClass, this);
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
    
    /**
     * Returns the javascript module containing the widget class.
     *
     * @return Widget module.
     */
    public String getWidgetModule() {
        return widgetModule;
    }
    
    /**
     * Returns the javascript class for the widget.
     *
     * @return Widget class.
     */
    public String getWidgetClass() {
        return widgetClass;
    }
    
    /**
     * Returns the cardinality of a child tag.
     *
     * @param childTag A child tag.
     * @return Cardinality of the child tag, or null if the tag is not a valid child.
     */
    public Cardinality getCardinality(String childTag) {
        Cardinality cardinality = childTags.get(childTag);
        return cardinality == null ? childTags.get("*") : cardinality;
    }
    
    /**
     * Returns an immutable map of all child tags.
     *
     * @return Map of child tags.
     */
    public Map<String, Cardinality> getChildTags() {
        return Collections.unmodifiableMap(childTags);
    }
    
    /**
     * Returns true if this component allows children.
     *
     * @return True if this component allows children.
     */
    public boolean childrenAllowed() {
        return childTags.size() > 0;
    }
    
    /**
     * Validate that a child defined by the component definition is valid for this parent.
     *
     * @param childDefinition Definition for child component.
     * @param childCount Current child count.
     * @exception ComponentException Thrown if child fails validation.
     */
    public void validateChild(ComponentDefinition childDefinition, IntSupplier childCount) {
        assertTrue(childrenAllowed(), "Children are not allowed");
        childDefinition.validateParent(this);
        Cardinality cardinality = getCardinality(childDefinition.tag);
        assertTrue(cardinality != null, "%s is not a valid child", childDefinition.componentClass);
        assertTrue(!cardinality.hasMaximum() || childCount.getAsInt() < cardinality.getMaximum(),
            "A maximum of %d children of type %s are allowed", cardinality.getMaximum(), childDefinition.componentClass);
    }
    
    /**
     * Validate that a component defined by the component definition is a valid parent for this
     * component.
     *
     * @param parentDefinition Definition for parent component.
     * @exception ComponentException Thrown if child fails validation.
     */
    public void validateParent(ComponentDefinition parentDefinition) {
        assertTrue(isParentTag(parentDefinition.tag), "%s is not a valid parent", parentDefinition.componentClass);
    }
    
    /**
     * Returns true if the tag is a valid parent tag.
     *
     * @param tag Tag to be tested.
     * @return True if the tag is a valid parent tag.
     */
    public boolean isParentTag(String tag) {
        return parentTags.contains(tag) || parentTags.contains("*");
    }
    
    /**
     * Returns an immutable set of parent tags.
     *
     * @return Set of valid parent tags.
     */
    public Set<String> getParentTags() {
        return Collections.unmodifiableSet(parentTags);
    }
    
    /**
     * Returns how to handle content for this component type.
     *
     * @return How to handle content.
     */
    public ContentHandling contentHandling() {
        return contentHandling;
    }
    
    // Processors for component annotations
    
    /**
     * Registers a parent tag.
     *
     * @param tag The tag, or "*" to indicate any parent tag is valid.
     */
    private void addParentTag(String tag) {
        parentTags.add(tag);
    }
    
    /**
     * Registers a child tag.
     *
     * @param tag A child tag.
     */
    private void addChildTag(ChildTag tag) {
        childTags.put(tag.value(), new Cardinality(tag.minimum(), tag.maximum()));
    }
    
    /**
     * Returns true if the method is static.
     *
     * @param method Method to test.
     * @return True if the method is static.
     */
    private boolean isStatic(Method method) {
        return Modifier.isStatic(method.getModifiers());
    }
    
    /**
     * Registers a property getter.
     *
     * @param method The getter method.
     */
    /*package*/ void _addGetter(Method method) {
        PropertyGetter getter = method.getAnnotation(PropertyGetter.class);
        
        if (getter != null) {
            String name = getter.value();

            if (!this.getters.containsKey(name)) {
                if (isStatic(method) || method.getReturnType() == Void.TYPE || method.getParameterTypes().length > 0) {
                    throw new IllegalArgumentException("Bad signature for getter method: " + method.getName());
                }

                this.getters.put(name, getter.hide() ? null : method);
            }
        }
    }
    
    /**
     * Returns an immutable map of getter methods.
     *
     * @return Map of getter methods.
     */
    public Map<String, Method> getGetters() {
        return Collections.unmodifiableMap(getters);
    }
    
    /**
     * Registers a property setter.
     *
     * @param method The setter method.
     */
    /*package*/ void _addSetter(Method method) {
        PropertySetter setter = method.getAnnotation(PropertySetter.class);
        
        if (setter != null) {
            String name = setter.value();
            
            if (!setters.containsKey(name)) {
                int length = method.getParameterCount();
                
                if (isStatic(method) || length == 0 || length > 2
                        || (length == 2 && method.getParameterTypes()[0] != String.class)) {
                    throw new IllegalArgumentException("Bad signature for setter method: " + method.getName());
                }
                
                setters.put(name, setter.hide() ? null : method);
            }
        }
    }
    
    /**
     * Returns an immutable map of setter methods.
     *
     * @return Map of setter methods.
     */
    public Map<String, Method> getSetters() {
        return Collections.unmodifiableMap(setters);
    }
    
    /**
     * Registers a factory parameter.
     *
     * @param method The factory parameter method.
     */
    /*package*/ void _addFactoryParameter(Method method) {
        FactoryParameter parameter = method.getAnnotation(FactoryParameter.class);

        if (parameter != null) {
            String name = parameter.value();
            
            if (!parameters.containsKey(name)) {
                if (isStatic(method) || method.getParameterTypes().length != 1) {
                    throw new IllegalArgumentException("Bad signature for factory parameter method: " + method.getName());
                }
                
                parameters.put(name, method);
            }
        }
    }
    
    /**
     * Returns an immutable map of factory parameters.
     *
     * @return Map of factory parameters.
     */
    public Map<String, Method> getFactoryParameters() {
        return Collections.unmodifiableMap(parameters);
    }
    
    @Override
    public boolean equals(Object object) {
        return object instanceof ComponentDefinition && ((ComponentDefinition) object).componentClass == componentClass;
    }
}
