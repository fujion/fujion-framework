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
package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for grid lines
 */
public class GridLineOptions extends Options {
    
    /**
     * Length and spacing of dashes on grid lines. See <a href=
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
     * The color of the grid lines. The first color applies to the first grid line, the second to
     * the second grid line and so on.
     */
    @Option("color")
    public String[] color$array;

    /**
     * The color of all grid lines.
     * <p>
     * Default: "rgba(0, 0, 0, 0.1)"
     */
    @Option("color")
    public String color$string;
    
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
     * Stroke width of individual grid lines.
     */
    @Option("lineWidth")
    public int[] lineWidth$array;

    /**
     * Stroke width of all grid lines.
     * <p>
     * Default: 1
     */
    @Option("lineWidth")
    public Integer lineWidth$number;
    
    /**
     * If true, grid lines will be shifted to be between labels.
     * <p>
     * Default: true for bar chart; false otherwise
     */
    @Option
    public Boolean offsetGridLines;
    
    /**
     * Length in pixels that the grid lines will draw into the axis area.
     * <p>
     * Default: 10
     */
    @Option
    public Integer tickMarkLength;
    
    /**
     * Length and spacing of dashes of the grid line for the first index (index 0). See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/setLineDash">MDN</a>.
     */
    @Option
    public int[] zeroLineBorderDash;

    /**
     * Offset for line dashes of the grid line for the first index (index 0). See See <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/lineDashOffset">MDN</a>.
     * <p>
     * Default: 0
     */
    @Option
    public Integer zeroLineBorderDashOffset;

    /**
     * Stroke color of the grid line for the first index (index 0).
     * <p>
     * Default: "rgba(0, 0, 0, 0.25)"
     */
    @Option
    public String zeroLineColor;

    /**
     * Stroke width of the grid line for the first index (index 0).
     * <p>
     * Default: 1
     */
    @Option
    public Integer zeroLineWidth;
    
}
