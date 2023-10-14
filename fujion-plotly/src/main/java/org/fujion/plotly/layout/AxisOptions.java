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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.plotly.common.ExponentFormatEnum;
import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.common.ShowTickEnum;
import org.fujion.plotly.common.TickModeEnum;

/**
 * Options common to every axis.
 */
public abstract class AxisOptions extends Options {

    /**
     * Determines whether or not the range of this axis is computed in relation to the input data.
     * See rangemode for more info. If range is provided, then autorange is set to FALSE. Defaults
     * to TRUE.
     */
    public enum AutoRangeEnum {

        FALSE, REVERSED, TRUE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * Specifies the ordering logic for the case of categorical variables.
     */
    public enum CategoryOrderEnum {

        /**
         * Use the order that is present in the data supplied. This is the default behavior.
         */
        TRACE,
        /**
         * Use the ascending alphanumerical order of the category names.
         */
        CATEGORY_ASCENDING,
        /**
         * Use the descending alphanumerical order of the category names.
         */
        CATEGORY_DESCENDING,
        /**
         * Derive the ordering from the attribute "categoryarray". If a category is not found in the
         * "categoryarray" array, the sorting behavior for that attribute will be identical to the
         * "trace" mode. The unspecified categories will follow the categories in "categoryarray".
         */
        ARRAY;

        @Override
        public String toString() {
            return name().toLowerCase().replace("_", " ");
        }
    }
    
    /**
     * The range mode. Defaults to NORMAL.
     */
    public enum RangeModeEnum {

        /**
         * The range is non-negative, regardless of the input data.
         */
        NONNEGATIVE,
        /**
         * The range is computed in relation to the extrema of the input data.
         */
        NORMAL,
        /**
         * The range extends to 0, regardless of the input data.
         */
        TOZERO;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Determines whether axis labels are drawn on the low side, the high side, both, or neither
     * side of the axis.
     */
    public enum ShowTickLabelsEnum {
        BOTH, END, NONE, START;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }

    }
    
    /**
     * The axis type. When AUTO, plotly attempts to determined the axis type by looking into the
     * data of the traces that referenced the axis in question.
     */
    public enum TypeEnum {
        // @formatter:off
            AUTO,
            CATEGORY,
            DATE,
            LINEAR,
            LOG;
        // @formatter:on
        
        @Override
        public String toString() {
            return this == TypeEnum.AUTO ? "-" : name().toLowerCase();
        }
    }

    /**
     * Determines whether or not the range of this axis is computed in relation to the input data.
     * Defaults to TRUE.
     */
    @Option
    public AutoRangeEnum autorange;
    
    /**
     * Specifies the ordering logic for the case of categorical variables. By default, plotly uses
     * "trace", which specifies the order that is present in the data supplied.
     */
    @Option
    public CategoryOrderEnum categoryorder;

    /**
     * The order in which categories on this axis appear. Only has an effect if "categoryorder" is
     * set to "array". Used with "categoryorder".
     */
    @Option
    public String[] categoryarray;

    /**
     * Default for all colors associated with this axis all at once: line, font, tick, and grid
     * colors. Grid color is lightened by blending this with the plot background Individual pieces
     * can override this. Default is "#444".
     */
    @Option
    public String color;

    /**
     * The stride between grid lines along the axis. Defaults to 1.
     */
    @Option
    public Integer dtick;

    /**
     * Determines a formatting rule for the tick exponents.
     */
    @Option
    public ExponentFormatEnum exponentformat;
    
    /**
     * Determines whether or not this axis is zoom-able. If true, then zoom is disabled.
     */
    @Option
    public Boolean fixedrange;

    /**
     * The grid line color.
     */
    @Option
    public String gridcolor;

    /**
     * The width (in px) of the grid line. Defaults to 1.
     */
    @Option
    public Integer gridwidth;

    /**
     * The axis line color. Defaults to "#444".
     */
    @Option
    public String linecolor;

    /**
     * The width (in px) of the axis line. Defaults to 1.
     */
    @Option
    public Integer linewidth;

    /**
     * The maximum number of ticks for the particular axis. The actual number of ticks will be
     * chosen automatically to be less than or equal to "nticks". Has an effect only if "tickmode"
     * is set to "auto".
     */
    @Option
    public Integer nticks;

    /**
     * The range of this axis. If the axis "type" is "log", then you must take the log of your
     * desired range (e.g. to set the range from 1 to 100, set the range from 0 to 2). If the axis
     * "type" is "date", it should be date strings, like date data, though Date objects and unix
     * milliseconds will be accepted and converted to strings. If the axis "type" is "category", it
     * should be numbers, using the scale where each category is assigned a serial number from zero
     * in the order it appears.
     */
    @Option
    public Object[] range;

    /**
     * Determines the range mode. Defaults to NORMAL.
     */
    @Option
    public RangeModeEnum rangemode;

    /**
     * If true, even 4-digit integers are separated.
     */
    @Option
    public Boolean separatethousands;
    
    /**
     * Controls when to display exponent.
     */
    @Option
    public ShowTickEnum showexponent;
    
    /**
     * Determines whether or not grid lines are drawn. If true (the default), the grid lines are
     * drawn at every tick mark.
     */
    @Option
    public Boolean showgrid;

    /**
     * Determines whether or not a line bounding this axis is drawn.
     */
    @Option
    public Boolean showline;

    /**
     * Determines whether axis labels are drawn on the low side, the high side, both, or neither
     * side of the axis. Defaults to START.
     */
    @Option
    public ShowTickLabelsEnum showticklabels;
    
    /**
     * How to display tick label prefix.
     */
    @Option
    public ShowTickEnum showtickprefix;
    
    /**
     * How to display a tick label suffix.
     */
    @Option
    public ShowTickEnum showticksuffix;
    
    /**
     * The starting index of grid lines along the axis. Defaults to 0.
     */
    @Option
    public Integer tick0;

    /**
     * The angle of the tick labels with respect to the horizontal. For example, a "tickangle" of
     * -90 draws the tick labels vertically. Defaults to "auto".
     */
    @Option
    public String tickangle;
    
    /**
     * The tick font.
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
     * Range ["min", "max"], where "min", "max" - dtick values which describe some zoom level, it is
     * possible to omit "min" or "max" value by passing "null".
     */
    @Option("tickformatstops.dtickrange")
    public Double[] tickformatstops_dtickrange;
    
    /**
     * dtickformat for described zoom level, the same as "tickformat".
     */
    @Option("tickformatstops.value")
    public String tickformatstops_value;

    /**
     * The tick mode for the axis.
     */
    @Option
    public TickModeEnum tickmode;
    
    /**
     * Tick label prefix.
     */
    @Option
    public String tickprefix;
    
    /**
     * Tick label suffix.
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
    public double[] tickvals;

    /**
     * The title of the axis.
     */
    @Option
    public String title;

    /**
     * The font of the axis.
     */
    @Option
    public final FontOptions titlefont = new FontOptions();

    /**
     * The axis type. Defaults to AUTO.
     */
    @Option
    public TypeEnum type;

}
