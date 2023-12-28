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
package org.fujion.model;

import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.IteratorUtils;

import java.util.*;

/**
 * Implementation of a list model.
 *
 * @param <M> The class of the model object.
 */
public class ListModel<M> implements IListModel<M> {

    private final LinkedList<M> list = new LinkedList<>();

    private final List<IListModelListener> listeners = new ArrayList<>();

    /**
     * Convenience method for converting a list to a list model. If the list is already a list
     * model, it is returned as is. Otherwise, a list model is created and populated with the
     * contents of the list. This method is null safe, so a null input will return a null output.
     *
     * @param <M> The class of the model object.
     * @param list The list to check.
     * @return A list model from the original list.
     */
    public static <M> IListModel<M> toListModel(List<M> list) {
        return list == null ? null : list instanceof IListModel ? (IListModel<M>) list : new ListModel<>(list);
    }

    public ListModel() {
    }

    public ListModel(Collection<M> list) {
        this.list.addAll(list);
    }

    @Override
    public void add(int index, M value) {
        list.add(index, value);
        fireEvent(ListEventType.ADD, index, index);
    }

    @Override
    public boolean add(M value) {
        if (list.add(value)) {
            int i = list.size() - 1;
            fireEvent(ListEventType.ADD, i, i);
            return true;
        }

        return false;
    }

    @Override
    public boolean addAll(Collection<? extends M> c) {
        return addAll(list.size(), c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends M> c) {
        int i = list.size();

        if (list.addAll(c)) {
            int delta = list.size() - i;
            fireEvent(ListEventType.ADD, index, index + delta - 1);
            return true;
        }

        return false;
    }

    @Override
    public boolean addEventListener(IListModelListener listener) {
        return listeners.add(listener);
    }

    @Override
    public void clear() {
        int i = list.size();

        if (i > 0) {
            list.clear();
            fireEvent(ListEventType.DELETE, 0, i - 1);
        }
    }

    @Override
    public boolean contains(Object value) {
        return list.contains(value);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    /**
     * Fires a list event to all listeners.
     *
     * @param type The type of event.
     * @param startIndex Index of the first affected element.
     * @param endIndex Index of last affected element.
     */
    private void fireEvent(ListEventType type, int startIndex, int endIndex) {
        for (IListModelListener listener : listeners) {
            listener.onListChange(type, startIndex, endIndex);
        }
    }

    @Override
    public M get(int index) {
        return list.get(index);
    }

    @Override
    public int indexOf(Object value) {
        return list.indexOf(value);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Iterator<M> iterator() {
        return IteratorUtils.unmodifiableIterator(list.iterator());
    }

    @Override
    public int lastIndexOf(Object value) {
        return list.lastIndexOf(value);
    }

    @Override
    public ListIterator<M> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<M> listIterator(int index) {
        return IteratorUtils.unmodifiableListIterator(list.listIterator(index));
    }

    @Override
    public M remove(int index) {
        M value = list.remove(index);
        fireEvent(ListEventType.DELETE, index, index);
        return value;
    }

    @Override
    public boolean remove(Object value) {
        int i = list.indexOf(value);

        if (i >= 0) {
            remove(i);
            return true;
        }

        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (list.removeAll(c)) {
            fireEvent(ListEventType.CHANGE, -1, -1);
            return true;
        }

        return false;
    }

    @Override
    public boolean removeRange(int start, int end) {
        validateIndex(start);
        validateIndex(end);
        boolean result = false;

        for (int i = end; i >= start; i--) {
            list.remove(i);
            result = true;
        }

        if (result) {
            fireEvent(ListEventType.DELETE, start, end);
        }

        return result;
    }

    /**
     * Throws an exception if the index is outside the bounds of the underlying list.
     *
     * @param index Index to validate
     * @exception IndexOutOfBoundsException If index is out of bounds.
     */
    private void validateIndex(int index) {
        if (index < 0 || index >= list.size()) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
    }

    @Override
    public void removeAllListeners() {
        listeners.clear();
    }

    @Override
    public boolean removeEventListener(IListModelListener listener) {
        return listeners.remove(listener);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (list.retainAll(c)) {
            fireEvent(ListEventType.CHANGE, -1, -1);
            return true;
        }

        return false;
    }

    @Override
    public M set(int index, M value) {
        M result = list.set(index, value);

        if (result != value) {
            fireEvent(ListEventType.REPLACE, index, index);
        }

        return result;
    }

    @Override
    public int size() {
        return list.size();
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public void sort(Comparator<? super M> comparator, boolean ascending) {
        comparator = comparator != null ? comparator : ComparatorUtils.NATURAL_COMPARATOR;
        comparator = ascending ? comparator : ComparatorUtils.reversedComparator(comparator);
        boolean changed = false;
        M[] a = (M[]) list.toArray();
        Arrays.sort(a, comparator);

        for (int newIndex = 0; newIndex < a.length; newIndex++) {
            int oldIndex = list.indexOf(a[newIndex]);
            changed |= oldIndex != newIndex;
            swap(newIndex, oldIndex);
        }

        if (changed) {
            fireEvent(ListEventType.SORT, -1, -1);
        }
    }

    @Override
    public List<M> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void swap(int index1, int index2) {
        if (index1 != index2) {
            M item1 = list.get(index1);
            M item2 = list.get(index2);
            list.set(index1, item2);
            list.set(index2, item1);
            fireEvent(ListEventType.SWAP, index1, index2);
        }
    }

    @Override
    public void swap(M item1, M item2) {
        swap(list.indexOf(item1), list.indexOf(item2));
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }
}
