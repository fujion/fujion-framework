/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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
 * Stencil functions.
 */
public enum StencilFunction implements IEnumWithValue {
    /**
     * Decrements the current stencil buffer value. Clamps to 0.
     */
    DECR(7683),
    /**
     * Decrements the current stencil buffer value. Wraps stencil buffer value to the maximum
     * representable unsigned value when decrementing a stencil buffer value of 0.
     */
    DECR_WRAP(34056),
    /**
     * Increments the current stencil buffer value. Clamps to the maximum representable unsigned
     * value.
     */
    INCR(7682),
    /**
     * Increments the current stencil buffer value. Wraps stencil buffer value to zero when
     * incrementing the maximum representable unsigned value.
     */
    INCR_WRAP(34055),
    /**
     * Inverts the current stencil buffer value bitwise.
     */
    INVERT(5386),
    /**
     * Keeps the current value.
     */
    KEEP(7680),
    /**
     * Sets the stencil buffer value to the reference value as specified by
     * {@link RenderingContextWebGL#stencilFunc}.
     */
    REPLACE(7681),
    /**
     * Sets the stencil buffer value to 0.
     */
    ZERO(0);
    
    private int value;

    StencilFunction(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }

}
