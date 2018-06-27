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
package org.fujion.canvas.webgl;

import org.fujion.ancillary.IEnumWithValue;

/**
 * Internal formats for texture buffers.
 */
public enum TextureBufferInternalFormat implements IEnumWithValue {
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
    R11F_G11F_B10F(35898),
    /**
     * WebGL 2 only
     */
    R16F(33325),
    /**
     * WebGL 2 only
     */
    R32F(33326),
    /**
     * WebGL 2 only
     */
    R8(33321),
    /**
     * WebGL 2 only
     */
    R8UI(33330),
    /**
     * WebGL 2 only
     */
    RG16F(33327),
    /**
     * WebGL 2 only
     */
    RG16UI(33338),
    /**
     * WebGL 2 only
     */
    RG32F(33328),
    /**
     * WebGL 2 only
     */
    RG32UI(33340),
    /**
     * WebGL 2 only
     */
    RG8(33323),
    /**
     * WebGL 2 only
     */
    RG8UI(33336),
    /**
     * Discards the alpha components and reads the red, green and blue components.
     */
    RGB(6407),
    /**
     * WebGL 2 only
     */
    RGB10_A2(32857),
    /**
     * WebGL 2 only
     */
    RGB16F(34843),
    /**
     * WebGL 2 only
     */
    RGB32F(34837),
    /**
     * WebGL 2 only
     */
    RGB5_A1(32855),
    /**
     * WebGL 2 only
     */
    RGB565(36194),
    /**
     * WebGL 2 only
     */
    RGB8(32849),
    /**
     * WebGL 2 only
     */
    RGB8UI(36221),
    /**
     * WebGL 2 only
     */
    RGB9_E5(35901),
    /**
     * Red, green, blue and alpha components are read from the color buffer.
     */
    RGBA(6408),
    /**
     * WebGL 2 only
     */
    RGBA16F(34842),
    /**
     * WebGL 2 only
     */
    RGBA32F(34836),
    /**
     * WebGL 2 only
     */
    RGBA4(32854),
    /**
     * WebGL 2 only
     */
    RGBA8(32856),
    /**
     * WebGL 2 only
     */
    RGBA8UI(36220),
    /**
     * WebGL 2 only
     */
    SRGB8(35905),
    /**
     * WebGL 2 only
     */
    SRGB8_ALPHA8(35907);
    
    private int value;
    
    TextureBufferInternalFormat(int value) {
        this.value = value;
    }
    
    @Override
    public int value() {
        return value;
    }
    
}
