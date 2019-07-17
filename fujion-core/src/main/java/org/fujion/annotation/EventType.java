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

import java.lang.annotation.*;

/**
 * Class annotation to facilitate deserialization of events.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventType {

    /**
     * The name of the event represented by this class.
     * 
     * @return The event name.
     */
    String value();

    /**
     * Marks a field to be wired from client request data.
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface EventParameter {

        /**
         * The parameter name. If not specified, defaults to the name of the annotated field.
         * 
         * @return The parameter name.
         */
        String value() default "";

        /**
         * The action to be taken if wiring fails.
         * 
         * @return The on failure action.
         */
        OnFailure onFailure() default OnFailure.LOG;
    }
}
