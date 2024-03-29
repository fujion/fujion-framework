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

public enum TextureType implements IEnumWithValue {
    /**
     * A two-dimensional texture.
     */
    TEXTURE_2D(3553),
    /**
     * A two-dimensional array texture. (WebGL 2 only)
     */
    TEXTURE_2D_ARRAY(35866),
    /**
     * A three-dimensional texture. (WebGL 2 only)
     */
    TEXTURE_3D(32879),
    /**
     * A cube-mapped texture.
     */
    TEXTURE_CUBE_MAP(34067);
    
    private final int value;
    
    TextureType(int value) {
        this.value = value;
    }
    
    @Override
    public int value() {
        return value;
    }
}
