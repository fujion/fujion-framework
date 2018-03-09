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
 * Layout options for polar plots.
 */
public class PolarOptions extends Options {

    /**
     * The horizontal domain of the polar subplot (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    public int[] domain_x;
    
    /**
     * The vertical domain of the polar subplot (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    public int[] domain_y;
    
    /**
     * angular span of this polar subplot with two angles (in degrees). Sector are assumed to be
     * spanned in the counterclockwise direction with "0" corresponding to rightmost limit of the
     * polar subplot.
     * <p>
     * Default: [0,360]
     */
    public int[] sector;

    /**
     * The background color of the subplot.
     * <p>
     * Default: "#fff"
     */
    public String bgcolor;

    /**
     * Options for polar angular axis.
     */
    public final AngularAxisOptions angularaxis = new AngularAxisOptions();
    
    /**
     * Options for polar radial axis.
     */
    public final RadialAxisOptions radialaxis = new RadialAxisOptions();
    
}
