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
 * Options for axis labels.
 */
public class AxisLabelOptions extends Options {

    /**
     * What part of the string the given position is anchored to. Can be one of "left", "center" or
     * "right". In inverted charts, x axis label alignment and y axis alignment are swapped.
     * Defaults to "center".
     */
    @Option
    public AlignHorizontal align;

    /**
     * Enable or disable the axis labels. Defaults to true.
     */
    @Option
    public Boolean enabled;

    /**
     * Callback JavaScript function to format the label.
     */
    @Option(convertTo = JavaScript.class)
    public String formatter;

    /**
     * How to handle overflowing labels on horizontal axis. Can be undefined or "justify". If
     * "justify", labels will not render outside the plot area. If there is room to move it, it will
     * be aligned to the edge, else it will be removed. Defaults to undefined.
     */
    @Option
    public String overflow;

    /**
     * Rotation of the labels in degrees. Defaults to 0.
     */
    @Option
    public Integer rotation;

    /**
     * Horizontal axes only. The number of lines to spread the labels over to make room or tighter
     * labels. . Defaults to null.
     */
    @Option
    public Integer staggerLines;

    /**
     * To show only every n'th label on the axis, set the step to n. Setting the step to 2 shows
     * every other label. Defaults to null.
     */
    @Option
    public Integer step;

    /**
     * CSS styles for the label. Defaults to:
     *
     * <pre>
     *     color: '#6D869F',
     *     fontWeight: 'bold'
     * </pre>
     */
    @Option
    public final StyleOptions style = new StyleOptions();

    /**
     * The x position offset of the label relative to the tick position on the axis. Defaults to 0.
     */
    @Option
    public Integer x;

    /**
     * The y position offset of the label relative to the tick position on the axis. Defaults to 0.
     */
    @Option
    public Integer y;
}
