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
package org.fujion.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

/**
 * Annotation to mark a field to be serialized as part of an option map.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Option {
    
    /**
     * The value of the map key to be used when serializing the field. Will default to the field
     * name.
     *
     * @return Value of the map key.
     */
    String value() default "";

    /**
     * Optional destination type for the conversion.
     *
     * @return The destination type for the conversion. Default is no conversion.
     */
    Class<?> convertTo() default Object.class;
    
    /**
     * Optional converter to convert value to a new type.
     *
     * @return The converter to use. Default is no conversion.
     */
    Class<? extends Function<?, ?>> convertWith() default OptionScanner.NOPConverter.class;

    /**
     * Ignore the annotation. Useful to explicitly show that the field is not to be serialized.
     *
     * @return If true, ignore the annotation. Default is false.
     */
    boolean ignore() default false;
}