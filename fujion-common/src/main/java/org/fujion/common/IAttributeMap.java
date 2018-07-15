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
package org.fujion.common;

import java.util.Map;

/**
 * Implemented by classes that support attribute maps. Implementers need only implement the
 * <code>getAttributes</code> method. All other methods have default implementations.
 *
 * @param <K> The type of attribute key.
 * @param <V> The type of attribute value.
 */
public interface IAttributeMap<K, V> {

    /**
     * Returns the attribute map.
     *
     * @return The attribute map.
     */
    Map<K, V> getAttributes();
    
    /**
     * Returns true if the keyed attribute exists.
     *
     * @param key The attribute key.
     * @return True if the keyed attribute exists.
     */
    default boolean hasAttribute(K key) {
        return getAttributes().containsKey(key);
    }
    
    /**
     * Returns the value of the keyed attribute.
     *
     * @param key The attribute key.
     * @return The value of the keyed attribute, or null if not found.
     */
    default V getAttribute(K key) {
        return getAttributes().get(key);
    }
    
    /**
     * Sets the value of the keyed attribute.
     *
     * @param key The attribute key.
     * @param value The new value.
     * @return The previous value, or null if none.
     */
    default V setAttribute(K key, V value) {
        return getAttributes().put(key, value);
    }
    
    /**
     * Removes the keyed attribute.
     *
     * @param key The attribute key.
     * @return The previous value, or null if none.
     */
    default V removeAttribute(K key) {
        return getAttributes().remove(key);
    }
}
