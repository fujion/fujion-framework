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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;

/**
 * Layout options for longitude/latitude axes.
 */
public class LonLatAxisOptions extends Options {

    /**
     * The graticule's longitude/latitude tick step.
     */
    public Double dtick;

    /**
     * The graticule's stroke color.
     * <p>
     * Default: "#eee"
     */
    public String gridcolor;

    /**
     * The graticule's stroke width (in px).
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 1
     */
    public Integer gridwidth;

    /**
     * The range of this axis (in degrees), sets the map's clipped coordinates.
     */
    public int[] range;

    /**
     * Determines whether or not graticule are shown on the map.
     */
    public Boolean showgrid;

    /**
     * The graticule's starting tick longitude/latitude.
     */
    public Double tick0;
}
