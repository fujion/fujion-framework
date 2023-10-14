/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2023 Fujion Framework
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
import org.fujion.annotation.Option;

/**
 * Options regarding the chart area and plot area as well as general chart options.
 */
public class ChartOptions extends Options {
    
    /**
     * Options for the zoom reset button.
     */
    public static class ResetZoomButtonOptions extends Options {
        
        public enum RelativeTo {
            plot, chart
        }

        /**
         * The position of the button.
         */
        @Option
        public final PositionOptions position = new PositionOptions();

        /**
         * What frame the button should be placed related to. Defaults to plot.
         */
        @Option
        public RelativeTo relativeTo;
        
        /**
         * The Z index for the reset zoom button. The default value places it below the tooltip that
         * has Z index 7. Defaults to 6.
         */
        @Option("theme.zIndex")
        public Integer theme_zIndex;
    }

    /**
     * When using multiple axis, the ticks of two or more opposite axes will automatically be
     * aligned by adding ticks to the axis or axes with the least ticks. This can be prevented by
     * setting alignTicks to false. If the grid lines look messy, it's a good idea to hide them for
     * the secondary axis by setting gridLineWidth to 0. Defaults to true.
     */
    @Option
    public Boolean alignTicks;

    /**
     * Set the overall animation for all chart updating. Animation can be disabled throughout the
     * chart by setting it to false here. It can be overridden for each individual API method as a
     * function parameter. The only animation not affected by this option is the initial series
     * animation, see plotOptions.series =&gt; animation. The animation can either be set as a
     * boolean or an AnimationOptions object. If true, it will use the 'swing' jQuery easing and a
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
    @Option
    public Object animation;

    /**
     * The background color or gradient for the outer chart area. Defaults to "#FFFFFF".
     */
    @Option
    public String backgroundColor;

    /**
     * The color of the outer chart border. The border is painted using vector graphic techniques to
     * allow rounded corners. Defaults to "#4572A7".
     */
    @Option
    public String borderColor;

    /**
     * The corner radius of the outer chart border. Defaults to 5.
     */
    @Option
    public Integer borderRadius;

    /**
     * The pixel width of the outer chart border. The border is painted using vector graphic
     * techniques to allow rounded corners. Defaults to 0.
     */
    @Option
    public Integer borderWidth;

    /**
     * A CSS class name to apply to the charts container div, allowing unique CSS styling for each
     * chart. Defaults to "".
     */
    @Option
    public String className;

    /**
     * In styled mode, this sets how many colors the class names should rotate between. With ten
     * colors, series (or points) are given class names like highcharts-color-0, highcharts-color-0
     * [...] highcharts-color-9. The equivalent in non-styled mode is to set colors using the colors
     * setting. Defaults to 10.
     */
    @Option
    public Integer colorCount;
    
    /**
     * A text description of the chart. If the Accessibility module is loaded, this is included by
     * default as a long description of the chart and its contents in the hidden screen reader
     * information region. Defaults to undefined.
     */
    @Option
    public String description;

    /**
     * An explicit height for the chart. By default the height is calculated from the offset height
     * of the containing element. Defaults to null.
     */
    @Option
    public String height;

    /**
     * If true, the axes will scale to the remaining visible series once one series is hidden. If
     * false, hiding and showing a series will not affect the axes or the other series. For stacks,
     * once one series within the stack is hidden, the rest of the stack will close in around it
     * even if the axis is not affected. Defaults to true.
     */
    @Option
    public Boolean ignoreHiddenSeries;

    /**
     * Whether to invert the axes so that the x axis is vartical and y axis is horizontal. When
     * true, the x axis is reversed by default. If a bar series is present in the chart, it will be
     * inverted automatically. Defaults to false.
     */
    @Option
    public Boolean inverted;

    /**
     * The margin between the bottom outer edge of the chart and the plot area. Use this to set a
     * fixed pixel value for the margin as opposed to the default dynamic margin. See also
     * spacingBottom. Defaults to 70.
     */
    @Option
    public Integer marginBottom;

    /**
     * The margin between the left outer edge of the chart and the plot area. Use this to set a
     * fixed pixel value for the margin as opposed to the default dynamic margin. See also
     * spacingLeft. Defaults to 80.
     */
    @Option
    public Integer marginLeft;

    /**
     * The margin between the right outer edge of the chart and the plot area. Use this to set a
     * fixed pixel value for the margin as opposed to the default dynamic margin. See also
     * spacingRight. Defaults to 50.
     */
    @Option
    public Integer marginRight;

    /**
     * The margin between the top outer edge of the chart and the plot area. Use this to set a fixed
     * pixel value for the margin as opposed to the default dynamic margin. See also spacingTop.
     * Defaults to null.
     */
    @Option
    public Integer marginTop;

