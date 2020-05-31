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

import org.fujion.common.RegistryMap.DuplicateAction;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Abstract base class for thread-safe registry of shared objects.
 *
 * @param <KEY> The class of the indexing key.
 * @param <VALUE> The class of the registered item.
 */
public abstract class AbstractRegistry<KEY, VALUE> implements Iterable<VALUE> {

    protected final Map<KEY, VALUE> map;

    protected AbstractRegistry() {
        this(null);
    }

    protected AbstractRegistry(DuplicateAction duplicateAction) {
        this(null, duplicateAction);
    }

    protected AbstractRegistry(Map<KEY, VALUE> map, DuplicateAction duplicateAction) {
        this.map = new RegistryMap<>(map, duplicateAction);
    }

    /**
     * Returns the key to use to store the item.
     *
     * @param item Item whose key is sought.
     * @return Key for the item.
     */
    protected abstract KEY getKey(VALUE item);

    /**
     * Returns the value associated with the specified key.
     *
     * @param key Key whose associated value is sought.
     * @return Value associated with the key.
     */
    public VALUE get(KEY key) {
        return map.get(key);
    }

    /**
     * Returns true if the registry contains the specified item.
     *
     * @param key Key of item.
     * @return True if the item is present.
     */
    public boolean contains(KEY key) {
        return map.containsKey(key);
    }

    /**
     * Returns a read-only collection of all registry entries.
     *
     * @return Collection of registry entries.
     */
    public Collection<VALUE> getAll() {
        return map.values();
    }

    /**
     * Adds an item to the registry.
     *
     * @param item Item to add.
     */
    public void register(VALUE item) {
        if (item != null) {
            KEY key = getKey(item);
            map.put(key, item);
            onRegister(key, item);
        }
    }

    /**
     * Called when a registry entry is added.  Default implementation does nothing.
     *
     * @param key The key of the item added.
     * @param value The item that was added.
     */
    protected void onRegister(KEY key, VALUE value) {
    }

    /**
     * Removes an item from the registry.
     *
     * @param item The item to remove.
     * @return The item that was removed, or null if not found.
     */
    public VALUE unregister(VALUE item) {
        return unregisterByKey(getKey(item));
    }

    /**
     * Removes an item from the registry using its key value.
     *
     * @param key The key of the item to remove.
     * @return The item that was removed, or null if not found.
     */
    public VALUE unregisterByKey(KEY key) {
        VALUE value = map.remove(key);

        if (value != null) {
            onUnregister(key, value);
        }

        return value;
    }

    /**
     * Called when a registry entry is removed.  Default implementation does nothing.
     *
     * @param key The key of the item removed.
     * @param value The item that was removed.
     */
    protected void onUnregister(KEY key, VALUE value) {
    }

    /**
     * Remove all registry entries.
     */
    protected void clear() {
        map.clear();
    }

    /**
     * Return number of entries.
     *
     * @return Number of entries.
     */
    public int size() {
        return map.size();
    }

    /**
     * Iterate over value set.
     */
    @Override
    public Iterator<VALUE> iterator() {
        return map.values().iterator();
    }

}
