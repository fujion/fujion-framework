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
package org.fujion.highcharts;

import org.fujion.annotation.Option;

/**
 * Options for X-range series.
 * <p>
 * The X-range series displays ranges on the X axis, typically time intervals with a start and end
 * date.
 */
public class PlotXRange extends PlotOptions {
    
    /**
     * Defaults to 3.
     */
    @Option
    public Integer borderRadius;

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
     * The fill color to be used for partial fills. Defaults to a darker shade of the point color.
     * Defaults to undefined.
     */
    @Option("partialFill.fill")
    public String partialFill_fill;
}
