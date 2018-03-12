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
package org.fujion.canvas.webgl;

import org.fujion.ancillary.IEnumWithValue;

/**
 * Possible binding points for a buffer.
 */
public enum BufferBinding implements IEnumWithValue {
    /**
     * Buffer containing vertex attributes, such as vertex coordinates, texture coordinate data, or
     * vertex color data.
     */
    ARRAY_BUFFER(34962),
    /**
     * Buffer for copying from one buffer object to another. (WebGL 2 only)
     */
    COPY_READ_BUFFER(36662),
    /**
     * Buffer for copying from one buffer object to another. (WebGL 2 only)
     */
    COPY_WRITE_BUFFER(36663),
    /**
     * Buffer used for element indices.
     */
    ELEMENT_ARRAY_BUFFER(34963),
    /**
     * Buffer used for pixel transfer operations. (WebGL 2 only)
     */
    PIXEL_PACK_BUFFER(35051),
    /**
     * Buffer used for pixel transfer operations. (WebGL 2 only)
     */
    PIXEL_UNPACK_BUFFER(35052),
    /**
     * Buffer for transform feedback operations. (WebGL 2 only)
     */
    TRANSFORM_FEEDBACK_BUFFER(35982),
    /**
     * Buffer used for storing uniform blocks. (WebGL 2 only)
     */
    UNIFORM_BUFFER(35345);
    
    private int value;
    
    BufferBinding(int value) {
        this.value = value;
    }
    
    @Override
    public int value() {
        return value;
    }
}
