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
package org.fujion.sparkline;

import java.util.Map;

import org.fujion.annotation.Option;

/**
 * Options for bar plots.
 */
public class BarPlot extends AbstractPlot {
    
    /**
     * CSS color used for positive values.
     */
    @Option
    public String barColor;

    /**
     * Space between each bar, in pixels.
     */
    @Option
    public Integer barSpacing;

    /**
     * Width of each bar, in pixels.
     */
    @Option
    public Integer barWidth;

    /**
     * An array of values to specify a color for each individual bar. For example if your chart has
     * three values 1,3,1 you can set colorMap$array=["red", "green", "blue"]
     */
    @Option("colorMap")
    public String[] colorMap$array;

    /**
     * A range map to map specific values to selected colors. For example if you want all values of
     * -2 to appear yellow, set colorMap: { '-2': '#ff0' }.
     */
    @Option("colorMap")
    public Map<String, String> colorMap$map;

    /**
     * CSS color used for negative values.
     */
    @Option
    public String negBarColor;

    /**
     * CSS color used for values equal to null. By default null values are omitted entirely, but
     * setting this adds a thin marker for the entry - This can be useful if your chart is pretty
     * sparse; perhaps try setting it to a light grey or something equally unobtrusive.
     */
    @Option
    public String nullColor;

    /**
     * An array of colors to use for stacked bar charts. The first series will use the first value
     * in the array, the second series will use the second, etc.
     */
    @Option
    public String[] stackedBarColor;

    /**
     * Centers the y-axis at zero if true.
     * <p>
     * Default: true
     */
    @Option
    public Boolean zeroAxis;

    /**
     * CSS color used for values equal to zero.
     */
    @Option
    public String zeroColor;
    
    protected BarPlot() {
        super(SparklineType.BAR);
    }
    
}
