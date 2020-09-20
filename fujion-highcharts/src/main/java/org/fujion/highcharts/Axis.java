/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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

import java.util.ArrayList;
import java.util.List;

/**
 * A chart axis.
 */
public class Axis extends Options {

    protected Axis(List<Axis> axes) {
        super();
        this.index = axes.size();
        axes.add(this);
    }

    /**
     * Whether to allow decimals in this axis' ticks. When counting integers, like persons or hits
     * on a web page, decimals must be avoided in the axis tick labels. Defaults to true.
     */
    @Option
    public Boolean allowDecimals;

    /**
     * When using an alternate grid color, a band is painted across the plot area between every
     * other grid line. Defaults to null.
     */
    @Option
    public String alternateGridColor;

    /**
     * If categories are present for the x-axis, names are used instead of numbers for that axis.
     * Example: categories: ['Apples', 'Bananas', 'Oranges'] Defaults to none.
     */
    @Option
    public final List<String> categories = new ArrayList<>();

    /**
     * The highest allowed value for automatically computed axis extremes.
     */
    @Option
    public Double ceiling;

    /**
     * For a date/time axis, the scale will automatically adjust to the appropriate unit. This
     * member gives the default string representations used for each unit.
     *
     * @see DateTimeFormatOptions
     */
    @Option
    public final DateTimeFormatOptions dateTimeLabelFormats = new DateTimeFormatOptions();

    /**
     * Whether to force the axis to end on a tick. Use this option with the maxPadding option to
     * control the axis end. Defaults to false.
     */
    @Option
    public Boolean endOnTick;

    /**
     * The lowest allowed value for automatically computed axis extremes. Defaults to null.
     */
    @Option
    public Double floor;

    /**
     * Color of the grid lines extending the ticks across the plot area. Defaults to "#C0C0C0".
     */
    @Option
    public String gridLineColor;

    /**
     * The dash or dot style of the grid lines. Defaults to Solid.
     */
    @Option
    public DashStyle gridLineDashStyle;

    /**
     * The width of the grid lines extending the ticks across the plot area. Defaults to 0.
     */
    @Option
    public Integer gridLineWidth;

    /**
     * An id for the axis. Defaults to null.
     */
    @Option
    public String id;

    /**
     * The axis labels show the number or category for each tick.
     */
    @Option
    public AxisLabelOptions labels;

    /**
     * The color of the line marking the axis itself. Defaults to "#C0D0E0".
     */
    @Option
    public String lineColor;

    /**
     * The width of the line marking the axis itself. Defaults to 1.
     */
    @Option
    public Integer lineWidth;

    /**
     * Index of another axis that this axis is linked to. When an axis is linked to a master axis,
     * it will take the same extremes as the master, but as assigned by min or max or by
     * setExtremes. It can be used to show additional info, or to ease reading the chart by
     * duplicating the scales. Defaults to null.
     */
    @Option
    public Integer linkedTo;

    /**
     * The maximum value of the axis. If null, the max value is automatically calculated. If the
     * endOnTick option is true, the max value might be rounded up. The actual maximum value is also
     * influenced by chart.alignTicks. Defaults to null.
     */
    @Option
    public Double max;

    /**
     * Padding of the max value relative to the length of the axis. A padding of 0.05 will make a
     * 100px axis 5px longer. This is useful when you don't want the highest data value to appear on
     * the edge of the plot area. When the axis' max option is set or a max extreme is set using
     * axis.setExtremes(), the maxPadding will be ignored. Defaults to 0.01.
     */
    @Option
    public Double maxPadding;

    /**
     * The minimum value of the axis. If null the min value is automatically calculated. If the
     * startOnTick option is true, the min value might be rounded down. Defaults to null.
     */
    @Option
    public Double min;

    /**
     * Padding of the min value relative to the length of the axis. A padding of 0.05 will make a
     * 100px axis 5px longer. This is useful when you don't want the lowest data value to appear on
     * the edge of the plot area. When the axis' min option is set or a min extreme is set using
     * axis.setExtremes(), the minPadding will be ignored. Defaults to 0.01.
     */
    @Option
    public Double minPadding;

    /**
     * The minimum range to display on this axis. The entire axis will not be allowed to span over a
     * smaller interval than this. For example, for a datetime axis the main unit is milliseconds.
     * If minRange is set to 3600000, you can't zoom in more than to one hour. The default minRange
     * for the x axis is five times the smallest interval between any of the data points. On a
     * logarithmic axis, the unit for the minimum range is the power. So a minRange of 1 means that
     * the axis can be zoomed to 10-100, 100-1000, 1000-10000 etc.
     */
    @Option
    public Double minRange;

    /**
     * The minimum tick interval allowed in axis values. For example on zooming in on an axis with
     * daily data, this can be used to prevent the axis from showing hours. Defaults to undefined.
     */
    @Option
    public Double minTickInterval;

    /**
     * Color of the minor, secondary grid lines. Defaults to #E0E0E0.
     */
    @Option
    public String minorGridLineColor;

    /**
     * The dash or dot style of the minor grid lines. Defaults to Solid.
     */
    @Option
    public DashStyle minorGridLineDashStyle;

    /**
     * Width of the minor, secondary grid lines. Defaults to 1.
     */
    @Option
    public Integer minorGridLineWidth;

    /**
     * Color for the minor tick marks. Defaults to #A0A0A0.
     */
    @Option
    public String minorTickColor;

