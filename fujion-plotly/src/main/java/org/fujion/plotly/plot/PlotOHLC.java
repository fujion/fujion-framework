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
package org.fujion.plotly.plot;

import org.fujion.annotation.Option;
import org.fujion.plotly.common.CalendarTypeEnum;
import org.fujion.plotly.common.DashStyleEnum;

/**
 * Options for OHLC plot.
 * <p>
 * An open-high-low-close chart (also OHLC) is a type of chart typically used to illustrate
 * movements in the price of a financial instrument over time. Each vertical line on the chart shows
 * the price range (the highest and lowest prices) over one unit of time, e.g., one day or one hour.
 * Tick marks project from each side of the line indicating the opening price (e.g., for a daily bar
 * chart this would be the starting price for that day) on the left, and the closing price for that
 * time period on the right. The bars may be shown in different hues depending on whether prices
 * rose or fell in that period.
 */
public class PlotOHLC extends PlotOptions {

    /**
     * The close values.
     */
    @Option
    public double[] close;

    /**
     * Options for displaying decreasing trend.
     */
    @Option
    public final TrendOptions decreasing = new TrendOptions();

    /**
     * The high values.
     */
    @Option
    public double[] high;

    /**
     * Options for displaying increasing trend.
     */
    @Option
    public final TrendOptions increasing = new TrendOptions();

    /**
     * The dash style of lines.
     * <p>
     * Default: SOLID
     */
    @Option("line.dash")
    public DashStyleEnum line_dash$enum;

    /**
     * An alternative in the form of a dash length list in px (eg "5px,10px,2px,2px").
     */
    @Option("line.dash")
    public String line_dash$string;
    
    /**
     * Note that this style setting can also be set per direction via "increasing.line.width" and
     * "decreasing.line.width".
     * <p>
     * Default: 2
     */
    @Option("line.width")
    public Integer line_width;

    /**
     * The low values.
     */
    @Option
    public double[] low;

    /**
     * The open values.
     */
    @Option
    public double[] open;

    /**
     * Hover text elements associated with each sample point. The items are mapped in order to this
     * trace's sample points.
     */
    @Option("text")
    public String[] text$array;

    /**
     * Hover text elements associated with each sample point to the same value.
     */
    @Option("text")
    public String text$string;
    
    /**
     * The width of the open/close tick marks relative to the "x" minimal interval.
     * <p>
     * Constraints: &ge;0 and &le;0.5
     * <p>
     * Default: 0.3
     */
    @Option
    public Double tickwidth;
    
    /**
     * The x coordinates. If absent, linear coordinate will be generated.
     */
    @Option
    public double[] x;

    /**
     * Reference between this trace's x coordinates and a 2D Cartesian x axis. If "x" (the default
     * value), the x coordinates refer to "layout.xaxis". If "x2", the x coordinates refer to
     * "layout.xaxis2", and so on.
     */
    @Option
    public String xaxis;
    
    /**
     * The calendar system to use with "x" date data.
     * <p>
     * Default: GREGORIAN
     */
    @Option
    public CalendarTypeEnum xcalendar;
    
    /**
     * Reference between this trace's y coordinates and a 2D Cartesian y axis. If "y" (the default
     * value), the y coordinates refer to "layout.yaxis". If "y2", the y coordinates refer to
     * "layout.xaxis2", and so on.
     */
    @Option
    public String yaxis;
    
}
