/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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

import org.fujion.ancillary.OptionMap;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all plot types.
 * <p>
 * PlotOptions is a wrapper for config objects for each series type. The config objects for each
 * series can also be overridden for each series item as given in the series array. Configuration
 * options for the series are given in three levels. Options for all series in a chart are given in
 * the plotOptions.series object. Then options for all series of a specific type are given in the
 * plotOptions of that type, for example plotOptions.line. Next, options for one single series are
 * given in the series array.
 */
public abstract class PlotOptions extends Options {
    
    /**
     * The text identifier of the plot type.
     */
    @Option(ignore = true)
    protected String type;
    
    /**
     * Allow this series' points to be selected by clicking on the markers, bars or pie slices.
     * Defaults to false.
     */
    @Option
    public Boolean allowPointSelect;
    
    /**
     * Enable or disable the initial animation when a series is displayed. Since version 2.1, the
     * animation can be set as a configuration object. Please note that this option only applies to
     * the initial animation of the series itself. For other animations, see #chart.animation and
     * the animation parameter under the API methods.
     */
    @Option
    public final AnimationOptions animation = new AnimationOptions();
    
    /**
     * For some series, there is a limit that shuts down initial animation by default when the total
     * number of points in the chart is too high. For example, for a column chart and its
     * derivatives, animation doesn't run if there is more than 250 points totally. To disable this
     * cap, set animationLimit to Infinity.
     */
    @Option
    public Integer animationLimit;
    
    /**
     * Set the point threshold for when a series should enter boost mode. Setting it to e.g. 2000
     * will cause the series to enter boost mode when there are 2000 or more points in the series.
     * To disable boosting on the series, set the boostThreshold to 0. Setting it to 1 will force
     * boosting. Requires modules/boost.js.
     */
    @Option
    public Integer boostThreshold;

    /**
     * A CSS class name to apply to the series' graphical elements.
     */
    @Option
    public String className;

    /**
     * The main color or the series. In line type series it applies to the line and the point
     * markers unless otherwise specified. In bar type series it applies to the bars unless a color
     * is specified per point. The default value is pulled from the options.colors array.
     */
    @Option
    public String color;
    
    /**
     * Styled mode only. A specific color index to use for the series, so its graphic
     * representations are given the class name highcharts-color-{n}. Defaults to undefined.
     */
    @Option
    public Integer colorIndex;
    
    /**
     * When using automatic point colors pulled from the options.colors collection, this option
     * determines whether the chart should receive one color per series or one color per point.
     * Defaults to false.
     */
    @Option
    public Boolean colorByPoint;
    
    /**
     * A series specific or series type specific color set to apply instead of the global colors
     * when colorByPoint is true.
     */
    @Option
    public List<String> colors = new ArrayList<>();
    
    /**
     * Polar charts only. Whether to connect the ends of a line series plot across the extremes.
     * Defaults to true.
     */
    @Option
    public Boolean connectEnds;
    
    /**
     * Whether to connect a graph line across null points. Defaults to false.
     */
    @Option
    public Boolean connectNulls;
    
    /**
     * When the series contains less points than the crop threshold, all points are drawn, event if
     * the points fall outside the visible plot area at the current zoom. The advantage of drawing
     * all points (including markers and columns), is that animation is performed on updates. On the
     * other hand, when the series contains more points than the crop threshold, the series data is
     * cropped to only contain points that fall within the plot area. The advantage of cropping away
     * invisible points is to increase performance on large series. . Defaults to 300.
     */
    @Option
    public Double cropThreshold;
    
    /**
     * You can set the cursor to "pointer" if you have click events attached to the series, to
     * signal to the user that the points and lines can be clicked. Defaults to ''.
     */
    @Option
    public String cursor;
    
    /**
     * A name for the dash style to use for the graph. Applies only to series type having a graph,
     * like line, spline, area and scatter in case it has a lineWidth. The value for the dashStyle
     * include:
     * <ul>
     * <li>Solid</li>
     * <li>ShortDash</li>
     * <li>ShortDot</li>
     * <li>ShortDashDot</li>
     * <li>ShortDashDotDot</li>
     * <li>Dot</li>
     * <li>Dash</li>
     * <li>LongDash</li>
     * <li>DashDot</li>
     * <li>LongDashDot</li>
     * <li>LongDashDotDot</li>
     * </ul>
     * Defaults to null.
     */
    @Option
    public DashStyle dashStyle;
    
