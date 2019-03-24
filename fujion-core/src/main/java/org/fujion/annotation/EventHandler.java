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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a handler method for one or more specific events.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(EventHandlers.class)
public @interface EventHandler {

    /**
     * The event type(s) to be handled.
     *
     * @return The event type(s) to be handled.
     */
    String[] value();

    /**
     * The wiring mode(s). If specified, will only be wired when one of the modes is active.
     *
     * @return The wiring mode(s).
     */
    String[] mode() default "";

    /**
     * The event target(s). If prefixed with an "@" character, the target is assumed to be the name
     * of an instance variable (member field). Otherwise, it represents the name associated with the
     * target component.
     *
     * @return The event target(s).
     */
    String[] target() default {};

    /**
     * Action to be taken if event handler cannot be wired.
     *
     * @return The on failure action.
     */
    OnFailure onFailure() default OnFailure.EXCEPTION;

    /**
     * If true, register the handler with the client.
     *
     * @return If true, register the handler with the client.
     */
    boolean syncToClient() default true;
}
