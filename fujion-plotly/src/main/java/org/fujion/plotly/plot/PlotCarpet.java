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

import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.layout.ABCAxisOptions;

/**
 * Options for carpet plot.
 * <p>
 * A carpet plot is any of a few different specific types of plot. The more common plot referred to
 * as a carpet plot is one that illustrates the interaction between two or more independent
 * variables and one or more dependent variables in a two-dimensional plot. Besides the ability to
 * incorporate more variables, another feature that distinguishes a carpet plot from an equivalent
 * contour plot or 3D surface plot is that a carpet plot can be used to more accurately interpolate
 * data points. A conventional carpet plot can capture the interaction of up to three independent
 * variables and three dependent variables and still be easily read and interpolated.
 */
public class PlotCarpet extends PlotOptions {

    /**
     * An array containing values of the first parameter value.
     */
    public double[] a;

    /**
     * Alternate to "a". Builds a linear space of a coordinates. Use with "da" where "a0" is the
     * starting coordinate and "da" the step.
     * <p>
     * Default: 0
     */
    public Double a0;

    /**
     * Style options for a axis.
     */
    public final ABCAxisOptions aaxis = new ABCAxisOptions();

    /**
     * A two dimensional array of y coordinates at each carpet point.
     */
    public double[][] b;

    /**
     * Alternate to "b". Builds a linear space of a coordinates. Use with "db" where "b0" is the
     * starting coordinate and "db" the step.
     * <p>
     * Default: 0
     */
    public Double b0;

    /**
     * Style options for b axis.
     */
    public final ABCAxisOptions baxis = new ABCAxisOptions();

    /**
     * An identifier for this carpet, so that "scattercarpet" and "scattercontour" traces can
     * specify a carpet plot on which they lie.
     */
    public String carpet;

    /**
     * The shift applied to each successive row of data in creating a cheater plot. Only used if "x"
     * is been omitted.
     * <p>
     * Default: 1
     */
    public Double cheaterslope;

    /**
     * Default for all colors associated with this axis all at once: line, font, tick, and grid
     * colors. Grid color is lightened by blending this with the plot background Individual pieces
     * can override this.
     * <p>
     * Default: "#444"
     */
    public String color;

    /**
     * The a coordinate step. See "a0" for more info.
     * <p>
     * Default: 1
     */
    public Double da;
    
    /**
     * The b coordinate step. See "b0" for more info.
     * <p>
     * Default: 1
     */
    public Double db;
    
    /**
     * The default font used for axis &amp; tick labels on this carpet.
     */
    public final FontOptions font = new FontOptions();
    
    /**
     * A two dimensional array of x coordinates at each carpet point. If ommitted, the plot is a
     * cheater plot and the xaxis is hidden by default.
     */
    public double[][] x;
    
    /**
     * Reference between this trace's x coordinates and a 2D Cartesian x axis. If "x" (the default
     * value), the x coordinates refer to "layout.xaxis". If "x2", the x coordinates refer to
     * "layout.xaxis2", and so on.
     */
    public String xaxis;
    
    /**
     * A two dimensional array of y coordinates at each carpet point.
     */
    public double[][] y;
    
    /**
     * Reference between this trace's y coordinates and a 2D Cartesian y axis. If "y" (the default
     * value), the y coordinates refer to "layout.yaxis". If "y2", the y coordinates refer to
     * "layout.xaxis2", and so on.
     */
    public String yaxis;
    
}
