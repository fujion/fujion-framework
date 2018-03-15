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

import org.fujion.annotation.Option;
import org.fujion.plotly.common.PolarDirectionEnum;
import org.fujion.plotly.common.PolarUnitsEnum;

/**
 * Layout options for polar angular axis.
 */
public class AngularAxisOptions extends AxisOptions {
    
    /**
     * The direction corresponding to positive angles.
     */
    @Option
    public PolarDirectionEnum direction;

    /**
     * The hover text formatting rule using d3 formatting mini-languages which are very similar to
     * those in Python. We add one item to d3's date formatter: "%{n}f" for fractional seconds with
     * n digits. For example, "2016-10-13 09:15:23.456" with tickformat "%H~%M~%S.%2f" would display
     * "09~15~23.46".
     */
    @Option
    public String hoverformat;

    /**
     * The layer on which this axis is displayed.
     * <p>
     * Default: ABOVE
     */
    @Option
    public LayerEnum layer;

    /**
     * The angular period. Has an effect only when "angularaxis.type" is CATEGORY.
     */
    @Option
    public Double period;

    /**
     * The start position (in degrees) of the angular axis By default, polar subplots with
     * "direction" set to "counterclockwise" get a "rotation" of "0" which corresponds to due East
     * (like what mathematicians prefer). In turn, polar with "direction" set to "clockwise" get a
     * rotation of "90" which corresponds to due North (like on a compass),
     */
    @Option
    public Integer rotation;

    /**
     * The format unit of the formatted "theta" values. Has an effect only when "angularaxis.type"
     * is LINEAR.
     * <p>
     * Default: DEGREES
     */
    @Option
    public PolarUnitsEnum thetaunit;

    /**
     * The tick color.
     * <p>
     * Default: "#444"
     */
    @Option
    public String tickcolor;

    /**
     * The tick length (in px).
     * <p>
     * Default: 5
     */
    @Option
    public Integer ticklen;

    /**
     * Determines whether and where ticks are drawn.
     */
    @Option
    public TicksEnum ticks;

    /**
     * The tick width (in px).
     * <p>
     * Default: 1
     */
    @Option
    public Integer tickwidth;
    
    /**
     * A single toggle to hide the axis while preserving interaction like dragging.
     * <p>
     * Default: true when a cheater plot is present on the axis, otherwise false.
     */
    @Option
    public Boolean visible;
}
