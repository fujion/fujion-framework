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
package org.fujion.plotly.plot;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.plotly.common.ExponentFormatEnum;
import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.common.HorizontalAlignEnum;
import org.fujion.plotly.common.MeasurementEnum;
import org.fujion.plotly.common.ShowTickEnum;
import org.fujion.plotly.common.TickModeEnum;
import org.fujion.plotly.common.VerticalAlignEnum;

/**
 * Options for color bar.
 */
public class ColorbarOptions extends Options {
    
    /**
     * Determines whether ticks are drawn and where.
     */
    public enum TickPositionEnum {
        
        /**
         * Ticks are drawn inside the axis lines.
         */
        INSIDE,
        /**
         * Ticks are drawn outside the axis lines.
         */
        OUTSIDE;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Determines the location of the colorbar title with respect to the color bar.
     */
    public enum TitleSideEnum {

        BOTTOM, RIGHT, TOP;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * The color of padded area.
     * <p>
     * Default: "#000"
     */
    @Option
    public String bgcolor;
    
    /**
     * The color of the border enclosing this color bar.
     * <p>
     * Default: "#444"
     */
    @Option
    public String bordercolor;

    /**
     * The width (in px) of the border enclosing this color bar.
     * <p>
     * Range &ge;0
     * <p>
     * Default: 0
     */
    @Option
    public Integer borderwidth;

    /**
     * The step in-between ticks on this axis. Use with "tick0". Must be a positive number, or
     * special strings available to "log" and "date" axes. If the axis "type" is "log", then ticks
     * are set every 10^(n"dtick) where n is the tick number. For example, to set a tick mark at 1,
     * 10, 100, 1000, ... set dtick to 1. To set tick marks at 1, 100, 10000, ... set dtick to 2. To
     * set tick marks at 1, 5, 25, 125, 625, 3125, ... set dtick to log_10(5), or 0.69897000433.
     * "log" has several special values; "L&lt;f&gt;", where "f" is a positive number, gives ticks
     * linearly spaced in value (but not position). For example "tick0" = 0.1, "dtick" = "L0.5" will
     * put ticks at 0.1, 0.6, 1.1, 1.6 etc. To show powers of 10 plus small digits between, use "D1"
     * (all digits) or "D2" (only 2 and 5). "tick0" is ignored for "D1" and "D2". If the axis "type"
     * is "date", then you must convert the time to milliseconds. For example, to set the interval
     * between ticks to one day, set "dtick" to 86400000.0. "date" also has special values
     * "M&lt;n&gt;" gives ticks spaced by a number of months. "n" must be a positive integer. To set
     * ticks on the 15th of every third month, set "tick0" to "2000-01-15" and "dtick" to "M3". To
     * set ticks every 4 years, set "dtick" to "M48"
     */
    @Option("dtick")
    public Double dtick$number;
    
    /**
     * The step in-between ticks on this axis. Use with "tick0". Must be a positive number, or
     * special strings available to "log" and "date" axes. If the axis "type" is "log", then ticks
     * are set every 10^(n"dtick) where n is the tick number. For example, to set a tick mark at 1,
     * 10, 100, 1000, ... set dtick to 1. To set tick marks at 1, 100, 10000, ... set dtick to 2. To
     * set tick marks at 1, 5, 25, 125, 625, 3125, ... set dtick to log_10(5), or 0.69897000433.
     * "log" has several special values; "L&lt;f&gt;", where "f" is a positive number, gives ticks
     * linearly spaced in value (but not position). For example "tick0" = 0.1, "dtick" = "L0.5" will
     * put ticks at 0.1, 0.6, 1.1, 1.6 etc. To show powers of 10 plus small digits between, use "D1"
     * (all digits) or "D2" (only 2 and 5). "tick0" is ignored for "D1" and "D2". If the axis "type"
     * is "date", then you must convert the time to milliseconds. For example, to set the interval
     * between ticks to one day, set "dtick" to 86400000.0. "date" also has special values
     * "M&lt;n&gt;" gives ticks spaced by a number of months. "n" must be a positive integer. To set
     * ticks on the 15th of every third month, set "tick0" to "2000-01-15" and "dtick" to "M3". To
     * set ticks every 4 years, set "dtick" to "M48"
     */
    @Option("dtick")
    public String dtick$string;

    /**
     * Formatting rule for the tick exponents.
     */
    @Option
    public ExponentFormatEnum exponentformat;
    
    /**
     * The length of the color bar This measure excludes the padding of both ends. That is, the
     * color bar length is this length minus the padding on both ends.
     * <p>
     * Default: 1
     */
    @Option
    public Integer len;
    
    /**
     * Determines whether this color bar's length (i.e. the measure in the color variation
     * direction) is set in units of plot "fraction" (the default) or in "pixels". Use "len" to set
     * the value.
     */
    @Option
    public MeasurementEnum lenmode;

    /**
     * Specifies the maximum number of ticks for the particular axis. The actual number of ticks
     * will be chosen automatically to be less than or equal to "nticks". Has an effect only if
     * "tickmode" is set to "auto".
     * <p>
     * Default: 0
     */
    @Option
    public Integer nticks;
    
    /**
     * The axis line color.
     * <p>
     * Default: "#444"
     */
    @Option
    public String outlinecolor;
    
    /**
     * The width (in px) of the axis line.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 1.
     */
    @Option
    public Integer outlinewidth;
    
    /**
     * If true, even 4-digit integers are separated.
     */
    @Option
    public Boolean separatethousands;
    
    /**
     * Controls display of the tick exponent.
     */
    @Option
    public ShowTickEnum showexponent;
    
