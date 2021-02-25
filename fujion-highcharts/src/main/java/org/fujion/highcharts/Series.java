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

import org.fujion.ancillary.OptionMap;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * A chart series.
 */
public class Series extends Options {
    
    /**
     * A list of data points. Range series values are given by low and high. Example:
     *
     * <pre>
     * data: [{
     *     name: 'Point 1',
     *     color: '#00FF00',
     *     y: 0
     * }, {
     *     name: 'Point 2',
     *     color: '#FF00FF',
     *     y: 5
     * }]
     * </pre>
     */
    @Option
    public final List<DataPoint> data = new ArrayList<>();
    
    /**
     * The index of the series in the chart, affecting the internal index in the chart.series array,
     * the visible Z index as well as the order in the legend. Defaults to undefined.
     */
    @Option
    public Integer index;
    
    /**
     * The sequential index of the series in the legend.
     */
    @Option
    public Integer legendIndex;
    
    /**
     * The name of the series as shown in the legend, tooltip etc. Defaults to "".
     */
    @Option
    public String name;
    
    /**
     * This option allows grouping series in a stacked chart. The stack option can be a string or a
     * number or anything else, as long as the grouped series' stack options match each other.
     * Defaults to null.
     */
    @Option
    public Object stack;
    
    /**
     * The type of series. Defaults to "line".
     */
    @Option
    public final PlotType type;
    
    /**
     * When using dual or multiple x axes, this number defines which xAxis the particular series is
     * connected to. It refers to the index of the axis in the xAxis array, with 0 being the first.
     * Defaults to 0.
     */
    @Option
    public Integer xAxis;
    
    /**
     * When using dual or multiple y axes, this number defines which yAxis the particular series is
     * connected to. It refers to the index of the axis in the yAxis array, with 0 being the first.
     * Defaults to 0.
     */
    @Option
    public Integer yAxis;
    
    /**
     * Additional options.
     */
    @Option(ignore = true)
    public final PlotOptions plotOptions;
    
    protected Series(PlotType type) {
        this.type = type;
        this.plotOptions = type.newInstance();
    }
    
    /**
     * Adds a single data point.
     *
     * @param y Y value
     * @return The newly added data point.
     */
    public DataPoint addDataPoint(double y) {
        DataPoint dp = new DataPoint();
        dp.y = y;
        data.add(dp);
        return dp;
    }
    
    /**
     * Adds a single data point.
     *
     * @param x X value
     * @param y Y value
     * @return The newly added data point.
     */
    public DataPoint addDataPoint(double x, double y) {
        DataPoint dp = addDataPoint(y);
        dp.x = x;
        return dp;
    }
    
    /**
     * Adds a list of data point values.
     *
     * @param values Data points to add.
     */
    public void addDataPoints(List<DataPoint> values) {
        data.addAll(values);
    }
    
    /**
     * Adds a list of y values.
     *
     * @param yvalues Y values to add.
     */
    public void addDataPoints(double... yvalues) {
        for (double y : yvalues) {
            addDataPoint(y);
        }
    }
    
    /**
     * Clear all data points.
     */
    public void clear() {
        data.clear();
    }
    
    /**
     * Override to merge plot options into series options.
     */
    @Override
    public OptionMap toMap() {
        OptionMap map = super.toMap();
        map.putAll(plotOptions.toMap());
        return map;
    }
}
