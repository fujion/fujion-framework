/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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
import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.common.VHOrientationEnum;

/**
 * Options for bar plot.
 * <p>
 * A bar plot presents categorical data with rectangular bars with heights or lengths proportional
 * to the values that they represent. The bars can be plotted vertically or horizontally.
 */
public class PlotBar extends PlotOptions {
    
    /**
     * Constrain the size of text inside or outside a region to be no larger than the region itself.
     */
    public enum ConstrainTextEnum {
        BOTH, INSIDE, NONE, OUTSIDE;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Determines where the bar base is drawn (in position axis units). In "stack" or "relative" bar
     * mode, traces that set "base" will be excluded and drawn in "overlay" mode instead.
     */
    public String base;

    /**
     * Constrain the size of text inside or outside a bar to be no larger than the bar itself.
     * <p>
     * Default: BOTH
     */
    public ConstrainTextEnum constraintext;
    
    /**
     * The x coordinate step. See {@link #x0$number x0} for more info.
     */
    public Double dx;
    
    /**
     * The y coordinate step. See {@link #y0$number y0} for more info.
     */
    public Double dy;
    
    /**
     * Options for x-axis error bars.
     */
    public final ErrorBarOptions error__x = new ErrorBarOptions();

    /**
     * Options for y-axis error bars.
     */
    public final ErrorBarOptions error__y = new ErrorBarOptions();

    /**
     * Hover text elements associated with each (x,y) pair. If a single string, the same string
     * appears over all the data points. If an array of string, the items are mapped in order to the
     * this trace's (x,y) coordinates. To be seen, trace "hoverinfo" must contain a "text" flag.
     */
    public String hovertext;
    
    /**
     * The font used for text lying inside the bar.
     */
    public final FontOptions insidetextfont = new FontOptions();
    
    /**
     * Options for marker.
     */
    public final MarkerOptions marker = new MarkerOptions();
    
    /**
     * Shifts the position where the bar is drawn (in position axis units). In "group" bar mode,
     * traces that set "offset" will be excluded and drawn in "overlay" mode instead.
     */
    public int[] offset$array;

    /**
     * Alternate form where offset is constant. Shifts the position where the bar is drawn (in
     * position axis units). In "group" bar mode, traces that set "offset" will be excluded and
     * drawn in "overlay" mode instead.
     */
    public Integer offset$number;
    
    /**
     * The orientation of the bars.
     */
    public VHOrientationEnum orientation;
    
    /**
     * The font used for text lying outside the bar.
     */
    public final FontOptions outsidetextfont = new FontOptions();

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
     * The text font.
     */
    public final FontOptions textfont = new FontOptions();
    
    /**
     * Specifies the location of the text relative to the bar.
     */
    public TextRegionEnum textposition;
    
    /**
     *
     */
    public final SelectedOptions unselected = new SelectedOptions();
    
    /**
     * The bar width (in position axis units).
     */
    public int[] width$array;
    
    /**
     * Alternate form where width is constant. The bar width (in position axis units).
     */
    public Integer width$number;
    
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
