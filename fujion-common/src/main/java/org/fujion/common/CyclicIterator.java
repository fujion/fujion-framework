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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Generates an iterator that cycles over an underlying collection (i.e., when it has reached the
 * end of the collection, it starts over at the beginning).
 *
 * @param <T> The element type.
 */
public class CyclicIterator<T> implements Iterator<T> {
    
    private final Collection<T> collection;

    private Iterator<T> iterator;

    private final boolean hasNext;

    public CyclicIterator(Collection<T> collection) {
        this.collection = collection;
        hasNext = collection != null && !collection.isEmpty();
    }

    public CyclicIterator(T[] array) {
        this.collection = array == null ? null : Arrays.asList(array);
        hasNext = collection != null && !collection.isEmpty();
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }
    
    @Override
    public T next() {
        iterator = iterator == null || !iterator.hasNext() ? collection.iterator() : iterator;
        return iterator.next();
    }

    /**
     * Resets the iterator to the first element.
     */
    public void reset() {
        iterator = null;
    }
}
