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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Wraps a map, providing the ability to control how duplicate keys are handled.
 *
 * @param <KEY> The class of the indexing key.
 * @param <VALUE> The class of the stored item.
 */
public class RegistryMap<KEY, VALUE> implements Map<KEY, VALUE> {
    
    /**
     * Possible actions to take when attempting to store a duplicate key.
     */
    public enum DuplicateAction {
        /**
         * Replace existing key value (default).
         */
        REPLACE,
        /**
         * Ignore attempt to replace existing key value.
         */
        IGNORE,
        /**
         * Throw exception on duplicate key.
         */
        ERROR
    }
    
    private final Map<KEY, VALUE> map;
    
    private final DuplicateAction duplicateAction;
    
    /**
     * Defaults to concurrent hash map and replaceable keys.
     */
    public RegistryMap() {
        this(null, null);
    }
    
    /**
     * Wraps the specified map, allowing replaceable keys.
     *
     * @param map Map to be wrapped. If null, a concurrent hash map is created and used.
     */
    public RegistryMap(Map<KEY, VALUE> map) {
        this(map, null);
    }
    
    /**
     * Uses concurrent hash map.
     *
     * @param duplicateAction Behavior on attempt to replace existing key.
     */
    public RegistryMap(DuplicateAction duplicateAction) {
        this(null, duplicateAction);
    }
    
    /**
     * @param map Map to be wrapped. If null, a concurrent hash map is created and used.
     * @param duplicateAction Behavior on attempt to replace existing key.
     */
    public RegistryMap(Map<KEY, VALUE> map, DuplicateAction duplicateAction) {
        this.duplicateAction = duplicateAction == null ? DuplicateAction.REPLACE : duplicateAction;
        this.map = map == null ? new ConcurrentHashMap<>() : map;
    }
    
    @Override
    public int size() {
        return map.size();
    }
    
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }
    
    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }
    
    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }
    
    @Override
    public VALUE get(Object key) {
        return map.get(key);
    }
    
    @Override
    public VALUE put(KEY key, VALUE value) {
        VALUE oldValue = null;
        
        if (key != null) {
            oldValue = map.get(key);
            
            if (value == null) {
                map.remove(key);
                return oldValue;
            }
            
            if (oldValue == null) {
                map.put(key, value);
            } else {
                switch (duplicateAction) {
                    case IGNORE:
                        break;
                    
                    case REPLACE:
                        map.put(key, value);
                        break;
                    
                    case ERROR:
                        Assert.isTrue(oldValue.equals(value),
                                "Cannot modify existing entry with the key '%s'.", key);
                        break;
                }
            }
        }
        
        return oldValue;
    }
    
    @Override
    public VALUE remove(Object key) {
        return map.remove(key);
    }
    
    @Override
    public void putAll(Map<? extends KEY, ? extends VALUE> m) {
        for (Entry<? extends KEY, ? extends VALUE> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public void clear() {
        map.clear();
    }
    
    @Override
    public Set<KEY> keySet() {
        return Collections.unmodifiableSet(map.keySet());
    }
    
    @Override
    public Collection<VALUE> values() {
        return Collections.unmodifiableCollection(map.values());
    }
    
    @Override
    public Set<java.util.Map.Entry<KEY, VALUE>> entrySet() {
        return Collections.unmodifiableSet(map.entrySet());
    }
    
}
