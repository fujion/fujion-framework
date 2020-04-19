/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
import org.fujion.plotly.plot.PlotHistogram.HistFuncEnum;
import org.fujion.plotly.plot.PlotHistogram.HistNormEnum;

/**
 * Options for 2D histogram contour plot.
 */
public class PlotHistogram2DContour extends PlotOptions {

    /**
     * Determines whether or not the x axis bin attributes are picked by an algorithm. Note that
     * this should be set to false if you want to manually set the number of bins using the
     * attributes in xbins.
     */
    @Option
    public Boolean autobinx;

    /**
     * Determines whether or not the y axis bin attributes are picked by an algorithm. Note that
     * this should be set to false if you want to manually set the number of bins using the
     * attributes in ybins.
     */
    @Option
    public Boolean autobiny;

    /**
     * Determines whether or not the colorscale is picked using the sign of the input z values.
     */
    @Option
    public Boolean autocolorscale;

    /**
     * Determines whether or not the contour level attributes are picked by an algorithm. If "true",
     * the number of contour levels can be set in "ncontours". If "false", set the contour level
     * attributes in "contours".
     * <p>
     * Default: true
     */
    @Option
    public Boolean autocontour;
    
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
     * Controls the display of the contours.
     */
    @Option
    public final ContoursOptions contours = new ContoursOptions();
    
    /**
     * Specifies the binning function used for this histogram trace.
     * <p>
     * Default: COUNT
     */
    @Option
    public HistFuncEnum histfunc;

    /**
     * Specifies the type of normalization used for this histogram trace.
     */
    @Option
    public HistNormEnum histnorm;

    /**
     * The color of line bounding the box(es).
     */
    @Option("line.color")
    public String line_color;
    
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
     * Has an effect only if "shape" is set to "spline" The amount of smoothing. "0" corresponds to
     * no smoothing (equivalent to a "linear" shape).
     * <p>
     * Default: 1
     */
    @Option("line.smoothing")
    public Double line_smoothing;

    /**
     * The width (in px) of line bounding the box(es).
     */
    @Option("line.width")
    public Integer line_width;
    
    /**
     * The aggregation data.
     */
    @Option("marker.color")
    public String[] marker_color;

    /**
     * Specifies the maximum number of desired bins. This value will be used in an algorithm that
     * will decide the optimal bin size such that the histogram best visualizes the distribution of
     * the data.
     * <p>
     * Default: 0
     */
    @Option
    public Integer nbinsx;

    /**
     * Specifies the maximum number of desired bins. This value will be used in an algorithm that
     * will decide the optimal bin size such that the histogram best visualizes the distribution of
     * the data.
     * <p>
     * Default: 0
     */
    @Option
    public Integer nbinsy;

    /**
     * The maximum number of contour levels. The actual number of contours will be chosen
     * automatically to be less than or equal to the value of "ncontours". Has an effect only if
     * "autocontour" is "true" or if "contours.size" is missing.
     * <p>
     * Constraints: &gt;0.
     * <p>
     * Default: 15
     */
    @Option
    public Integer ncontours;

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
     * Reference between this trace's x coordinates and a 2D Cartesian x axis. If "x" (the default
     * value), the x coordinates refer to "layout.xaxis". If "x2", the x coordinates refer to
     * "layout.xaxis2", and so on.
     */
    @Option
    public String xaxis;

    /**
     * Options for the x axis bins.
     */
    @Option
    public final BinOptions xbins = new BinOptions();

    /**
     * The calendar system to use with "x" date data.
     * <p>
     * Default: GREGORIAN.
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
     * Reference between this trace's y coordinates and a 2D Cartesian y axis. If "y" (the default
     * value), the y coordinates refer to "layout.yaxis". If "y2", the y coordinates refer to
     * "layout.xaxis2", and so on.
     */
    @Option
    public String yaxis;
    
    /**
     * Options for the y axis bins.
     */
    @Option
    public final BinOptions ybins = new BinOptions();
    
    /**
     * The calendar system to use with "y" date data.
     * <p>
     * Default: GREGORIAN
     */
    @Option
    public CalendarTypeEnum ycalendar;
    
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
    
}
