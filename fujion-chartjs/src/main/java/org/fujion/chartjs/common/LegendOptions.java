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

/**
 * Options for controlling legend display.
 */
public class LegendOptions extends Options {
    
    /**
     * If true, the legend is shown.
     * <p>
     * Default: true;
     */
    @Option
    public Boolean display;
    
    /**
     * Marks that this box should take the full width of the canvas (pushing down other boxes).
     * <p>
     * Default: true
     */
    @Option
    public Boolean fullWidth;
    
    /**
     * Legend label options.
     */
    @Option
    public final LegendLabelOptions labels = new LegendLabelOptions();

    /**
     * A callback that is called when a click event is registered on a label item.
     */
    @Option(convertTo = JavaScript.class)
    public String onClick;

    /**
     * A callback that is called when a 'mousemove' event is registered on top of a label item.
     */
    @Option(convertTo = JavaScript.class)
    public String onHover;

    /**
     * Position of the legend.
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
}
