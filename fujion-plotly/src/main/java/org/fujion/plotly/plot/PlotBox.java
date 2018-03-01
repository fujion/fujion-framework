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
package org.fujion.plotly.plot;

import org.fujion.plotly.common.CalendarTypeEnum;
import org.fujion.plotly.common.VHOrientationEnum;

/**
 * Options for box plot.
 * <p>
 * A box plot graphically depicts groups of numerical data through their quartiles. Box plots may
 * also have lines extending vertically from the boxes (whiskers) indicating variability outside the
 * upper and lower quartiles. Outliers may be plotted as individual points. Box plots are
 * non-parametric: they display variation in samples of a statistical population without making any
 * assumptions of the underlying statistical distribution. The spacings between the different parts
 * of the box indicate the degree of dispersion (spread) and skewness in the data, and show
 * outliers. In addition to the points themselves, they allow one to visually estimate various
 * L-estimators, notably the interquartile range, midhinge, range, mid-range, and trimean. Box plots
 * can be drawn either horizontally or vertically.
 */
public class PlotBox extends PlotOptions {

    /**
     * Controls whether mean and standard deviation are shown.
     */
    public enum MeanEnum {

        /**
         * Neither mean nor standard deviation are drawn.
         */
        FALSE,
        /**
         * The standard deviation is also drawn.
         */
        SD,
        /**
         * The mean of the box(es)" underlying distribution is drawn as a dashed line inside the
         * box(es).
         */
        TRUE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Controls whether mean and standard deviation are drawn.
     */
    public MeanEnum boxmean;

    /**
     * Controls which sample points are shown.
     */
    public PointsFilterEnum boxpoints;

    /**
     * The fill color.
     * <p>
     * Default: a half-transparent variant of the line color, marker color, or marker line color,
     * whichever is available.
     */
    public String fillcolor;

    /**
     * Any combination of "points", "fills" joined with a "+". Examples: "points", "fills",
     * "points+fills" Do the hover effects highlight individual points (markers or line points) or
     * do they highlight filled regions? If the fill is "toself" or "tonext" and there are no
     * markers or text, then the default is "fills", otherwise it is "points".
     */
    public String hoveron;

    /**
     * The amount of jitter in the sample points drawn. If "0", the sample points align along the
     * distribution axis. If "1", the sample points are drawn in a random jitter of width equal to
     * the width of the box(es).
     * <p>
     * Constraints: &ge;0 and &le;1
     */
    public Double jitter;

    /**
     * The color of line bounding the box(es).
     */
    public String line_color;

    /**
     * The width (in px) of line bounding the box(es).
     */
    public Integer line_width;

    /**
     * Options for marker.
     */
    public final MarkerOptions marker = new MarkerOptions();

    /**
     * Determines whether or not notches should be drawn.
     */
    public Boolean notched;
    
    /**
     * The width of the notches relative to the box" width. For example, with 0, the notches are as
     * wide as the box(es).
     * <p>
     * Default: 0.25
     */
    public Double notchwidth;
    
    /**
     * The orientation of the box(es).
     */
    public VHOrientationEnum orientation;

    /**
     * The position of the sample points in relation to the box(es). If "0", the sample points are
     * places over the center of the box(es). Positive (negative) values correspond to positions to
     * the right (left) for vertical boxes and above (below) for horizontal boxes.
     * <p>
     * Constraints: &ge;-2 and &le;2
     */
    public Double pointpos;
    
    /**
     *
     */
    public final SelectedOptions selected = new SelectedOptions();
    
    /**
     * Text elements associated with each (x,y) pair. The items are mapped in order to the this
     * trace's (x,y) coordinates. If trace "hoverinfo" contains a "text" flag and "hovertext" is not
     * set, these elements will be seen in the hover labels.
     */
    public String[] text$array;
    
    /**
     * Text elements associated with each (x,y) pair. Alternate form where the same string appears
     * over all the data points. If trace "hoverinfo" contains a "text" flag and "hovertext" is not
     * set, these elements will be seen in the hover labels.
     */
    public String text$string;
    
    /**
     *
     */
    public final SelectedOptions unselected = new SelectedOptions();

    /**
     * The width of the whiskers relative to the box" width. For example, with 1, the whiskers are
     * as wide as the box(es).
     * <p>
     * Default: 0.5
     */
    public Double whiskerwidth;
    
    /**
     * Data points for x-axis.
     */
    public Object[] x;

    /**
     * Data points for x-axis. Alternate form for numeric data.
     */
    public double[] x$number;
    
    /**
     * Alternate to "x". Builds a linear space of x coordinates. Use with "dx" where "x0" is the
     * starting coordinate and "dx" the step.
     */
    public Double x0$number;
    
    /**
     * Alternate form of x0, where the step will be appended to a string value.
     */
    public String x0$string;

    /**
     * Reference between this trace's x coordinates and a 2D Cartesian x axis. If "x" (the default
     * value), the x coordinates refer to "layout.xaxis". If "x2", the x coordinates refer to
     * "layout.xaxis2", and so on.
     */
    public String xaxis;

    /**
     * The calendar system to use with "x" date data.
     * <p>
     * Default: GREGORIAN
     */
    public CalendarTypeEnum xcalendar;

    /**
     * Data points for y-axis.
     */
    public Object[] y;

    /**
     * Data points for y-axis. Alternate form for numeric data.
     */
    public double[] y$number;
    
    /**
     * Alternate to "y". Builds a linear space of y coordinates. Use with "dy" where "y0" is the
     * starting coordinate and "dy" the step.
     */
    public Double y0$number;
    
    /**
     * Alternate form of y0, where the step will be appended to a string value.
     */
    public String y0$string;
    
    /**
     * Reference between this trace's y coordinates and a 2D Cartesian y axis. If "y" (the default
     * value), the y coordinates refer to "layout.yaxis". If "y2", the y coordinates refer to
     * "layout.xaxis2", and so on.
     */
    public String yaxis;
    
    /**
     * The calendar system to use with "y" date data.
     * <p>
     * Default: GREGORIAN
     */
    public CalendarTypeEnum ycalendar;

}
