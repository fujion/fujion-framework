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
 * Winding orientations.
 */
public enum WindingMode implements IEnumWithValue {
    /**
     * Counterclockwise winding.
     */
    CCW(2305),
    /**
     * Clockwise winding.
     */
    CW(2304);
    
    private final int value;
    
    WindingMode(int value) {
        this.value = value;
    }
    
    @Override
    public int value() {
        return value;
    }
    
}
