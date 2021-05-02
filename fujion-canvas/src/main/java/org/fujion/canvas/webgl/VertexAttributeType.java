/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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

import org.fujion.ancillary.IEnumWithValue;

/**
 * Types of vertex attributes.
 */
public enum VertexAttributeType implements IEnumWithValue {
    /**
     * A Double[4] array representing the current value of the vertex attribute at the given index.
     */
    CURRENT_VERTEX_ATTRIB(34342),
    /**
     * The currently bound WebGLBuffer.
     */
    VERTEX_ATTRIB_ARRAY_BUFFER_BINDING(34975),
    /**
     * Frequency divisor used for instanced rendering.
     */
    VERTEX_ATTRIB_ARRAY_DIVISOR(35070),
    /**
     * Whether the vertex attribute is enabled at this index.
     */
    VERTEX_ATTRIB_ARRAY_ENABLED(34338),
    /**
     * Whether or not an integer data type is in the vertex attribute array at the given index.
     */
    VERTEX_ATTRIB_ARRAY_INTEGER(35069),
    /**
     * Whether fixed-point data types are normalized for the vertex attribute array at the given
     * index.
     */
    VERTEX_ATTRIB_ARRAY_NORMALIZED(34922),
    /**
     * The size of an element of the vertex array.
     */
    VERTEX_ATTRIB_ARRAY_SIZE(34339),
    /**
     * The number of bytes between successive elements in the array. 0 means that the elements are
     * sequential.
     */
    VERTEX_ATTRIB_ARRAY_STRIDE(34340),
    /**
     * The array type (see {@link ValueType}).
     */
    VERTEX_ATTRIB_ARRAY_TYPE(34341);

    private final int value;
    
    VertexAttributeType(int value) {
        this.value = value;
    }
    
    @Override
    public int value() {
        return value;
    }
    
}
