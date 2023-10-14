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
package org.fujion.highcharts;

import org.fujion.annotation.Option;

/**
 * Options for variwide chart.
 * <p>
 * A variwide chart (related to marimekko chart) is a column chart with a variable width expressing
 * a third dimension.
 */
public class PlotVariwide extends PlotOptions {
    
    /**
     * Whether to group non-stacked columns or to let them render independent of each other.
     * Non-grouped columns will be laid out individually and overlap each other. Defaults to true.
     */
    @Option
    public Boolean grouping;
    
    /**
     * In a variwide chart, the group padding is 0 in order to express the horizontal stacking of
     * items. Defaults to 0.
     */
    @Option
    public Integer groupPadding;

    /**
     * The maximum allowed pixel width for a column, translated to the height of a bar in a bar
     * chart. This prevents the columns from becoming too wide when there is a small number of
     * points in the chart. Defaults to null.
     */
    @Option
    public Integer maxPointWidth;
    
    /**
     * In a variwide chart, the point padding is 0 in order to express the horizontal stacking of
     * items. Defaults to 0.
     */
    @Option
    public Integer pointPadding;
}
