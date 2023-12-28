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
package org.fujion.chartjs.plot;

import org.fujion.ancillary.JavaScript;
import org.fujion.annotation.Option;
import org.fujion.chartjs.common.Point;
import org.fujion.chartjs.enums.CartesianAxisEnum;
import org.fujion.chartjs.enums.PointStyleEnum;
import org.fujion.chartjs.enums.PositionEnum;

/**
 * Options for bar plots.
 */
public class PlotBar extends PlotOptions {

    /**
     * Base value for the bar in data units along the value axis. If not set, defaults to the value axis base value.
     */
    @Option
    public Double base;

    /**
     * Base value for the bar in data units along the value axis. If not set, defaults to the value axis base value.
     */
    @Option
    public double[] base$array;

    /**
     * Base value for the bar in data units along the value axis. If not set, defaults to the value axis base value.
     */
    @Option(convertTo = JavaScript.class)
    public String base$function;

    /**
     * Radius of the border in pixels applied to all corners of the rectangle, except corners touching the borderSkipped.
     */
    @Option
    public Integer borderRadius;

    /**
     * Radius of the border in pixels applied to all corners of the rectangle, except corners touching the borderSkipped.
     */
    @Option
    public int[] borderRadius$array;

    /**
     * Radius of the border in pixels applied to all corners of the rectangle, except corners touching the borderSkipped.
     */
    @Option(convertTo = JavaScript.class)
    public String borderRadius$function;

    /**
     * Which edge to skip drawing the border for.
     */
    @Option
    public PositionEnum borderSkipped;

    /**
     * Which edge to skip drawing the border for.
     */
    @Option
    public PositionEnum[] borderSkipped$array;

    /**
     * Which edge to skip drawing the border for.
     */
    @Option(convertTo = JavaScript.class)
    public String borderSkipped$function;

    /**
     * The plot data (as numbers).
     */
    @Option
    public double[] data$number;

    /**
     * The plot data (as points).
     */
    @Option
    public Point[] data$points;

    /**
     * The bar border radius when hovered (in pixels).
     */
    @Option
    public Integer hoverBorderRadius;

    /**
     * The bar border radius when hovered (in pixels).
     */
    @Option
    public int[] hoverBorderRadius$array;

    /**
     * The bar border radius when hovered (in pixels).
     */
    @Option(convertTo = JavaScript.class)
    public String hoverBorderRadius$function;

    /**
     * The base axis of the dataset. X for vertical bars and Y for horizontal bars.
     * <p>
     * Default: X
     */
    @Option
    public CartesianAxisEnum indexAxis;

    /**
     * The label for the dataset which appears in the legend and tooltips.
     */
    @Option
    public String label;

    /**
     * The drawing order of dataset.
     */
    @Option
    public Integer order;

    /**
     * Style of the point.
     */
    @Option
    public PointStyleEnum pointStyle;

    /**
     * Style of the point.
     */
    @Option(convertTo = JavaScript.class)
    public String pointStyle$function;

    /**
     * The ID of the x-axis to plot this dataset on.
     * <p>
     * Default: the ID of the first found x-axis
     */
    @Option
    public String xAxisID;

    /**
     * The ID of the y-axis to plot this dataset on.
     * <p>
     * Default: the ID of the first found y-axis.
     */
    @Option
    public String yAxisID;

}
