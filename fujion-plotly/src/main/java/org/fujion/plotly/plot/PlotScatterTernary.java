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
 * Options for ternary scatter plot.
 */
public class PlotScatterTernary extends PlotOptions {
    
    /**
     * The quantity of component "a" in each data point. If "a", "b", and "c" are all provided, they
     * need not be normalized, only the relative values matter. If only two arrays are provided they
     * must be normalized to match "ternary&lt;i&gt;.sum".
     */
    public double[] a;

    /**
     * The quantity of component "a" in each data point. If "a", "b", and "c" are all provided, they
     * need not be normalized, only the relative values matter. If only two arrays are provided they
     * must be normalized to match "ternary&lt;i&gt;.sum".
     */
    public double[] b;
    
    /**
     * The quantity of component "c" in each data point. If "a", "b", and "c" are all provided, they
     * need not be normalized, only the relative values matter. If only two arrays are provided they
     * must be normalized to match "ternary&lt;i&gt;.sum".
     */
    public double[] c;
    
    /**
     * Determines whether or not markers and text nodes are clipped about the subplot axes. To show
     * markers and text nodes above axis lines and tick labels, make sure to set "xaxis.layer" and
     * "yaxis.layer" to "below traces".
     * <p>
     * Default: true
     */
    public Boolean cliponaxis;
    
    /**
     * Determines whether or not gaps (i.e. {nan} or missing values) in the provided data arrays are
     * connected.
     */
    public Boolean connectgaps;

    /**
     * The area to fill with a solid color. Use with "fillcolor". Only NONE, TOSELF, TONEXT are
     * recognized.
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
     * Hover text elements associated with each (x,y) pair as an array of strings where the items
     * are mapped in order to the this trace's (x,y) coordinates. To be seen, trace "hoverinfo" must
     * contain a "text" flag.
     */
    public String[] hovertext$array;
    
    /**
     * Hover text elements associated with each (x,y) pair as a single string where the same string
     * appears over all the data points. To be seen, trace "hoverinfo" must contain a "text" flag.
     */
    public String hovertext$string;
    
    /**
     * Any combination of "points", "fills" joined with a "+" examples: "points", "fills",
     * "points+fills" Do the hover effects highlight individual points (markers or line points) or
     * do they highlight filled regions? If the fill is "toself" or "tonext" and there are no
     * markers or text, then the default is "fills", otherwise it is "points".
     */
    public String hoveron;
    
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
     * "markers", "lines+markers", "lines+markers+text", "none" default: "markers" Determines the
     * drawing mode for this scatter trace. If the provided "mode" includes "text" then the "text"
     * elements appear at the coordinates. Otherwise, the "text" elements appear on hover. If there
     * are less than 20 points, then the default is "lines+markers". Otherwise, "lines".
     */
    public String mode;
    
    /**
     *
     */
    public final SelectedOptions selected = new SelectedOptions();

    /**
     * Reference between this trace's data coordinates and a ternary subplot. If "ternary" (the
     * default value), the data refer to "layout.ternary". If "ternary2", the data refer to
     * "layout.ternary2", and so on.
     */
    public String subplot;
    
    /**
     * The number each triplet should sum to, if only two of "a", "b", and "c" are provided. This
     * overrides "ternary&lt;i&gt;.sum" to normalize this specific trace, but does not affect the
     * values displayed on the axes. 0 (or missing) means to use ternary&lt;i&gt;.sum
     */
    public Double sum;
    
    /**
     * Text elements associated with each (a,b,c) point as an array of strings where the items are
     * mapped in order to the the data points in (a,b,c).
     */
    public String[] text$array;

    /**
     * Text elements associated with each (a,b,c) point as a single string where the same string
     * appears over all the data points.
     */
    public String text$string;
    
    /**
     * The text font.
     */
    public final FontOptions textfont = new FontOptions();

    /**
     * The positions of the "text" elements with respects to the (x,y) coordinates.
     * <p>
     * Default: MIDDLE_CENTER
     */
    public TextPositionEnum textposition;
    
    /**
     *
     */
    public final SelectedOptions unselected = new SelectedOptions();
    
}
