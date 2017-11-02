/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections.IteratorUtils;
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
     * Casts a list containing elements of class T to a list containing elements of a subclass S.
     *
     * @param <T> Class of list elements.
     * @param <S> Target subclass for cast.
     * @param list List to be recast.
     * @param clazz Class to which to cast list elements.
     * @return The recast list.
     */
    @SuppressWarnings("unchecked")
    public static <T, S extends T> List<S> castList(List<T> list, Class<S> clazz) {
        return (List<S>) list;
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
                ? IteratorUtils.filteredListIterator((ListIterator<T>) iterator, (element) -> {
                    return type.isInstance(element);
                })
                : IteratorUtils.filteredIterator(iterator, (element) -> {
                    return type.isInstance(element);
                });
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
        return () -> {
            return iteratorForType(collection, type);
        };
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
     * Enforce static class.
     */
    private MiscUtil() {
    }
}
