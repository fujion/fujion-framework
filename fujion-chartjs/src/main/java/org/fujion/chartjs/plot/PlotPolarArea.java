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
package org.fujion.chartjs.plot;

import org.fujion.ancillary.JavaScript;
import org.fujion.annotation.Option;
import org.fujion.chartjs.enums.BorderAlignEnum;

/**
 * Options for polar area plots.
 */
public class PlotPolarArea extends PlotOptions {

    /**
     * If true, the chart will animate in with a rotation animation.
     * <p>
     * Default: true
     */
    @Option("animation.animateRotate")
    public Boolean animation_animateRotate;
    
    /**
     * If true, will animate scaling the chart from the center outwards.
     * <p>
     * Default: true
     */
    @Option("animation.animateScale")
    public Boolean animation_animateScale;

    /**
     * When CENTER, the borders of arcs next to each other will overlap.
     * When INNER, it is guaranteed that all borders will not overlap.
     * <p>
     * Default: CENTER
     */
    @Option
    public BorderAlignEnum borderAlign;

    /**
     * When CENTER, the borders of arcs next to each other will overlap.
     * When INNER, it is guaranteed that all borders will not overlap.
     * <p>
     * Default: CENTER
     */
    @Option
    public BorderAlignEnum[] borderAlign$array;

    /**
     * When CENTER, the borders of arcs next to each other will overlap.
     * When INNER, it is guaranteed that all borders will not overlap.
     * <p>
     * Default: CENTER
     */
    @Option(convertTo = JavaScript.class)
    public String borderAlign$function;

    /**
     * The data points.
     */
    @Option
    public double[] data;
    
}
