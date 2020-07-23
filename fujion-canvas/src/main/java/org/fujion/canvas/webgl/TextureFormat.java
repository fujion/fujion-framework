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

import org.fujion.ancillary.IEnumWithValue;

/**
 * Internal formats for texture buffers.
 */
public enum TextureFormat implements IEnumWithValue {
    /**
     * Discards the red, green and blue components and reads the alpha component.
     */
    ALPHA(6406),
    /**
     * Each color component is a luminance component, alpha is 1.0.
     */
    LUMINANCE(6409),
    /**
     * Each component is a luminance/alpha component.
     */
    LUMINANCE_ALPHA(6410),
    /**
     * WebGL 2 only
     */
    RED(6403),
    /**
     * WebGL 2 only
     */
    RED_INTEGER(36244),
    /**
     * WebGL 2 only
     */
    RG(33319),
    /**
     * WebGL 2 only
     */
    RG_INTEGER(33320),
    /**
     * Discards the alpha components and reads the red, green and blue components.
     */
    RGB(6407),
    /**
     * Red, green, blue and alpha components are read from the color buffer.
     */
    RGBA(6408),
    /**
     * WebGL 2 only
     */
    RGBA_INTEGER(36249);

    private final int value;

    TextureFormat(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }

}
