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
 * Data type of the pixel data
 */
public enum PixelDataType implements IEnumWithValue {
    // @formatter:off

    FLOAT(5126),
    UNSIGNED_BYTE(5121),
    UNSIGNED_SHORT_4_4_4_4(32819),
    UNSIGNED_SHORT_5_5_5_1(32820),
    UNSIGNED_SHORT_5_6_5(33635);
    // @formatter:on
    
    private int value;

    PixelDataType(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }

}
