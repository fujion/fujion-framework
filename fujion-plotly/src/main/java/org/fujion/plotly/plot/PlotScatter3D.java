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
import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.common.ProjectionOptions;
import org.fujion.plotly.common.TextPositionEnum;

/**
 * Options for 3D scatter plot.
 * <p>
 * A 3D scatter plot uses x, y and z coordinates to display values for three variables for a set of
 * data.
 */
public class PlotScatter3D extends PlotOptions {
    
    /**
     * Determines whether or not gaps (i.e. {nan} or missing values) in the provided data arrays are
     * connected.
     */
    public Boolean connectgaps;
    
    /**
     * Options for x-axis error bars.
     */
    public final ErrorBarOptions error__x = new ErrorBarOptions();
    
    /**
     * Options for y-axis error bars.
     */
    public final ErrorBarOptions error__y = new ErrorBarOptions();
    
    /**
     * Options for z-axis error bars.
     */
    public final ErrorBarOptions error__z = new ErrorBarOptions();

    /**
     * Text elements associated with each (x,y,z) triplet as an array of string where the items are
     * mapped in order to the this trace's (x,y,z) coordinates. To be seen, trace "hoverinfo" must
     * contain a "text" flag.
     */
    public String[] hovertext$array;

    /**
     * Text elements associated with each (x,y,z) triplet as a single string that appears over all
     * the data points. If an array of string, the items are mapped in order to the this trace's
     * (x,y,z) coordinates. To be seen, trace "hoverinfo" must contain a "text" flag.
     */
    public String hovertext$string;

    /**
     * Style options for the line.
     */
    public final LineOptions line = new LineOptions();

    /**
     * Options for marker.
     */
    public final MarkerOptions marker = new MarkerOptions();

    /**
     * Any combination of "lines", "markers", "text" joined with a "+" OR "none". examples: "lines",
     * "markers", "lines+markers", "lines+markers+text", "none" default: "lines+markers" Determines
     * the drawing mode for this scatter trace. If the provided "mode" includes "text" then the
     * "text" elements appear at the coordinates. Otherwise, the "text" elements appear on hover. If
     * there are less than 20 points, then the default is "lines+markers". Otherwise, "lines".
     */
    public String mode;

    /**
     * Projection options for x axis.
     */
    public final ProjectionOptions projection_x = new ProjectionOptions();
    
    /**
     * Projection options for y axis.
     */
    public final ProjectionOptions projection_y = new ProjectionOptions();

    /**
     * Projection options for z axis.
     */
    public final ProjectionOptions projection_z = new ProjectionOptions();

    /**
     * Reference between this trace's 3D coordinate system and a 3D scene. If "scene" (the default
     * value), the (x,y,z) coordinates refer to "layout.scene". If "scene2", the (x,y,z) coordinates
     * refer to "layout.scene2", and so on.
     */
    public String scene;

    /**
     * If "-1" (the default), the scatter points are not filled with a surface. If "0", "1", "2",
     * the scatter points are filled with a Delaunay surface about the x, y, z respectively.
     */
    public Integer surfaceaxis;
    
    /**
     * The surface fill color.
     */
    public String surfacecolor;
    
    /**
     * Text elements associated with each (x,y,z) triplet as an array of strings where the items are
     * mapped in order to the this trace's (x,y,z) coordinates. If trace "hoverinfo" contains a
     * "text" flag and "hovertext" is not set, these elements will be seen in the hover labels.
     */
    public String[] text$array;
    
    /**
     * Text elements associated with each (x,y,z) triplet as a single string that appears over all
     * the data points. If trace "hoverinfo" contains a "text" flag and "hovertext" is not set,
     * these elements will be seen in the hover labels.
     */
    public String text$string;
    
    /**
     * The text font.
     */
    public final FontOptions textfont = new FontOptions();
    
    /**
     * The positions of the "text" elements with respects to the (x,y) coordinates.
     * <p>
     * Default: TOP_CENTER
     */
    public TextPositionEnum textposition;
    
    /**
     * Data points for x-axis.
     */
    public Object[] x;
    
    /**
     * Data points for x-axis. Alternate form for numeric data.
     */
    public double[] x$number;

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
     * The calendar system to use with "y" date data.
     * <p>
     * Default: GREGORIAN
     */
    public CalendarTypeEnum ycalendar;

    /**
     * Data points for z-axis.
     */
    public Object[] z;

    /**
     * Data points for z-axis. Alternate form for numeric data.
     */
    public double[] z$number;

    /**
     * The calendar system to use with "z" date data.
     * <p>
     * Default: GREGORIAN
     */
    public CalendarTypeEnum zcalendar;
}
