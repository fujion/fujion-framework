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
package org.fujion.chartjs.axis;

import org.fujion.annotation.Option;
import org.fujion.chartjs.common.GridLineOptions;
import org.fujion.chartjs.common.PositionEnum;
import org.fujion.chartjs.common.ScaleLabelOptions;

/**
 * Options common to all Cartesian axes.
 */
public abstract class CartesianAxisOptions extends AxisOptions {
    
    /**
     * If true, automatically calculates how many labels that can be shown and hides labels
     * accordingly. Turn it off to show all labels no matter what.
     * <p>
     * Default: true
     */
    @Option
    public Boolean autoSkip;
    
    /**
     * Padding between the ticks on the horizontal axis when autoSkip is enabled. Note: Only
     * applicable to horizontal scales.
     * <p>
     * Default: 0
     */
    @Option
    public Integer autoSkipPadding;
    
    /**
     * Grid line configuration.
     */
    @Option
    public final GridLineOptions gridLines = new GridLineOptions();

    /**
     * Distance in pixels to offset the label from the centre point of the tick (in the y direction
     * for the x axis, and the x direction for the y axis). Note: this can cause labels at the edges
     * to be cropped by the edge of the canvas.
     * <p>
     * Default: 0
     */
    @Option
    public Integer labelOffset;

    /**
     * Maximum rotation for tick labels when rotating to condense labels. Note: Rotation doesn't
     * occur until necessary. Note: Only applicable to horizontal scales.
     * <p>
     * Default: 90
     */
    @Option
    public Integer maxRotation;

    /**
     * Minimum rotation for tick labels. Note: Only applicable to horizontal scales.
     * <p>
     * Default: 0
     */
    @Option
    public Integer minRotation;

    /**
     * Flips tick labels around axis, displaying the labels inside the chart instead of outside.
     * Note: Only applicable to vertical scales.
     * <p>
     * Default: false
     */
    @Option
    public Boolean mirror;

    /**
     * If true, extra space is added to the both edges and the axis is scaled to fit into the chart
     * area. This is set to true in the bar chart by default.
     * <p>
     * Default: true for bar charts; false otherwise
     */
    @Option
    public Boolean offset;

    /**
     * Padding between the tick label and the axis. When set on a vertical axis, this applies in the
     * horizontal (X) direction. When set on a horizontal axis, this applies in the vertical (Y)
     * direction.
     * <p>
     * Default: 10
     */
    @Option
    public Integer padding;

    /**
     * Position of the axis in the chart.
     */
    @Option
    public PositionEnum position;

    /**
     * Scale title configuration.
     */
    @Option
    public final ScaleLabelOptions scaleLabel = new ScaleLabelOptions();

    /**
     * Type of scale being employed. Custom scales can be created and registered with a string key.
     * This allows changing the type of an axis for a chart.
     */
    @Option
    public String type;

}
