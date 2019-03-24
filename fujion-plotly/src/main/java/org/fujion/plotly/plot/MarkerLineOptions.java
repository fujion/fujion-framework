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

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

public class MarkerLineOptions extends Options {

    /**
     * Has an effect only if "marker.line.color" is set to a numerical array. Determines whether the
     * colorscale is a default palette ("autocolorscale: true") or the palette determined by
     * "marker.line.colorscale". In case "colorscale" is unspecified or "autocolorscale" is true,
     * the default palette will be chosen according to whether numbers in the "color" array are all
     * positive, all negative or mixed.
     */
    @Option
    public Boolean autocolorscale;
    
    /**
     * Has an effect only if "marker.line.color" is set to a numerical array and "cmin", "cmax" are
     * set by the user. In this case, it controls whether the range of colors in "colorscale" is
     * mapped to the range of values in the "color" array ("cauto: true"), or the "cmin"/"cmax"
     * values ("cauto: false").
     * <p>
     * Default: false (when "cmin", "cmax" are set by the user)
     */
    @Option
    public Boolean cauto;
    
    /**
     * The upper bound of the color domain. Value should be associated to the "marker.line.color"
     * array index, and if set, "marker.line.cmin" must be set as well.
     */
    @Option
    public Integer cmax;
    
    /**
     * The lower bound of the color domain. Value should be associated to the "marker.line.color"
     * array index, and if set, "marker.line.cmax" must be set as well.
     */
    @Option
    public Integer cmin;
    
    /**
     * The line color. Array elements are mapped to the colorscale relative to the max and min
     * values of the array or relative to "cmin" and "cmax" if set.
     */
    @Option("color")
    public int[] color$array;

    /**
     * Sets all line colors to the same value.
     */
    @Option("color")
    public String color$string;
    
    /**
     * The colorscale and only has an effect if "marker.line.color" is set to a numerical array. The
     * colorscale must be an array containing arrays mapping a normalized value to an rgb, rgba,
     * hex, hsl, hsv, or named color string. At minimum, a mapping for the lowest (0) and highest
     * (1) values are required. For example, "[[0, "rgb(0,0,255)", [1, "rgb(255,0,0)"]]". To control
     * the bounds of the colorscale in color space, use "marker.line.cmin" and "marker.line.cmax".
     * Alternatively, "colorscale" may be a palette name string of the following list: Greys,
     * YlGnBu, Greens, YlOrRd, Bluered, RdBu, Reds, Blues, Picnic, Rainbow, Portland, Jet, Hot,
     * Blackbody, Earth, Electric, Viridis, Cividis
     */
    @Option
    public Object colorscale;

    /**
     * The border line color of the outlier sample points.
     * <p>
     * Default: marker color
     */
    @Option
    public String outliercolor;
    
    /**
     * Sets the border line width (in px) of the outlier sample points.
     * <p>
     * Default: 1
     */
    @Option
    public Integer outlierwidth;
    
    /**
     * Has an effect only if "marker.line.color" is set to a numerical array. Reverses the color
     * mapping if true ("cmin" will correspond to the last color in the array and "cmax" will
     * correspond to the first color).
     */
    @Option
    public Boolean reversescale;
    
    /**
     * The width (in px) of the lines bounding the marker points.
     */
    @Option("width")
    public int[] width$array;

    /**
     * The width (in px) of the lines bounding the marker points to the same value.
     */
    @Option("width")
    public Integer width$number;
    
}
