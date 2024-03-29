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

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * Miscellaneous utility methods.
 */
public class MiscUtil {

    /**
     * Returns true if the specified file exists.
     *
     * @param fileName File name.
     * @return True if file exists.
     */
    public static boolean fileExists(String fileName) {
        return new File(fileName).exists();
    }

    /**
     * Converts a checked exception to unchecked. If the original exception is already unchecked, it
     * is simply returned.
     *
     * @param e The original exception.
     * @return The returned unchecked exception.
     */
    public static RuntimeException toUnchecked(Throwable e) {
        if (e instanceof InvocationTargetException) {
            e = ((InvocationTargetException) e).getTargetException();
        }

        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }

        return new RuntimeException(e);
    }

    /**
     * Throws an exception, converting it to unchecked as necessary.  Can be used as a function or method.
     *
     * @param e   The original exception.  If null, no exception is thrown.
     * @param <T> Arbitrary return type.
     * @return Never returns a value.
     */
    public static <T> T throwUnchecked(Throwable e) {
        if (e != null) {
            throw toUnchecked(e);
        }

        return null;
    }

    /**
     * Formats an exception for display.
     *
     * @param exc Exception to format.
     * @return The displayable form of the exception.
     */
    public static String formatExceptionForDisplay(Throwable exc) {
        Throwable root = ExceptionUtils.getRootCause(exc);
        return exc == null ? null : ExceptionUtils.getMessage(root == null ? exc : root);
    }

    /**
     * If the supplier produces a null pointer or index-out-of-bounds exception, returns null.
     * This is the equivalent of optional chaining.
     *
     * @param supplier The supplier of the value.
     * @param <T>      The type of the value.
     * @return The returned value, possibly null.
     */
    public static <T> T asNull(Supplier<T> supplier) {
        return withDefault(null, supplier);
    }

    /**
     * If the supplier produces a null pointer or index-out-of-bounds exception, returns the default value.
     * This is the equivalent of optional chaining.
     *
     * @param deflt    The default value to use.
     * @param supplier The supplier of the value.
     * @param <T>      The type of the value.
     * @return The returned value, possibly null.
     */
    public static <T> T withDefault(T deflt, Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return deflt;
        }
    }

    /**
     * Returns an array of parameter types given an array of parameters. Unlike other libraries,
     * this allows null parameter values.
     *
     * @param parameters Array of parameters.
     * @return Array of parameter types.
     */
    public static Class<?>[] getParameterTypes(Object... parameters) {
        int len = parameters == null ? 0 : parameters.length;
        Class<?>[] parameterTypes = new Class[len];

        for (int i = 0; i < len; i++) {
            parameterTypes[i] = parameters[i] == null ? null : parameters[i].getClass();
        }

        return parameterTypes;
    }

    /**
     * Casts a value to the specified type, returning null if the cast is not possible.
     *
     * @param value The value to cast.
     * @param type  The type to cast to.
     * @param <T>   The type to cast to.
     * @return The original value, cast to the specified type, or null if the cast is not possible.
     */
    public static <T> T castTo(Object value, Class<T> type) {
        return type.isInstance(value) ? type.cast(value) : null;
    }

    /**
     * Returns the first class from a list of candidate classes that is assignable from the specified
     * type.
     *
     * @param type       The type.
     * @param candidates A list of candidate types.
     * @return The first class that is assignable from the specified type.
     */
    public static Class<?> firstAssignable(Class<?> type, Class<?>... candidates) {
        return firstAssignable(type, Arrays.asList(candidates));
    }

    /**
     * Returns the first class in a collection of candidate classes that is assignable from the specified
     * type.
     *
     * @param type       The type.
     * @param candidates A collection of candidate types.
     * @return The first class that is assignable from the specified type.
     */
    public static Class<?> firstAssignable(Class<?> type, Collection<Class<?>> candidates) {
        return candidates.stream()
                .filter(candidate -> candidate.isAssignableFrom(type))
                .findFirst()
                .orElse(null);
    }

    /**
     * Converts a glob search pattern to a regular expression.
     *
     * @param glob Glob search pattern.
     * @return Regular expression.
     */
    public static Pattern globToRegex(String glob) {
        StringBuilder sb = new StringBuilder(glob.length()).append('^');
        int inGroup = 0;
        int inClass = 0;
        int firstIndexInClass = -1;
        char[] arr = glob.toCharArray();
        int last = arr.length - 1;

        for (int i = 0; i <= last; i++) {
            char ch = arr[i];
            switch (ch) {
                case '\\' -> {
                    if (++i >= arr.length) {
                        sb.append('\\');
                    } else {
                        char next = arr[i];
                        switch (next) {
                            case ',':
                                // escape not needed
                                break;
                            case 'Q':
                            case 'E':
                                // extra escape needed
                                sb.append('\\');
                            default:
                                sb.append('\\');
                        }
                        sb.append(next);
                    }
                }
                case '*' -> {
                    if (inClass != 0) {
                        sb.append('*');
                    } else if (i < last && arr[i + 1] == '*') {
                        i++;
                        sb.append("(.*)");
                    } else {
                        sb.append("([^\\\\/]*)");
                    }
                }
                case '?' -> {
                    if (inClass != 0) {
                        sb.append('?');
                    } else {
                        sb.append("(.)");
                    }
                }
                case '[' -> {
                    inClass++;
                    firstIndexInClass = i + 1;
                    sb.append('[');
                }
                case ']' -> {
                    inClass--;
                    sb.append(']');
                }
                case '.', '(', ')', '+', '|', '^', '$', '@', '%' -> {
                    if (inClass == 0 || (firstIndexInClass == i && ch == '^')) {
                        sb.append('\\');
                    }
                    sb.append(ch);
                }
                case '!' -> {
                    if (firstIndexInClass == i) {
                        sb.append('^');
                    } else {
                        sb.append('!');
                    }
                }
                case '{' -> {
                    inGroup++;
                    sb.append('(');
                }
                case '}' -> {
                    inGroup--;
                    sb.append(')');
                }
                case ',' -> {
                    if (inGroup > 0) {
                        sb.append('|');
                    } else {
                        sb.append(',');
                    }
                }
                default -> sb.append(ch);
            }
        }

        return Pattern.compile(sb.append('$').toString());
    }

    /**
     * Creates a new instance of a class using its no-arg constructor.
     *
     * @param type The type to be instantiated.
     * @param <T>  The type to be instantiated.
     * @return An instance of the specified type.
     */
    public static <T> T newInstance(Class<T> type) {
        try {
            Constructor<T> ctor = type.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (Exception e) {
            throw toUnchecked(e);
        }
    }

    /**
     * Enforce static class.
     */
    private MiscUtil() {
    }

}
