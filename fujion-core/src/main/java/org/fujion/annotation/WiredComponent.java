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
package org.fujion.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Flags a component instance for wiring.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface WiredComponent {

    /**
     * The name associated with the component instance to be wired to this field. If not specified,
     * is assumed to be the same as the field name.
     *
     * @return The component name.
     */
    String value() default "";

    /**
     * Determines the action to be taken if the annotated field already has an assigned value. If
     * true, the existing value is overwritten. If false, it is treated as a wiring failure.
     *
     * @return The overwrite action.
     */
    boolean overwrite() default false;

    /**
     * The action to be taken if wiring fails.
     *
     * @return The on failure action.
     */
    OnFailure onFailure() default OnFailure.EXCEPTION;
}
