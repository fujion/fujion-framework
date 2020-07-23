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
package org.fujion.canvas.webgl;

import org.apache.commons.lang3.ArrayUtils;
import org.fujion.ancillary.IEnumWithValue;

/**
 * Value type of array elements.
 */
public enum ValueType implements IEnumWithValue {
    /**
     * Signed byte (8 bits).
     */
    BYTE(5120),
    /**
     * Signed integer (32 bits).
     */
    INT(5124),
    /**
     * Signed short (16 bits).
     */
    SHORT(5122),
    /**
     * Floating point (32 bit).
     */
    FLOAT(5126),
    /**
     * Floating point (16 bit).
     */
    HALF_FLOAT(5131),
    /**
     * Unsigned byte (8 bits).
     */
    UNSIGNED_BYTE(5121),
    /**
     * Unsigned short (16 bits).
     */
    UNSIGNED_SHORT(5123),
    /**
     * Unsigned integer (32 bits) (when using the OES_element_index_uint extension).
     */
    UNSIGNED_INT(5125);
    
    private final int value;

    /**
     * Validate that the specified value is one of the allowed types.
     *
     * @param value Value to check.
     * @param allowedValues Allowed types.
     * @exception IllegalArgumentException Thrown if the value is not allowed.
     */
    public static void validate(ValueType value, ValueType... allowedValues) {
        if (!ArrayUtils.contains(allowedValues, value)) {
            throw new IllegalArgumentException("Value type must be one of " + ArrayUtils.toString(allowedValues));
        }
    }

    ValueType(int value) {
        this.value = value;
    }
    
    @Override
    public int value() {
        return value;
    }
    
}
