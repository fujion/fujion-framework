/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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

import org.fujion.annotation.Option;

/**
 * Options for column series.
 * <p>
 * Column series display one column per value along an X axis.
 */
public class PlotColumn extends PlotOptions {

    /**
     * Depth of the columns in a 3D column chart. Requires highcharts-3d.js. Defaults to 25.
     */
    @Option
    public Integer depth;
    
    /**
     * 3D columns only. The color of the edges. Similar to borderColor, except it defaults to the
     * same color as the column. Defaults to undefined.
     */
    @Option
    public String edgeColor;

    /**
     * 3D columns only. The width of the colored edges. Defaults to 1.
     */
    @Option
    public Integer edgeWidth;

    /**
     * Whether to group non-stacked columns or to let them render independent of each other.
     * Non-grouped columns will be laid out individually and overlap each other. Defaults to true.
     */
    @Option
    public Boolean grouping;

    /**
     * The spacing between columns on the Z Axis in a 3D chart. Requires highcharts-3d.js. Defaults
     * to 1.
     */
    @Option
    public Integer groupZPadding;

    /**
     * The maximum allowed pixel width for a column, translated to the height of a bar in a bar
     * chart. This prevents the columns from becoming too wide when there is a small number of
     * points in the chart. Defaults to null.
     */
    @Option
    public Integer maxPointWidth;
    
    /**
     * A pixel value specifying a fixed width for each column or bar. When null, the width is
     * calculated from the pointPadding and groupPadding. Defaults to null.
     */
    @Option
    public Integer pointWidth;
}
