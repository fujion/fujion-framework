/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
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
import org.fujion.annotation.JavaScript;

/**
 * Options for data labels.
 */
public class DataLabelOptions extends Options {
    
    /**
     * The alignment of the data label compared to the point. Can be one of "left", "center" or
     * "right". Defaults to "center".
     */
    public AlignHorizontal align;
    
    /**
     * Whether to allow data labels to overlap. To make the labels less sensitive for overlapping,
     * the dataLabels.padding can be set to 0. Defaults to false.
     */
    public Boolean allowOverlap;
    
    /**
     * The background color or gradient for the data label. Defaults to undefined.
     */
    public String backgroundColor;
    
    /**
     * The border color for the data label. Defaults to undefined.
     */
    public String borderColor;
    
    /**
     * The border radius in pixels for the data label. Defaults to 0.
     */
    public Integer borderRadius;
    
    /**
     * The border width in pixels for the data label. Defaults to 0.
     */
    public Integer borderWidth;
    
    /**
     * A class name for the data label. Particularly in styled mode, this can be used to give each
     * series' or point's data label unique styling. In addition to this option, a default color
     * class name is added so that we can give the labels a contrast text shadow. Defaults to
     * undefined.
     */
    public String className;

    /**
     * The text color for the data labels. Defaults to null.
     */
    public String color;
    
    /**
     * Whether to hide data labels that are outside the plot area. By default, the data label is
     * moved inside the plot area according to the overflow option. Defaults to true.
     */
    public Boolean crop;
    
    /**
     * Whether to defer displaying the data labels until the initial series animation has finished.
     * Defaults to true.
     */
    public Boolean defer;

    /**
     * Enable or disable the data labels. Defaults to false.
     */
    public Boolean enabled;
    
    /**
     * A format string for the data label. Available variables are the same as for formatter.
     * Defaults to {y}.
     */
    public String format;
    
    /**
     * Callback JavaScript function to format the data label.
     */
    @JavaScript
    public String formatter;
    
    /**
     * For points with an extent, like columns or map areas, whether to align the data label inside
     * the box or to the actual value point. Defaults to false in most cases, true in stacked
     * columns. Defaults to undefined.
     */
    public Boolean inside;
    
    /**
     * How to handle overflowing labels on horizontal axis. Can be undefined or "justify". If
     * "justify", labels will not render outside the plot area. If there is room to move it, it will
     * be aligned to the edge, else it will be removed. Defaults to undefined.
     */
    public String overflow;
    
    /**
     * When either the borderWidth or the backgroundColor is set, this is the padding within the
     * box. Defaults to 2. Defaults to 2.
     */
    public Integer padding;
    
    /**
     * Rotation of the labels in degrees. Defaults to 0.
     */
    public Integer rotation;
    
    /**
     * The shadow of the box. Works best with borderWidth or backgroundColor. The shadow can be an
     * object configuration containing color, offsetX, offsetY, opacity and width. Defaults to
     * false.
     */
    public Object shadow;
    
    /**
     * The name of a symbol to use for the border around the label. Symbols are predefined functions
     * on the Renderer object. Defaults to square.
     */
    public String shape;

    /**
     * CSS styles for the label.
     */
    public final StyleOptions style = new StyleOptions();
    
    /**
     * Whether to use HTML to render the labels. Defaults to false.
     */
    public Boolean useHTML;
    
    /**
     * The vertical alignment of a data label. Can be one of top, middle or bottom. The default
     * value depends on the data, for instance in a column chart, the label is above positive values
     * and below negative values. Defaults to bottom.
     */
    public AlignVertical verticalAlign;
    
    /**
     * The x position offset of the label relative to the point. Defaults to 0.
     */
    public Integer x;
    
    /**
     * The y position offset of the label relative to the point. Defaults to -6.
     */
    public Integer y;

    /**
     * The Z index of the data labels. The default Z index puts it above the series. Use a Z index
     * of 2 to display it behind the series. Defaults to 6.
     */
    public Integer zIndex;
}
