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
package org.fujion.ancillary;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

import org.apache.commons.lang.UnhandledException;
import org.fujion.component.BaseComponent;

/**
 * Run time exception related to a component operation.
 */
public class ComponentException extends UnhandledException {
    
    private static final long serialVersionUID = 1L;
    
    private final BaseComponent component;
    
    private final Class<? extends BaseComponent> componentClass;
    
    private final String message;

    /**
     * Asserts that a condition is true, throwing a ComponentException if it is not.
     *
     * @param condition Condition to test.
     * @param message The exception message.
     * @exception ComponentException Thrown if the condition is not met.
     */
    public static void assertTrue(boolean condition, Supplier<String> message) {
        assertTrue(condition, null, null, message);
    }

    /**
     * Asserts that a condition is true, throwing a ComponentException if it is not.
     *
     * @param condition Condition to test.
     * @param componentClass Class of the component that caused the exception. May be null.
     * @param message The exception message.
     * @exception ComponentException Thrown if the condition is not met.
     */
    public static void assertTrue(boolean condition, Class<? extends BaseComponent> componentClass,
                                  Supplier<String> message) {
        assertTrue(condition, componentClass, null, message);
    }

    /**
     * Asserts that a condition is true, throwing a ComponentException if it is not.
     *
     * @param condition Condition to test.
     * @param component Component instance that caused the exception. May be null.
     * @param message The exception message.
     * @exception ComponentException Thrown if the condition is not met.
     */
    public static void assertTrue(boolean condition, BaseComponent component, Supplier<String> message) {
        assertTrue(condition, null, component, message);
    }

    /**
     * Asserts that a condition is true, throwing a ComponentException if it is not.
     *
     * @param condition Condition to test.
     * @param componentClass Class of the component that caused the exception. May be null.
     * @param component Component instance that caused the exception. May be null.
     * @param message The exception message.
     * @exception ComponentException Thrown if the condition is not met.
     */
    private static void assertTrue(boolean condition, Class<? extends BaseComponent> componentClass, BaseComponent component,
                                   Supplier<String> message) {
        if (!condition) {
            throw new ComponentException((Throwable) null, componentClass, component, message.get());
        }
    }

    /**
     * Asserts that a condition is true, throwing a ComponentException if it is not.
     *
     * @param condition Condition to test.
     * @param message The exception message.
     * @param args Optional message parameters
     * @exception ComponentException Thrown if the condition is not met.
     */
    public static void assertTrue(boolean condition, String message, Object... args) {
        assertTrue(condition, null, null, message, args);
    }

    /**
     * Asserts that a condition is true, throwing a ComponentException if it is not.
     *
     * @param condition Condition to test.
     * @param componentClass Class of the component that caused the exception. May be null.
     * @param message The exception message.
     * @param args Optional message parameters
     * @exception ComponentException Thrown if the condition is not met.
     */
    public static void assertTrue(boolean condition, Class<? extends BaseComponent> componentClass, String message,
                                  Object... args) {
        assertTrue(condition, componentClass, null, message, args);
    }

    /**
     * Asserts that a condition is true, throwing a ComponentException if it is not.
     *
     * @param condition Condition to test.
     * @param component Component instance that caused the exception. May be null.
     * @param message The exception message.
     * @param args Optional message parameters
     * @exception ComponentException Thrown if the condition is not met.
     */
    public static void assertTrue(boolean condition, BaseComponent component, String message, Object... args) {
        assertTrue(condition, null, component, message, args);
    }

    /**
     * Asserts that a condition is true, throwing a ComponentException if it is not.
     *
     * @param condition Condition to test.
     * @param componentClass Class of the component that caused the exception. May be null.
     * @param component Component instance that caused the exception. May be null.
     * @param message The exception message.
     * @param args Optional message parameters
     * @exception ComponentException Thrown if the condition is not met.
     */
    private static void assertTrue(boolean condition, Class<? extends BaseComponent> componentClass, BaseComponent component,
                                   String message, Object... args) {
        if (!condition) {
            throw new ComponentException(null, componentClass, component, message, args);
        }
    }

    private static String formatMessage(Class<? extends BaseComponent> componentClass, BaseComponent component,
                                        String message, Object... args) {
        Object object = component != null ? component : componentClass;
        message = args == null || args.length == 0 ? message : String.format(message, args);
        return (object == null ? "" : object + ": ") + message;
    }

    private static Throwable getCause(Throwable cause) {
        return cause instanceof InvocationTargetException ? cause.getCause() : cause;
    }
    
    private ComponentException(Throwable cause, Class<? extends BaseComponent> componentClass, BaseComponent component,
                               String message, Object... args) {
        super(getCause(cause));
        this.message = formatMessage(componentClass, component, message, args);
        this.component = component;
        this.componentClass = component != null ? component.getClass() : componentClass;
    }

    public ComponentException(Throwable cause, String message, Object... args) {
        this(cause, null, null, message, args);
    }

    public ComponentException(Throwable cause, Class<? extends BaseComponent> componentClass, String message,
                              Object... args) {
        this(cause, componentClass, null, message, args);
    }

    public ComponentException(Throwable cause, BaseComponent component, String message, Object... args) {
        this(cause, null, component, message, args);
    }

    public ComponentException(String message, Object... args) {
        this(null, null, null, message, args);
    }
    
    public ComponentException(Class<? extends BaseComponent> componentClass, String message, Object... args) {
        this(null, componentClass, null, message, args);
    }
    
    public ComponentException(BaseComponent component, String message, Object... args) {
        this(null, null, component, message, args);
    }
    
    /**
     * Returns the component instance that caused the exception.
     *
     * @return Component instance that caused the exception. May be null.
     */
    public BaseComponent getComponent() {
        return component;
    }
    
    /**
     * Returns the class of the component that caused the exception. If a component instance is
     * associated with the exception, the class will be that of the component instance. However, if
     * the exception occurred while attempting to create an instance of a component, the component
     * instance will be null and only the component class will be set.
     *
     * @return Class of the component that caused the exception. May be null.
     */
    public Class<? extends BaseComponent> getComponentClass() {
        return componentClass;
    }
    
    /**
     * Override default behavior and simply prepend this exception's message to that of its
     * superclass.
     */
    @Override
    public String getMessage() {
        String message = super.getMessage();

        if (message == null) {
            message = this.message;
        } else if (this.message != null) {
            message = this.message + "\n" + message;
        }

        return message;
    }
}
