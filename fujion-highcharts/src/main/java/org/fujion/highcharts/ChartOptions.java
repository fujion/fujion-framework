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

/**
 * Options regarding the chart area and plot area as well as general chart options.
 */
public class ChartOptions extends Options {
    
    /**
     * When using multiple axis, the ticks of two or more opposite axes will automatically be
     * aligned by adding ticks to the axis or axes with the least ticks. This can be prevented by
     * setting alignTicks to false. If the grid lines look messy, it's a good idea to hide them for
     * the secondary axis by setting gridLineWidth to 0. Defaults to true.
     */
    public Boolean alignTicks;

    /**
     * Set the overall animation for all chart updating. Animation can be disabled throughout the
     * chart by setting it to false here. It can be overridden for each individual API method as a
     * function parameter. The only animation not affected by this option is the initial series
     * animation, see plotOptions.series =&gt; animation. The animation can either be set as a
     * boolean or a configuration object. If true, it will use the 'swing' jQuery easing and a
     * duration of 500 ms. If used as a configuration object, the following properties are
     * supported:
     * <ul>
     * <li><b>duration</b> - The duration of the animation in milliseconds.</li>
     * <li><b>easing</b> - When using jQuery as the general framework, the easing can be set to
     * linear or swing. More easing functions are available with the use of jQuery plug-ins, most
     * notably the jQuery UI suite. See the jQuery docs. When using MooTools as the general
     * framework, use the property name transition instead of easing.</li>
     * </ul>
     * Defaults to true.
     */
    public Object animation;

    /**
     * The background color or gradient for the outer chart area. Defaults to "#FFFFFF".
     */
    public String backgroundColor;

    /**
     * The color of the outer chart border. The border is painted using vector graphic techniques to
     * allow rounded corners. Defaults to "#4572A7".
     */
    public String borderColor;

    /**
     * The corner radius of the outer chart border. Defaults to 5.
     */
    public Integer borderRadius;

    /**
     * The pixel width of the outer chart border. The border is painted using vector graphic
     * techniques to allow rounded corners. Defaults to 0.
     */
    public Integer borderWidth;

    /**
     * A CSS class name to apply to the charts container div, allowing unique CSS styling for each
     * chart. Defaults to "".
     */
    public String className;

    /**
     * An explicit height for the chart. By default the height is calculated from the offset height
     * of the containing element. Defaults to null.
     */
    public Integer height;

    /**
     * If true, the axes will scale to the remaining visible series once one series is hidden. If
     * false, hiding and showing a series will not affect the axes or the other series. For stacks,
     * once one series within the stack is hidden, the rest of the stack will close in around it
     * even if the axis is not affected. Defaults to true.
     */
    public Boolean ignoreHiddenSeries;

    /**
     * Whether to invert the axes so that the x axis is vartical and y axis is horizontal. When
     * true, the x axis is reversed by default. If a bar series is present in the chart, it will be
     * inverted automatically. Defaults to false.
     */
    public Boolean inverted;

    /**
     * The margin between the bottom outer edge of the chart and the plot area. Use this to set a
     * fixed pixel value for the margin as opposed to the default dynamic margin. See also
     * spacingBottom. Defaults to 70.
     */
    public Integer marginBottom;

    /**
     * The margin between the left outer edge of the chart and the plot area. Use this to set a
     * fixed pixel value for the margin as opposed to the default dynamic margin. See also
     * spacingLeft. Defaults to 80.
     */
    public Integer marginLeft;

    /**
     * The margin between the right outer edge of the chart and the plot area. Use this to set a
     * fixed pixel value for the margin as opposed to the default dynamic margin. See also
     * spacingRight. Defaults to 50.
     */
    public Integer marginRight;

    /**
     * The margin between the top outer edge of the chart and the plot area. Use this to set a fixed
     * pixel value for the margin as opposed to the default dynamic margin. See also spacingTop.
     * Defaults to null.
     */
    public Integer marginTop;

    /**
     * Options to render charts in 3 dimensions. This feature requires highcharts-3d.js.
     */
    public final ThreeDOptions options3d = new ThreeDOptions();

    /**
     * The background color or gradient for the plot area. Defaults to null.
     */
    public String plotBackgroundColor;

    /**
     * The URL for an image to use as the plot background. To set an image as the background for the
     * entire chart, set a CSS background image to the container element. Defaults to null.
     */
    public String plotBackgroundImage;

    /**
     * The color of the inner chart or plot area border. Defaults to "#C0C0C0".
     */
    public String plotBorderColor;

    /**
     * The pixel width of the plot area border. Defaults to 0.
     */
    public Integer plotBorderWidth;

    /**
     * Whether to apply a drop shadow to the plot area. Requires that plotBackgroundColor be set.
     * The shadow can be a ShadowOptions object. Defaults to false.
     *
     * @see ShadowOptions
     */
    public Object plotShadow;

    /**
     * When true, Cartesian charts like line, spline, area and column are transformed into the polar
     * coordinate system.
     */
    public Boolean polar;

    /**
     * Whether to reflow the chart to fit the width of the container div on resizing the window.
     * Under Fujion, the container handles resizing, so this option is forced to false.
     */
    protected final Boolean reflow = false;

    /**
     * Id of the parent element that will host the chart. This will be set automatically.
     */
    protected String renderTo;

    /**
     * The button that appears after a selection zoom, allowing the user to reset zoom.
     */
    public Object resetZoomButton;

    /**
     * The background color of the marker square when selecting (zooming in on) an area of the
     * chart. Defaults to rgba(69,114,167,0.25). Defaults to rgba(69114,167,0.25).
     */
    public String selectionMarkerFill;

    /**
     * Whether to apply a drop shadow to the outer chart area. Requires that backgroundColor be set.
     * The shadow can be a ShadowOptions object. Defaults to false.
     *
     * @see ShadowOptions
     */
    public Boolean shadow;

    /**
     * Whether to show the axes initially. This only applies to empty charts where series are added
     * dynamically, as axes are automatically added to Cartesian series. Defaults to false.
     */
    public Boolean showAxes;

    /**
     * The space between the bottom edge of the chart and the content (plot area, axis title and
     * labels, title, subtitle or legend in top position). Defaults to 15.
     */
    public Integer spacingBottom;

    /**
     * The space between the left edge of the chart and the content (plot area, axis title and
     * labels, title, subtitle or legend in top position). Defaults to 10.
     */
    public Integer spacingLeft;

    /**
     * The space between the right edge of the chart and the content (plot area, axis title and
     * labels, title, subtitle or legend in top position). Defaults to 10.
     */
    public Integer spacingRight;

    /**
     * The space between the top edge of the chart and the content (plot area, axis title and
     * labels, title, subtitle or legend in top position). . Defaults to 10.
     */
    public Integer spacingTop;

    /**
     * Additional CSS styles to apply inline to the container div. Note that since the default font
     * styles are applied in the renderer, it is ignorant of the individual chart options and must
     * be set globally. Defaults to:
     *
     * <pre>
     *     fontFamily: '"Lucida Grande", "Lucida Sans Unicode", Verdana, Arial, Helvetica, sans-serif'
     *     fontSize: '12px'
     * </pre>
     */
    public final StyleOptions style = new StyleOptions();

    /**
     * The series type. Defaults to LINE.
     */
    protected PlotType type = PlotType.LINE;

    /**
     * An explicit width for the chart. By default the width is calculated from the offset width of
     * the containing element. Defaults to null.
     */
    public Integer width;

    /**
     * Decides in what dimensions the user can zoom by dragging the mouse. Can be one of x, y or xy.
     * Defaults to null.
     */
    public ZoomType zoomType;

}
