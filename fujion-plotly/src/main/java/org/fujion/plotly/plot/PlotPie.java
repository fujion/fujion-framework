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

import org.fujion.annotation.Option;
import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.common.PolarDirectionEnum;

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
    @Option
    public PolarDirectionEnum direction;

    /**
     * The label step. See {@link #label0} for more info.
     * <p>
     * Default: 1
     */
    @Option
    public Integer dlabel;

    /**
     * The horizontal domain of this pie trace (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    @Option
    public int[] domain_x;

    /**
     * The vertical domain of this pie trace (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    @Option
    public int[] domain_y;
    
    /**
     * The fraction of the radius to cut out of the pie. Use this to make a donut chart.
     * <p>
     * Default: 0
     */
    @Option
    public Double hole;
    
    /**
     * Hover text elements associated with each sector.items are mapped in order of this trace's
     * sectors. To be seen, trace "hoverinfo" must contain a "text" flag.
     */
    @Option
    public String[] hovertext$array;

    /**
     * Alternative to an array, the same string appears for all data points.
     */
    @Option
    public String hovertext$string;

    /**
     * The font used for "textinfo" lying inside the pie.
     */
    @Option
    public final FontOptions insidetextfont = new FontOptions();

    /**
     * Alternate to "labels". Builds a numeric set of labels. Use with "dlabel" where "label0" is
     * the starting label and "dlabel" the step.
     * <p>
     * Default: 0
     */
    @Option
    public Integer label0;

    /**
     * The sector labels. If "labels" entries are duplicated, we sum associated "values" or simply
     * count occurrences if "values" is not provided. For other array attributes (including color)
     * we use the first non-empty entry among all occurrences of the label.
     */
    @Option
    public String[] labels;
    
    /**
     * The color of each sector of this pie chart. If not specified, the default trace color set is
     * used to pick the sector colors.
     */
    @Option
    public String[] marker_colors;
    
    /**
     * The color of the line enclosing each sector.
     * <p>
     * Default: "#444"
     */
    @Option
    public String marker_line_color;
    
    /**
     * The width (in px) of the line enclosing each sector.
     * <p>
     * Default: 0
     */
    @Option
    public Integer marker_line_width;

    /**
     * The font used for "textinfo" lying outside the pie.
     */
    @Option
    public final FontOptions outsidetextfont = new FontOptions();
    
    /**
     * The fraction of larger radius to pull the sectors out from the center. Use an array to
     * highlight one or more slices.
     */
    @Option
    public double[] pull$array;
    
    /**
     * Alternative form for pull. The fraction of larger radius to pull the sectors out from the
     * center. Pulls all slices apart from each other equally.
     */
    @Option
    public Double pull$number;

    /**
     * Instead of the first slice starting at 12 o"clock, rotate to some other angle.
     * <p>
     * Constraints: &ge;-360 and &le;360
     * <p>
     * Default: 0
     */
    @Option
    public Integer rotation;
    
    /**
     * If there are multiple pies that should be sized according to their totals, link them by
     * providing a non-empty group id here shared by every trace in the same group.
     */
    @Option
    public String scalegroup;

    /**
     * Determines whether or not the sectors are reordered from largest to smallest.
     * <p>
     * Default: true
     */
    @Option
    public Boolean sort;

    /**
     * Text elements associated with each sector. If trace "textinfo" contains a "text" flag, these
     * elements will seen on the chart. If trace "hoverinfo" contains a "text" flag and "hovertext"
     * is not set, these elements will be seen in the hover labels.
     */
    @Option
    public String[] text;

    /**
     * The font used for "textinfo".
     */
    @Option
    public final FontOptions textfont = new FontOptions();
    
    /**
     * Determines which trace information appear on the graph. Any combination of "label", "text",
     * "value", "percent" joined with a "+" OR "none". examples: "label", "text", "label+text",
     * "label+text+value", "none".
     */
    @Option
    public String textinfo;
    
    /**
     * Specifies the location of the "textinfo".
     */
    @Option
    public TextRegionEnum textposition;

    /**
     * The values of the sectors of this pie chart. If omitted, we count occurrences of each label.
     */
    @Option
    public Object[] values;
    
    /**
     * The values of the sectors of this pie chart. If omitted, we count occurrences of each label.
     */
    @Option
    public double[] values$number;
    
}
