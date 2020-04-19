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
 * Sets both the RGB blend equation and alpha blend equation to a single equation.
 */
public enum BlendMode implements IEnumWithValue {
    /**
     * source + destination
     */
    FUNC_ADD(32774),
    /**
     * source - destination
     */
    FUNC_SUBTRACT(32778),
    /**
     * Maximum of source and destination
     */
    MAX(32776),
    /**
     * Minimum of source and destination
     */
    MIN(32775);
    
    private int value;
    
    BlendMode(int value) {
        this.value = value;
    }
    
    @Override
    public int value() {
        return value;
    }
}
