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

import org.fujion.plotly.common.CalendarTypeEnum;

/**
 * Options for candlestick plot.
 * <p>
 * A candlestick plot a style of financial chart used to describe price movements of a security,
 * derivative, or currency. Each "candlestick" typically shows one day, thus a one-month chart may
 * show the 20 trading days as 20 "candlesticks". Shorter intervals than one day are common on
 * computer charts, longer are possible.
 */
public class PlotCandlestick extends PlotOptions {

    /**
     * The close values.
     */
    public double[] close;
    
    /**
     * Options for displaying decreasing trends.
     */
    public final TrendOptions decreasing = new TrendOptions();
    
    /**
     * The high values.
     */
    public double[] high;
    
    /**
     * Options for displaying increasing trends.
     */
    public final TrendOptions increasing = new TrendOptions();
    
    /**
     * The width (in px) of line bounding the box(es). Note that this style setting can also be set
     * per direction via "increasing.line.width" and "decreasing.line.width".
     * <p>
     * Default: 2
     */
    public Integer line_width;
    
    /**
     * The low values.
     */
    public double[] low;

    /**
     * The open values.
     */
    public double[] open;
    
    /**
     * Hover text elements associated with each sample point. The items are mapped in order to the
     * trace's sample points.
     */
    public String[] text$array;

    /**
     * Hover text elements associated with each sample point. Alternate form where the same string
     * appears over all the data points.
     */
    public String text$string;

    /**
     * The width of the whiskers relative to the box" width. For example, with 1, the whiskers are
     * as wide as the box(es).
     * <p>
     * &ge;0 and &le;1
     * <p>
     * Default: 0
     */
    public Double whiskerwidth;

    /**
     * Data points for x-axis. If absent, linear coordinate will be generated.
     */
    public Object[] x;

    /**
     * Data points for x-axis. Alternate form for numeric data.
     */
    public double[] x$number;
    
    /**
     * Reference between this trace's x coordinates and a 2D Cartesian x axis. If "x" (the default
     * value), the x coordinates refer to "layout.xaxis". If "x2", the x coordinates refer to
     * "layout.xaxis2", and so on.
     */
    public String xaxis;

    /**
     * The calendar system to use with "x" date data.
     * <p>
     * Default: GREGORIAN
     */
    public CalendarTypeEnum xcalendar;

    /**
     * Reference between this trace's y coordinates and a 2D Cartesian y axis. If "y" (the default
     * value), the y coordinates refer to "layout.yaxis". If "y2", the y coordinates refer to
     * "layout.xaxis2", and so on.
     */
    public String yaxis;
    
}
