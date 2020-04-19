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
package org.fujion.annotation;

import org.fujion.ancillary.ComponentFactory;
import org.fujion.event.PropertychangeEvent;
import org.fujion.model.IBinding.IReadBinding;
import org.fujion.model.IBinding.IWriteBinding;

import java.lang.annotation.*;

/**
 * Class annotation to control deserialization of a Fujion resource.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
    
    /**
     * Methods of handling text content nodes.
     */
    enum ContentHandling {
        /**
         * Text content throws an exception.
         */
        ERROR,
        /**
         * Text content is ignored.
         */
        IGNORE,
        /**
         * Text content is internally represented as an attribute named #text.
         */
        AS_ATTRIBUTE,
        /**
         * Text content is internally represented as a child component of the class Content.
         */
        AS_CHILD
    }
    
    /**
     * Marks a property getter.
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface PropertyGetter {
        
        /**
         * The property name.
         *
         * @return The property name.
         */
        String value();
        
        /**
         * If true, the getter may be bound to a {@link IWriteBinding write binder}. A property must
         * signal a change in value via a {@link PropertychangeEvent} in order to serve as a data
         * source for a write binder.
         *
         * @return If true, the getter may be bound to a {@link IWriteBinding write binder}.
         */
        boolean bindable() default true;
        
        /**
         * If true, hide the getter method from the deserializer. Use this to hide a getter
         * annotated in a superclass.
         *
         * @return If true, hide the getter method from the deserializer.
         */
        boolean hide() default false;

        /**
         * Optional description of property.
         *
         * @return Description of property.
         */
        String description() default "";
    }
    
    /**
     * Marks a property setter
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface PropertySetter {
        
        /**
         * The property name.
         *
         * @return The property name.
         */
        String value();
        
        /**
         * If true, the setter may be bound to a {@link IReadBinding read binder}.
         *
         * @return If true, the setter may be bound to a {@link IReadBinding read binder}.
         */
        boolean bindable() default true;
        
        /**
         * If true, hide the setter method from the deserializer. Use this to hide a setter
         * annotated in a superclass.
         *
         * @return If true, hide the setter method from the deserializer.
         */
        boolean hide() default false;
        
        /**
         * If true, defer invoking the setter until deserialization is complete.
         *
         * @return If true, defer invoking the setter until deserialization is complete.
         */
        boolean defer() default false;

        /**
         * The default value for the property.
         *
         * @return The default value for the property, if any.
         */
        String defaultValue() default "";
        
        /**
         * Optional description of property.
         *
         * @return Description of property.
         */
        String description() default "";
    }
    
    /**
     * Binds a factory parameter to an XML attribute. Such attributes are used to modify factory
     * settings that affect component creation.
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface FactoryParameter {
        
        /**
         * The attribute name.
         *
         * @return The attribute name.
         */
        String value();
        
        /**
         * The default value for the parameter.
         *
         * @return The default value for the parameter, if any.
         */
        String defaultValue() default "";
        
        /**
         * Optional description of property.
         *
         * @return Description of property.
         */
        String description() default "";
    }
    
    /**
     * Represents a child tag and its cardinality.
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.ANNOTATION_TYPE)
    @interface ChildTag {
        
        /**
         * The child tag.
         *
         * @return The child tag.
         */
        String value();
        
        /**
         * Minimum number of occurrences.
         *
         * @return Minimum number of occurrences.
         */
        int minimum() default 0;
        
        /**
         * Maximum number of occurrences.
         *
         * @return Maximum number of occurrences.
         */
        int maximum() default Integer.MAX_VALUE;
        
    }
    
    /**
     * The XML tag corresponding to this component.
     *
     * @return The XML tag corresponding to this component.
     */
    String tag();
    
    /**
     * How to handle text content associated with the tag.
     *
     * @return The content handling.
     */
    ContentHandling content() default ContentHandling.ERROR;
    
    /**
     * The allowable parent tag(s) for this component.
     *
     * @return The allowable parent tag(s).
     */
    String[] parentTag() default {};
    
    /**
     * The allowable child tag(s) for this component, including cardinality.
     *
     * @return The allowable child tag(s).
     */
    ChildTag[] childTag() default {};
    
    /**
     * The class of the factory for creating this component.
     *
     * @return The factory class.
     */
    Class<? extends ComponentFactory> factoryClass() default ComponentFactory.class;
    
    /**
     * The JavaScript module containing the widget.
     *
     * @return The JavaScript module name.
     */
    String widgetModule() default "fujion-widget";
    
    /**
     * The JavaScript class for the widget.
     *
     * @return The JavaScript widget class.
     */
    String widgetClass();
    
    /**
     * Optional description of component.
     *
     * @return Description of component.
     */
    String description() default "";
}
