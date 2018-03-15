/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * A single data point.
 */
public class DataPoint extends Options implements Comparable<DataPoint> {

    /**
     * Individual color for the point. Defaults to null.
     */
    @Option
    public String color;

    /**
     * Individual data label for each point.
     *
     * @see DataLabelOptions
     */
    @Option
    public final DataLabelOptions dataLabels = new DataLabelOptions();

    /**
     * An id for the point. Defaults to null.
     */
    @Option
    public String id;

    /**
     * Pies only. The sequential index of the pie slice in the legend. Defaults to undefined.
     */
    @Option
    public Integer legendIndex;

    /**
     * Marker options.
     */
    @Option
    public final MarkerOptions marker = new MarkerOptions();

    /**
     * The name of the point as shown in the legend, tooltip, dataLabel etc. Defaults to "".
     */
    @Option
    public String name;

    /**
     * Pie series only. Whether to display a slice offset from the center. Defaults to false.
     */
    @Option
    public Boolean sliced;

    /**
     * The x value of the point. Defaults to null.
     */
    @Option
    public Double x;

    /**
     * The x value of the point. Defaults to null.
     */
    @Option
    public Double y;

    /**
     * Sort by x value, then y value.
     */
    @Override
    public int compareTo(DataPoint dp) {
        int result = compare(x, dp.x);
        return result == 0 ? compare(y, dp.y) : result;
    }

    private int compare(Double d1, Double d2) {
        return d1 == d2 ? 0 : d1 == null ? -1 : d2 == null ? 1 : d1.compareTo(d2);
    }
}
