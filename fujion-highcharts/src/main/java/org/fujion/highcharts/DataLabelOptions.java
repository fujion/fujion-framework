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

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for data labels.
 */
public class DataLabelOptions extends Options {

    /**
     * The alignment of the data label compared to the point. Can be one of "left", "center" or
     * "right". Defaults to "center".
     */
    @Option
    public AlignHorizontal align;

    /**
     * Whether to allow data labels to overlap. To make the labels less sensitive for overlapping,
     * the dataLabels.padding can be set to 0. Defaults to false.
     */
    @Option
    public Boolean allowOverlap;

    /**
     * The background color or gradient for the data label. Defaults to undefined.
     */
    @Option
    public String backgroundColor;

    /**
     * The border color for the data label. Defaults to undefined.
     */
    @Option
    public String borderColor;

    /**
     * The border radius in pixels for the data label. Defaults to 0.
     */
    @Option
    public Integer borderRadius;

    /**
     * The border width in pixels for the data label. Defaults to 0.
     */
    @Option
    public Integer borderWidth;

    /**
     * A class name for the data label. Particularly in styled mode, this can be used to give each
     * series' or point's data label unique styling. In addition to this option, a default color
     * class name is added so that we can give the labels a contrast text shadow. Defaults to
     * undefined.
     */
    @Option
    public String className;
    
    /**
     * The text color for the data labels. Defaults to null.
     */
    @Option
    public String color;

    /**
     * Whether to hide data labels that are outside the plot area. By default, the data label is
     * moved inside the plot area according to the overflow option. Defaults to true.
     */
    @Option
    public Boolean crop;

    /**
     * Whether to defer displaying the data labels until the initial series animation has finished.
     * Defaults to true.
     */
    @Option
    public Boolean defer;
    
    /**
     * Enable or disable the data labels. Defaults to false.
     */
    @Option
    public Boolean enabled;

    /**
     * A format string for the data label. Available variables are the same as for formatter.
     * Defaults to {y}.
     */
    @Option
    public String format;

    /**
     * Callback JavaScript function to format the data label.
     */
    @Option(convertTo = JavaScript.class)
    public String formatter;

    /**
     * For points with an extent, like columns or map areas, whether to align the data label inside
     * the box or to the actual value point. Defaults to false in most cases, true in stacked
     * columns. Defaults to undefined.
     */
    @Option
    public Boolean inside;

    /**
     * How to handle overflowing labels on horizontal axis. Can be undefined or "justify". If
     * "justify", labels will not render outside the plot area. If there is room to move it, it will
     * be aligned to the edge, else it will be removed. Defaults to undefined.
     */
    @Option
    public String overflow;

    /**
     * When either the borderWidth or the backgroundColor is set, this is the padding within the
     * box. Defaults to 2. Defaults to 2.
     */
    @Option
    public Integer padding;

    /**
     * Rotation of the labels in degrees. Defaults to 0.
     */
    @Option
    public Integer rotation;

    /**
     * The shadow of the box. Works best with borderWidth or backgroundColor. The shadow can be an
     * object configuration containing color, offsetX, offsetY, opacity and width. Defaults to
     * false.
     */
    @Option
    public Object shadow;

    /**
     * The name of a symbol to use for the border around the label. Symbols are predefined functions
     * on the Renderer object. Defaults to square.
     */
    @Option
    public String shape;
    
    /**
     * CSS styles for the label.
     */
    @Option
    public final StyleOptions style = new StyleOptions();

    /**
     * Whether to use HTML to render the labels. Defaults to false.
     */
    @Option
    public Boolean useHTML;

    /**
     * The vertical alignment of a data label. Can be one of top, middle or bottom. The default
     * value depends on the data, for instance in a column chart, the label is above positive values
     * and below negative values. Defaults to bottom.
     */
    @Option
    public AlignVertical verticalAlign;

    /**
     * The x position offset of the label relative to the point. Defaults to 0.
     */
    @Option
    public Integer x;

    /**
     * The y position offset of the label relative to the point. Defaults to -6.
     */
    @Option
    public Integer y;
    
    /**
     * The Z index of the data labels. The default Z index puts it above the series. Use a Z index
     * of 2 to display it behind the series. Defaults to 6.
     */
    @Option
    public Integer zIndex;
}