    /**
     * Options for data labels.
     *
     * @see DataLabelOptions
     */
    @Option
    public final DataLabelOptions dataLabels = new DataLabelOptions();
    
    /**
     * Requires the Accessibility module. A description of the series to add to the screen reader
     * information about the series. Defaults to undefined.
     */
    @Option
    public String description;

    /**
     * Enable or disable the mouse tracking for a specific series. This includes point tooltips and
     * click events on graphs and points. For large datasets it improves performance. Defaults to
     * true.
     */
    @Option
    public Boolean enableMouseTracking;
    
    /**
     * Requires the Accessibility module. By default, series are exposed to screen readers as
     * regions. By enabling this option, the series element itself will be exposed in the same way
     * as the data points. This is useful if the series is not used as a grouping entity in the
     * chart, but you still want to attach a description to the series. Defaults to undefined.
     */
    @Option
    public Boolean exposeElementToA11y;
    
    /**
     * Determines whether the series should look for the nearest point in both dimensions or just
     * the x-dimension when hovering the series. Defaults to 'xy' for scatter series and 'x' for
     * most other series. If the data has duplicate x-values, it is recommended to set this to 'xy'
     * to allow hovering over all points. Applies only to series types using nearest neighbor search
     * (not direct hover) for tooltip. Defaults to x.
     */
    @Option
    public String findNearestPointBy;
    
    /**
     * Whether to use the Y extremes of the total chart width or only the zoomed area when zooming
     * in on parts of the X axis. By default, the Y axis adjusts to the min and max of the visible
     * data. Cartesian series only. Defaults to false.
     */
    @Option
    public Boolean getExtremesFromAll;

    /**
     * An id for the series. Defaults to null.
     */
    @Option
    public String id;
    
    /**
     * An array specifying which option maps to which key in the data point array. This makes it
     * convenient to work with unstructured data arrays from different sources. Defaults to
     * undefined.
     */
    @Option
    public final List<String> keys = new ArrayList<>();

    /**
     * Text labels for the plot bands.
     */
    @Option
    public final PlotLabelOptions label = new PlotLabelOptions();
    
    /**
     * The line cap used for line ends and line joins on the graph. Defaults to round.
     */
    @Option
    public String linecap;

    /**
     * Pixel with of the graph line. Defaults to 2.
     */
    @Option
    public Integer lineWidth;
    
    /**
     * The id of another series to link to. Additionally, the value can be ":previous" to link to
     * the previous series. When two series are linked, only the first one appears in the legend.
     * Toggling the visibility of this also toggles the linked series.
     */
    @Option
    public String linkedTo;
    
    /**
     * Options for point markers.
     *
     * @see MarkerOptions
     */
    @Option
    public final MarkerOptions marker = new MarkerOptions();
    
    /**
     * The color for the parts of the graph or points that are below the threshold. Defaults to
     * null.
     */
    @Option
    public String negativeColor;
    
    /**
     * If no x values are given for the points in a series, pointInterval defines the interval of
     * the x values. For example, if a series contains one value every decade starting from year 0,
     * set pointInterval to 10. Defaults to 1.
     */
    @Option
    public Double pointInterval;
    
    /**
     * On datetime series, this allows for setting the pointInterval to irregular time units, day,
     * month and year. A day is usually the same as 24 hours, but pointIntervalUnit also takes the
     * DST crossover into consideration when dealing with local time. Combine this option with
     * pointInterval to draw weeks, quarters, 6 months, 10 years etc. Please note that this options
     * applies to the series data, not the interval of the axis ticks, which is independent.
     * Defaults to undefined.
     */
    @Option
    public String pointIntervalUnit;
    
    /**
     * Possible values: null, "on", "between". In a column chart, when pointPlacement is "on", the
     * point will not create any padding of the X axis. In a polar column chart this means that the
     * first column points directly north. If the pointPlacement is "between", the columns will be
     * laid out between ticks. This is useful for example for visualising an amount between two
     * points in time or in a certain sector of a polar chart. Defaults to null in cartesian charts,
     * "between" in polar charts.
     */
    @Option
    public String pointPlacement;
    
