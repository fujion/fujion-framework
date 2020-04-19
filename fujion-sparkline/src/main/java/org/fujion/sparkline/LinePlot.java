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
package org.fujion.sparkline;

import java.util.Map;

import org.fujion.annotation.Option;

public class LinePlot extends AbstractPlot {
    
    /**
     * If true then the y values supplied to plot will be clipped to fall between chartRangeMin and
     * chartRangeMax. By default, chartRangeMin/Max just ensure that the chart spans at least that
     * range of values, but do not constrain it.
     */
    @Option
    public Boolean chartRangeClip;
    
    /**
     * Specifies the maximum value to use for the X value of the chart.
     */
    @Option
    public Double chartRangeMaxX;
    
    /**
     * Specifies the minimum value to use for the X value of the chart.
     */
    @Option
    public Double chartRangeMinX;
    
    /**
     * Defaults pixel width for each value in the chart.
     * <p>
     * Default: 3
     */
    @Option
    public Integer defaultPixelsPerValue;
    
    /**
     * By default the normal range is drawn behind the fill area of the chart. Setting this option
     * to true causes it to be drawn over the top of the fill area.
     */
    @Option
    public Boolean drawNormalOnTop;
    
    /**
     * Specifies a color for the vertical line that appears through a value when moused over.
     * <p>
     * Default: "#f22"
     */
    @Option
    public String highlightLineColor;
    
    /**
     * Specifies a color for the spot that appears on a value when moused over.
     * <p>
     * Default: "#f5f"
     */
    @Option
    public String highlightSpotColor;
    
    /**
     * Line width in pixels.
     * <p>
     * Default: 1
     */
    @Option
    public Integer lineWidth;
    
    /**
     * The CSS color of the marker displayed for the maximum value. Set to an empty string to hide
     * it.
     */
    @Option
    public String maxSpotColor;
    
    /**
     * The CSS color of the marker displayed for the minimum value. Set to an empty string to hide
     * it.
     */
    @Option
    public String minSpotColor;
    
    /**
     * Threshold value below which to draw a bar to denote the "normal" or expected range of values.
     * For example the green bar here might denote a normal operating temperature range
     */
    @Option
    public Double normalRangeMax;
    
    /**
     * Threshold value above which to draw a bar to denote the "normal" or expected range of values.
     * For example the green bar here might denote a normal operating temperature range
     */
    @Option
    public Double normalRangeMin;
    
    /**
     * The CSS color of the final value marker. Set to an empty string to hide it.
     */
    @Option
    public String spotColor;
    
    /**
     * Radius of all spot markers, in pixels. Default: 1.5
     */
    @Option
    public Double spotRadius;
    
    /**
     * Specifies which points to draw spots on, and with which color. Accepts a range. For example,
     * to render green spots on all values less than 50 and red on values higher use {':49': 'green,
     * '50:': 'red'}.
     */
    @Option
    public Map<String, String> valueSpots;
    
    /**
     * By default the values supplied to line charts are presumed to be y values mapping on to
     * sequential (integer) x values. If you need to supply x values explicitly, do so here.
     */
    @Option("xvalues")
    double[] xvalues$double;
    
    /**
     * By default the values supplied to line charts are presumed to be y values mapping on to
     * sequential (integer) x values. If you need to supply x values explicitly, do so here.
     */
    @Option("xvalues")
    int[] xvalues$int;
    
    protected LinePlot() {
        super(SparklineType.LINE);
    }

}
