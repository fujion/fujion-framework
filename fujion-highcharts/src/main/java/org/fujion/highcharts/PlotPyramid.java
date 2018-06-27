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
package org.fujion.highcharts;

import java.util.ArrayList;
import java.util.List;

import org.fujion.annotation.Option;

/**
 * Options for pyramid series.
 * <p>
 * A pyramid series is a special type of funnel, without neck and reversed by default.
 */
public class PlotPyramid extends PlotOptions {

    /**
     * The center of the series. By default, it is centered in the middle of the plot area, so it
     * fills the plot area height. Defaults to ["50%", "50%"].
     */
    @Option
    public final List<String> center = new ArrayList<>();

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
     * The height of the funnel or pyramid. If it is a number it defines the pixel height, if it is
     * a percentage string it is the percentage of the plot area height. Defaults to 100%.
     */
    @Option
    public String height;

    /**
     * The size of the inner diameter for the pie. A size greater than 0 renders a donut chart. Can
     * be a percentage or pixel value. Percentages are relative to the pie size. Pixel values are
     * given as integers. Note: in Highcharts &lt; 4.1.2, the percentage was relative to the plot
     * area, not the pie size. Defaults to 0.
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
     * The pyramid neck width is zero by default, as opposed to the funnel, which shares the same
     * layout logic. Defaults to 0%.
     */
    @Option
    public String neckHeight;

    /**
     * The pyramid neck width is zero by default, as opposed to the funnel, which shares the same
     * layout logic. Defaults to 0%.
     */
    @Option
    public String neckWidth;
    
    /**
     * The pyramid is reversed by default, as opposed to the funnel, which shares the layout engine,
     * and is not reversed. Defaults to true.
     */
    @Option
    public Boolean reversed;

    /**
     * The start angle of the pie slices in degrees where 0 is top and 90 right. Defaults to 0.
     */
    @Option
    public Integer startAngle;

    /**
     * The width of the funnel compared to the width of the plot area, or the pixel width if it is a
     * number. Defaults to 90%.
     */
    @Option
    public String width;
}
