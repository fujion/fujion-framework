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
 * Functions that compare incoming pixel depth to the current depth buffer value.
 */
public enum DepthFunction implements IEnumWithValue {
    
    /**
     * Always pass.
     */
    ALWAYS(519),
    /**
     * Pass if the incoming value equals the the depth buffer value.
     */
    EQUAL(514),
    /**
     * Pass if the incoming value is greater than or equal to the depth buffer value.
     */
    GEQUAL(518),
    /**
     * Pass if the incoming value is greater than the depth buffer value.
     */
    GREATER(516),
    /**
     * Pass if the incoming value is less than or equal to the depth buffer value.
     */
    LEQUAL(515),
    /**
     * Pass if the incoming value is less than the depth buffer value.
     */
    LESS(513),
    /**
     * Never pass.
     */
    NEVER(512),
    /**
     * Pass if the incoming value is not equal to the depth buffer value.
     */
    NOTEQUAL(517);
    
    private int value;
    
    DepthFunction(int value) {
        this.value = value;
    }
    
    @Override
    public int value() {
        return value;
    }
    
}
