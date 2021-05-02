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
package org.fujion.canvas.d2;

/**
 * Determines how two connecting segments (of lines, arcs or curves) with non-zero lengths in a
 * shape are joined together (degenerate segments with zero lengths, whose specified endpoints and
 * control points are exactly at the same position, are skipped).
 */
public enum LineJoin {

    /**
     * Fills an additional triangular area between the common endpoint of connected segments, and
     * the separate outside rectangular corners of each segment.
     */
    BEVEL,
    /**
     * Connected segments are joined by extending their outside edges to connect at a single point,
     * with the effect of filling an additional lozenge-shaped area. This setting is affected by the
     * miterLimit property.
     */
    MITER,
    /**
     * Rounds off the corners of a shape by filling an additional sector of disc centered at the
     * common endpoint of connected segments. The radius for these rounded corners is equal to the
     * line width.
     */
    ROUND;
    
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
