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
package org.fujion.chartjs.plot;

import org.fujion.annotation.Option;
import org.fujion.chartjs.common.Point;

/**
 * Options for line plots.
 */
public class PlotLine extends PlotOptions {
    
    public enum CubicInterpolationModeEnum {
        /**
         * Uses a custom weighted cubic interpolation, which produces pleasant curves for all types
         * of datasets.
         */
        DEFAULT,
        /**
         * More suited to y = f(x) datasets : it preserves monotonicity (or piecewise monotonicity)
         * of the dataset being interpolated, and ensures local extremums (if any) stay at input
         * data points.
         */
        MONOTONE;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Boundary-based fill mode.
     */
    public enum FillEnum {
        END, ORIGIN, START;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    public enum SteppedLineEnum {
        AFTER, BEFORE;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * Cap style of the line. See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineCap">MDN</a>.
     */
    @Option
    public String borderCapStyle;
    
    /**
     * Length and spacing of dashes. See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/setLineDash">MDN</a>.
     */
    @Option
    public int[] borderDash;
    
    /**
     * Offset for line dashes. See See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineDashOffset">MDN</a>.
     */
    @Option
    public Integer borderDashOffset;

    /**
     * Line joint style. See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineJoin">MDN</a>.
     */
    @Option
    public String borderJoinStyle;
    
    /**
     * Algorithm used to interpolate a smooth curve from the discrete data points.
     */
    @Option
    public CubicInterpolationModeEnum cubicInterpolationMode;

    /**
     * The plot data (as numbers).
     */
    @Option("data")
    public double[] data$number;

    /**
     * The plot data (as points).
     */
    @Option("data")
    public Point[] data$points;
    
    /**
     * If false, fill mode is disabled.
     */
    @Option("fill")
    public Boolean fill$boolean;
    
    /**
     * A boundary-based fill.
     */
    @Option("fill")
    public FillEnum fill$enum;

    /**
     * An absolute dataset index.
     */
    @Option("fill")
    public Integer fill$number;

    /**
     * A relative dataset index (e.g., "+1" or "-2").
     */
    @Option("fill")
    public String fill$string;

    /**
     * The label for the dataset which appears in the legend and tooltips.
     */
    @Option
    public String label;

    /**
     * Bezier curve tension of the line. Set to 0 to draw straightlines. This option is ignored if
     * monotone cubic interpolation is used.
     */
    @Option
    public Double lineTension;

    /**
     * The fill color for points.
     */
    @Option
    public String pointBackgroundColor;

    /**
     * The border color for points.
     */
    @Option
    public String pointBorderColor;

    /**
     * The width of the point border in pixels.
     */
    @Option("pointBorderWidth")
    public int[] pointBorderWidth$array;
    
    /**
     * The width of the point border in pixels.
     */
    @Option("pointBorderWidth")
    public Integer pointBorderWidth$number;
    
    /**
     * The pixel size of the non-displayed point that reacts to mouse events.
     */
    @Option("pointHitRadius")
    public int[] pointHitRadius$array;

    /**
     * The pixel size of the non-displayed point that reacts to mouse events.
     */
    @Option("pointHitRadius")
    public Integer pointHitRadius$number;

    /**
     * Point background color when hovered.
     */
    @Option("pointHoverBackgroundColor")
    public String[] pointHoverBackgroundColor$array;

    /**
     * Point background color when hovered.
     */
    @Option("pointHoverBackgroundColor")
    public String pointHoverBackgroundColor$string;

    /**
     * Point border color when hovered.
     */
    @Option("pointHoverBorderColor")
    public String[] pointHoverBorderColor$array;

    /**
     * Point border color when hovered.
     */
    @Option("pointHoverBorderColor")
    public String pointHoverBorderColor$string;

    /**
     * Border width of point when hovered.
     */
    @Option("pointHoverBorderWidth")
    public int[] pointHoverBorderWidth$array;
    
    /**
     * Border width of point when hovered.
     */
    @Option("pointHoverBorderWidth")
    public Integer pointHoverBorderWidth$number;

    /**
     * The radius of the point when hovered.
     */
    @Option("pointHoverRadius")
    public int[] pointHoverRadius$array;

    /**
     * The radius of the point when hovered.
     */
    @Option("pointHoverRadius")
    public Integer pointHoverRadius$number;

    /**
     * The radius of the point shape in pixels. If set to 0, the point is not rendered.
     */
    @Option("pointRadius")
    public int[] pointRadius$array;

    /**
     * The radius of the point shape in pixels. If set to 0, the point is not rendered.
     */
    @Option("pointRadius")
    public Integer pointRadius$number;

    /**
     * Style of the point.
     */
    @Option("pointStyle")
    public PointStyleEnum[] pointStyle$array;

    /**
     * Style of the point.
     */
    @Option("pointStyle")
    public PointStyleEnum pointStyle$enum;

    /**
     * If false, the line is not drawn for this dataset.
     */
    @Option
    public Boolean showLine;

    /**
     * If true, lines will be drawn between points with no or null data. If false, points with NaN
     * data will create a break in the line
     */
    @Option
    public Boolean spanGaps;

    /**
     * Stepped line interpolation mode.
     * <p>
     * Default: no interpolation
     */
    @Option
    public SteppedLineEnum steppedLine;

    /**
     * The ID of the x axis to plot this dataset on. If not specified, this defaults to the ID of
     * the first found x axis
     */
    @Option
    public String xAxisID;

    /**
     * The ID of the y axis to plot this dataset on. If not specified, this defaults to the ID of
     * the first found y axis.
     */
    @Option
    public String yAxisID;
    
}
