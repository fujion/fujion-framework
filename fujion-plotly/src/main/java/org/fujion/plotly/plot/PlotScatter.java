/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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
import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.common.TextPositionEnum;

/**
 * Options for scatter plot.
 * <p>
 * A scatter plot uses Cartesian coordinates to display values for two variables for a set of data.
 */
public class PlotScatter extends PlotOptions {

    /**
     * Determines whether or not markers and text nodes are clipped about the subplot axes. To show
     * markers and text nodes above axis lines and tick labels, make sure to set "xaxis.layer" and
     * "yaxis.layer" to "below traces".
     * <p>
     * Default: true
     */
    @Option
    public Boolean cliponaxis;
    
    /**
     * Determines whether or not gaps (i.e. {nan} or missing values) in the provided data arrays are
     * connected.
     */
    @Option
    public Boolean connectgaps;
    
    /**
     * The x coordinate step. See {@link #x0$number x0} for more info.
     */
    @Option
    public Double dx;
    
    /**
     * The y coordinate step. See {@link #y0$number y0} for more info.
     */
    @Option
    public Double dy;
    
    /**
     * Options for x-axis error bars.
     */
    @Option
    public final ErrorBarOptions error_x = new ErrorBarOptions();

    /**
     * Options for y-axis error bars.
     */
    @Option
    public final ErrorBarOptions error_y = new ErrorBarOptions();
    
    /**
     * The area to fill with a solid color. Use with "fillcolor".
     */
    @Option
    public FillAreaEnum fill;
    
    /**
     * The fill color.
     * <p>
     * Default: a half-transparent variant of the line color, marker color, or marker line color,
     * whichever is available.
     */
    @Option
    public String fillcolor;
    
    /**
     * Any combination of "points", "fills" joined with a "+". Examples: "points", "fills",
     * "points+fills" Do the hover effects highlight individual points (markers or line points) or
     * do they highlight filled regions? If the fill is "toself" or "tonext" and there are no
     * markers or text, then the default is "fills", otherwise it is "points".
     */
    @Option
    public String hoveron;
    
    /**
     * Sets hover text elements associated with each (x,y) pair. If a single string, the same string
     * appears over all the data points. If an array of string, the items are mapped in order to the
     * this trace's (x,y) coordinates. To be seen, trace "hoverinfo" must contain a "text" flag.
     */
    @Option
    public String hovertext;

    /**
     * Style options for the line.
     */
    @Option
    public final LineOptions line = new LineOptions();

    /**
     * Options for marker.
     */
    @Option
    public final MarkerOptions marker = new MarkerOptions();

    /**
     * Any combination of "lines", "markers", "text" joined with a "+" OR "none". examples: "lines",
     * "markers", "lines+markers", "lines+markers+text", "none" Determines the drawing mode for this
     * scatter trace. If the provided "mode" includes "text" then the "text" elements appear at the
     * coordinates. Otherwise, the "text" elements appear on hover. If there are less than 20
     * points, then the default is "lines+markers". Otherwise, "lines".
     */
    @Option
    public String mode;

    /**
     *
     */
    @Option
    public final SelectedOptions selected = new SelectedOptions();

    /**
     * Sets text elements associated with each (x,y) pair. The items are mapped in order to the
     * trace's (x,y) coordinates. If trace "hoverinfo" contains a "text" flag and "hovertext" is not
     * set, these elements will be seen in the hover labels.
     */
    @Option("text")
    public String[] text$array;
    
    /**
     * Sets text elements associated with each (x,y) pair. Alternate form where the same string
     * appears over all the data points. If trace "hoverinfo" contains a "text" flag and "hovertext"
     * is not set, these elements will be seen in the hover labels.
     */
    @Option("text")
    public String text$string;
    
    /**
     * The text font.
     */
    @Option
    public final FontOptions textfont = new FontOptions();
    
    /**
     * The positions of the "text" elements with respects to the (x,y) coordinates.
     * <p>
     * Default: MIDDLE_CENTER
     */
    @Option
    public TextPositionEnum textposition;

    /**
     *
     */
    @Option
    public final SelectedOptions unselected = new SelectedOptions();

    /**
     * Data points for x-axis.
     */
    @Option
    public Object[] x;
    
    /**
     * Data points for x-axis. Alternate form for numeric data.
     */
    @Option("x")
    public double[] x$number;

    /**
     * Alternate to "x". Builds a linear space of x coordinates. Use with "dx" where "x0" is the
     * starting coordinate and "dx" the step.
     */
    @Option("x0")
    public Double x0$number;
    
    /**
     * Alternate form of x0, where the step will be appended to a string value.
     */
    @Option("x0")
    public String x0$string;

    /**
     * Sets a reference between this trace's x coordinates and a 2D Cartesian x axis. If "x" (the
     * default value), the x coordinates refer to "layout.xaxis". If "x2", the x coordinates refer
     * to "layout.xaxis2", and so on.
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
     * Data points for y-axis.
     */
    @Option
    public Object[] y;
    
    /**
     * Data points for y-axis. Alternate form for numeric data.
     */
    @Option("y")
    public double[] y$number;
    
    /**
     * Alternate to "y". Builds a linear space of y coordinates. Use with "dy" where "y0" is the
     * starting coordinate and "dy" the step.
     */
    @Option("y0")
    public Double y0$number;

    /**
     * Alternate form of y0, where the step will be appended to a string value.
     */
    @Option("y0")
    public String y0$string;

    /**
     * Reference between this trace's y coordinates and a 2D Cartesian y axis. If "y" (the default
     * value), the y coordinates refer to "layout.yaxis". If "y2", the y coordinates refer to
     * "layout.xaxis2", and so on.
     */
    @Option
    public String yaxis;

    /**
     * The calendar system to use with "y" date data.
     * <p>
     * Default: GREGORIAN
     */
    @Option
    public CalendarTypeEnum ycalendar;
    
}
