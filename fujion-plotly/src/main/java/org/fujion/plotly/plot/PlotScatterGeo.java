/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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
import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.common.TextPositionEnum;

/**
 * Options for geo scatter plot.
 */
public class PlotScatterGeo extends PlotOptions {
    
    /**
     * Determines whether or not gaps (i.e. {nan} or missing values) in the provided data arrays are
     * connected.
     */
    @Option
    public Boolean connectgaps;

    /**
     * The area to fill with a solid color. Only NONE and TOSELF are recognized.
     */
    @Option
    public FillAreaEnum fill;

    /**
     * The fill color.
     * <p>
     * Default: A half-transparent variant of the line color, marker color, or marker line color,
     * whichever is available.
     */
    @Option
    public String fillcolor;
    
    /**
     * Sets a reference between this trace's geospatial coordinates and a geographic map. If "geo"
     * (the default value), the geospatial coordinates refer to "layout.geo". If "geo2", the
     * geospatial coordinates refer to "layout.geo2", and so on.
     */
    @Option
    public String geo;

    /**
     * Sets hover text elements associated with each (lon,lat) pair or item in "locations" as an
     * array of string where the items are mapped in order to the this trace's (lon,lat) or
     * "locations" coordinates. To be seen, trace "hoverinfo" must contain a "text" flag.
     */
    @Option("hovertext")
    public String[] hovertext$array;
    
    /**
     * Sets hover text elements associated with each (lon,lat) pair or item in "locations" as a
     * single string where the same string appears over all the data points. To be seen, trace
     * "hoverinfo" must contain a "text" flag.
     */
    @Option("hovertext")
    public String hovertext$string;
    
    /**
     * The latitude coordinates (in degrees North).
     */
    @Option
    public double[] lat;
    
    /**
     * Style options for the line.
     */
    @Option
    public final LineOptions line = new LineOptions();
    
    /**
     * Determines the set of locations used to match entries in "locations" to regions on the map.
     */
    @Option
    public LocationModeEnum locationmode;
    
    /**
     * The coordinates via location IDs or names. Coordinates correspond to the centroid of each
     * location given. See "locationmode" for more info.
     */
    @Option
    public String[] locations;

    /**
     * The longitude coordinates (in degrees East).
     */
    @Option
    public double[] lon;

    /**
     * Options for marker.
     */
    @Option
    public final MarkerOptions marker = new MarkerOptions();

    /**
     * Any combination of "lines", "markers", "text" joined with a "+" OR "none". examples: "lines",
     * "markers", "lines+markers", "lines+markers+text", "none" default: "lines+markers" Determines
     * the drawing mode for this scatter trace. If the provided "mode" includes "text" then the
     * "text" elements appear at the coordinates. Otherwise, the "text" elements appear on hover. If
     * there are less than 20 points, then the default is "lines+markers". Otherwise, "lines".
     */
    @Option
    public String mode;

    /**
    *
    */
    @Option
    public final SelectedOptions selected = new SelectedOptions();

    /**
     * Sets text elements associated with each (lon,lat) pair or item in "locations" as an array of
     * string where the items are mapped in order to the this trace's (lon,lat) or "locations"
     * coordinates. If trace "hoverinfo" contains a "text" flag and "hovertext" is not set, these
     * elements will be seen in the hover labels.
     */
    @Option("text")
    public String[] text$array;

    /**
     * Sets text elements associated with each (lon,lat) pair or item in "locations" as a single
     * string where the same string appears over all the data points. If trace "hoverinfo" contains
     * a "text" flag and "hovertext" is not set, these elements will be seen in the hover labels.
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
     * Default: TOP_CENTER
     */
    @Option
    public TextPositionEnum textposition;

    /**
    *
    */
    @Option
    public final SelectedOptions unselected = new SelectedOptions();
}
