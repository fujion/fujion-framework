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

import org.fujion.annotation.Option;
import org.fujion.plotly.common.VHOrientationEnum;

/**
 * Options for violin plot.
 */
public class PlotViolin extends PlotOptions {

    /**
     * Determines the metric by which the width of each violin is determined.
     */
    public enum ScaleModeEnum {

        /**
         * Violins are scaled by the number of sample points making up each violin.
         */
        COUNT,
        /**
         * Each violin has the same (max) width.
         */
        WIDTH;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * Determines on which side of the position value the density function making up one half of a
     * violin is plotted. Useful when comparing two violin traces under "overlay" mode, where one
     * trace has "side" set to "positive" and the other to "negative".
     */
    public enum SideEnum {

        BOTH, NEGATIVE, POSITIVE
        
    }
    
    /**
     * Determines the method by which the span in data space where the density function will be
     * computed.
     */
    public enum SpanModeEnum {
        
        /**
         * The span goes from the sample's minimum to its maximum value.
         */
        HARD,
        /**
         * For custom span settings; use with the span attribute.
         */
        MANUAL,
        /**
         * The span goes from the sample's minimum value minus two bandwidths to the sample"s
         * maximum value plus two bandwidths.
         */
        SOFT;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * The bandwidth used to compute the kernel density estimate. By default, the bandwidth is
     * determined by Silverman's rule of thumb.
     */
    @Option
    public Double bandwidth;
    
    /**
     * Determines if an miniature box plot is drawn inside the violins.
     */
    @Option
    public final ViolinBoxOptions box = new ViolinBoxOptions();
    
    /**
     * The fill color.
     * <p>
     * Default: A half-transparent variant of the line color, marker color, or marker line color,
     * whichever is available.
     */
    @Option
    public String fillcolor;
    
    /**
     * Any combination of "violins", "points", "kde" joined with a "+" OR "all". examples:
     * "violins", "points", "violins+points", "violins+points+kde", "all" default:
     * "violins+points+kde"
     */
    @Option
    public String hoveron;
    
    /**
     * The amount of jitter in the sample points drawn. If "0", the sample points align along the
     * distribution axis. If "1", the sample points are drawn in a random jitter of width equal to
     * the width of the violins.
     * <p>
     * Constraints: &ge;0 and &le;1
     */
    @Option
    public Double jitter;
    
    /**
     * The color of line bounding the violin(s).
     */
    @Option("line.color")
    public String line_color;
    
    /**
     * The width (in px) of line bounding the violin(s).
     * <p>
     * Default: 2
     */
    @Option("line.width")
    public Integer line_width;
    
    /**
     * Options for marker.
     */
    @Option
    public final MarkerOptions marker = new MarkerOptions();

    /**
     * The mean line color.
     */
    @Option("meanline.color")
    public String meanline_color;
    
    /**
     * Determines if a line corresponding to the sample's mean is shown inside the violins. If
     * "box.visible" is turned on, the mean line is drawn inside the inner box. Otherwise, the mean
     * line is drawn from one side of the violin to other.
     */
    @Option("meanline.visible")
    public Boolean meanline_visible;
    
    /**
     * The mean line width.
     */
    @Option("meanline.width")
    public Integer meanline_width;

    /**
     * The orientation of the violin(s).
     */
    @Option
    public VHOrientationEnum orientation;
    
    /**
     * The position of the sample points in relation to the violins. If "0", the sample points are
     * placed over the center of the violins. Positive/negative values correspond to positions to
     * the right/left for vertical violins and above/below for horizontal violins.
     */
    @Option
    public Integer pointpos;
    
    /**
     * Determines which points are displayed.
     */
    @Option
    public PointsFilterEnum points;
    
    /**
     * If there are multiple violins that should be sized according to to some metric (see
     * "scalemode"), link them by providing a non-empty group id here shared by every trace in the
     * same group.
     */
    @Option
    public String scalegroup;

    /**
     * The metric by which the width of each violin is determined.
     * <p>
     * Default: WIDTH
     */
    @Option
    public ScaleModeEnum scalemode;

    /**
    *
    */
    @Option
    public final SelectedOptions selected = new SelectedOptions();
    
    /**
     * Determines on which side of the position value the density function making up one half of a
     * violin is plotted. Useful when comparing two violin traces under "overlay" mode, where one
     * trace has "side" set to "positive" and the other to "negative".
     */
    @Option
    public SideEnum side;

    /**
     * The span in data space for which the density function will be computed. Has an effect only
     * when "spanmode" is set to MANUAL.
     */
    @Option
    public double[] span;

    /**
     * The method by which the span in data space where the density function will be computed.
     * <p>
     * Default: SOFT
     */
    @Option
    public SpanModeEnum spanmode;

    /**
     * The text elements associated with each sample value as an array of string where the items are
     * mapped in order to the this trace's (x,y) coordinates. To be seen, trace "hoverinfo" must
     * contain a "text" flag.
     */
    @Option("text")
    public String[] text$array;

    /**
     * The text elements associated with each sample value as a single string where the same string
     * appears over all the data points. To be seen, trace "hoverinfo" must contain a "text" flag.
     */
    @Option("text")
    public String text$string;

    /**
    *
    */
    @Option
    public final SelectedOptions unselected = new SelectedOptions();
    
    /**
     * The x sample data or coordinates.
     */
    @Option
    public double[] x;
    
    /**
     * The x coordinate of the box.
     */
    @Option("x0")
    public Double x0$number;
    
    /**
     * The x coordinate of the box as a categorical string.
     */
    @Option("x0")
    public String x0$string;

    /**
     * Reference between this trace's x coordinates and a 2D Cartesian x axis. If "x" (the default
     * value), the x coordinates refer to "layout.xaxis". If "x2", the x coordinates refer to
     * "layout.xaxis2", and so on.
     */
    @Option
    public String xaxis;
    
    /**
     * The y sample data or coordinates.
     */
    @Option
    public double[] y;
    
    /**
     * The y coordinate of the box.
     */
    @Option("y0")
    public Double y0$number;
    
    /**
     * The y coordinate of the box as a categorical string.
     */
    @Option("y0")
    public String y0$string;
    
    /**
     * Reference between this trace's y coordinates and a 2D Cartesian y axis. If "y" (the default
     * value), the y coordinates refer to "layout.yaxis". If "y2", the y coordinates refer to
     * "layout.xaxis2", and so on.
     */
    @Option
    public String yaxis;

}
