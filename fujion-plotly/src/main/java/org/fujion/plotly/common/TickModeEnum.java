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
 * The tick mode for an axis.
 */
public enum TickModeEnum {

    /**
     * The number of ticks is set via nticks.
     */
    AUTO,
    /**
     * The placement of the ticks is determined by a starting position "tick0" and a tick step
     * "dtick" (LINEAR is the default value if "tick0" and "dtick" are provided).
     */
    LINEAR,
    /**
     * The placement of the ticks is set via "tickvals" and the tick text is "ticktext". (ARRAY is
     * the default value if "tickvals" is provided).
     */
    ARRAY;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
