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
package org.fujion.highcharts;

import org.fujion.annotation.Option;

/**
 * Options for stream graph.
 * <p>
 * A stream graph is a type of stacked area graph which is displaced around a central axis,
 * resulting in a flowing, organic shape.
 */
public class PlotStreamGraph extends PlotOptions {

    /**
     * Fill color or gradient for the area. When null, the series' color is used with the series'
     * fillOpacity. In styled mode, the fill color can be set with the .highcharts-area class name.
     * Defaults to null.
     */
    @Option
    public String fillColor;

    /**
     * Fill opacity for the area. When you set an explicit fillColor, the fillOpacity is not
     * applied. Instead, you should define the opacity in the fillColor with an rgba color
     * definition. The fillOpacity setting, also the default setting, overrides the alpha component
     * of the color setting. In styled mode, the fill opacity can be set with the .highcharts-area
     * class name. Defaults to 1.
     */
    @Option
    public Double fillOpacity;
    
    /**
     * A separate color for the graph line. By default the line takes the color of the series, but
     * the lineColor setting allows setting a separate color for the line without altering the
     * fillColor. In styled mode, the line stroke can be set with the .highcharts-graph class name.
     * Defaults to null.
     */
    @Option
    public String lineColor;

    /**
     * A separate color for the negative part of the area. In styled mode, a negative color is set
     * with the .highcharts-negative class name. Defaults to undefined.
     */
    @Option
    public String negativeFillColor;
    
    /**
     * Whether the whole area or just the line should respond to mouseover tooltips and other mouse
     * or touch events. Defaults to false.
     */
    @Option
    public Boolean trackByArea;
}
