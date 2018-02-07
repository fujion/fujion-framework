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
package org.fujion.highcharts;

/**
 * Options for bar series.
 * <p>
 * A bar series is a special type of column series where the columns are horizontal.
 */
public class PlotBar extends PlotOptions {
    
    /**
     * Depth of the columns in a 3D column chart. Requires highcharts-3d.js. Defaults to 25.
     */
    public Integer depth;
    
    /**
     * 3D columns only. The color of the edges. Similar to borderColor, except it defaults to the
     * same color as the column. Defaults to undefined.
     */
    public String edgeColor;

    /**
     * 3D columns only. The width of the colored edges. Defaults to 1.
     */
    public Integer edgeWidth;

    /**
     * Whether to group non-stacked columns or to let them render independent of each other.
     * Non-grouped columns will be laid out individually and overlap each other. Defaults to true.
     */
    public Boolean grouping;
    
    /**
     * The spacing between columns on the Z Axis in a 3D chart. Requires highcharts-3d.js. Defaults
     * to 1.
     */
    public Integer groupZPadding;
    
    /**
     * The maximum allowed pixel width for a column, translated to the height of a bar in a bar
     * chart. This prevents the columns from becoming too wide when there is a small number of
     * points in the chart. Defaults to null.
     */
    public Integer maxPointWidth;

    /**
     * The X axis range that each point is valid for. This determines the width of the column. On a
     * categorized axis, the range will be 1 by default (one category unit). On linear and datetime
     * axes, the range will be computed as the distance between the two closest data points.
     */
    public Double pointRange;
    
    /**
     * A pixel value specifying a fixed width for each column or bar. When null, the width is
     * calculated from the pointPadding and groupPadding. Defaults to null.
     */
    public Integer pointWidth;
    
}
