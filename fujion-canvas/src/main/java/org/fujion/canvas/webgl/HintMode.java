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
 * Available hint modes.
 */
public enum HintMode implements IEnumWithValue {
    /**
     * There is no preference for this behavior.
     */
    DONT_CARE(4352),
    /**
     * The most efficient behavior should be used.
     */
    FASTEST(4353),
    /**
     * The most correct or the highest quality option should be used.
     */
    NICEST(4354);

    private int value;
    
    HintMode(int value) {
        this.value = value;
    }
    
    @Override
    public int value() {
        return value;
    }
    
}
