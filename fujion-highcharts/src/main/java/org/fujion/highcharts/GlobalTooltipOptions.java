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

import org.fujion.ancillary.JavaScript;
import org.fujion.annotation.Option;

/**
 * Global options for tooltip rendering.
 */
public class GlobalTooltipOptions extends TooltipOptions {

    /**
     * Enable or disable animation of the tooltip. In old browsers combined with data-heavy charts,
     * the animation may be slow, so turning it off can be a good idea. Defaults to true.
     */
    @Option
    public Boolean animation;
    
    /**
     * The background color or gradient for the tooltip. Defaults to "rgba(255, 255, 255, .85)".
     */
    @Option
    public String backgroundColor;
    
    /**
     * The color of the tooltip border. When null, the border takes the color of the corresponding
     * series or point. Defaults to "auto".
     */
    @Option
    public String borderColor;
    
    /**
     * The radius of the rounded border corners. Defaults to 5.
     */
    @Option
    public Integer borderRadius;
    
    /**
     * The pixel width of the tooltip border. Defaults to 2.
     */
    @Option
    public Integer borderWidth;
    
    /**
     * Display crosshairs to connect the points with their corresponding axis values. The crosshairs
     * can be defined as a boolean, an array of booleans or an object.
     * <ul>
     * <li><b>Boolean</b> - If the crosshairs option is true, a single crosshair relating to the x
     * axis will be shown.</li>
     * <li><b>Array of booleans</b> - In an array of booleans, the first value turns on the x axis
     * crosshair and the second value to the y axis crosshair. Use [true, true] to show complete
     * crosshairs.</li>
     * <li><b>Array of objects</b> - In an array of objects, the first value applies to the x axis
     * crosshair and the second value to the y axis crosshair. For each dimension, a width, color,
     * dashStyle and zIndex can be given.</li>
     * </ul>
     * Defaults to null.
     */
    @Option
    public Object crosshairs;
    
    /**
     * Enable or disable the tooltip. Defaults to true.
     */
    @Option
    public Boolean enabled;
    
    /**
     * Callback function to format the text of the tooltip.
     */
    @Option(convertTo = JavaScript.class)
    public String formatter;
    
    /**
     * A callback function to place the tooltip in a default position.
     */
    @Option(convertTo = JavaScript.class)
    public String positioner;
    
    /**
     * Whether to apply a drop shadow to the tooltip. Defaults to true.
     */
    @Option
    public Boolean shadow;
    
    /**
     * The name of a symbol to use for the border around the tooltip. Defaults to callout.
     */
    @Option
    public String shape;

    /**
     * When the tooltip is shared, the entire plot area will capture mouse movement, and tooltip
     * texts for all series will be shown in a single bubble. This is recommended for single series
     * charts and for iPad optimized sites. Defaults to false.
     */
    @Option
    public Boolean shared;
    
    /**
     * Proximity snap for graphs or single points. Does not apply to bars, columns and pie slices.
     * It defaults to 10 for mouse-powered devices and 25 for touch devices. Defaults to 10/25.
     */
    @Option
    public Integer snap;

    /**
     * CSS styles for the tooltip. The tooltip can also be styled through the CSS class
     * .highcharts-tooltip. Default value:
     *
     * <pre>
     * style: {
     *     color: '#333333',
     *     fontSize: '9pt',
     *     padding: '5px'
     * }
     * </pre>
     */
    @Option
    public final StyleOptions style = new StyleOptions();
    
    /**
     * Use HTML to render the contents of the tooltip instead of SVG. Using HTML allows advanced
     * formatting like tables and images in the tooltip. It is also recommended for rtl languages as
     * it works around rtl bugs in early Firefox. Defaults to false.
     */
    @Option
    public Boolean useHTML;
    
}
