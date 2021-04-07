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
import org.fujion.chartjs.common.Point;
import org.fujion.chartjs.enums.*;

/**
 * Options for line plots.
 */
public class PlotLine extends PlotOptions {

    /**
     * Cap style of the line.
     * <p>
     * Default: BUTT
     */
    @Option
    public LineCapStyleEnum borderCapStyle;

    /**
     * Cap style of the line.
     */
    @Option(convertTo = JavaScript.class)
    public String borderCapStyle$function;

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
     * Offset for line dashes.
     */
    @Option(convertTo = JavaScript.class)
    public String borderDashOffset$function;

    /**
     * Line join style.
     * <p>
     * Default: MITER
     */
    @Option
    public LineJoinStyleEnum borderJoinStyle;

    /**
     * Line join style.
     */
    @Option(convertTo = JavaScript.class)
    public String borderJoinStyle$function;

    /**
     * Algorithm used to interpolate a smooth curve from the discrete data points.
     */
    @Option
    public CubicInterpolationModeEnum cubicInterpolationMode;

    /**
     * Custom algorithm used to interpolate a smooth curve from the discrete data points.
     */
    @Option(convertTo = JavaScript.class)
    public String cubicInterpolationMode$function;

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
     * Compute fill.
     */
    @Option(convertTo = JavaScript.class)
    public String fill$function;

    /**
     * Cap style of the line.
     * <p>
     * Default: BUTT
     */
    @Option
    public LineCapStyleEnum hoverBorderCapStyle;

    /**
     * Cap style of the line.
     */
    @Option(convertTo = JavaScript.class)
    public String hoverBorderCapStyle$function;

    /**
     * Length and spacing of dashes. See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/setLineDash">MDN</a>.
     */
    @Option
    public int[] hoverBorderDash;

    /**
     * Length and spacing of dashes.
     */
    @Option(convertTo = JavaScript.class)
    public String hoverBorderDash$function;

    /**
     * Offset for line dashes. See See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineDashOffset">MDN</a>.
     */
    @Option
    public Integer hoverBorderDashOffset;

    /**
     * Offset for line dashes.
     */
    @Option(convertTo = JavaScript.class)
    public String hoverBorderDashOffset$function;

    /**
     * Line join style.
     * <p>
     * Default: MITER
     */
    @Option
    public LineJoinStyleEnum hoverBorderJoinStyle;

    /**
     * Line join style.
     */
    @Option(convertTo = JavaScript.class)
    public String hoverBorderJoinStyle$function;

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
     * The fill color for points.
     */
    @Option
    public String[] pointBackgroundColor$array;

    /**
     * The fill color for points.
     */
    @Option(convertTo = JavaScript.class)
    public String pointBackgroundColor$function;

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
     * The radius of the point shape in pixels.
     */
    @Option(convertTo = JavaScript.class)
    public String pointRadius$function;

    /**
     * The rotation of the point in degrees.
     * <p>
     * Default: 0
     */
    @Option
    public Integer pointRotation;

    /**
     * The rotation of the point in degrees.
     */
    @Option
    public int[] pointRotation$array;

    /**
     * The rotation of the point in degrees.
     */
    @Option(convertTo = JavaScript.class)
    public String pointRotation$function;

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
     * Style of the point.
     */
    @Option(convertTo = JavaScript.class)
    public String pointStyle$function;

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
    public SteppedLineEnum stepped$enum;

    /**
     * Stepped line interpolation mode.
     * <p>
     * Default: false
     */
    @Option
    public Boolean stepped$boolean;

    /**
     * The ID of the x axis to plot this dataset on.
     * <p>
     * Default: the ID of the first found x axis
     */
    @Option
    public String xAxisID;

    /**
     * The ID of the y axis to plot this dataset on.
     * <p>
     * Default: the ID of the first found y axis.
     */
    @Option
    public String yAxisID;
    
}
