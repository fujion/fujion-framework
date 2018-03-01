/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
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
 * Options for GL scatter plot.
 */
public class PlotScatterGL extends PlotOptions {

    /**
     * Determines whether or not gaps (i.e. {nan} or missing values) in the provided data arrays are
     * connected.
     */
    public Boolean connectgaps;

    /**
     * The x coordinate step. See {@link #x0$number x0} for more info.
     */
    public Double dx;

    /**
     * The y coordinate step. See {@link #y0$number y0} for more info.
     */
    public Double dy;

    /**
     * Options for x-axis error bars.
     */
    public final ErrorBarOptions error__x = new ErrorBarOptions();
    
    /**
     * Options for y-axis error bars.
     */
    public final ErrorBarOptions error__y = new ErrorBarOptions();

    /**
     * The area to fill with a solid color.
     * <p>
     * Default: NONE
     */
    public FillAreaEnum fill;

    /**
     * The fill color.
     * <p>
     * Default: a half-transparent variant of the line color, marker color, or marker line color,
     * whichever is available.
     */
    public String fillcolor;

    /**
     * Any combination of "points", "fills" joined with a "+". Examples: "points", "fills",
     * "points+fills" Do the hover effects highlight individual points (markers or line points) or
     * do they highlight filled regions? If the fill is "toself" or "tonext" and there are no
     * markers or text, then the default is "fills", otherwise it is "points".
     */
    public String hoveron;
    
    /**
     * Style options for the line.
     */
    public final LineOptions line = new LineOptions();
    
    /**
     * Options for marker.
     */
    public final MarkerOptions marker = new MarkerOptions();
    
    /**
     * Any combination of "lines", "markers" joined with a "+" OR "none". examples: "lines",
     * "markers", "lines+markers", "none" Determines the drawing mode for this scatter trace.
     */
    public String mode;
    
    /**
     *
     */
    public final SelectedOptions selected = new SelectedOptions();

    /**
     * Text elements associated with each (x,y) pair to appear on hover as an array of string where
     * the items are mapped in order to the this trace's (x,y) coordinates.
     */
    public String[] text$array;

    /**
     * Text elements associated with each (x,y) pair to appear on hover as a single string where the
     * same string appears over all the data points.
     */
    public String text$string;

    /**
     *
     */
    public final SelectedOptions unselected = new SelectedOptions();
    
    /**
     * Data points for x-axis.
     */
    public Object[] x;

    /**
     * Data points for x-axis. Alternate form for numeric data.
     */
    public double[] x$number;
    
    /**
     * Alternate to "x". Builds a linear space of x coordinates. Use with "dx" where "x0" is the
     * starting coordinate and "dx" the step.
     */
    public Double x0$number;
    
    /**
     * Alternate form of x0, where the step will be appended to a string value.
     */
    public String x0$string;

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
     * Data points for y-axis.
     */
    public Object[] y;

    /**
     * Data points for y-axis. Alternate form for numeric data.
     */
    public double[] y$number;
    
    /**
     * Alternate to "y". Builds a linear space of y coordinates. Use with "dy" where "y0" is the
     * starting coordinate and "dy" the step.
     */
    public Double y0$number;
    
    /**
     * Alternate form of y0, where the step will be appended to a string value.
     */
    public String y0$string;
    
    /**
     * Reference between this trace's y coordinates and a 2D Cartesian y axis. If "y" (the default
     * value), the y coordinates refer to "layout.yaxis". If "y2", the y coordinates refer to
     * "layout.xaxis2", and so on.
     */
    public String yaxis;
    
    /**
     * The calendar system to use with "y" date data.
     * <p>
     * Default: GREGORIAN
     */
    public CalendarTypeEnum ycalendar;

}
