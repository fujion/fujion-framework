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
 * Texture types for copyTex* methods.
 */
public enum TextureType2 implements IEnumWithValue {
    /**
     * A two-dimensional texture.
     */
    TEXTURE_2D(3553),
    /**
     * Negative X face for a cube-mapped texture.
     */
    TEXTURE_CUBE_MAP_NEGATIVE_X(34070),
    /**
     * Negative Y face for a cube-mapped texture.
     */
    TEXTURE_CUBE_MAP_NEGATIVE_Y(34072),
    /**
     * Negative Z face for a cube-mapped texture.
     */
    TEXTURE_CUBE_MAP_NEGATIVE_Z(34074),
    /**
     * Positive X face for a cube-mapped texture.
     */
    TEXTURE_CUBE_MAP_POSITIVE_X(34069),
    /**
     * Positive Y face for a cube-mapped texture.
     */
    TEXTURE_CUBE_MAP_POSITIVE_Y(34071),
    /**
     * Positive Z face for a cube-mapped texture.
     */
    TEXTURE_CUBE_MAP_POSITIVE_Z(34073);

    private final int value;

    TextureType2(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }
}
