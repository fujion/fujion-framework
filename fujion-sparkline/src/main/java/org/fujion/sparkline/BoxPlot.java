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
package org.fujion.sparkline;

import org.fujion.annotation.Option;

public class BoxPlot extends AbstractPlot {
    
    /**
     * CSS fill color used for the box.
     */
    @Option
    public String boxFillColor;

    /**
     * CSS line color used to outline the box.
     */
    @Option
    public String boxLineColor;

    /**
     * If minvalue and maxvalue are set then the scale of the plot is fixed. By default minValue and
     * maxValue are deduced from the values supplied.
     */
    @Option
    public Double maxValue;

    /**
     * CSS color used to draw the median line.
     */
    @Option
    public String medianColor;

    /**
     * If minvalue and maxvalue are set then the scale of the plot is fixed. By default minValue and
     * maxValue are deduced from the values supplied.
     */
    @Option
    public Double minValue;

    /**
     * Set the inter-quartile range multiplier used to calculate values that qualify as an outlier.
     * <p>
     * Default: 1.5
     */
    @Option
    public Double outlierIQR;

    /**
     * CSS color used to draw the outlier circles.
     */
    @Option
    public String outlierLineColor;

    /**
     * If set to false, then the values supplied are used to calculate the box data points for you.
     * If true then you must pre-calculate the points (see below).
     * <p>
     * Default: false
     */
    @Option
    public Boolean raw;

    /**
     * If true, then outliers (values &gt; 1.5x the IQR) are marked with circles and the whiskers are
     * placed at Q1 and Q3 instead of the least and greatest value.
     * <p>
     * Default: true
     */
    @Option
    public Boolean showOutliers;

    /**
     * Radius in pixels to draw the outlier circles.
     */
    @Option
    public String spotRadius;

    /**
     * If set to a value, then a small crosshair is drawn at that point to represent a target value.
     */
    @Option
    public Double target;

    /**
     * CSS color used to draw the target crosshair.
     */
    @Option
    public String targetColor;

    /**
     * CSS color used to draw the whiskers.
     */
    @Option
    public String whiskerColor;

    /**
     * An array of values specifying which fields to display in a tooltip and in what order.
     */
    @Option
    public String[] tooltipFormatFieldlist;

    /**
     * Specifies which key holds the field name to reference above. For box plots this should be
     * "field".
     */
    @Option
    public String tooltipFormatFieldlistKey;

    protected BoxPlot() {
        super(SparklineType.BOX);
    }

}
