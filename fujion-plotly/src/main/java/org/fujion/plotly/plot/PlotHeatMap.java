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
package org.fujion.plotly.plot;

import org.fujion.annotation.Option;
import org.fujion.plotly.common.CalendarTypeEnum;

/**
 * Options for heat map plot.
 * <p>
 * A heat map is a graphical representation of data where the individual values contained in a
 * matrix are represented as colors.
 */
public class PlotHeatMap extends PlotOptions {

    /**
     * Determines whether or not the colorscale is picked using the sign of the input z values.
     */
    @Option
    public Boolean autocolorscale;
    
    /**
     * Settings for the colorbar.
     */
    @Option
    public final ColorbarOptions colorbar = new ColorbarOptions();

    /**
     * The colorscale. Must be an array containing arrays mapping a normalized value to an rgb,
     * rgba, hex, hsl, hsv, or named color string. At minimum, a mapping for the lowest (0) and
     * highest (1) values are required. For example, "[[0, "rgb(0,0,255)", [1, "rgb(255,0,0)"]]". To
     * control the bounds of the colorscale in z space, use zmin and zmax
     */
    @Option
    public Object[] colorscale;

    /**
     * Determines whether or not gaps (i.e. {nan} or missing values) in the "z" data are filled in.
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
     * Reverses the colorscale.
     */
    @Option
    public Boolean reversescale;

    /**
     * Determines whether or not a colorbar is displayed for this trace.
     * <p>
     * Default: true
     */
    @Option
    public Boolean showscale;

    /**
     * The text elements associated with each z value.
     */
    @Option("text")
    public String[] text$array;

    /**
     * Text constant associated with each z value.
     */
    @Option("text")
    public String text$string;
    
    /**
     * Transposes the z data.
     */
    @Option
    public Boolean transpose;
    
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
     * The horizontal gap (in pixels) between bricks.
     * <p>
     * Default: 0
     */
    @Option
    public Integer xgap;

    /**
     * Controls how x coordinates are represented.
     */
    @Option
    public CoordinateTypeEnum xtype;

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

    /**
     * The vertical gap (in pixels) between bricks.
     * <p>
     * Default: 0
     */
    @Option
    public Integer ygap;
    
    /**
     * Controls how y coordinates are represented.
     */
    @Option
    public CoordinateTypeEnum ytype;
    
    /**
     * Data points for z-axis.
     */
    @Option
    public Object[] z;
    
    /**
     * Data points for z-axis. Alternate form for numeric data.
     */
    @Option("z")
    public double[] z$number;
    
    /**
     * Determines the whether or not the color domain is computed with respect to the input data.
     */
    @Option
    public Boolean zauto;
    
    /**
     * The hover text formatting rule using d3 formatting mini-languages which are very similar to
     * those in Python. See
     * <a href="https://github.com/d3/d3-format/blob/master/README.md#locale_format">d3 format
     * reference.</a>
     */
    @Option
    public String zhoverformat;

    /**
     * The upper bound of color domain.
     */
    @Option
    public Double zmax;

    /**
     * The lower bound of color domain.
     */
    @Option
    public Double zmin;

    /**
     * Picks a smoothing algorithm use to smooth "z" data.
     */
    @Option
    public SmoothingEnum zsmooth;

}
