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
 * Available shader types.
 */
public enum ShaderType implements IEnumWithValue {
    /**
     * A fragment shader.
     */
    FRAGMENT_SHADER(35632),
    /**
     * A vertex shader.
     */
    VERTEX_SHADER(35633);

    private final int value;
    
    ShaderType(int value) {
        this.value = value;
    }
    
    @Override
    public int value() {
        return value;
    }
}
