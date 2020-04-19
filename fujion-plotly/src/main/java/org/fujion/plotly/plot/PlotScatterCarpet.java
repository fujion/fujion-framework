/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
 * Options for carpet scatter plot.
 */
public class PlotScatterCarpet extends PlotOptions {
    
    /**
     * The quantity of component "a" in each data point. If "a", "b", and "c" are all provided, they
     * need not be normalized, only the relative values matter. If only two arrays are provided they
     * must be normalized to match "ternary&lt;i&gt;.sum".
     */
    @Option
    public double[] a;
    
    /**
     * The quantity of component "a" in each data point. If "a", "b", and "c" are all provided, they
     * need not be normalized, only the relative values matter. If only two arrays are provided they
     * must be normalized to match "ternary&lt;i&gt;.sum".
     */
    @Option
    public double[] b;

    /**
     * An identifier for this carpet, so that "scattercarpet" and "scattercontour" traces can
     * specify a carpet plot on which they lie
     */
    @Option
    public String carpet;
    
    /**
     * Determines whether or not gaps (i.e. {nan} or missing values) in the provided data arrays are
     * connected.
     */
    @Option
    public Boolean connectgaps;

    /**
     * The area to fill with a solid color. Use with "fillcolor".
     * <p>
     * Constraints: Only NONE, TOSELF, TONEXT are recognized
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
     * Any combination of "points", "fills" joined with a "+". Examples: "points", "fills",
     * "points+fills" Do the hover effects highlight individual points (markers or line points) or
     * do they highlight filled regions? If the fill is "toself" or "tonext" and there are no
     * markers or text, then the default is "fills", otherwise it is "points".
     */
    @Option
    public String hoveron;
    
    /**
     * Style options for the line.
     */
    @Option
    public final LineOptions line = new LineOptions();
    
    /**
     * Options for marker.
     */
    @Option
    public final MarkerOptions marker = new MarkerOptions();
    
    /**
     * Any combination of "lines", "markers", "text" joined with a "+" OR "none". examples: "lines",
     * "markers", "lines+markers", "lines+markers+text", "none" Determines the drawing mode for this
     * scatter trace. If the provided "mode" includes "text" then the "text" elements appear at the
     * coordinates. Otherwise, the "text" elements appear on hover. If there are less than 20
     * points, then the default is "lines+markers". Otherwise, "lines".
     */
    @Option
    public String mode;

    /**
     *
     */
    @Option
    public final SelectedOptions selected = new SelectedOptions();
    
    /**
     * Text elements associated with each (a,b,c) point as an array of strings where the items are
     * mapped in order to the the data points in (a,b,c).
     */
    @Option("text")
    public String[] text$array;

    /**
     * Text elements associated with each (a,b,c) point as a single string where the same string
     * appears over all the data points.
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
     * Default: MIDDLE_CENTER
     */
    @Option
    public TextPositionEnum textposition;
    
    /**
     *
     */
    @Option
    public final SelectedOptions unselected = new SelectedOptions();
    
    /**
     * Reference between this trace's x coordinates and a 2D Cartesian x axis. If "x" (the default
     * value), the x coordinates refer to "layout.xaxis". If "x2", the x coordinates refer to
     * "layout.xaxis2", and so on.
     */
    @Option
    public String xaxis;

    /**
     * Reference between this trace's y coordinates and a 2D Cartesian y axis. If "y" (the default
     * value), the y coordinates refer to "layout.yaxis". If "y2", the y coordinates refer to
     * "layout.xaxis2", and so on.
     */
    @Option
    public String yaxis;
    
}
