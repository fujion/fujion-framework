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
package org.fujion.ancillary;

import org.fujion.client.IClientTransform;

/**
 * Implemented by enums that have an associated integer value. This value is to be used when passed
 * in a client invocation.
 */
public interface IEnumWithValue extends IClientTransform {
    
    /**
     * Returns the value associated with an enum element.
     *
     * @return Value associated with an enum element.
     */
    int value();
    
    /**
     * Transform to integer value for client invocation.
     *
     * @see org.fujion.client.IClientTransform#transformForClient()
     */
    @Override
    default Integer transformForClient() {
        return value();
    }

    /**
     * Looks up an enumeration constant given its numeric value.
     *
     * @param <T> The enum class.
     * @param enm The enum class.
     * @param value The numeric value to find.
     * @return The enumeration constant sought, or null if none found.
     */
    static <T extends Enum<T>> T fromValue(Class<T> enm, int value) {
        for (T mbr : enm.getEnumConstants()) {
            if (value == ((IEnumWithValue) mbr).value()) {
                return mbr;
            }
        }
        
        return null;
    }

}
