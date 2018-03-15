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

/**
 * Options for surface plot.
 */
public class PlotSurface extends PlotOptions {
    
    /**
     * Determines whether or not the color scale is picked using the sign of the input z values.
     */
    @Option
    public Boolean autocolorscale;

    /**
     * Determines the whether or not the color domain is computed with respect to the input data.
     */
    @Option
    public Boolean cauto;

    /**
     * The upper bound of color domain.
     */
    @Option
    public Integer cmax;

    /**
     * The lower bound of color domain.
     */
    @Option
    public Integer cmin;

    /**
     * Options for color bar display.
     */
    @Option
    public final ColorbarOptions colorbar = new ColorbarOptions();
    
    /**
     * The colorscale and only has an effect if "color" is set to a numerical array. The colorscale
     * must be an array containing arrays mapping a normalized value to an rgb, rgba, hex, hsl, hsv,
     * or named color string. At minimum, a mapping for the lowest (0) and highest (1) values are
     * required. For example, "[[0, "rgb(0,0,255)", [1, "rgb(255,0,0)"]]". To control the bounds of
     * the colorscale in color space, use "cmin" and "cmax".
     */
    @Option
    public Object[] colorscale$array;

    /**
     * Alternatively, "colorscale" may be a palette name string of the following list: Greys,
     * YlGnBu, Greens, YlOrRd, Bluered, RdBu, Reds, Blues, Picnic, Rainbow, Portland, Jet, Hot,
     * Blackbody, Earth, Electric, Viridis, Cividis
     */
    @Option
    public String colorscale$string;
    
    /**
     * Controls the display of the x contours.
     */
    @Option
    public final ContourOptions contours_x = new ContourOptions();

    /**
     * Controls the display of the y contours.
     */
    @Option
    public final ContourOptions contours_y = new ContourOptions();
    
    /**
     * Controls the display of the z contours.
     */
    @Option
    public final ContourOptions contours_z = new ContourOptions();

    /**
     * Determines whether or not a surface is drawn. For example, set "hidesurface" to "false"
     * "contours.x.show" to "true" and "contours.y.show" to "true" to draw a wire frame plot.
     */
    @Option
    public Boolean hidesurface;
    
    /**
     * Options for lighting effect.
     */
    @Option
    public final LightingOptions lighting = new LightingOptions();

    /**
     * Numeric vector, representing the X coordinate for each vertex.
     * <p>
     * Constraints: &ge;-100000 and &le;100000
     * <p>
     * Default: 10
     */
    @Option
    public Double lightposition_x;
    
    /**
     * Numeric vector, representing the Y coordinate for each vertex. *
     * <p>
     * Constraints: &ge;-100000 and &le;100000
     * <p>
     * Default: 10000
     */
    @Option
    public Double lightposition_y;
    
    /**
     * Numeric vector, representing the Z coordinate for each vertex. *
     * <p>
     * Constraints: &ge;-100000 and &le;100000
     * <p>
     * Default: 0
     */
    @Option
    public Double lightposition_z;
    
    /**
     * Reverses the colorscale.
     */
    @Option
    public Boolean reversescale;

    /**
     * Reference between this trace's 3D coordinate system and a 3D scene. If "scene" (the default
     * value), the (x,y,z) coordinates refer to "layout.scene". If "scene2", the (x,y,z) coordinates
     * refer to "layout.scene2", and so on.
     */
    @Option
    public String scene;

    /**
     * Determines whether or not a colorbar is displayed for this trace.
     * <p>
     * Default: true
     */
    @Option
    public Boolean showscale;

    /**
     * The surface fill color.
     */
    @Option
    public String surfacecolor;

    /**
     * The text elements associated with each z value. If trace "hoverinfo" contains a "text" flag
     * and "hovertext" is not set, these elements will be seen in the hover labels.
     */
    @Option
    public String[] text$array;
    
    /**
     * The text elements associated with each z value to the same value. If trace "hoverinfo"
     * contains a "text" flag and "hovertext" is not set, these elements will be seen in the hover
     * labels.
     */
    @Option
    public String text$string;
    
    /**
     * Data points for x-axis.
     */
    @Option
    public Object[] x;

    /**
     * Data points for x-axis. Alternate form for numeric data.
     */
    @Option
    public double[] x$number;
    
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
    @Option
    public double[] y$number;
    
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
    @Option
    public double[] z$number;
    
    /**
     * The calendar system to use with "z" date data.
     * <p>
     * Default: GREGORIAN
     */
    @Option
    public CalendarTypeEnum zcalendar;
}
