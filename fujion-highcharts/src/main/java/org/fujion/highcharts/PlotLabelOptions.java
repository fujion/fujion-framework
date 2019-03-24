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

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Series labels are placed as close to the series as possible in a natural way, seeking to avoid
 * other series. The goal of this feature is to make the chart more easily readable, like if a human
 * designer placed the labels in the optimal position. The series labels currently work with series
 * types having a graph or an area. Requires the series-label.js module.
 */
public class PlotLabelOptions extends Options {
    
    /**
     * An array of boxes to avoid when laying out the labels. Defaults to undefined.
     */
    @Option
    public final List<Box> boxesToAvoid = new ArrayList<>();
    
    /**
     * Allow labels to be placed distant to the graph if necessary, and draw a connector line to the
     * graph. Defaults to true.
     */
    @Option
    public Boolean connectorAllowed;

    /**
     * If the label is closer than this to a neighbour graph, draw a connector. Defaults to 24.
     */
    @Option
    public Integer connectorNeighbourDistance;
    
    /**
     * Enable the series label per series. Defaults to true.
     */
    @Option
    public Boolean enabled;

    /**
     * For area-like series, allow the font size to vary so that small areas get a smaller font
     * size. The default applies this effect to area-like series but not line-like series. Defaults
     * to null.
     */
    @Option
    public Integer maxFontSize;
    
    /**
     * For area-like series, allow the font size to vary so that small areas get a smaller font
     * size. The default applies this effect to area-like series but not line-like series. Defaults
     * to null.
     */
    @Option
    public Integer minFontSize;

    /**
     * Draw the label on the area of an area series. By default it is drawn on the area. Set it to
     * false to draw it next to the graph instead. Defaults to null.
     */
    @Option
    public Boolean onArea;
    
    /**
     * Styles for the series label. The color defaults to the series color, or a contrast color if
     * onArea.
     */
    @Option
    public final StyleOptions style = new StyleOptions();
    
}