    /**
     * Determines whether or not the tick labels are drawn.
     * <p>
     * Default: true
     */
    @Option
    public Boolean showticklabels;
    
    /**
     * Controls the display of the tick prefix.
     */
    @Option
    public ShowTickEnum showtickprefix;

    /**
     * Controls the display of the tick suffix.
     */
    @Option
    public ShowTickEnum showticksuffix;

    /**
     * The thickness of the color bar This measure excludes the size of the padding, ticks and
     * labels.
     * <p>
     * Default: 30
     */
    @Option
    public Integer thickness;
    
    /**
     * Determines whether this color bar's thickness (i.e. the measure in the constant color
     * direction) is set in units of plot "fraction" or in "pixels" (the default). Use "thickness"
     * to set the value.
     */
    @Option
    public MeasurementEnum thicknessmode;
    
    /**
     * The placement of the first tick on this axis. Use with "dtick". If the axis "type" is "log",
     * then you must take the log of your starting tick (e.g. to set the starting tick to 100, set
     * the "tick0" to 2) except when "dtick"="L&lt;f&gt;" (see "dtick" for more info). If the axis
     * "type" is "date", it should be a date string, like date data. If the axis "type" is
     * "category", it should be a number, using the scale where each category is assigned a serial
     * number from zero in the order it appears.
     */
    @Option("tick0")
    public Double tick0$number;
    
    /**
     * The placement of the first tick on this axis. Use with "dtick". If the axis "type" is "log",
     * then you must take the log of your starting tick (e.g. to set the starting tick to 100, set
     * the "tick0" to 2) except when "dtick"="L&lt;f&gt;" (see "dtick" for more info). If the axis
     * "type" is "date", it should be a date string, like date data. If the axis "type" is
     * "category", it should be a number, using the scale where each category is assigned a serial
     * number from zero in the order it appears.
     */
    @Option("tick0")
    public String tick0$string;

    /**
     * The angle of the tick labels with respect to the horizontal. For example, a "tickangle" of
     * -90 draws the tick labels vertically.
     */
    @Option
    public Integer tickangle;
    
    /**
     * The tick color.
     * <p>
     * Default: "#444"
     */
    @Option
    public String tickcolor;

    /**
     * The color bar's tick label font.
     */
    @Option
    public final FontOptions tickfont = new FontOptions();
    
    /**
     * The tick label formatting rule using d3 formatting mini-languages which are very similar to
     * those in Python. We add one item to d3's date formatter: "%{n}f" for fractional seconds with
     * n digits. For example, "2016-10-13 09:15:23.456" with tickformat "%H~%M~%S.%2f" would display
     * "09~15~23.46"
     */
    @Option
    public String tickformat;
    
    /**
     * Range ["min", "max"], where "min", "max" - dtick values which describe some zoom level. It is
     * possible to omit "min" or "max" value by passing "null".
     */
    @Option("tickformatstops.dtickrange")
    public Double[] tickformatstops_dtickrange;
    
    /**
     * Format for described zoom level, the same as "tickformat"
     */
    @Option("tickformatstops.value")
    public String tickformatstops_value;
    
    /**
     * The tick length (in px).
     * <p>
     * Default: 5
     */
    @Option
    public Integer ticklen;
    
    /**
     * The tick mode for this axis.
     */
    @Option
    public TickModeEnum tickmode;
    
    /**
     * The tick label prefix.
     */
    @Option
    public String tickprefix;
    
    /**
     * Determines whether ticks are drawn and where.
     * <p>
     * Default: no ticks
     */
    @Option
    public TickPositionEnum ticks;

    /**
     * The tick label suffix.
     */
    @Option
    public String ticksuffix;

    /**
     * The text displayed at the ticks position via "tickvals". Only has an effect if "tickmode" is
     * set to "array". Used with "tickvals".
     */
    @Option
    public String[] ticktext;

    /**
     * The values at which ticks on this axis appear. Only has an effect if "tickmode" is set to
     * "array". Used with "ticktext".
     */
    @Option
    public Object[] tickvals;

    /**
     * The tick width (in px).
     * <p>
     * Default: 1
     */
    @Option
    public Integer tickwidth;

    /**
     * The title of the color bar.
     */
    @Option
    public String title;

    /**
     * The font of the title of the color bar.
     */
    @Option
    public final FontOptions titlefont = new FontOptions();

    /**
     * Determines the location of the colorbar title with respect to the color bar.
     */
    @Option
    public TitleSideEnum titleside;

    /**
     * The x position of the color bar (in plot fraction).
     * <p>
     * Constraints: &ge;-2 and &le;3
     * <p>
     * Default: 1.02
     */
    @Option
    public Double x;

    /**
     * The color bar's horizontal position anchor. This anchor binds the "x" position to the "left
     * (the default), "center" or "right" of the color bar.
     */
    @Option
    public HorizontalAlignEnum xanchor;

    /**
     * The amount of padding (in px) along the x direction.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 10
     */
    @Option
    public Integer xpad;
    
    /**
     * The y position of the color bar (in plot fraction).
     * <p>
     * Constraints: &ge;-2 and &le;3
     * <p>
     * Default: 0.5
     */
    @Option
    public Double y;
    
    /**
     * The color bar's vertical position anchor This anchor binds the "y" position to the "top",
     * "middle" (the default) or "bottom" of the color bar.
     */
    @Option
    public VerticalAlignEnum yanchor;

    /**
     * The amount of padding (in px) along the y direction.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 10
     */
    @Option
    public Integer ypad;
}
