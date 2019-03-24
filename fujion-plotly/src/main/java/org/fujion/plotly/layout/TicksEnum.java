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
package org.fujion.plotly.layout;

/**
 * Controls if and where ticks are drawn.
 */
public enum TicksEnum {
    /**
     * Ticks are drawn inside the axis lines.
     */
    INSIDE,
    /**
     * Ticks are drawn outside the axis lines.
     */
    OUTSIDE,
    /**
     * No ticks are drawn.
     */
    NONE;

    @Override
    public String toString() {
        return this == TicksEnum.NONE ? "" : name().toLowerCase();
    }
    
}