    /**
     * If no x values are given for the points in a series, pointStart defines on what value to
     * start. For example, if a series contains one yearly value starting from 1945, set pointStart
     * to 1945. Defaults to 0.
     */
    @Option
    public Double pointStart;
    
    /**
     * Whether to select the series initially. If showCheckbox is true, the checkbox next to the
     * series name will be checked for a selected series. Defaults to false.
     */
    @Option
    public Boolean selected;
    
    /**
     * Boolean value whether to apply a drop shadow to the graph line. Optionally can be a
     * ShadowOptions object. Defaults to true.
     *
     * @see ShadowOptions
     */
    @Option
    public Object shadow;
    
    /**
     * If true, a checkbox is displayed next to the legend item to allow selecting the series. The
     * state of the checkbox is determined by the selected option. Defaults to false.
     */
    @Option
    public Boolean showCheckbox;
    
    /**
     * Whether to display this particular series or series type in the legend. Defaults to true.
     */
    @Option
    public Boolean showInLegend;
    
    /**
     * If set to True, the accessibility module will skip past the points in this series for
     * keyboard navigation. Defaults to undefined.
     */
    @Option
    public Boolean skipKeyboardNavigation;

    /**
     * When this is true, the series will not cause the Y axis to cross the zero plane (or threshold
     * option) unless the data actually crosses the plane. For example, if softThreshold is false, a
     * series of 0, 1, 2, 3 will make the Y axis show negative values according to the minPadding
     * option. If softThreshold is true, the Y axis starts at 0. Defaults to true.
     */
    @Option
    public Boolean softThreshold;
    
    /**
     * Whether to stack the values of each series on top of each other. Possible values are null to
     * disable, "normal" to stack by value or "percent". Defaults to null.
     */
    @Option
    public String stacking;
    
    /**
     * Whether to apply steps to the line. Possible values are left, center and right. Defaults to
     * undefined.
     */
    @Option
    public AlignHorizontal step;

    /**
     * Sticky tracking of mouse events. When true, the mouseOut event on a series isn't triggered
     * until the mouse moves over another series, or out of the plot area. When false, the mouseOut
     * event on a series is triggered when the mouse leaves the area around the series' graph or
     * markers. This also implies the tooltip. When stickyTracking is false and tooltip.shared is
     * false, the tooltip will be hidden when moving the mouse between series. Defaults to true.
     */
    @Option
    public Boolean stickyTracking;
    
    /**
     * The threshold, also called zero level or base level. For line type series this is only used
     * in conjunction with negativeColor. Defaults to 0.
     */
    @Option
    public Double threshold;
    
    /**
     * A configuration object for the tooltip rendering of each single series.
     */
    @Option
    public final TooltipOptions tooltip = new TooltipOptions();
    
    /**
     * When a series contains a data array that is longer than this, only one dimensional arrays of
     * numbers, or two dimensional arrays with x and y values are allowed. Also, only the first
     * point is tested, and the rest are assumed to be the same format. This saves expensive data
     * checking and indexing in long series. Defaults to 1000.
     */
    @Option
    public Integer turboThreshold;
    
    /**
     * Set the initial visibility of the series. Defaults to true.
     */
    @Option
    public Boolean visible;
    
    /**
     * Defines the axis on which the zones are applied. Defaults to y.
     */
    @Option
    public String zoneAxis;
    
    /**
     * An array defining zones within a series. Zones can be applied to the X axis, Y axis or Z axis
     * for bubbles, according to the zoneAxis option. In styled mode, the color zones are styled
     * with the .highcharts-zone-{n} class, or custom classed from the className option.
     */
    @Option
    public final List<Zone> zones = new ArrayList<>();
    
    /**
     * If type is not null, place options under a submap indexed by the type id.
     */
    @Override
    public OptionMap toMap() {
        OptionMap map = super.toMap();
        
        if (type != null) {
            OptionMap newMap = new OptionMap();
            newMap.put(type, map);
            map = newMap;
        }
        
        return map;
    }
    
}
