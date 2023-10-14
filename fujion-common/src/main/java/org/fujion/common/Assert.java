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
package org.fujion.common;

import java.util.function.Supplier;

/**
 * Assertion methods.
 */
public class Assert {

    /**
     * Always fails, throwing an IllegalArgument Exception.
     *
     * @param message The exception message.
     * @param params Optional parameters if formatted message.
     * @param <T> Pseudo return type to allow use as function.
     * @return Never returns a value.
     * @throws IllegalArgumentException always thrown.
     */
    public static <T> T fail(
            String message,
            Object... params) {
        throw new IllegalArgumentException(getMessage(message, params));
    }

    /**
     * Always fails, throwing an IllegalArgument Exception.
     *
     * @param message The exception message.
     * @param params Optional parameters if formatted message.
     * @param <T> Pseudo return type to allow use as function.
     * @return Never returns a value.
     * @throws IllegalArgumentException always thrown.
     */
    public static <T> T fail(
            Supplier<String> message,
            Object... params) {
        return fail(message.get(), params);
    }

    /**
     * Asserts that a condition is true, throwing an IllegalArgumentException if not.
     *
     * @param condition The condition to test.
     * @param message   The exception message.
     * @param params Optional parameters if formatted message.
     * @throws IllegalArgumentException if condition is false.
     */
    public static void isTrue(
            boolean condition,
            String message,
            Object... params) {
        if (!condition) {
            fail(message, params);
        }
    }

    /**
     * Asserts that a tested condition is true, throwing an IllegalArgumentException if not.
     *
     * @param condition The tested condition.
     * @param message   The exception message.
     * @param params Optional parameters if formatted message.
     * @throws IllegalArgumentException if condition is false.
     */
    public static void isTrue(
            boolean condition,
            Supplier<String> message,
            Object... params) {
        if (!condition) {
            fail(message, params);
        }
    }

    /**
     * Asserts that a condition is false, throwing an IllegalArgumentException if not.
     *
     * @param condition The condition to test.
     * @param message   The exception message.
     * @param params Optional parameters if formatted message.
     * @throws IllegalArgumentException if condition is true.
     */
    public static void isFalse(
            boolean condition,
            String message,
            Object... params) {
        isTrue(!condition, message, params);
    }

    /**
     * Asserts that a tested condition is false, throwing an IllegalArgumentException if not.
     *
     * @param condition The tested condition.
     * @param message   The exception message.
     * @param params Optional parameters if formatted message.
     * @throws IllegalArgumentException if condition is true.
     */
    public static void isFalse(
            boolean condition,
            Supplier<String> message,
            Object... params) {
        isTrue(!condition, message, params);
    }

    /**
     * Asserts that a value is null, throwing an IllegalArgumentException if not.
     *
     * @param value The value to check.
     * @param message   The exception message.
     * @param params Optional parameters if formatted message.
     * @throws IllegalArgumentException if value is not null.
     */
    public static void isNull(
            Object value,
            String message,
            Object... params) {
        isTrue(value == null, message, params);
    }

    /**
     * Asserts that a value is null, throwing an IllegalArgumentException if not.
     *
     * @param value The value to check.
     * @param message   The exception message.
     * @param params Optional parameters if formatted message.
     * @throws IllegalArgumentException if value is not null.
     */
    public static void isNull(
            Object value,
            Supplier<String> message,
            Object... params) {
        isTrue(value == null, message, params);
    }

    /**
     * Asserts that a value is not null, throwing an IllegalArgumentException if it is.
     *
     * @param value The value to check.
     * @param message   The exception message.
     * @param params Optional parameters if formatted message.
     * @throws IllegalArgumentException if value is null.
     */
    public static void notNull(
            Object value,
            String message,
            Object... params) {
        isTrue(value != null, message, params);
    }

    /**
     * Asserts that a value is not null, throwing an IllegalArgumentException if it is.
     *
     * @param value The value to check.
     * @param message   The exception message.
     * @param params Optional parameters if formatted message.
     * @throws IllegalArgumentException if value is null.
     */
    public static void notNull(
            Object value,
            Supplier<String> message,
            Object... params) {
        isTrue(value != null, message, params);
    }

    /**
     * Asserts that a tested state is true, throwing an IllegalStateException if not.
     *
     * @param state   The tested state.
     * @param message The exception message.
     * @param params Optional parameters if formatted message.
     * @throws IllegalStateException if state is false.
     */
    public static void state(
            boolean state,
            String message,
            Object... params) {
        if (!state) {
            throw new IllegalStateException(getMessage(message, params));
        }
    }

    /**
     * Asserts that a tested state is true, throwing an IllegalStateException if not.
     *
     * @param state   The tested state.
     * @param message The exception message.
     * @param params Optional parameters if formatted message.
     * @throws IllegalStateException if state is false.
     */
    public static void state(
            boolean state,
            Supplier<String> message,
            Object... params) {
        if (!state) {
            throw new IllegalStateException(getMessage(message.get(), params));
        }
    }

    /**
     * Returns the message text with formatting applied.
     *
     * @param message The exception message.
     * @param params Optional parameters if formatted message.
     * @return The fully formatted message.
     */
    private static String getMessage(String message, Object... params) {
        return params == null || params.length == 0 ? message : StrUtil.formatMessage(message, params);
    }

}
