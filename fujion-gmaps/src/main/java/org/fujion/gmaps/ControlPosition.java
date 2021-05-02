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
package org.fujion.gmaps;

/**
 * Identifiers used to specify the placement of controls on the map. Controls are positioned
 * relative to other controls in the same layout position. Controls that are added first are
 * positioned closer to the edge of the map. Elements in the top or bottom row flow towards the
 * middle of the row. Elements in the left or right column flow towards the middle of the column.
 */
public enum ControlPosition {
    /**
     * Elements are positioned in the center of the bottom row.
     */
    BOTTOM_CENTER,
    /**
     * Elements are positioned in the bottom left and flow towards the middle. Elements are
     * positioned to the right of the Google logo.
     */
    BOTTOM_LEFT,
    /**
     * Elements are positioned in the bottom right and flow towards the middle. Elements are
     * positioned to the left of the copyrights.
     */
    BOTTOM_RIGHT,
    /**
     * Elements are positioned on the left, above bottom-left elements, and flow upwards.
     */
    LEFT_BOTTOM,
    /**
     * Elements are positioned in the center of the left side.
     */
    LEFT_CENTER,
    /**
     * Elements are positioned on the left, below top-left elements, and flow downwards.
     */
    LEFT_TOP,
    /**
     * Elements are positioned on the right, above bottom-right elements, and flow upwards.
     */
    RIGHT_BOTTOM,
    /**
     * Elements are positioned in the center of the right side.
     */
    RIGHT_CENTER,
    /**
     * Elements are positioned on the right, below top-right elements, and flow downwards.
     */
    RIGHT_TOP,
    /**
     * Elements are positioned in the center of the top row.
     */
    TOP_CENTER,
    /**
     * Elements are positioned in the top left and flow towards the middle.
     */
    TOP_LEFT,
    /**
     * Elements are positioned in the top right and flow towards the middle.
     */
    TOP_RIGHT
}
