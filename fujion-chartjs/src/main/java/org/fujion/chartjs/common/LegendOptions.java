/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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
package org.fujion.chartjs.common;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.chartjs.enums.AlignmentEnum;
import org.fujion.chartjs.enums.PositionEnum;

/**
 * Options for controlling legend display.
 */
public class LegendOptions extends Options {

    /**
     * Legend label options.
     */
    @Option
    public final LegendLabelOptions labels = new LegendLabelOptions();

    /**
     * Alignment of the legend.
     * <p>
     * Default: CENTER
     */
    @Option
    public AlignmentEnum align;

    /**
     * If true, the legend is shown.
     * <p>
     * Default: true;
     */
    @Option
    public Boolean display;

    /**
     * Maximum height of the legend, in pixels
     */
    @Option
    public Integer maxHeight;

    /**
     * Maximum width of the legend, in pixels
     */
    @Option
    public Integer maxWidth;

    /**
     * Marks that this box should take the full width/height of the canvas (moving other boxes).
     * This is unlikely to need to be changed in day-to-day use.
     * <p>
     * Default: true
     */
    @Option
    public Boolean fullSize;

    /**
     * A callback that is called when a click event is registered on a label item.
     * <p>
     * Arguments: [event, legendItem, legend]
     */
    @Option(convertTo = JavaScript.class)
    public String onClick;

    /**
     * A callback that is called when a 'mousemove' event is registered on top of a label item.
     * <p>
     * Arguments: [event, legendItem, legend]
     */
    @Option(convertTo = JavaScript.class)
    public String onHover;

    /**
     * A callback that is called when a 'mousemove' event is registered outside of a previously hovered label item.
     * <p>
     * Arguments: [event, legendItem, legend]
     */
    @Option(convertTo = JavaScript.class)
    public String onLeave;

    /**
     * Position of the legend.
     * <p>
     * Default: TOP
     */
    @Option
    public PositionEnum position;

    /**
     * Legend will show datasets in reverse order.
     * <p>
     * Default: false
     */
    @Option
    public Boolean reverse;

    /**
     * Set to true for rendering the legends from right to left.
     *
     * Default: false
     */
    @Option
    public Boolean rtl;

    /**
     * Options for the legend title.
     */
    @Option
    public final TitleOptions title = new TitleOptions();
}
