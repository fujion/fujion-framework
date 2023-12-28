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
 * Formulas for the blending colors.
 */
public enum BlendFactor implements IEnumWithValue {
    /**
     * Multiplies all colors by a constant alpha value.
     */
    CONSTANT_ALPHA(32771),
    /**
     * Multiplies all colors by a constant color.
     */
    CONSTANT_COLOR(32769),
    /**
     * Multiplies all colors by the destination alpha value.
     */
    DST_ALPHA(772),
    /**
     * Multiplies all colors by the destination color.
     */
    DST_COLOR(774),
    /**
     * Multiplies all colors by 1.
     */
    ONE(1),
    /**
     * Multiplies all colors by 1 minus a constant alpha value.
     */
    ONE_MINUS_CONSTANT_ALPHA(32772),
    /**
     * Multiplies all colors by 1 minus a constant color.
     */
    ONE_MINUS_CONSTANT_COLOR(32770),
    /**
     * Multiplies all colors by 1 minus the destination alpha value.
     */
    ONE_MINUS_DST_ALPHA(773),
    /**
     * Multiplies all colors by 1 minus each destination color.
     */
    ONE_MINUS_DST_COLOR(775),
    /**
     * Multiplies all colors by 1 minus the source alpha value.
     */
    ONE_MINUS_SRC_ALPHA(771),
    /**
     * Multiplies all colors by 1 minus each source color.
     */
    ONE_MINUS_SRC_COLOR(769),
    /**
     * Multiplies all colors by the source alpha value.
     */
    SRC_ALPHA(770),
    /**
     * Multiplies the RGB colors by the smallest of either the source alpha value or the value of 1
     * minus the destination alpha value. The alpha value is multiplied by 1.
     */
    SRC_ALPHA_SATURATE(776),
    /**
     * Multiplies all colors by the source colors.
     */
    SRC_COLOR(768),
    /**
     * Multiplies all colors by 0.
     */
    ZERO(0);
    
    private final int value;
    
    BlendFactor(int value) {
        this.value = value;
    }
    
    @Override
    public int value() {
        return value;
    }
    
}
