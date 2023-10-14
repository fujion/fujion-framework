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
package org.fujion.canvas.webgl;

import org.fujion.ancillary.IEnumWithValue;

/**
 * Types of texture parameters.
 */
public enum TextureParameterType implements IEnumWithValue {
    /**
     * Texture mipmap level.
     */
    TEXTURE_BASE_LEVEL(33084),
    /**
     * Comparison function.
     */
    TEXTURE_COMPARE_FUNC(34893),
    /**
     * Texture comparison mode.
     */
    TEXTURE_COMPARE_MODE(34892),
    /**
     * Immutability of the texture format and size.
     */
    TEXTURE_IMMUTABLE_FORMAT(37167),
    /**
     * Immutability levels.
     */
    TEXTURE_IMMUTABLE_LEVELS(33503),
    /**
     * Texture magnification filter.
     */
    TEXTURE_MAG_FILTER(10240),
    /**
     * Maximum texture mipmap array level.
     */
    TEXTURE_MAX_LEVEL(33085),
    /**
     * Texture maximum level-of-detail value .
     */
    TEXTURE_MAX_LOD(33083),
    /**
     * Texture minification filter.
     */
    TEXTURE_MIN_FILTER(10241),
    /**
     * Texture minimum level-of-detail value.
     */
    TEXTURE_MIN_LOD(33082),
    /**
     * Wrapping function for texture coordinate r.
     */
    TEXTURE_WRAP_R(32882),
    /**
     * Wrapping function for texture coordinate s.
     */
    TEXTURE_WRAP_S(10242),
    /**
     * Wrapping function for texture coordinate t.
     */
    TEXTURE_WRAP_T(10243);

    private final int value;

    TextureParameterType(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }

}
