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
package org.fujion.plotly.common;

/**
 * Determines a formatting rule for the exponents.
 */
public enum ExponentFormatEnum {

    /**
     * Suppresses exponential form (e.g., 1,000,000,000).
     */
    NONE("none"),
    /**
     * Lower case exponent character (e.g., 1e+9).
     */
    LOWER_E("e"),
    /**
     * Upper case exponent character (e.g., 1E+9).
     */
    UPPER_E("E"),
    /**
     * Show in power notation (e.g., 1x10^9, with 9 as a superscript).
     */
    POWER("power"),
    /**
     * Use SI units (e.g., 1G)
     */
    SI("SI"),
    /**
     * Use number units (e.g., 1B)
     */
    B("B");

    private final String value;
    
    ExponentFormatEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
