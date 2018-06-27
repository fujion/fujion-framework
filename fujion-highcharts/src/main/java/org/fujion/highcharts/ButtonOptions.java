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

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for buttons (e.g., print or export buttons).
 */
public class ButtonOptions extends Options {
    
    /**
     * Alignment for the buttons. Defaults to "right".
     */
    @Option
    public AlignHorizontal align;
    
    /**
     * The pixel spacing between buttons. Defaults to 3.
     */
    @Option
    public Integer buttonSpacing;
    
    /**
     * The CSS class name of the context button. Defaults to highcharts-contextbutton.
     */
    @Option
    public String className;

    /**
     * Whether to enable buttons. Defaults to true.
     */
    @Option
    public Boolean enabled;
    
    /**
     * Pixel height of the buttons. Defaults to 20.
     */
    @Option
    public Integer height;
    
    /**
     * The class name of the menu appearing from the button. Defaults to highcharts-contextmenu.
     */
    @Option
    public String menuClassName;
    
    /**
     * A collection of config options for the menu items. Each options object consists of a text
     * option which is a string to show in the menu item, as well as an onclick parameter which is a
     * callback function to run on click. By default, there is one menu item for each of the
     * available export types. Menu items can be customized by defining a new array of items and
     * assigning null to unwanted positions.
     */
    @Option
    public final List<ActionOptions> menuItems = new ArrayList<>();
    
    /**
     * A click handler callback to use on the button directly instead of the default. The "this"
     * variable will be the Highcharts object.
     */
    @Option(convertTo = JavaScript.class)
    public String onclick;
    
    /**
     * The symbol for the button. Points to a definition function in the Highcharts.Renderer.symbols
     * collection. The default exportIcon function is part of the exporting module. Defaults to
     * menu.
     */
    @Option
    public String symbol;
    
    /**
     * Defaults to #666666.
     */
    @Option
    public String symbolFill;
    
    /**
     * The pixel size of the symbol on the button. Defaults to 14.
     */
    @Option
    public Integer symbolSize;
    
    /**
     * The color of the symbol's stroke or line. Defaults to #666666.
     */
    @Option
    public String symbolStroke;
    
    /**
     * The pixel stroke width of the symbol on the button. Defaults to 1.
     */
    @Option
    public Integer symbolStrokeWidth;
    
    /**
     * The x position of the center of the symbol inside the button. Defaults to 12.5.
     */
    @Option
    public Double symbolX;
    
    /**
     * The y position of the center of the symbol inside the button. Defaults to 10.5.
     */
    @Option
    public Double symbolY;
    
    /**
     * A text string to add to the individual button. Defaults to null.
     */
    @Option
    public String text;
    
    /**
     * The vertical alignment of the buttons. Can be one of "top", "middle" or "bottom". Defaults to
     * "top".
     */
    @Option
    public AlignVertical verticalAlign;
    
    /**
     * The pixel width of the button. Defaults to 24.
     */
    @Option
    public Integer width;
    
    /**
     * The horizontal position of the button relative to the align option. Defaults to -10.
     */
    @Option
    public Integer x;
    
    /**
     * The vertical offset of the button's position relative to its verticalAlign. Defaults to 0.
     */
    @Option
    public Integer y;
    
}
