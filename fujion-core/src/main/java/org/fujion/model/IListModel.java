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
package org.fujion.model;

import java.util.Comparator;
import java.util.List;

/**
 * Describes a tracked list used to hold model objects.
 *
 * @param <M> The type of the model object.
 */
public interface IListModel<M> extends List<M> {

    /**
     * Interface for listeners receiving events when the list is modified.
     */
    interface IListModelListener {

        void onListChange(ListEventType type, int startIndex, int endIndex);
    }

    /**
     * Types of list mutation operations.
     */
    enum ListEventType {
        ADD, DELETE, REPLACE, SWAP, CHANGE, SORT
    }

    /**
     * Register a listener to receive events when the list is modified.
     *
     * @param listener The listener to add.
     * @return True if the listener was registered. False if already registered.
     */
    boolean addEventListener(IListModelListener listener);

    /**
     * Removes all listeners.
     */
    void removeAllListeners();

    /**
     * Removes a single event listener.
     *
     * @param listener The listener to remove.
     * @return True if the listener was removed. False if it was not registered.
     */
    boolean removeEventListener(IListModelListener listener);

    /**
     * Removes a range of elements by index.
     *
     * @param start Start of range (inclusive).
     * @param end End of range (inclusive).
     * @return True if the model changed as a result of the operation.
     */
    boolean removeRange(int start, int end);
    
    /**
     * Swap two list entries by index.
     *
     * @param index1 Index of first entry.
     * @param index2 Index of second entry.
     */
    void swap(int index1, int index2);

    /**
     * Swap two list entries.
     *
     * @param value1 The first entry.
     * @param value2 The second entry.
     */
    void swap(M value1, M value2);

    /**
     * Sorts the list given the comparator and the sort direction.
     *
     * @param comparator The comparator to use in sorting. If null, a comparator using the model
     *            objects' native sort order will be applied.
     * @param ascending If true, sort list in ascending order; otherwise, in descending order.
     */
    void sort(Comparator<? super M> comparator, boolean ascending);

    /**
     * Sorts the list in ascending order given the comparator.
     *
     * @param comparator The comparator to use in sorting. If null, a comparator using the model
     *            objects' native sort order will be applied.
     */
    @Override
    default void sort(Comparator<? super M> comparator) {
        sort(comparator, true);
    }
}
