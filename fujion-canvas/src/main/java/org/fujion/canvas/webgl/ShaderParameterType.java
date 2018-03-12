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
 * Types of shader parameters.
 */
public enum ShaderParameterType implements IEnumWithValue {
    /**
     * Whether or not the last shader compilation was successful.
     */
    COMPILE_STATUS(35713),
    /**
     * Whether or not the shader is flagged for deletion.
     */
    DELETE_STATUS(35712),
    /**
     * The shader type (ShaderType enum).
     */
    SHADER_TYPE(35663);

    private int value;

    ShaderParameterType(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }

}
