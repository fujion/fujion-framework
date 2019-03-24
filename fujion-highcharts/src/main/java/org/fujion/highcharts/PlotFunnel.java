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
package org.fujion.highcharts;

import java.util.ArrayList;
import java.util.List;

import org.fujion.annotation.Option;

/**
 * Options for funnel chart.
 * <p>
 * Funnel charts are a type of chart often used to visualize stages in a sales project, where the
 * top are the initial stages with the most clients. It requires that the modules/funnel.js file is
 * loaded.
 */
public class PlotFunnel extends PlotPie {

    /**
     * The center of the series. By default, it is centered in the middle of the plot area, so it
     * fills the plot area height. Defaults to ["50%", "50%"].
     */
    @Option
    public final List<String> center = new ArrayList<>();
    
    /**
     * The height of the neck, the lower part of a funnel. If it is a number it defines the pixel
     * height, if it is a percentage string it is the percentage of the plot area height.
     */
    @Option
    public String height;

    /**
     * The height of the neck, the lower part of the funnel. A number defines pixel width, a
     * percentage string defines a percentage of the plot area height. Defaults to 25%.
     */
    @Option
    public String neckHeight;

    /**
     * The width of the neck, the lower part of the funnel. A number defines pixel width, a
     * percentage string defines a percentage of the plot area width.
     */
    @Option
    public String neckWidth;

    /**
     * A reversed funnel has the widest area down. A reversed funnel with no neck width and neck
     * height is a pyramid. Defaults to false.
     */
    @Option
    public Boolean reversed;
    
    /**
     * The width of the funnel compared to the width of the plot area, or the pixel width if it is a
     * number. Defaults to 90%.
     */
    @Option
    public String width;
}
