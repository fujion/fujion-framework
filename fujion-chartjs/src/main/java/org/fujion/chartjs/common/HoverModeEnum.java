/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
package org.fujion.chartjs.common;

/**
 * Interaction modes.
 */
public enum HoverModeEnum {
    /**
     * Finds items in the same dataset. If the intersect setting is true, the first intersecting
     * item is used to determine the index in the data. If intersect false the nearest item is used
     * to determine the index.
     */
    DATASET,
    /**
     * Finds item at the same index. If the intersect setting is true, the first intersecting item
     * is used to determine the index in the data. If intersect false the nearest item, in the x
     * direction, is used to determine the index.
     */
    INDEX,
    /**
     * Gets the item that is nearest to the point. The nearest item is determined based on the
     * distance to the center of the chart item (point, bar). If 2 or more items are at the same
     * distance, the one with the smallest area is used. If intersect is true, this is only
     * triggered when the mouse position intersects an item in the graph. This is very useful for
     * combo charts where points are hidden behind bars.
     */
    NEAREST,
    /**
     * Finds all of the items that intersect the point.
     */
    POINT,
    /**
     * Returns all items that would intersect based on the X coordinate of the position only. Would
     * be useful for a vertical cursor implementation. Note that this only applies to Cartesian
     * charts.
     */
    X,
    /**
     * Returns all items that would intersect based on the Y coordinate of the position. This would
     * be useful for a horizontal cursor implementation. Note that this only applies to Cartesian
     * charts.
     */
    Y;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
