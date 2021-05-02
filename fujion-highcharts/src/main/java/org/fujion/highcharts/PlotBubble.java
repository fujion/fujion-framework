/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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
 * Options for bubble series.
 * <p>
 * A bubble series is a three dimensional series type where each point renders an X, Y and Z value.
 * Each points is drawn as a bubble where the position along the X and Y axes mark the X and Y
 * values, and the size of the bubble relates to the Z value. Requires highcharts-more.js.
 */
public class PlotBubble extends PlotOptions {
    
    /**
     * Whether to display negative sized bubbles. The threshold is given by the zThreshold option,
     * and negative bubbles can be visualized by setting negativeColor. Defaults to true.
     */
    @Option
    public Boolean displayNegative;
    
    /**
     * Maximum bubble size. Bubbles will automatically size between the minSize and maxSize to
     * reflect the z value of each bubble. Can be either pixels (when no unit is given), or a
     * percentage of the smallest one of the plot width and height. Defaults to 20%.
     */
    @Option
    public String maxSize;
    
    /**
     * Minimum bubble size. Bubbles will automatically size between the minSize and maxSize to
     * reflect the z value of each bubble. Can be either pixels (when no unit is given), or a
     * percentage of the smallest one of the plot width and height. Defaults to 8.
     */
    @Option
    public String minSize;
    
    /**
     * Whether the bubble's value should be represented by the area or the width of the bubble. The
     * default, area, corresponds best to the human perception of the size of each bubble. Defaults
     * to area.
     */
    @Option
    public String sizeBy;
    
    /**
     * When this is true, the absolute value of z determines the size of the bubble. This means that
     * with the default zThreshold of 0, a bubble of value -1 will have the same size as a bubble of
     * value 1, while a bubble of value 0 will have a smaller size according to minSize. Defaults to
     * false.
     */
    @Option
    public Boolean sizeByAbsoluteValue;
    
    /**
     * When displayNegative is false, bubbles with lower Z values are skipped. When displayNegative
     * is true and a negativeColor is given, points with lower Z is colored. Defaults to 0.
     */
    @Option
    public Double zThreshold;
}
