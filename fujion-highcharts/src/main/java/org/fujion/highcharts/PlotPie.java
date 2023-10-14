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
 * Options for pie chart.
 * <p>
 * A pie chart is a circular graphic which is divided into slices to illustrate numerical
 * proportion.
 */
public class PlotPie extends PlotOptions {

    /**
     * The thickness of a 3D pie. Requires highcharts-3d.js Defaults to 0.
     */
    @Option
    public Integer depth;
    
    /**
     * The end angle of the pie in degrees where 0 is top and 90 is right. Defaults to startAngle
     * plus 360. Defaults to null.
     */
    @Option
    public Integer endAngle;

    /**
     * The size of the inner diameter for the pie. A size greater than 0 renders a doughnut chart.
     * Can be a percentage or pixel value. Percentages are relative to the size of the plot area.
     * Pixel values are given as integers. Defaults to 0.
     */
    @Option
    public String innerSize;

    /**
     * The minimum size for a pie in response to auto margins. The pie will try to shrink to make
     * room for data labels in side the plot area, but only to this size. Defaults to 80.
     */
    @Option
    public Integer minSize;

    /**
     * The start angle of the pie slices in degrees where 0 is top and 90 right. Defaults to 0.
     */
    @Option
    public Integer startAngle;
    
}
