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
    public Boolean autobinx;
    
    /**
     * Determines whether or not the y axis bin attributes are picked by an algorithm. Note that
     * this should be set to false if you want to manually set the number of bins using the
     * attributes in ybins.
     */
    public Boolean autobiny;
    
    /**
     * Determines whether or not the colorscale is picked using the sign of the input z values.
     */
    public Boolean autocolorscale;
    
    /**
     * Settings for the colorbar.
     */
    public final ColorbarOptions colorbar = new ColorbarOptions();

    /**
     * The colorscale. Must be an array containing arrays mapping a normalized value to an rgb,
     * rgba, hex, hsl, hsv, or named color string. At minimum, a mapping for the lowest (0) and
     * highest (1) values are required. For example, "[[0, "rgb(0,0,255)", [1, "rgb(255,0,0)"]]". To
     * control the bounds of the colorscale in z space, use zmin and zmax
     */
    public Object[] colorscale;
    
    /**
     * Specifies the binning function used for this histogram trace.
     * <p>
     * Default: COUNT
     */
    public HistFuncEnum histfunc;
    
    /**
     * Specifies the type of normalization used for this histogram trace.
     */
    public HistNormEnum histnorm;

    /**
     * The aggregation data.
     */
    public String[] marker_color;
    
    /**
     * Specifies the maximum number of desired bins. This value will be used in an algorithm that
     * will decide the optimal bin size such that the histogram best visualizes the distribution of
     * the data.
     * <p>
     * Default: 0
     */
    public Integer nbinsx;
    
    /**
     * Specifies the maximum number of desired bins. This value will be used in an algorithm that
     * will decide the optimal bin size such that the histogram best visualizes the distribution of
     * the data.
     * <p>
     * Default: 0
     */
    public Integer nbinsy;
    
    /**
     * Reverses the colorscale.
     */
    public Boolean reversescale;
    
    /**
     * Determines whether or not a colorbar is displayed for this trace.
     * <p>
     * Default: true.
     */
    public Boolean showscale;
    
    /**
     * Data points for x-axis.
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
     * Options for the x axis bins.
     */
    public final BinOptions xbins = new BinOptions();
    
    /**
     * The calendar system to use with "x" date data.
     * <p>
     * Default: GREGORIAN
     */
    public CalendarTypeEnum xcalendar;
    
    /**
     * The horizontal gap (in pixels) between bricks.
     * <p>
     * Default: 0
     */
    public Integer xgap;
    
    /**
     * Data points for y-axis.
     */
    public Object[] y;

    /**
     * Data points for y-axis. Alternate form for numeric data.
     */
    public double[] y$number;

    /**
     * Reference between this trace's y coordinates and a 2D Cartesian y axis. If "y" (the default
     * value), the y coordinates refer to "layout.yaxis". If "y2", the y coordinates refer to
     * "layout.xaxis2", and so on.
     */
    public String yaxis;
    
    /**
     * Options for the y axis bins.
     */
    public final BinOptions ybins = new BinOptions();
    
    /**
     * The calendar system to use with "y" date data.
     * <p>
     * Default: GREGORIAN
     */
    public CalendarTypeEnum ycalendar;

    /**
     * The vertical gap (in pixels) between bricks.
     * <p>
     * Default: 0
     */
    public Integer ygap;

    /**
     * Data points for the aggregation data.
     */
    public Object[] z;

    /**
     * Data points for z-axis. Alternate form for numeric data.
     */
    public double[] z$number;

    /**
     * Determines the whether or not the color domain is computed with respect to the input data.
     */
    public Boolean zauto;

    /**
     * The hover text formatting rule using d3 formatting mini-languages which are very similar to
     * those in Python. See
     * <a href="https://github.com/d3/d3-format/blob/master/README.md#locale_format">d3 format
     * reference.</a>
     */
    public String zhoverformat;

    /**
     * The upper bound of color domain.
     */
    public Double zmax;

    /**
     * The lower bound of color domain.
     */
    public Double zmin;

    /**
     * Picks a smoothing algorithm use to smooth "z" data.
     */
    public SmoothingEnum zsmooth;

}
