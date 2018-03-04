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
package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.JavaScript;

/**
 * Options for controlling legend display.
 */
public class LegendOptions extends Options {

    /**
     * If true, the legend is shown.
     * <p>
     * Default: true;
     */
    public Boolean display;

    /**
     * Marks that this box should take the full width of the canvas (pushing down other boxes).
     * <p>
     * Default: true
     */
    public Boolean fullWidth;

    /**
     * Legend label options.
     */
    public final LegendLabelOptions labels = new LegendLabelOptions();
    
    /**
     * A callback that is called when a click event is registered on a label item.
     */
    @JavaScript
    public String onClick;
    
    /**
     * A callback that is called when a 'mousemove' event is registered on top of a label item.
     */
    @JavaScript
    public String onHover;
    
    /**
     * Position of the legend.
     */
    public PositionEnum position;
    
    /**
     * Legend will show datasets in reverse order.
     * <p>
     * Default: false
     */
    public Boolean reverse;
}
