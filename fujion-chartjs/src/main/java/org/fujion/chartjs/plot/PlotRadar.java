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
import org.fujion.chartjs.enums.FillEnum;
import org.fujion.chartjs.enums.LineCapStyleEnum;
import org.fujion.chartjs.enums.LineJoinStyleEnum;
import org.fujion.chartjs.enums.PointStyleEnum;

/**
 * Options for radar plots.
 */
public class PlotRadar extends PlotOptions {

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
     * Offset for line dashes. See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineDashOffset">MDN</a>.
     */
    @Option
    public Integer borderDashOffset;

    /**
     * Offset for line dashes. See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineDashOffset">MDN</a>.
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
     * The plot data.
     */
    @Option
    public double[] data;
    
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
     * Fill as an absolute dataset index.
     */
    @Option
    public Integer fill$number;

    /**
     * Fill as a relative dataset index (e.g., "+1" or "-2").
     */
    @Option
    public String fill$string;

    /**
     * Computed fill.
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
     * Length and spacing of dashes. See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/setLineDash">MDN</a>.
     */
    @Option(convertTo = JavaScript.class)
    public String hoverBorderDash$function;

    /**
     * Offset for line dashes. See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineDashOffset">MDN</a>.
     */
    @Option
    public Integer hoverBorderDashOffset;

    /**
     * Offset for line dashes. See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineDashOffset">MDN</a>.
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
     * The border color for points.
     */
    @Option
    public String[] pointBorderColor$array;

    /**
     * The border color for points.
     */
    @Option(convertTo = JavaScript.class)
    public String pointBorderColor$function;

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
     * The width of the point border in pixels.
     */
    @Option(convertTo = JavaScript.class)
    public String pointBorderWidth$function;

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
     * The pixel size of the non-displayed point that reacts to mouse events.
     */
    @Option(convertTo = JavaScript.class)
    public String pointHitRadius$function;

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
     * Point background color when hovered.
     */
    @Option(convertTo = JavaScript.class)
    public String pointHoverBackgroundColor$function;

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
     * Point border color when hovered.
     */
    @Option(convertTo = JavaScript.class)
    public String pointHoverBorderColor$function;

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
     * Border width of point when hovered.
     */
    @Option(convertTo = JavaScript.class)
    public String pointHoverBorderWidth$function;

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
     * The radius of the point when hovered.
     */
    @Option(convertTo = JavaScript.class)
    public String pointHoverRadius$function;

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
     * The radius of the point shape in pixels. If set to 0, the point is not rendered.
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
     * Bézier curve tension of the line. Set to 0 to draw straightlines. This option is ignored if
     * monotone cubic interpolation is used.
     */
    @Option
    public Double tension;

}
