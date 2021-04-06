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

import org.fujion.ancillary.JavaScript;
import org.fujion.annotation.Option;
import org.fujion.chartjs.common.*;

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
        AFTER, BEFORE, MIDDLE, FALSE, TRUE;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * Cap style of the line.
     *
     * Default: BUTT
     */
    @Option
    public LineCapStyleEnum borderCapStyle;
    
    /**
     * Length and spacing of dashes. See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/setLineDash">MDN</a>.
     */
    @Option
    public int[] borderDash;

    /**
     * Length and spacing of dashes. See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/setLineDash">MDN</a>.
     */
    @Option(convertTo = JavaScript.class)
    public String borderDash$function;

    /**
     * Offset for line dashes. See See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineDashOffset">MDN</a>.
     */
    @Option
    public Integer borderDashOffset;

    /**
     * Line join style.
     *
     * Default: MITER
     */
    @Option
    public LineJoinStyleEnum borderJoinStyle;

    /**
     * Algorithm used to interpolate a smooth curve from the discrete data points.
     */
    @Option
    public CubicInterpolationModeEnum cubicInterpolationMode;

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
     * If false, fill mode is disabled.
     */
    @Option
    public Boolean fill$boolean;
    
    /**
     * A boundary-based fill.
     */
    @Option
    public FillEnum fill$enum;

    /**
     * An absolute dataset index.
     */
    @Option
    public Integer fill$number;

    /**
     * A relative dataset index (e.g., "+1" or "-2").
     */
    @Option
    public String fill$string;

    /**
     * Cap style of the line.
     *
     * Default: BUTT
     */
    @Option
    public LineCapStyleEnum hoverBorderCapStyle;

    /**
     * Length and spacing of dashes. See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/setLineDash">MDN</a>.
     */
    @Option
    public int[] hoverBorderDash;

    /**
     * Offset for line dashes. See See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineDashOffset">MDN</a>.
     */
    @Option
    public Integer hoverBorderDashOffset;

    /**
     * Line join style.
     *
     * Default: MITER
     */
    @Option
    public LineJoinStyleEnum hoverBorderJoinStyle;

    /**
     * The base axis of the dataset. X for horizontal lines and Y for vertical lines.
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
     * Bezier curve tension of the line. Set to 0 to draw straightlines. This option is ignored if
     * monotone cubic interpolation is used.
     *
     * Default: 0
     */
    @Option
    public Integer tension;

    /**
     * The drawing order of dataset.
     */
    @Option
    public Integer order;

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
    @Option
    public Integer pointBorderWidth;

    /**
     * The width of the point border in pixels.
     */
    @Option
    public int[] pointBorderWidth$array;

    /**
     * The pixel size of the non-displayed point that reacts to mouse events.
     */
    @Option
    public Integer pointHitRadius;

    /**
     * The pixel size of the non-displayed point that reacts to mouse events.
     */
    @Option
    public int[] pointHitRadius$array;

    /**
     * Point background color when hovered.
     */
    @Option
    public String pointHoverBackgroundColor;

    /**
     * Point background color when hovered.
     */
    @Option
    public String[] pointHoverBackgroundColor$array;

    /**
     * Point border color when hovered.
     */
    @Option
    public String pointHoverBorderColor;

    /**
     * Point border color when hovered.
     */
    @Option
    public String[] pointHoverBorderColor$array;

    /**
     * Border width of point when hovered.
     */
    @Option
    public Integer pointHoverBorderWidth;

    /**
     * Border width of point when hovered.
     */
    @Option
    public int[] pointHoverBorderWidth$array;

    /**
     * The radius of the point when hovered.
     */
    @Option
    public Integer pointHoverRadius;

    /**
     * The radius of the point when hovered.
     */
    @Option
    public int[] pointHoverRadius$array;

    /**
     * The radius of the point shape in pixels. If set to 0, the point is not rendered.
     */
    @Option
    public Integer pointRadius;

    /**
     * The radius of the point shape in pixels. If set to 0, the point is not rendered.
     */
    @Option
    public int[] pointRadius$array;

    /**
     * The rotation of the point in degrees.
     *
     * Default: 0
     */
    @Option
    public Integer pointRotation;

    /**
     * Style of the point.
     */
    @Option
    public PointStyleEnum pointStyle;

    /**
     * Style of the point.
     */
    @Option
    public PointStyleEnum[] pointStyle$array;

    /**
     * If false, the line is not drawn for this dataset.
     */
    @Option
    public Boolean showLine;

    /**
     * If true, lines will be drawn between points with no or null data. If false, points with NaN
     * data will create a break in the line.
     */
    @Option
    public Boolean spanGaps$boolean;

    /**
     * A number specifying the maximum gap length to span. The unit of the value depends on the scale used.
     */
    @Option
    public Integer spanGaps$number;

    /**
     * Stepped line interpolation mode.
     * <p>
     * Default: no interpolation
     */
    @Option
    public SteppedLineEnum stepped;

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
