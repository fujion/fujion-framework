/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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
package org.fujion.ancillary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.fujion.client.IClientTransform;

/**
 * Subclasses HashMap to prevent entry of null values or empty collections/maps.
 */
public class OptionMap extends HashMap<String, Object> {
    
    /**
     * Interface for classes capable of generating an option map.
     */
    public interface IOptionMapConverter extends IClientTransform {
        
        /**
         * Return object as an option map.
         *
         * @return Option map derived from object instance.
         */
        OptionMap toMap();
        
        /**
         * @see org.fujion.client.IClientTransform#transformForClient()
         */
        @Override
        default Object transformForClient() {
            return toMap();
        }
    }
    
    private static final long serialVersionUID = 1L;
    
    /**
     * If the value is null, simply remove any existing value for this key.
     */
    @Override
    public Object put(String key, Object value) {
        value = convert(value);
        return isEmpty(value) ? remove(key) : super.put(key, value);
    }
    
    /**
     * Performs conversions on selected values types.
     *
     * @param value Value to convert.
     * @return Converted (or original) value.
     */
    private Object convert(Object value) {
        if (value != null) {
            if (value instanceof IOptionMapConverter) {
                value = ((IOptionMapConverter) value).toMap();
            } else if (value instanceof Collection) {
                value = convertCollection((Collection<?>) value);
            }
        }
        
        return value;
    }
    
    /**
     * Converts items in a collection.
     *
     * @param items Collection of items to be examined.
     * @return List of converted items.
     */
    private Collection<Object> convertCollection(Collection<?> items) {
        if (items.isEmpty()) {
            return null;
        }
        
        Collection<Object> list = new ArrayList<>();
        
        for (Object item : items) {
            list.add(convert(item));
        }
        
        return list;
    }
    
    /**
     * Returns true if the object is either null or is an empty map or collection.
     *
     * @param value The value to check.
     * @return True if the object is empty.
     */
    private boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        }
        
        if (value instanceof Collection) {
            return ((Collection<?>) value).isEmpty();
        }
        
        if (value instanceof Map) {
            return ((Map<?, ?>) value).isEmpty();
        }
        
        return false;
    }
}
