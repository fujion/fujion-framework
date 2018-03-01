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

import org.fujion.ancillary.Options;
import org.fujion.plotly.common.SymbolEnum;

public class MarkerOptions extends Options {

    public enum GradientTypeEnum {
        HORIZONTAL, NONE, RADIAL, VERTICAL;

        @Override
        public String toString() {
            return name().toLowerCase();
        }

    }
    
    public enum SizeModeEnum {
        AREA, DIAMETER;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Has an effect only if "marker.color" is set to a numerical array. Determines whether the
     * colorscale is a default palette ("autocolorscale: true") or the palette determined by
     * "marker.colorscale". In case "colorscale" is unspecified or "autocolorscale" is true, the
     * default palette will be chosen according to whether numbers in the "color" array are all
     * positive, all negative or mixed.
     */
    public Boolean autocolorscale;
    
    /**
     * Determines if colors are blended together for a translucency effect in case "opacity" is
     * specified as a value less then "1". Setting "blend" to "true" reduces zoom/pan speed if used
     * with large numbers of points.
     */
    public Boolean blend;

    /**
     * Specifies what fraction of the marker area is covered with the border.
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 0
     */
    public Double border_arearatio;
    
    /**
     * The stroke color. It accepts a specific color. If the color is not fully opaque and there are
     * hundreds of thousands of points, it may cause slower zooming and panning.
     */
    public String border_color;

    /**
     * Has an effect only if "marker.color" is set to a numerical array and "cmin", "cmax" are set
     * by the user. In this case, it controls whether the range of colors in "colorscale" is mapped
     * to the range of values in the "color" array ("cauto: true"), or the "cmin"/"cmax" values
     * ("cauto: false").
     * <p>
     * Default: false (when "cmin", "cmax" are set by the user)
     */
    public Boolean cauto;
    
    /**
     * The upper bound of the color domain. Value should be associated to the "marker.color" array
     * index, and if set, "marker.cmin" must be set as well.
     */
    public Integer cmax;
    
    /**
     * The lower bound of the color domain. Value should be associated to the "marker.color" array
     * index, and if set, "marker.cmax" must be set as well.
     */
    public Integer cmin;
    
    /**
     * The marker color. It accepts either a specific color or an array of numbers that are mapped
     * to the colorscale relative to the max and min values of the array or relative to "cmin" and
     * "cmax" if set.
     */
    public String color;

    /**
     * Color bar options.
     */
    public final ColorbarOptions colorbar = new ColorbarOptions();

    /**
     * For pie charts, specifies the color of each sector. If not specified, the default trace color
     * set is used to pick the sector colors.
     */
    public String[] colors;
    
    /**
     * The colorscale and only has an effect if "marker.color" is set to a numerical array. The
     * colorscale must be an array containing arrays mapping a normalized value to an rgb, rgba,
     * hex, hsl, hsv, or named color string. At minimum, a mapping for the lowest (0) and highest
     * (1) values are required. For example, "[[0, "rgb(0,0,255)", [1, "rgb(255,0,0)"]]". To control
     * the bounds of the colorscale in color space, use "marker.cmin" and "marker.cmax".
     * Alternatively, "colorscale" may be a palette name string of the following list: Greys,
     * YlGnBu, Greens, YlOrRd, Bluered, RdBu, Reds, Blues, Picnic, Rainbow, Portland, Jet, Hot,
     * Blackbody, Earth, Electric, Viridis, Cividis.
     */
    public Object colorscale;
    
    /**
     * The final color of the gradient fill: the center color for radial, the right for horizontal,
     * or the bottom for vertical.
     */
    public String gradient_color;

    /**
     * The type of gradient used to fill the markers.
     */
    public GradientTypeEnum[] gradient_type$array;
    
    /**
     * The type of gradient used to fill the markers to the same value.
     */
    public GradientTypeEnum gradient_type$enum;
    
    /**
     * Line options.
     */
    public final MarkerLineOptions line = new MarkerLineOptions();

    /**
     * Maximum number of points to be drawn on the graph. 0 corresponds to no limit.
     * <p>
     * Default: 0
     */
    public Integer maxdisplayed;

    /**
     * The marker opacity.
     * <p>
     * Constraints: &ge;0 and &le;1
     */
    public Double opacity;

    /**
     * The color of the outlier sample points.
     */
    public String outliercolor;
    
    /**
     * Has an effect only if "marker.color" is set to a numerical array. Reverses the color mapping
     * if true ("cmin" will correspond to the last color in the array and "cmax" will correspond to
     * the first color).
     */
    public Boolean reversescale;
    
    /**
     * Has an effect only if "color" is set to a numerical array. Determines whether or not a
     * colorbar is displayed.
     */
    public Boolean showscale;

    /**
     * The marker size (in px).
     */
    public int[] size$array;
    
    /**
     * The marker size (in px).
     */
    public Integer size$number;

    /**
     * The maximum size (in px) of the rendered marker points. Effective when the pointcloud shows
     * only few points.
     * <p>
     * Default: 20
     */
    public Integer sizemax;
    
    /**
     * The minimum size (in px) of the rendered marker points.
     * <p>
     * Default: 0
     */
    public Integer sizemin;
    
    /**
     * The rule for which the data in "size" is converted to pixels.
     * <p>
     * Default: DIAMETER
     */
    public SizeModeEnum sizemode;

    /**
     * The scale factor used to determine the rendered size of marker points. Use with "sizemin" and
     * "sizemode".
     * <p>
     * Default: 1
     */
    public Integer sizeref;
    
    /**
     * The marker symbol type.
     */
    public SymbolEnum symbol;
    
}