    /**
     * Tick interval in scale units for the minor ticks. On a linear axis, if "auto", the minor tick
     * interval is calculated as a fifth of the tickInterval. If null, minor ticks are not shown. On
     * logarithmic axes, the unit is the power of the value. For example, setting the
     * minorTickInterval to 1 puts one tick on each of 0.1, 1, 10, 100 etc. Setting the
     * minorTickInterval to 0.1 produces 9 ticks between 1 and 10, 10 and 100 etc. A
     * minorTickInterval of "auto" on a log axis results in a best guess, attempting to enter
     * approximately 5 minor ticks between each major tick. Defaults to null.
     */
    @Option
    public Double minorTickInterval;

    /**
     * The pixel length of the minor tick marks. Defaults to 2.
     */
    @Option
    public Integer minorTickLength;

    /**
     * The position of the minor tick marks relative to the axis line. Can be one of inside and
     * outside. Defaults to outside.
     */
    @Option
    public String minorTickPosition;

    /**
     * The pixel width of the minor tick mark. Defaults to 0.
     */
    @Option
    public Integer minorTickWidth;

    /**
     * The distance in pixels from the plot area to the axis line. A positive offset moves the axis
     * with its line, labels and ticks away from the plot area. This is typically used when two or
     * more axes are displayed on the same side of the plot. Defaults to 0.
     */
    @Option
    public Integer offset;

    /**
     * Whether to display the axis on the opposite side of the normal. The normal is on the left
     * side for vertical axes and bottom for horizontal, so the opposite sides will be right and top
     * respectively. This is typically used with dual or multiple axes. Defaults to false.
     */
    @Option
    public Boolean opposite;

    /**
     * A colored band stretching across the plot area marking an interval on the axis.
     *
     * @see PlotBandOptions
     */
    @Option
    public final List<PlotBandOptions> plotBands = new ArrayList<>();

    /**
     * A line stretching across the plot area, marking a specific value on one of the axes.
     *
     * @see PlotLineOptions
     */
    @Option
    public final List<PlotLineOptions> plotLines = new ArrayList<>();

    /**
     * Whether to reverse the axis so that the highest number is closest to origin. If the chart is
     * inverted, the x axis is reversed by default. Defaults to false.
     */
    @Option
    public Boolean reversed;

    /**
     * Whether to show the axis line and title when the axis has no data. Defaults to true.
     */
    @Option
    public Boolean showEmpty;

    /**
     * Whether to show the first tick label. Defaults to true.
     */
    @Option
    public Boolean showFirstLabel;

    /**
     * Whether to show the last tick label. Defaults to false.
     */
    @Option
    public Boolean showLastLabel;

    /**
     * For datetime axes, this decides where to put the tick between weeks. 0 = Sunday, 1 = Monday.
     * Defaults to 1.
     */
    @Option
    public Integer startOfWeek;

    /**
     * Whether to force the axis to start on a tick. Use this option with the maxPadding option to
     * control the axis start. Defaults to false.
     */
    @Option
    public Boolean startOnTick;

    /**
     * Color for the main tick marks. Defaults to #C0D0E0.
     */
    @Option
    public String tickColor;

    /**
     * The interval of the tick marks in axis units. When null, the tick interval is computed to
     * approximately follow the tickPixelInterval on linear and datetime axes. On categorized axes,
     * a null tickInterval will default to 1, one category. Note that datetime axes are based on
     * milliseconds, so for example an interval of one day is expressed as 24 * 3600 * 1000. On
     * logarithmic axes, the tickInterval is based on powers, so a tickInterval of 1 means one tick
     * on each of 0.1, 1, 10, 100 etc. A tickInterval of 2 means a tick of 0.1, 10, 1000 etc. A
     * tickInterval of 0.2 puts a tick on 0.1, 0.2, 0.4, 0.6, 0.8, 1, 2, 4, 6, 8, 10, 20, 40 etc.
     * Defaults to null.
     */
    @Option
    public Double tickInterval;

    /**
     * The pixel length of the main tick marks. Defaults to 5.
     */
    @Option
    public Integer tickLength;

    /**
     * If tickInterval is null this option sets the approximate pixel interval of the tick marks.
     * Not applicable to categorized axis. Defaults to 72 for the Y axis and 100 for the X axis.
     */
    @Option
    public Integer tickPixelInterval;

    /**
     * The position of the major tick marks relative to the axis line. Can be one of inside and
     * outside. Defaults to "outside".
     */
    @Option
    public String tickPosition;

    /**
     * The pixel width of the major tick marks. Defaults to 1.
     */
    @Option
    public Integer tickWidth;

    /**
     * For categorized axes only. If "on" the tick mark is placed in the center of the category, if
     * "between" the tick mark is placed between categories. Defaults to "between".
     */
    @Option
    public String tickmarkPlacement;

    /**
     * The axis title, showing next to the axis line.
     */
    @Option
    public final AxisTitleOptions title = new AxisTitleOptions();

    /**
     * The type of axis. Can be one of "linear", "logarithmic" or "datetime". In a datetime axis,
     * the numbers are given in milliseconds, and tick marks are placed on appropriate values like
     * full hours or days. Defaults to "linear".
     */
    @Option
    public String type;

    /**
     * Index of the axis within the chart.
     */
    @Option(ignore = true)
    public final int index;
}
