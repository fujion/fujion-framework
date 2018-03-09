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
import org.fujion.plotly.common.TextPositionEnum;

/**
 * Options for map box scatter plot.
 */
public class PlotScatterMapBox extends PlotOptions {

    /**
     * Determines whether or not gaps (i.e. {nan} or missing values) in the provided data arrays are
     * connected.
     */
    public Boolean connectgaps;
    
    /**
     * The area to fill with a solid color. Only NONE and TOSELF are recognized.
     */
    public FillAreaEnum fill;
    
    /**
     * The fill color.
     * <p>
     * Default: a half-transparent variant of the line color, marker color, or marker line color,
     * whichever is available.
     */
    public String fillcolor;

    /**
     * Hover text elements associated with each (lon,lat) pair or item in "locations" as an array of
     * string where the items are mapped in order to the this trace's (lon,lat) or "locations"
     * coordinates. To be seen, trace "hoverinfo" must contain a "text" flag.
     */
    public String[] hovertext$array;

    /**
     * Hover text elements associated with each (lon,lat) pair or item in "locations" as a single
     * string where the same string appears over all the data points. To be seen, trace "hoverinfo"
     * must contain a "text" flag.
     */
    public String hovertext$string;

    /**
     * The latitude coordinates (in degrees North).
     */
    public double[] lat;

    /**
     * The line color.
     */
    public String line_color;

    /**
     * The line width (in px).
     * <p>
     * Default: 2
     */
    public Integer line_width;

    /**
     * The longitude coordinates (in degrees East).
     */
    public double[] lon;
    
    /**
     * Options for marker.
     */
    public final MarkerOptions marker = new MarkerOptions();
    
    /**
     * Any combination of "lines", "markers", "text" joined with a "+" OR "none". examples: "lines",
     * "markers", "lines+markers", "lines+markers+text", "none" default: "markers" Determines the
     * drawing mode for this scatter trace. If the provided "mode" includes "text" then the "text"
     * elements appear at the coordinates. Otherwise, the "text" elements appear on hover.
     */
    public String mode;
    
    /**
    *
    */
    public final SelectedOptions selected = new SelectedOptions();
    
    /**
     * Reference between this trace's data coordinates and a mapbox subplot. If "mapbox" (the
     * default value), the data refer to "layout.mapbox". If "mapbox2", the data refer to
     * "layout.mapbox2", and so on.
     */
    public String subplot;
    
    /**
     * Text elements associated with each (lon,lat) pair or item in "locations" as an array of
     * string where the items are mapped in order to the this trace's (lon,lat) or "locations"
     * coordinates. If trace "hoverinfo" contains a "text" flag and "hovertext" is not set, these
     * elements will be seen in the hover labels.
     */
    public String[] text$array;
    
    /**
     * Text elements associated with each (lon,lat) pair or item in "locations" as a single string
     * where the same string appears over all the data points. If trace "hoverinfo" contains a
     * "text" flag and "hovertext" is not set, these elements will be seen in the hover labels.
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
    *
    */
    public final SelectedOptions unselected = new SelectedOptions();
    
}
