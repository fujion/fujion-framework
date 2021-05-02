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
package org.fujion.plotly.plot;

import org.fujion.annotation.Option;
import org.fujion.plotly.common.CalendarTypeEnum;
import org.fujion.plotly.plot.PlotHistogram.HistFuncEnum;
import org.fujion.plotly.plot.PlotHistogram.HistNormEnum;

/**
 * Options for 2D histogram plot.
 */
public class PlotHistogram2D extends PlotOptions {

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
     * Reverses the colorscale.
     */
    @Option
    public Boolean reversescale;
    
    /**
     * Determines whether or not a colorbar is displayed for this trace.
     * <p>
     * Default: true.
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
     * The vertical gap (in pixels) between bricks.
     * <p>
     * Default: 0
     */
    @Option
    public Integer ygap;

    /**
     * Data points for the aggregation data.
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
