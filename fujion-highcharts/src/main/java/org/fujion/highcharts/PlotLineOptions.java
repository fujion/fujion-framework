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

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * A line stretching across the plot area, marking a specific value on one of the axes.
 */
public class PlotLineOptions extends Options {
    
    /**
     * The color of the plot line. Defaults to null.
     */
    @Option
    public String color;
    
    /**
     * The dashing or dot style for the plot line. Defaults to Solid.
     */
    @Option
    public DashStyle dashStyle;
    
    /**
     * An id used for identifying the plot line in Axis.removePlotBand. Defaults to null.
     */
    @Option
    public String id;
    
    /**
     * The position of the line in axis units. Defaults to null.
     */
    @Option
    public Double value;
    
    /**
     * The width or thickness of the plot line. Defaults to null.
     */
    @Option
    public Integer width;
    
    /**
     * The z index of the plot line within the chart. Defaults to null.
     */
    @Option
    public Integer zIndex;
}