    /**
     * Options to render charts in 3 dimensions. This feature requires highcharts-3d.js.
     */
    @Option
    public final ThreeDOptions options3d = new ThreeDOptions();

    /**
     * Allows setting a key to switch between zooming and panning. Can be one of alt, ctrl, meta
     * (the command key on Mac and Windows key on Windows) or shift. The keys are mapped directly to
     * the key properties of the click event argument (event.altKey, event.ctrlKey, event.metaKey
     * and event.shiftKey). Defaults to undefined.
     */
    @Option
    public String panKey;
    
    /**
     * Allow panning in a chart. Best used with panKey to combine zooming and panning. On touch
     * devices, when the tooltip.followTouchMove option is true (default), panning requires two
     * fingers. To allow panning with one finger, set followTouchMove to false. Defaults to false.
     */
    @Option
    public Boolean panning;

    /**
     * Equivalent to {@link #zoomType}, but for multi-touch gestures only. By default, the pinchType
     * is the same as the zoomType setting. However, pinching can be enabled separately in some
     * cases, for example in stock charts where a mouse drag pans the chart, while pinching is
     * enabled. When tooltip.followTouchMove is true, pinchType only applies to two-finger touches.
     * Defaults to null.
     */
    @Option
    public String pinchType;

    /**
     * The background color or gradient for the plot area. Defaults to null.
     */
    @Option
    public String plotBackgroundColor;

    /**
     * The URL for an image to use as the plot background. To set an image as the background for the
     * entire chart, set a CSS background image to the container element. Defaults to null.
     */
    @Option
    public String plotBackgroundImage;

    /**
     * The color of the inner chart or plot area border. Defaults to "#C0C0C0".
     */
    @Option
    public String plotBorderColor;

    /**
     * The pixel width of the plot area border. Defaults to 0.
     */
    @Option
    public Integer plotBorderWidth;

    /**
     * Whether to apply a drop shadow to the plot area. Requires that plotBackgroundColor be set.
     * Defaults to no shadow.
     *
     * @see ShadowOptions
     */
    @Option
    public ShadowOptions plotShadow;

    /**
     * When true, Cartesian charts like line, spline, area and column are transformed into the polar
     * coordinate system.
     */
    @Option
    public Boolean polar;

    /**
     * Whether to reflow the chart to fit the width of the container div on resizing the window.
     * Under Fujion, the container handles resizing, so this option is forced to false.
     */
    @Option
    protected final Boolean reflow = false;

    /**
     * The button that appears after a selection zoom, allowing the user to reset zoom.
     */
    @Option
    public final ResetZoomButtonOptions resetZoomButton = new ResetZoomButtonOptions();

    /**
     * The background color of the marker square when selecting (zooming in on) an area of the
     * chart. Defaults to rgba(51,92,173,0.25).
     */
    @Option
    public String selectionMarkerFill;

    /**
     * Whether to apply a drop shadow to the outer chart area. Requires that backgroundColor be set.
     * Defaults to no shadow
     *
     * @see ShadowOptions
     */
    @Option
    public ShadowOptions shadow;

    /**
     * Whether to show the axes initially. This only applies to empty charts where series are added
     * dynamically, as axes are automatically added to Cartesian series. Defaults to false.
     */
    @Option
    public Boolean showAxes;

    /**
     * The space between the bottom edge of the chart and the content (plot area, axis title and
     * labels, title, subtitle or legend in top position). Defaults to 15.
     */
    @Option
    public Integer spacingBottom;

    /**
     * The space between the left edge of the chart and the content (plot area, axis title and
     * labels, title, subtitle or legend in top position). Defaults to 10.
     */
    @Option
    public Integer spacingLeft;

    /**
     * The space between the right edge of the chart and the content (plot area, axis title and
     * labels, title, subtitle or legend in top position). Defaults to 10.
     */
    @Option
    public Integer spacingRight;

    /**
     * The space between the top edge of the chart and the content (plot area, axis title and
     * labels, title, subtitle or legend in top position). . Defaults to 10.
     */
    @Option
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
    @Option
    public final StyleOptions style = new StyleOptions();

    /**
     * The series type. Defaults to LINE.
     */
    @Option
    protected PlotType type = PlotType.LINE;

    /**
     * A text description of the chart type. If the Accessibility module is loaded, this will be
     * included in the description of the chart in the screen reader information region. Highcharts
     * will by default attempt to guess the chart type, but for more complex charts it is
     * recommended to specify this property for clarity. Defaults to undefined.
     */
    @Option
    public String typeDescription;

    /**
     * An explicit width for the chart. By default the width is calculated from the offset width of
     * the containing element. Defaults to null.
     */
    @Option
    public Integer width;

    /**
     * Decides in what dimensions the user can zoom by dragging the mouse. Can be one of x, y or xy.
     * Defaults to null.
     */
    @Option
    public ZoomType zoomType;

}
