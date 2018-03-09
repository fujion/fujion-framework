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
package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;

/**
 * Options for tick marks.
 */
public abstract class TickOptions extends Options {
    
    /**
     * If true, automatically calculates how many labels that can be shown and hides labels
     * accordingly. Turn it off to show all labels no matter what.
     * <p>
     * Default: true
     */
    public Boolean autoSkip;
    
    /**
     * Padding between the ticks on the horizontal axis when autoSkip is enabled. Note: Only
     * applicable to horizontal scales.
     * <p>
     * Default: 0
     */
    public Integer autoSkipPadding;
    
    /**
     * Distance in pixels to offset the label from the centre point of the tick (in the y direction
     * for the x axis, and the x direction for the y axis). Note: this can cause labels at the edges
     * to be cropped by the edge of the canvas.
     * <p>
     * Default: 0
     */
    public Integer labelOffset;
    
    /**
     * Maximum rotation for tick labels when rotating to condense labels. Note: Rotation doesn't
     * occur until necessary. Note: Only applicable to horizontal scales.
     * <p>
     * Default: 90
     */
    public Integer maxRotation;
    
    /**
     * Minimum rotation for tick labels. Note: Only applicable to horizontal scales.
     * <p>
     * Default: 0
     */
    public Integer minRotation;
    
    /**
     * Flips tick labels around axis, displaying the labels inside the chart instead of outside.
     * Note: Only applicable to vertical scales.
     * <p>
     * Default: false
     */
    public Boolean mirror;
    
    /**
     * Padding between the tick label and the axis. When set on a vertical axis, this applies in the
     * horizontal (X) direction. When set on a horizontal axis, this applies in the vertical (Y)
     * direction.
     * <p>
     * Default: 10
     */
    public Integer padding;
    
}
