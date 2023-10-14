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

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Generates an iterator that cycles over an underlying iterable (i.e., when it has reached the end
 * of the iteration, it starts over at the beginning).
 *
 * @param <T> The element type.
 */
public class CyclicIterator<T> implements Iterator<T> {

    private final Iterable<T> iterable;
    
    private final boolean hasNext;
    
    private Iterator<T> iterator;
    
    public CyclicIterator(Iterable<T> iterable) {
        this.iterable = iterable;
        reset();
        hasNext = iterator != null && iterator.hasNext();
    }
    
    public CyclicIterator(T[] array) {
        this(array == null ? null : Arrays.asList(array));
    }
    
    /**
     * This will always return true unless the wrapped iterable was null or empty.
     *
     * @return True unless the wrapped iterable was null or empty.
     */
    @Override
    public boolean hasNext() {
        return hasNext;
    }

    /**
     * Returns the next element in the underlying iteration, cycling to the first element if at the
     * end.
     */
    @Override
    public T next() {
        if (!hasNext) {
            throw new NoSuchElementException();
        }

        if (!iterator.hasNext()) {
            reset();
        }

        return iterator.next();
    }
    
    /**
     * Resets the iterator to the first element.
     */
    public void reset() {
        iterator = iterable == null ? null : iterable.iterator();
    }
}
