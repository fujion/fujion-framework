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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;

/**
 * Layout options for geo.
 */
public class GeoOptions extends Options {

    /**
     * The resolution of the base layers.
     */
    public enum ResolutionEnum {
        HIGH(50), LOW(110);

        private final int value;

        ResolutionEnum(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return Integer.toString(value);
        }
    }

    /**
     * The scope of the map.
     */
    public enum ScopeEnum {
        AFRICA, ASIA, EUROPE, NORTH_AMERICA, SOUTH_AMERICA, USA, WORLD;

        @Override
        public String toString() {
            return name().toLowerCase().replace("_", " ");
        }

    }

    /**
     * The background color of the map.
     * <p>
     * Default: "#444"
     */
    public String bgcolor;

    /**
     * The latitude of the map's center. For all projection types, the map's latitude center lies at
     * the middle of the latitude range by default.
     */
    public Double center_lat;
    
    /**
     * The longitude of the map's center. By default, the map's longitude center lies at the middle
     * of the longitude range for scoped projection and above "projection.rotation.lon" otherwise.
     */
    public Double center_lon;

    /**
     * The coastline color.
     * <p>
     * Default: "#444"
     */
    public String coastlinecolor;
    
    /**
     * The coastline stroke width (in px).
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 1
     */
    public Integer coastlinewidth;
    
    /**
     * The country boundary line color.
     * <p>
     * Default: "#444"
     */
    public String countrycolor;

    /**
     * The stroke width (in px) of country boundaries.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 1
     */
    public Integer countrywidth;
    
    /**
     * The horizontal domain of the geo subplot (in plot fraction). Note that geo subplots are
     * constrained by domain. In general, when "projection.scale" is set to 1, a map will fit either
     * its x or y domain, but not both.
     * <p>
     * Default: [0,1]
     */
    public int[] domain_x;
    
    /**
     * The vertical domain of the geo subplot (in plot fraction). Note that geo subplots are
     * constrained by domain. In general, when "projection.scale" is set to 1, a map will fit either
     * its x or y domain, but not both.
     * <p>
     * Default: [0,1]
     */
    public int[] domain_y;
    
    /**
     * The frame color.
     * <p>
     * Default: "#444"
     */
    public String framecolor;
    
    /**
     * The stroke width (in px) of the frame.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 1
     */
    public Integer framewidth;
    
    /**
     * The lake color.
     * <p>
     * Default: "#3399FF"
     */
    public String lakecolor;
    
    /**
     * The land mass color.
     * <p>
     * Default: "#F0DC82"
     */
    public String landcolor;
    
    /**
     * Options for latitude axis.
     */
    public final LonLatAxisOptions lataxis = new LonLatAxisOptions();
    
    /**
     * Options for longitude axis.
     */
    public final LonLatAxisOptions lonaxis = new LonLatAxisOptions();
    
    /**
     * The ocean color.
     * <p>
     * Default: "#3399FF"
     */
    public String oceancolor;
    
    /**
     * Projection options.
     */
    public final ProjectionOptions projection = new ProjectionOptions();
    
    /**
     * The resolution of the base layers. LOW corresponds to 110 km/mm, HIGH to 50 km/mm.
     */
    public ResolutionEnum resolution;
    
    /**
     * The river color.
     * <p>
     * Default: "#3399FF"
     */
    public String rivercolor;
    
    /**
     * The stroke width (in px) of the rivers.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 1
     */
    public Integer riverwidth;
    
    /**
     * The scope of the map.
     * <p>
     * Default: WORLD
     */
    public ScopeEnum scope;
    
    /**
     * Determines whether or not the coastlines are drawn.
     */
    public Boolean showcoastlines;
    
    /**
     * Determines whether or not country boundaries are drawn.
     */
    public Boolean showcountries;
    
    /**
     * Determines whether or not a frame is drawn around the map.
     */
    public Boolean showframe;

    /**
     * Determines whether or not lakes are filled in color.
     */
    public Boolean showlakes;

    /**
     * Determines whether or not land masses are filled in color.
     */
    public Boolean showland;

    /**
     * Determines whether or not oceans are filled in color.
     */
    public Boolean showocean;

    /**
     * Determines whether or not rivers are filled in color.
     */
    public Boolean showrivers;

    /**
     * Determines whether or not boundaries of subunits within countries (e.g. states, provinces)
     * are drawn.
     */
    public Boolean showsubunits;
    
    /**
     * The color of the subunits boundaries.
     * <p>
     * Default: "#444"
     */
    public String subunitcolor;
    
    /**
     * The stroke width (in px) of the subunits boundaries.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 1
     */
    public Integer subunitwidth;

}
