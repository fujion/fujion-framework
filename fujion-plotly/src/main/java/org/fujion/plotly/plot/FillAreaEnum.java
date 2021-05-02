/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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
package org.fujion.plotly.plot;

/**
 * The area to fill with a solid color. Use with "fillcolor".
 */
public enum FillAreaEnum {
    
    /**
     * No fill. This is the default.
     */
    NONE,
    /**
     * Fill to y = 0.
     */
    TOZEROY,
    /**
     * Fill to x = 0.
     */
    TOZEROX,
    /**
     * Fill between the endpoints of this trace and the endpoints of the trace before it, connecting
     * those endpoints with straight lines (to make a stacked area graph); if there is no trace
     * before it, behaves like TOZEROY.
     */
    TONEXTY,
    /**
     * Fill between the endpoints of this trace and the endpoints of the trace before it, connecting
     * those endpoints with straight lines (to make a stacked area graph); if there is no trace
     * before it, behaves like TOZEROX.
     */
    TONEXTX,
    /**
     * Connects the endpoints of the trace (or each segment of the trace if it has gaps) into a
     * closed shape.
     */
    TOSELF,
    /**
     * Fills the space between two traces if one completely encloses the other (eg consecutive
     * contour lines), and behaves like TOSELF if there is no trace before it. Should not be used if
     * one trace does not enclose the other.
     */
    TONEXT;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
