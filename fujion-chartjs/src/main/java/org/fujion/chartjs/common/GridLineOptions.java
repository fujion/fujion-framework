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
package org.fujion.chartjs.common;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for grid lines
 */
public class GridLineOptions extends Options {

    /**
     * If set, used as the color of the border line. If unset, the first color option is resolved and used.
     */
    @Option
    public String borderColor;

    /**
     * If set, used as the width of the border line. If unset, the first lineWidth option is resolved and used.
     */
    @Option
    public Integer borderWidth;

    /**
     * Length and spacing of dashes on grid lines. See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/setLineDash">MDN</a>.
     */
    @Option
    public int[] borderDash;

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
     * If true, gridlines are circular (on radar chart only).
     */
    @Option
    public Boolean circular;

    /**
     * The color of all grid lines.
     * <p>
     * Default: "rgba(0, 0, 0, 0.1)"
     */
    @Option
    public String color;

    /**
     * The color of the grid lines. The first color applies to the first grid line, the second to
     * the second grid line and so on.
     */
    @Option
    public String[] color$array;

    /**
     * The color of the grid lines. The first color applies to the first grid line, the second to
     * the second grid line and so on.
     */
    @Option(convertTo = JavaScript.class)
    public String color$function;

    /**
     * If false, do not display grid lines for this axis.
     * <p>
     * Default: true
     */
    @Option
    public Boolean display;

    /**
     * If true, draw border at the edge between the axis and the chart area.
     * <p>
     * Default: true
     */
    @Option
    public Boolean drawBorder;

    /**
     * If true, draw lines on the chart area inside the axis lines. This is useful when there are
     * multiple axes and you need to control which grid lines are drawn.
     * <p>
     * Default: true
     */
    @Option
    public Boolean drawOnChartArea;

    /**
     * If true, draw lines beside the ticks in the axis area beside the chart.
     * <p>
     * Default: true
     */
    @Option
    public Boolean drawTicks;

    /**
     * Stroke width of all grid lines.
     * <p>
     * Default: 1
     */
    @Option
    public Integer lineWidth;

    /**
     * Stroke width of individual grid lines.
     */
    @Option
    public int[] lineWidth$array;

    /**
     * Stroke width of individual grid lines.
     */
    @Option(convertTo = JavaScript.class)
    public String lineWidth$function;

    /**
     * If true, grid lines will be shifted to be between labels.
     * <p>
     * Default: true for bar chart; false otherwise
     */
    @Option
    public Boolean offset;

    /**
     * Length and spacing of the tick mark line.
     * <p>
     * Default: the grid line borderDash value.
     */
    @Option
    public int[] tickBorderDash;

    /**
     * Offset for the line dash of the tick mark.
     * <p>
     * Default: the grid line borderDashOffset value.
     */
    @Option
    public Integer tickBorderDashOffset;

    /**
     * Offset for the line dash of the tick mark.
     */
    @Option
    public int[] tickBorderDashOffset$array;

    /**
     * Offset for the line dash of the tick mark.
     */
    @Option(convertTo = JavaScript.class)
    public String tickBorderDashOffset$function;

    /**
     * Color of the tick line.
     * <p>
     * Default: the grid line color.
     */
    @Option
    public String tickColor;

    /**
     * Color of the tick line.
     */
    @Option
    public String[] tickColor$array;

    /**
     * Color of the tick line.
     */
    @Option(convertTo = JavaScript.class)
    public String tickColor$function;

    /**
     * Length in pixels that the grid lines will draw into the axis area.
     * <p>
     * Default: 8
     */
    @Option
    public Integer tickLength;

    /**
     * Width of the tick mark in pixels.
     * <p>
     * Default: the grid line width.
     */
    @Option
    public Integer tickWidth;

    /**
     * Width of the tick mark in pixels.
     */
    @Option
    public int[] tickWidth$array;

    /**
     * Width of the tick mark in pixels.
     */
    @Option(convertTo = JavaScript.class)
    public String tickWidth$function;

    /**
     * z-index of gridline layer. Values &le; 0 are drawn under datasets, &gt; 0 on top.
     * <p>
     * Default: 0
     */
    @Option
    public Integer z;
}
