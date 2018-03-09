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

import org.fujion.plotly.common.DashStyleEnum;
import org.fujion.plotly.common.PolarUnitsEnum;

/**
 * Options for polar GL scatter plot.
 */
public class PlotScatterPolarGL extends PlotOptions {
    
    /**
     * Determines whether or not gaps (i.e. {nan} or missing values) in the provided data arrays are
     * connected.
     */
    public Boolean connectgaps;
    
    /**
     * The area to fill with a solid color.
     */
    public FillAreaEnum fill;
    
    /**
     * The fill color.
     * <p>
     * Default: A half-transparent variant of the line color, marker color, or marker line color,
     * whichever is available.
     */
    public String fillcolor;
    
    /**
     * Any combination of "points", "fills" joined with a "+" examples: "points", "fills",
     * "points+fills" Do the hover effects highlight individual points (markers or line points) or
     * do they highlight filled regions? If the fill is "toself" or "tonext" and there are no
     * markers or text, then the default is "fills", otherwise it is "points".
     */
    public String hoveron;
    
    /**
     * The line color.
     */
    public String line_color;
    
    /**
     * The dash style.
     */
    public DashStyleEnum line_dash;
    
    /**
     * The line width (in px).
     * <p>
     * Default: 2
     */
    public Integer line_width;

    /**
     * Options for marker.
     */
    public final MarkerOptions marker = new MarkerOptions();

    /**
     * Any combination of "lines", "markers", "text" joined with a "+" OR "none". examples: "lines",
     * "markers", "lines+markers", "lines+markers+text", "none" Determines the drawing mode for this
     * scatter trace. If the provided "mode" includes "text" then the "text" elements appear at the
     * coordinates. Otherwise, the "text" elements appear on hover. If there are less than 20
     * points, then the default is "lines+markers". Otherwise, "lines".
     */
    public String mode;
    
    /**
     * The radial coordinates
     */
    public double[] r;

    /**
    *
    */
    public final SelectedOptions selected = new SelectedOptions();
    
    /**
     * Reference between this trace's data coordinates and a polar subplot. If "polar" (the default
     * value), the data refer to "layout.polar". If "polar2", the data refer to "layout.polar2", and
     * so on.
     */
    public String subplot;

    /**
     * Text elements associated with each (x,y) pair as an array of strings where the items are
     * mapped in order to the this trace's (x,y) coordinates. If trace "hoverinfo" contains a "text"
     * flag and "hovertext" is not set, these elements will be seen in the hover labels.
     */
    public String[] text$array;
    
    /**
     * Text elements associated with each (x,y) pair as a single string where the same string
     * appears over all the data points. If trace "hoverinfo" contains a "text" flag and "hovertext"
     * is not set, these elements will be seen in the hover labels.
     */
    public String text$string;
    
    /**
     * The angular coordinates
     */
    public double[] theta;

    /**
     * The unit of input "theta" values. Has an effect only when on "linear" angular axes. Defaults
     * to DEGREES.
     */
    public PolarUnitsEnum thetaunit;
    
    /**
    *
    */
    public final SelectedOptions unselected = new SelectedOptions();

}
