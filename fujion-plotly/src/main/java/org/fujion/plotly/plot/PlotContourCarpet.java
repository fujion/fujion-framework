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

import org.fujion.plotly.common.DashStyleEnum;

/**
 * Options for contour carpet plot.
 */
public class PlotContourCarpet extends PlotOptions {

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
     * The coordinate type for the a data.
     */
    public CoordinateTypeEnum atype;
    
    /**
     * Determines whether or not the colorscale is picked using the sign of the input z values.
     */
    public Boolean autocolorscale;
    
    /**
     * Determines whether or not the contour level attributes are picked by an algorithm. If "true"
     * (the default), the number of contour levels can be set in "ncontours". If "false", set the
     * contour level attributes in "contours".
     */
    public Boolean autocontour;
    
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
     * The coordinate type for the b data.
     */
    public CoordinateTypeEnum btype;
    
    /**
     * The "carpet" of the carpet axes on which this contour trace lies.
     */
    public String carpet;
    
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
     * Controls how data are represented within a contour plot.
     */
    public final ContoursOptions contours = new ContoursOptions();
    
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
     * The fill color if "contours.type" is "constraint".
     * <p>
     * Default: a half-transparent variant of the line color, marker color, or marker line color,
     * whichever is available.
     */
    public String fillcolor;
    
    /**
     * The color of line bounding the box(es).
     */
    public String line_color;

    /**
     * The dash style of lines.
     * <p>
     * Default: SOLID
     */
    public DashStyleEnum line_dash$enum;

    /**
     * An alternative in the form of a dash length list in px (eg "5px,10px,2px,2px").
     */
    public String line_dash$string;
    
    /**
     * Has an effect only if "shape" is set to "spline" The amount of smoothing. "0" corresponds to
     * no smoothing (equivalent to a "linear" shape).
     * <p>
     * Default: 1
     */
    public Double line_smoothing;
    
    /**
     * The width (in px) of line bounding the box(es).
     */
    public Integer line_width;

    /**
     * The maximum number of contour levels. The actual number of contours will be chosen
     * automatically to be less than or equal to the value of "ncontours". Has an effect only if
     * "autocontour" is "true" or if "contours.size" is missing.
     * <p>
     * Constraints: &gt;0
     * <p>
     * Default: 15
     */
    public Integer ncontours;

    /**
     * Reverses the colorscale.
     */
    public Boolean reversescale;
    
    /**
     * Determines whether or not a colorbar is displayed for this trace.
     * <p>
     * Default: true
     */
    public Boolean showscale;
    
    /**
     * The text elements associated with each z value.
     */
    public String[] text$array;
    
    /**
     * Text constant associated with each z value.
     */
    public String text$string;
    
    /**
     * Transposes the z data.
     */
    public Boolean transpose;
    
    /**
     * Reference between this trace's x coordinates and a 2D Cartesian x axis. If "x" (the default
     * value), the x coordinates refer to "layout.xaxis". If "x2", the x coordinates refer to
     * "layout.xaxis2", and so on.
     */
    public String xaxis;

    /**
     * Reference between this trace's y coordinates and a 2D Cartesian y axis. If "y" (the default
     * value), the y coordinates refer to "layout.yaxis". If "y2", the y coordinates refer to
     * "layout.xaxis2", and so on.
     */
    public String yaxis;

    /**
     * Data points for z-axis.
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
     * The upper bound of color domain.
     */
    public Double zmax;
    
    /**
     * The lower bound of color domain.
     */
    public Double zmin;
    
}
