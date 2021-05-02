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
package org.fujion.plotly.plot;

import org.fujion.annotation.Option;
import org.fujion.plotly.common.CalendarTypeEnum;
import org.fujion.plotly.common.VHOrientationEnum;

/**
 * Options for histogram plot.
 * <p>
 * A histogram is a type of bar graph. It is an accurate representation of the distribution of
 * numerical data. It is an estimate of the probability distribution of a continuous variable
 * (quantitative variable). A histogram is constructed by dividing the entire range of values into a
 * series of intervals (bins), and then counting how many values fall into each interval.The bins
 * are usually specified as consecutive, non-overlapping intervals of a variable. The bins must be
 * adjacent, and are often (but not required to be) of equal size.
 */
public class PlotHistogram extends PlotOptions {

    /**
     * Specifies the direction at which bins are accumulated
     */
    public enum CumulativeDirectionEnum {
        
        DECREASING, INCREASING;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Determines how the current bin is included.
     */
    public enum CurrentBinEnum {
        /**
         * Makes the opposite half-bin bias
         */
        EXCLUDE,
        /**
         * Removes the half-bin bias.
         */
        HALF,
        /**
         * The default for compatibility with various other tools, however it introduces a half-bin
         * bias to the results.
         */
        INCLUDE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * Specifies the binning function used for this histogram trace.
     */
    public enum HistFuncEnum {
        /**
         * Histogram values are computed using the average of the values lying inside each bin.
         */
        AVG,
        /**
         * Histogram values are computed by counting the number of values lying inside each bin.
         */
        COUNT,
        /**
         * Histogram values are computed using the maximum of the values lying inside each bin.
         */
        MAX,
        /**
         * Histogram values are computed using the minimum of the values lying inside each bin.
         */
        MIN,
        /**
         * Histogram values are computed using the sum of the values lying inside each bin.
         */
        SUM;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Specifies the type of normalization used for this histogram trace. By default, the span of
     * each bar corresponds to the number of occurrences (i.e. the number of data points lying
     * inside the bins).
     */
    public enum HistNormEnum {
        /**
         * The span of each bar corresponds to the number of occurrences in a bin divided by the
         * size of the bin interval (here, the sum of all bin AREAS equals the total number of
         * sample points).
         */
        DENSITY,
        /**
         * The span of each bar corresponds to the percentage of occurrences with respect to the
         * total number of sample points (here, the sum of all bin HEIGHTS equals 100%).
         */
        PERCENT,
        /**
         * The span of each bar corresponds to the fraction of occurrences with respect to the total
         * number of sample points (here, the sum of all bin HEIGHTS equals 1).
         */
        PROBABILITY,
        /**
         * The area of each bar corresponds to the probability that an event will fall into the
         * corresponding bin (here, the sum of all bin AREAS equals 1).
         */
        PROBABILITY_DENSITY;

        @Override
        public String toString() {
            return name().toLowerCase().replace("_", " ");
        }
    }
    
    /**
     * Determines whether or not the x axis bin attributes are picked by an algorithm. Note that
     * this should be set to false if you want to manually set the number of bins using the
     * attributes in xbins.
     */
    @Option
    public Boolean autobinx;

    /**
     * Determines whether or not the y axis bin attributes are picked by an algorithm. Note that
     * this should be set to false if you want to manually set the number of bins using the
     * attributes in ybins.
     */
    @Option
    public Boolean autobiny;

    /**
     * Only applies if cumulative is enabled. Sets whether the current bin is included, excluded, or
     * has half of its value included in the current cumulative value. INCLUDE is the default for
     * compatibility with various other tools, however it introduces a half-bin bias to the results.
     * EXCLUDE makes the opposite half-bin bias, and HALF removes it.
     */
    @Option("cumulative.currentbin")
    public CurrentBinEnum cumulative_currentbin;

    /**
     * Only applies if cumulative is enabled. If INCREASING (the default) we sum all prior bins, so
     * the result increases from left to right. If DECREASING we sum later bins so the result
     * decreases from left to right.
     */
    @Option("cumulative.direction")
    public CumulativeDirectionEnum cumulative_direction;

