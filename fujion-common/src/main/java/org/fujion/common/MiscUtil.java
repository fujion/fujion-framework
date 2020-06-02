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
package org.fujion.common;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
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
                case '\\':
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
                    break;

                case '*':
                    if (inClass != 0) {
                        sb.append('*');
                    } else if (i < last && arr[i + 1] == '*') {
                        i++;
                        sb.append("(.*)");
                    } else {
                        sb.append("([^\\\\/]*)");
                    }
                    break;

                case '?':
                    if (inClass != 0) {
                        sb.append('?');
                    } else {
                        sb.append("(.)");
                    }
                    break;

                case '[':
                    inClass++;
                    firstIndexInClass = i + 1;
                    sb.append('[');
                    break;

                case ']':
                    inClass--;
                    sb.append(']');
                    break;

                case '.':
                case '(':
                case ')':
                case '+':
                case '|':
                case '^':
                case '$':
                case '@':
                case '%':
                    if (inClass == 0 || (firstIndexInClass == i && ch == '^')) {
                        sb.append('\\');
                    }
                    sb.append(ch);
                    break;

                case '!':
                    if (firstIndexInClass == i) {
                        sb.append('^');
                    } else {
                        sb.append('!');
                    }
                    break;

                case '{':
                    inGroup++;
                    sb.append('(');
                    break;

                case '}':
                    inGroup--;
                    sb.append(')');
                    break;

                case ',':
                    if (inGroup > 0) {
                        sb.append('|');
                    } else {
                        sb.append(',');
                    }
                    break;

                default:
                    sb.append(ch);
            }
        }
        
        return Pattern.compile(sb.append('$').toString());
    }
    
    /**
     * Enforce static class.
     */
    private MiscUtil() {
    }
}
