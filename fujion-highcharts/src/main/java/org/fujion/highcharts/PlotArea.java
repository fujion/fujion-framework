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

/**
 * Options for area series.
 */
public class PlotArea extends PlotOptions {

    /**
     * Fill color or gradient for the area. When null, the series' color is used with the series'
     * fillOpacity. Defaults to null.
     */
    public String fillColor;

    /**
     * Fill opacity for the area. Defaults to .75.
     */
    public Double fillOpacity;

    /**
     * A separate color for the graph line. By default the line takes the color of the series, but
     * the lineColor setting allows setting a separate color for the line without altering the
     * fillColor. Defaults to null.
     */
    public String lineColor;

    /**
     * A separate color for the negative part of the area.
     */
    public String negativeFillColor;

    /**
     * Whether the whole area or just the line should respond to mouseover tooltips and other mouse
     * or touch events. Defaults to false.
     */
    public Boolean trackByArea;

}