    /**
     * If true, display the cumulative distribution by summing the binned values. Use the
     * "direction" and "centralbin" attributes to tune the accumulation method. Note: in this mode,
     * the "density" "histnorm" settings behave the same as their equivalents without "density": ""
     * and "density" both rise to the number of data points, and "probability" and "probability
     * density" both rise to the number of sample points.
     */
    @Option("cumulative.enabled")
    public Boolean cumulative_enabled;

    /**
     * Options for x-axis error bars.
     */
    @Option
    public final ErrorBarOptions error_x = new ErrorBarOptions();

    /**
     * Options for y-axis error bars.
     */
    @Option
    public final ErrorBarOptions error_y = new ErrorBarOptions();

    /**
     * Specifies the binning function used for this histogram trace.
     * <p>
     * Default: COUNT
     */
    @Option
    public HistFuncEnum histfunc;

    /**
     * Specifies the type of normalization used for this histogram trace.
     */
    @Option
    public HistNormEnum histnorm;

    /**
     * Options for marker.
     */
    @Option
    public final MarkerOptions marker = new MarkerOptions();

    /**
     * Specifies the maximum number of desired bins. This value will be used in an algorithm that
     * will decide the optimal bin size such that the histogram best visualizes the distribution of
     * the data.
     * <p>
     * Default: 0
     */
    @Option
    public Integer nbinsx;
    
    /**
     * Specifies the maximum number of desired bins. This value will be used in an algorithm that
     * will decide the optimal bin size such that the histogram best visualizes the distribution of
     * the data.
     * <p>
     * Default: 0
     */
    @Option
    public Integer nbinsy;

    /**
     * The orientation of the bars.
     */
    @Option
    public VHOrientationEnum orientation;

    /**
    *
    */
    @Option
    public final SelectedOptions selected = new SelectedOptions();

    /**
     * Text elements associated with each (x,y) pair. The items are mapped in order to the trace's
     * (x,y) coordinates. If trace "hoverinfo" contains a "text" flag and "hovertext" is not set,
     * these elements will be seen in the hover labels.
     */
    @Option("text")
    public String[] text$array;

    /**
     * Text elements associated with each (x,y) pair. Alternate form where the same string appears
     * over all the data points. If trace "hoverinfo" contains a "text" flag and "hovertext" is not
     * set, these elements will be seen in the hover labels.
     */
    @Option("text")
    public String text$string;

    /**
    *
    */
    @Option
    public final SelectedOptions unselected = new SelectedOptions();

    /**
     * Data points for x-axis.
     */
    @Option
    public Object[] x;
    
    /**
     * Data points for x-axis. Alternate form for numeric data.
     */
    @Option("x")
    public double[] x$number;
    
    /**
     * Reference between this trace's x coordinates and a 2D Cartesian x axis. If "x" (the default
     * value), the x coordinates refer to "layout.xaxis". If "x2", the x coordinates refer to
     * "layout.xaxis2", and so on.
     */
    @Option
    public String xaxis;
    
    /**
     * Options for the x axis bins.
     */
    @Option
    public final BinOptions xbins = new BinOptions();
    
    /**
     * The calendar system to use with "x" date data.
     * <p>
     * Default: GREGORIAN
     */
    @Option
    public CalendarTypeEnum xcalendar;

    /**
     * Data points for y-axis.
     */
    @Option
    public Object[] y;
    
    /**
     * Data points for y-axis. Alternate form for numeric data.
     */
    @Option("y")
    public double[] y$number;
    
    /**
     * Reference between this trace's y coordinates and a 2D Cartesian y axis. If "y" (the default
     * value), the y coordinates refer to "layout.yaxis". If "y2", the y coordinates refer to
     * "layout.xaxis2", and so on.
     */
    @Option
    public String yaxis;
    
    /**
     * Options for the y axis bins.
     */
    @Option
    public final BinOptions ybins = new BinOptions();
    
    /**
     * The calendar system to use with "y" date data.
     * <p>
     * Default: GREGORIAN
     */
    @Option
    public CalendarTypeEnum ycalendar;
    
}
