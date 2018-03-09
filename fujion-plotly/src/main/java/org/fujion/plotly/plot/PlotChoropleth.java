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

/**
 * Options for choropleth plot.
 * <p>
 * A choropleth plot is a thematic map in which areas are shaded or patterned in proportion to the
 * measurement of the statistical variable being displayed on the map, such as population density or
 * per-capita income.
 */
public class PlotChoropleth extends PlotOptions {

    /**
     * Determines whether or not the colorscale is picked using the sign of the input z values.
     */
    public Boolean autocolorscale;

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
     * Reference between this trace's geospatial coordinates and a geographic map. If "geo" (the
     * default value), the geospatial coordinates refer to "layout.geo". If "geo2", the geospatial
     * coordinates refer to "layout.geo2", and so on.
     */
    public String geo;

    /**
     * Determines the set of locations used to match entries in "locations" to regions on the map.
     * <p>
     * Default: ISO_3
     */
    public LocationModeEnum locationmode;

    /**
     * The coordinates via location IDs or names. See "locationmode" for more info.
     */
    public String[] locations;
    
    /**
     * Options for marker.
     */
    public final MarkerOptions marker = new MarkerOptions();

    /**
     * Reverses the colorscale.
     */
    public Boolean reversescale;

    /**
    *
    */
    public final SelectedOptions selected = new SelectedOptions();

    /**
     * Determines whether or not a colorbar is displayed for this trace.
     * <p>
     * Default: true
     */
    public Boolean showscale;

    /**
     * Text elements associated with each location.
     */
    public String[] text$array;

    /**
     * Text constant associated with each location.
     */
    public String text$string;

    /**
    *
    */
    public final SelectedOptions unselected = new SelectedOptions();

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
