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
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * The legend is a box containing a symbol and name for each series item or point item in the chart.
 */
public class LegendOptions extends Options {
    
    /**
     * The horizontal alignment of the legend box within the chart area. Defaults to "center".
     */
    @Option
    public AlignHorizontal align;
    
    /**
     * The background color of the legend, filling the rounded corner border. Defaults to null.
     */
    @Option
    public String backgroundColor;
    
    /**
     * The color of the drawn border around the legend. Defaults to #999999.
     */
    @Option
    public String borderColor;
    
    /**
     * The border corner radius of the legend. Defaults to 0.
     */
    @Option
    public Integer borderRadius;
    
    /**
     * The width of the drawn border around the legend. Defaults to 0.
     */
    @Option
    public Integer borderWidth;
    
    /**
     * Enable or disable the legend. Defaults to true.
     */
    @Option
    public Boolean enabled;
    
    /**
     * When the legend is floating, the plot area ignores it and is allowed to be placed below it.
     * Defaults to false.
     */
    @Option
    public Boolean floating;
    
    /**
     * In a legend with horizontal layout, the itemDistance defines the pixel distance between each
     * item. Defaults to 20.
     */
    @Option
    public Integer itemDistance;
    
    /**
     * CSS styles for each legend item when the corresponding series or point is hidden.
     */
    @Option
    public final StyleOptions itemHiddenStyle = new StyleOptions();
    
    /**
     * CSS styles for each legend item in hover mode.
     */
    @Option
    public final StyleOptions itemHoverStyle = new StyleOptions();
    
    /**
     * The pixel bottom margin for each legend item. Defaults to 0.
     */
    @Option
    public Integer itemMarginBottom;
    
    /**
     * The pixel top margin for each legend item. Defaults to 0.
     */
    @Option
    public Integer itemMarginTop;
    
    /**
     * CSS styles for each legend item.
     */
    @Option
    public final StyleOptions itemStyle = new StyleOptions();
    
    /**
     * The width for each legend item. This is useful in a horizontal layout with many items when
     * you want the items to align vertically. . Defaults to null.
     */
    @Option
    public Integer itemWidth;
    
    /**
     * Enable/disable keyboard navigation for the legend. Requires the Accessibility module.
     * Defaults to true.
     */
    @Option("keyboardNavigation.enabled")
    public Boolean keyboardNavigation_enabled;

    /**
     * A format string for each legend label. Available variables relates to properties on the
     * series, or the point in case of pies. Defaults to {name}.
     */
    @Option
    public String labelFormat;
    
    /**
     * Callback function to format each of the series' labels. The this keyword refers to the series
     * object, or the point object in case of pie charts. By default the series or point name is
     * printed. Defaults to undefined.
     */
    @Option(convertTo = JavaScript.class)
    public String labelFormatter;
    
    /**
     * The layout of the legend items. Can be one of "horizontal" or "vertical". Defaults to
     * "horizontal".
     */
    @Option
    public Orientation layout;
    
    /**
     * If the plot area sized is calculated automatically and the legend is not floating, the legend
     * margin is the space between the legend and the axis labels or plot area. Defaults to 12.
     */
    @Option
    public Integer margin;
    
    /**
     * Maximum pixel height for the legend. When the maximum height is extended, navigation will
     * show. Defaults to undefined.
     */
    @Option
    public Integer maxHeight;
    
    /**
     * Options for the paging or navigation appearing when the legend has overflowed.
     */
    @Option
    public final LegendNavigationOptions navigation = new LegendNavigationOptions();
    
    /**
     * The inner padding of the legend box. Defaults to 8.
     */
    @Option
    public Integer padding;
    
    /**
     * Whether to reverse the order of the legend items compared to the order of the series or
     * points as defined in the configuration object. Defaults to false.
     */
    @Option
    public Boolean reversed;
    
    /**
     * Whether to show the symbol on the right side of the text rather than the left side. This is
     * common in Arabic and Hebraic. Defaults to false.
     */
    @Option
    public Boolean rtl;
    
    /**
     * Whether to apply a drop shadow to the legend. A backgroundColor also needs to be applied for
     * this to take effect. Defaults to no shadow.
     */
    @Option
    public ShadowOptions shadow;
    
    /**
     * When this is true, the legend symbol width will be the same as the symbol height, which in
     * turn defaults to the font size of the legend items. Defaults to true.
     */
    @Option
    public Boolean squareSymbol;

    /**
     * CSS styles for the legend area. The position is determined by properties like align,
     * verticalAlign, x and y, but the styles are still parsed for backwards compatibility. Defaults
     * to undefined.
     */
    @Option
    public final StyleOptions style = new StyleOptions();

    /**
     * The pixel height of the symbol for series types that use a rectangle in the legend. Defaults
     * to the font size of legend items. Defaults to undefined.
     */
    @Option
    public Integer symbolHeight;
    
    /**
     * The pixel padding between the legend item symbol and the legend item text. Defaults to 5.
     */
    @Option
    public Integer symbolPadding;
    
    /**
     * The border radius of the symbol for series types that use a rectangle in the legend. Defaults
     * to half the symbolHeight. Defaults to undefined.
     */
    @Option
    public Integer symbolRadius;

    /**
     * The pixel width of the legend item symbol. When the squareSymbol option is set, this defaults
     * to the symbolHeight, otherwise 16. Defaults to undefined.
     */
    @Option
    public Integer symbolWidth;
    
    /**
     * A title to be added on top of the legend.
     */
    @Option
    public final LegendTitleOptions title = new LegendTitleOptions();
    
    /**
     * Whether to use HTML to render the legend item texts. When using HTML, legend.navigation is
     * disabled.
     */
    @Option
    public Boolean useHTML;
    
    /**
     * The vertical alignment of the legend box. Can be one of "top", "middle" or "bottom". Vertical
     * position can be further determined by the y option. Defaults to "bottom".
     */
    @Option
    public AlignVertical verticalAlign;
    
    /**
     * The width of the legend box, not including style.padding. . Defaults to null.
     */
    @Option
    public Integer width;
    
    /**
     * The x offset of the legend relative to its horizontal alignment align within
     * chart.spacingLeft and chart.spacingRight. Negative x moves it to the left, positive x moves
     * it to the right. Defaults to 0.
     */
    @Option
    public Integer x;
    
    /**
     * The vertical offset of the legend relative to it's vertical alignment verticalAlign within
     * chart.spacingTop and chart.spacingBottom. Negative y moves it up, positive y moves it down.
     * Defaults to 0.
     */
    @Option
    public Integer y;
}
