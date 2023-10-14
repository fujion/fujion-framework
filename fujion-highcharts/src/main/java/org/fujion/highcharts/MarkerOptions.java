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

import org.fujion.ancillary.OptionMap;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for point markers.
 */
public class MarkerOptions extends Options {

    /**
     * Enable or disable the point marker. Defaults to true.
     */
    @Option
    public Boolean enabled;

    /**
     * The threshold for how dense the point markers should be before they are hidden, given that
     * enabled is not defined. The number indicates the horizontal distance between the two closest
     * points in the series, as multiples of the marker.radius. In other words, the default value of
     * 2 means points are hidden if overlapping horizontally. Defaults to 2.
     */
    @Option
    public Integer enabledThreshold;

    /**
     * he fill color of the point marker. When null, the series' or point's color is used. Defaults
     * to null.
     */
    @Option
    public String fillColor;

    /**
     * Image markers only. Set the image width explicitly. When using this option, a width must also
     * be set. Defaults to null.
     */
    @Option
    public Integer height;
    
    /**
     * The color of the point marker's outline. When null, the series' or point's color is used.
     * Defaults to "#FFFFFF".
     */
    @Option
    public String lineColor;

    /**
     * The width of the point marker's outline. Defaults to 0.
     */
    @Option
    public Integer lineWidth;

    /**
     * The radius of the point marker. Defaults to 0.
     */
    @Option
    public Integer radius;

    /**
     * Marker states for hover.
     */
    @Option(ignore = true)
    public final MarkerStateOptions hover_state = new MarkerStateOptions();

    /**
     * Marker states for select.
     */
    @Option(ignore = true)
    public  final MarkerStateOptions select_state = new MarkerStateOptions();

    /**
     * Hover and select states.
     */
    @Option
    public final OptionMap states = new OptionMap();

    /**
     * A predefined shape or symbol for the marker. When null, the symbol is pulled from
     * options.symbols. Other possible values are "circle", "square", "diamond", "triangle" and
     * "triangle-down". Additionally, the URL to a graphic can be given on this form:
     * "url(graphic.png)". Defaults to null.
     */
    @Option
    public String symbol;

    /**
     * Image markers only. Set the image width explicitly. When using this option, a height must
     * also be set. Defaults to null.
     */
    @Option
    public Integer width;

    @Override
    public OptionMap toMap() {
        OptionMap map = super.toMap();
        states.clear();
        map.put("hover", hover_state.toMap());
        map.put("select", select_state.toMap());
        return map;
    }
}
