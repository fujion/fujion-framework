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

import org.fujion.plotly.common.PolarDirectionEnum;
import org.fujion.plotly.common.FontOptions;

/**
 * Options for pie chart plot.
 * <p>
 * A pie chart is a circular graphic which is divided into slices to illustrate numerical
 * proportion.
 */
public class PlotPie extends PlotOptions {
    
    /**
     * Specifies the direction at which succeeding sectors follow one another.
     * <p>
     * Default: COUNTERCLOCKWISE
     */
    public PolarDirectionEnum direction;
    
    /**
     * The label step. See {@link #label0} for more info.
     * <p>
     * Default: 1
     */
    public Integer dlabel;
    
    /**
     * The horizontal domain of this pie trace (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    public int[] domain_x;
    
    /**
     * The vertical domain of this pie trace (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    public int[] domain_y;

    /**
     * The fraction of the radius to cut out of the pie. Use this to make a donut chart.
     * <p>
     * Default: 0
     */
    public Double hole;

    /**
     * Hover text elements associated with each sector.items are mapped in order of this trace's
     * sectors. To be seen, trace "hoverinfo" must contain a "text" flag.
     */
    public String[] hovertext$array;
    
    /**
     * Alternative to an array, the same string appears for all data points.
     */
    public String hovertext$string;
    
    /**
     * The font used for "textinfo" lying inside the pie.
     */
    public final FontOptions insidetextfont = new FontOptions();
    
    /**
     * Alternate to "labels". Builds a numeric set of labels. Use with "dlabel" where "label0" is
     * the starting label and "dlabel" the step.
     * <p>
     * Default: 0
     */
    public Integer label0;
    
    /**
     * The sector labels. If "labels" entries are duplicated, we sum associated "values" or simply
     * count occurrences if "values" is not provided. For other array attributes (including color)
     * we use the first non-empty entry among all occurrences of the label.
     */
    public String[] labels;

    /**
     * The color of each sector of this pie chart. If not specified, the default trace color set is
     * used to pick the sector colors.
     */
    public String[] marker_colors;

    /**
     * The color of the line enclosing each sector.
     * <p>
     * Default: "#444"
     */
    public String marker_line_color;

    /**
     * The width (in px) of the line enclosing each sector.
     * <p>
     * Default: 0
     */
    public Integer marker_line_width;
    
    /**
     * The font used for "textinfo" lying outside the pie.
     */
    public final FontOptions outsidetextfont = new FontOptions();

    /**
     * The fraction of larger radius to pull the sectors out from the center. Use an array to
     * highlight one or more slices.
     */
    public double[] pull$array;

    /**
     * Alternative form for pull. The fraction of larger radius to pull the sectors out from the
     * center. Pulls all slices apart from each other equally.
     */
    public Double pull$number;
    
    /**
     * Instead of the first slice starting at 12 o"clock, rotate to some other angle.
     * <p>
     * Constraints: &ge;-360 and &le;360
     * <p>
     * Default: 0
     */
    public Integer rotation;

    /**
     * If there are multiple pies that should be sized according to their totals, link them by
     * providing a non-empty group id here shared by every trace in the same group.
     */
    public String scalegroup;
    
    /**
     * Determines whether or not the sectors are reordered from largest to smallest.
     * <p>
     * Default: true
     */
    public Boolean sort;
    
    /**
     * Text elements associated with each sector. If trace "textinfo" contains a "text" flag, these
     * elements will seen on the chart. If trace "hoverinfo" contains a "text" flag and "hovertext"
     * is not set, these elements will be seen in the hover labels.
     */
    public String[] text;
    
    /**
     * The font used for "textinfo".
     */
    public final FontOptions textfont = new FontOptions();

    /**
     * Determines which trace information appear on the graph. Any combination of "label", "text",
     * "value", "percent" joined with a "+" OR "none". examples: "label", "text", "label+text",
     * "label+text+value", "none".
     */
    public String textinfo;

    /**
     * Specifies the location of the "textinfo".
     */
    public TextRegionEnum textposition;
    
    /**
     * The values of the sectors of this pie chart. If omitted, we count occurrences of each label.
     */
    public Object[] values;

    /**
     * The values of the sectors of this pie chart. If omitted, we count occurrences of each label.
     */
    public double[] values$number;

}
