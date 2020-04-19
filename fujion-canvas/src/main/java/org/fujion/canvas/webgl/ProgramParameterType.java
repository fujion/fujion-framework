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
 * Types of program parameters.
 */
public enum ProgramParameterType implements IEnumWithValue {
    /**
     * The number of active attribute variables to a program.
     */
    ACTIVE_ATTRIBUTES(35721),
    /**
     * The number of uniform blocks containing active uniforms.
     */
    ACTIVE_UNIFORM_BLOCKS(35382),
    /**
     * The number of active uniform variables to a program.
     */
    ACTIVE_UNIFORMS(35718),
    /**
     * The number of attached shaders to a program.
     */
    ATTACHED_SHADERS(35717),
    /**
     * Whether or not the program is flagged for deletion.
     */
    DELETE_STATUS(35712),
    /**
     * Whether or not the last link operation was successful.
     */
    LINK_STATUS(35714),
    /**
     * The buffer mode when transform feedback is active. Returns a FeedbackBufferMode.
     */
    TRANSFORM_FEEDBACK_BUFFER_MODE(35967),
    /**
     * The number of varying variables to capture in transform feedback mode.
     */
    TRANSFORM_FEEDBACK_VARYINGS(35971),
    /**
     * Whether or not the last validation operation was successful.
     */
    VALIDATE_STATUS(35715);
    
    private int value;

    ProgramParameterType(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }

}
