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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Layout options for map boxes.
 */
public class MapboxOptions extends Options {
    
    /**
     * The mapbox access token to be used for this mapbox map. Alternatively, the mapbox access
     * token can be set in the configuration options under "mapboxAccessToken".
     */
    @Option
    public String accesstoken;

    /**
     * The bearing angle of the map (in degrees counter-clockwise from North).
     * <p>
     * Default: 0
     */
    @Option
    public Integer bearing;

    /**
     * The latitude of the center of the map (in degrees North).
     */
    @Option("center.lat")
    public Double center_lat;

    /**
     * The longitude of the center of the map (in degrees East).
     */
    @Option("center.lon")
    public Double center_lon;

    /**
     * The horizontal domain of this mapbox subplot (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    @Option("domain.x")
    public int[] domain_x;
    
    /**
     * The vertical domain of this mapbox subplot (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    @Option("domain.y")
    public int[] domain_y;

    /**
     * Options for layers.
     */
    @Option
    public final LayerOptions layers = new LayerOptions();

    /**
     * The pitch angle of the map (in degrees, where 0 means perpendicular to the surface of the
     * map).
     * <p>
     * Default: 0
     */
    @Option
    public Integer pitch;

    /**
     * The Mapbox map style as a number. Either input one of the default Mapbox style names or the
     * URL to a custom style or a valid Mapbox style JSON.
     */
    @Option
    public String style;

    /**
     * The zoom level of the map.
     * <p>
     * Default: 1
     */
    @Option
    public Double zoom;
}
