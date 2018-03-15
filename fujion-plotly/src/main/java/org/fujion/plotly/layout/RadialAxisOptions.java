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
import org.fujion.plotly.common.CalendarTypeEnum;
import org.fujion.plotly.common.PolarDirectionEnum;

/**
 * Layout options for polar angular axis.
 */
public class RadialAxisOptions extends AxisOptions {

    /**
     * The angle (in degrees) from which the radial axis is drawn. Note that by default, radial axis
     * line on the theta=0 line corresponds to a line pointing right (like what mathematicians
     * prefer).
     * <p>
     * Default: the first "polar.sector" angle.
     */
    @Option
    public Integer angle;

    /**
     * Determines on which side of radial axis line the tick and tick labels appear.
     * <p>
     * Default: CLOCKWISE
     */
    @Option
    public PolarDirectionEnum side;

    /**
     * The hover text formatting rule using d3 formatting mini-languages which are very similar to
     * those in Python. For numbers, see:
     * https://github.com/d3/d3-format/blob/master/README.md#locale_format And for dates see:
     * https://github.com/d3/d3-time-format/blob/master/README.md#locale_format We add one item to
     * d3's date formatter: "%{n}f" for fractional seconds with n digits. For example, "2016-10-13
     * 09:15:23.456" with tickformat "%H~%M~%S.%2f" would display "09~15~23.46"
     */
    @Option
    public String hoverformat;
    
    /**
     * Determines whether and where ticks are drawn.
     */
    @Option
    public TicksEnum ticks;
    
    /**
     * The tick length (in px).
     * <p>
     * Constraints: &ge;0
     * <p>
     * Length: 5
     */
    @Option
    public Integer ticklen;

    /**
     * The tick width (in px).
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 1
     */
    @Option
    public Integer tickwidth;

    /**
     * The tick color;
     * <p>
     * Default: "#444"
     */
    @Option
    public String tickcolor;

    /**
     * The layer on which this axis is displayed. Useful when used together with scatter-like traces
     * with "cliponaxis" set to "false" to show markers and/or text nodes above this axis.
     */
    @Option
    public LayerEnum layer;

    /**
     * The calendar system to use for "range" and "tick0" if this is a date axis. This does not set
     * the calendar for interpreting data on this axis, that's specified in the trace or via the
     * global "layout.calendar"
     */
    @Option
    public CalendarTypeEnum calendar;

    /**
     *
     */
    @Option
    public Boolean visible;
}
