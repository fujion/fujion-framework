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
import java.util.*;
import java.util.regex.Pattern;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.UnhandledException;

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
     * Returns true if the list contains the exact instance of the specified object.
     *
     * @param list List to search.
     * @param object Object instance to locate.
     * @return True if the object was found.
     */
    public static boolean containsInstance(List<?> list, Object object) {
        return indexOfInstance(list, object) > -1;
    }

    /**
     * Performs a lookup for the exact instance of an object in the list and returns its index, or
     * -1 if not found. This is different from the usual implementation of a list search that uses
     * the object's equals implementation.
     *
     * @param list List to search.
     * @param object Object instance to locate.
     * @return Index of the object.
     */
    public static int indexOfInstance(List<?> list, Object object) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == object) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Casts a list containing elements of class T to a list containing elements of a class S where
     * S must be assignment-compatible with T.
     *
     * @param <T> Class of list elements.
     * @param <S> Target class for cast.
     * @param list List to be recast.
     * @param clazz Class to which to cast list elements.
     * @return The recast list.
     */
    public static <T, S> List<S> castList(List<T> list, Class<S> clazz) {
        return castCollection(list, clazz);
    }

    /**
     * Casts a collection containing elements of class T to a collection containing elements of a
     * class S where S must be assignment-compatible with T.
     *
     * @param <T> Class of collection elements.
     * @param <CT> Class of Collection of T elements.
     * @param <S> Target class for cast.
     * @param <CS> Class of Collection of S elements.
     * @param collection Collection to be recast.
     * @param clazz Class to which to cast collection elements.
     * @return The recast collection.
     */
    @SuppressWarnings("unchecked")
    public static <CT extends Collection<T>, CS extends Collection<S>, T, S> CS castCollection(CT collection,
                                                                                               Class<S> clazz) {
        return (CS) collection;
    }

    /**
     * Returns a list iterator that produces only collection elements of the specified type.
     *
     * @param <T> Class of collection elements.
     * @param <S> Subclass of T to be used by iterator.
     * @param collection Collection to iterate.
     * @param type Type of element to return.
     * @return An iterator.
     */
    public static <T, S extends T> Iterator<S> iteratorForType(Collection<T> collection, Class<S> type) {
        return iteratorForType(collection.iterator(), type);
    }

    /**
     * Returns an iterator that returns only elements of the specified type.
     *
     * @param <T> Class of collection elements.
     * @param <S> Subclass of T to be used by iterator.
     * @param iterator Iterator to wrap.
     * @param type Type of element to return.
     * @return An iterator.
     */
    @SuppressWarnings("unchecked")
    public static <T, S extends T> Iterator<S> iteratorForType(Iterator<T> iterator, Class<S> type) {
        return iterator instanceof ListIterator
                ? IteratorUtils.filteredListIterator((ListIterator<T>) iterator, type::isInstance)
                        : IteratorUtils.filteredIterator(iterator, type::isInstance);
    }

    /**
     * Returns an iterable that produces only collection members of the specified type.
     *
     * @param <T> Class of collection elements.
     * @param <S> Subclass of T to be used by iterable.
     * @param collection Collection to iterate.
     * @param type Type of element to return.
     * @return An iterable.
     */
    public static <T, S extends T> Iterable<S> iterableForType(Collection<T> collection, Class<S> type) {
        return () -> iteratorForType(collection, type);
    }

    /**
     * Returns true if the two arrays intersect (i.e., have at least one element in common).
     *
     * @param ary1 The first array.
     * @param ary2 The second array.
     * @return True if the two arrays intersect.
     */
    public static boolean intersects(Object[] ary1, Object[] ary2) {
        return intersects(Arrays.asList(ary1), Arrays.asList(ary2));
    }
    
    /**
     * Returns true if the two iterables intersect (i.e., have at least one element in common).
     *
     * @param iter1 The first iterable.
     * @param iter2 The second iterable.
     * @return True if the two iterables intersect.
     */
    public static boolean intersects(Iterable<?> iter1, Iterable<?> iter2) {
        for (Object obj1 : iter1) {
            for (Object obj2 : iter2) {
                if (ObjectUtils.equals(obj1, obj2)) {
                    return true;
                }
            }
        }
        
        return false;
    }

    /**
     * Ensures that the list is not null, creating one if it is.
     *
     * @param list The list.
     * @param <T> The list's element type.
     * @return The original list, or a new one if the original was null.
     */
    public static <T> List<T> ensureList(List<T> list) {
        return list == null ? new ArrayList<T>() : list;
    }

    /**
     * Replaces the contents of the destination list with that of the source list.
     *
     * @param dest The destination list.  If null, a new list will be created.
     * @param source The source list.  Null is equivalent to an empty list.
     * @param <T> The lists' element type.
     * @return The modified (or newly created) destination list.
     */
    public static <T> List<T> replaceList(List<T> dest, Collection<T> source) {
        source = source == null ? Collections.emptyList() : source;

        if (dest == null) {
            dest = new ArrayList<>(source);
        } else {
            dest.clear();
            dest.addAll(source);
        }

        return dest;
    }

    /**
     * Replaces the contents of the destination list the specified elements.
     *
     * @param dest The destination list.  If null, a new list will be created.
     * @param elements A list of elements to be copied to the destination list.
     * @param <T> The lists' element type.
     * @return The modified (or newly created) destination list.
     */
    public static <T> List<T> replaceList(List<T> dest, T... elements) {
        return replaceList(dest, elements == null ? null : Arrays.asList(elements));
    }

    /**
     * Converts an array to a list, handling nulls.
     *
     * @param elements The array of elements.
     * @param <T> The element type.
     * @return The list containing the elements (null if input was null).
     */
    public static <T> List<T> toList(T... elements) {
        return elements == null ? null : Arrays.asList(elements);
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

        return new UnhandledException(e);
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
