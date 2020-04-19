/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
 * Options for variable pie series.
 * <p>
 * A variable pie series is a two dimensional series type, where each point renders an Y and Z
 * value. Each point is drawn as a pie slice where the size (arc) of the slice relates to the Y
 * value and the radius of pie slice relates to the Z value. Requires highcharts-more.js. T
 */
public class PlotVariablePie extends PlotPie {
    
    /**
     * The maximum size of the points' radius related to chart's plotArea. If a number is set, it
     * applies in pixels. Defaults to 100%.
     */
    @Option
    public String maxPointSize;
    
    /**
     * The minimum size of the points' radius related to chart's plotArea. If a number is set, it
     * applies in pixels. Defaults to 10%.
     */
    @Option
    public String minPointSize;

    /**
     * Whether the pie slice's value should be represented by the area or the radius of the slice.
     * Can be either area or radius. The default, area, corresponds best to the human perception of
     * the size of each pie slice. Defaults to area.
     */
    @Option
    public String sizeBy;
    
    /**
     * The maximum possible z value for the point's radius calculation. If the point's Z value is
     * bigger than zMax, the slice will be drawn according to the zMax value Defaults to undefined.
     */
    @Option
    public Double zMax;
    
    /**
     * The minimum possible z value for the point's radius calculation. If the point's Z value is
     * smaller than zMin, the slice will be drawn according to the zMin value. Defaults to
     * undefined.
     */
    @Option
    public Double zMin;
}
