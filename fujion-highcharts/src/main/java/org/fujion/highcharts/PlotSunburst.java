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

import java.util.ArrayList;
import java.util.List;

import org.fujion.annotation.Option;

/**
 * Options for sunburst plot.
 * <p>
 * A sunburst displays hierarchical data, where a level in the hierarchy is represented by a circle.
 * The center represents the root node of the tree. The visualization bears a resemblance to both
 * tree map and pie charts.
 */
public class PlotSunburst extends PlotOptions {

    /**
     * When enabled the user can click on a point which is a parent and zoom in on its children.
     * Defaults to false.
     */
    @Option
    public Boolean allowDrillToNode;
    
    /**
     * The center of the sunburst chart relative to the plot area. Can be percentages or pixel
     * values. Defaults to undefined.
     */
    @Option
    public List<String> center = new ArrayList<>();

    /**
     * Used together with the levels and allowDrillToNode options. When set to false the first level
     * visible when drilling is considered to be level one. Otherwise the level will be the same as
     * the tree structure. Defaults to true.
     */
    @Option
    public Boolean levelIsConstant;
    
    /**
     * Set options on specific levels. Takes precedence over series options, but not point options.
     */
    @Option
    public final List<LevelOptions> levels = new ArrayList<>();

    /**
     * How to interpret levelSize.value. percentage gives a width relative to result of outer radius
     * minus inner radius. pixels gives the ring a fixed width in pixels. weight takes the remaining
     * width after percentage and pixels, and distributes it accross all "weighted" levels. The
     * value relative to the sum of all weights determines the width. Defaults to weight.
     */
    @Option("levelSize.unit")
    public String levelSize_unit;

    /**
     * The value used for calculating the width of the ring. Its' affect is determined by
     * levelSize.unit. Defaults to 1.
     */
    @Option("levelSize.value")
    public Double levelSize_value;

    /**
     * Which point to use as a root in the visualization. Defaults to undefined.
     */
    @Option
    public String rootId;
    
    /**
     * If a point is sliced, moved out from the center, how many pixels should it be moved?.
     * Defaults to 10.
     */
    @Option
    public Integer slicedOffset;

    /**
     * The start angle of the pie slices in degrees where 0 is top and 90 right. Defaults to 0.
     */
    @Option
    public Double startAngle;
}
