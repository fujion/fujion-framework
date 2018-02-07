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
package org.fujion.highcharts;

import org.fujion.ancillary.Options;
import org.fujion.annotation.JavaScript;

/**
 * Options for tooltip rendering.
 */
public class TooltipOptions extends Options {
    
    /**
     * For series on a datetime axes, the date format in the tooltip's header will by default be
     * guessed based on the closest data points. This member gives the default string
     * representations used for each unit. For an overview of the replacement codes, see dateFormat.
     * Defaults to:
     * <ul>
     * <li>millisecond:"%A, %b %e, %H:%M:%S.%L"</li>
     * <li>second:"%A, %b %e, %H:%M:%S"</li>
     * <li>minute:"%A, %b %e, %H:%M"</li>
     * <li>hour:"%A, %b %e, %H:%M"</li>
     * <li>day:"%A, %b %e, %Y"</li>
     * <li>week:"Week from %A, %b %e, %Y"</li>
     * <li>month:"%B %Y"</li>
     * <li>year:"%Y"</li>
     * </ul>
     */
    public final DateTimeFormatOptions dateTimeLabelFormats = new DateTimeFormatOptions();
    
    /**
     * Whether the tooltip should follow the mouse as it moves across columns, pie slices and other
     * point types with an extent. By default it behaves this way for scatter, bubble and pie series
     * by override in the plotOptions for those series types. For touch moves to behave the same
     * way, {@link #followTouchMove} must be true also. Defaults to undefined.
     */
    public Boolean followPointer;
    
    /**
     * Whether the tooltip should follow the finger as it moves on a touch device. If this is true
     * and chart.panning is set,followTouchMove will take over one-finger touches, so the user needs
     * to use two fingers for zooming and panning. Defaults to undefined.
     */
    public Boolean followTouchMove;
    
    /**
     * A string to append to the tooltip format. Defaults to "".
     */
    public String footerFormat;

    /**
     * The HTML of the tooltip header line. Variables are enclosed by curly brackets. Available
     * variables are point.key, series.name, series.color and other members from the point and
     * series objects. The point.key variable contains the category name, x value or datetime string
     * depending on the type of axis. For datetime axes, the point.key date format can be set using
     * tooltip.xDateFormat. Defaults to
     * <code>&lt;span style="font-size: 10px"&gt;{point.key}&lt;/span&gt;&lt;br/&gt;</code>.
     */
    public String headerFormat;
    
    /**
     * The number of milliseconds to wait until the tooltip is hidden when mouse out from a point or
     * chart. Defaults to 500.
     */
    public Integer hideDelay;

    /**
     * Padding inside the tooltip, in pixels. Defaults to 8.
     */
    public Integer padding;
    
    /**
     * The HTML of the point's line in the tooltip. Variables are enclosed by curly brackets.
     * Available variables are point.x, point.y, series. name and series.color and other properties
     * on the same form. Furthermore, point.y can be extended by the tooltip.valuePrefix and
     * tooltip.valueSuffix variables. This can also be overridden for each series, which makes it a
     * good hook for displaying units. In styled mode, the dot is colored by a class name rather
     * than the point color. Defaults to
     * <code>&lt;span style="color:{point.color}"&gt;\u25CF&lt;/span&gt; {series.name}: &lt;b&gt;{point.y}&lt;/b&gt;&lt;br/&gt;</code>.
     */
    public String pointFormat;
    
    /**
     * A callback function for formatting the HTML output for a single point in the tooltip. Like
     * the pointFormat string, but with more flexibility. Defaults to undefined.
     */
    @JavaScript
    public String pointerFormatter;

    /**
     * Split the tooltip into one label per series, with the header close to the axis. This is
     * recommended over shared tooltips for charts with multiple line series, generally making them
     * easier to read. This option takes precedence over tooltip.shared. Defaults to undefined.
     */
    public Boolean split;
    
    /**
     * How many decimals to show in each series' y value. This may be overridden in each series'
     * tooltip options object. The default is to preserve all decimals. Defaults to undefined.
     */
    public Integer valueDecimals;

    /**
     * A string to prepend to each series' y value. Overridable in each series' tooltip options
     * object. Defaults to undefined.
     */
    public String valuePrefix;
    
    /**
     * A string to append to each series' y value. Overridable in each series' tooltip options
     * object. Defaults to undefined.
     */
    public String valueSuffix;

    /**
     * The format for the date in the tooltip header if the X axis is a datetime axis. The default
     * is a best guess based on the smallest distance between points in the chart. Defaults to
     * undefined.
     */
    public String xDateFormat;
}
