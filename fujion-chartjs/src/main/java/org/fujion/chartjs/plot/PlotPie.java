/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2023 Fujion Framework
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
 * Options for pie plots.
 */
public class PlotPie extends PlotOptions {

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
     * Default: false
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
     * Sweep to allow arcs to cover.
     * <p>
     * Default: 0 degrees
     */
    @Option
    public Double circumference;

    /**
     * The percentage of the chart that is cut out of the middle.
     * <p>
     * Default: 0 for pie chart, 50 for doughnut.
     */
    @Option
    public Integer cutout$pixels;

    /**
     * The # pixels of the chart that is cut out of the middle.
     * <p>
     * Default: 0 for pie chart, 50 for doughnut.
     */
    @Option(convertUsing = "value + '%'")
    public Integer cutout$percent;

    /**
     * The data points.
     */
    @Option
    public double[] data;

    /**
     * The background color when hovered as a single value.
     */
    @Option
    public String hoverBackgroundColor;

    /**
     * The background color when hovered as an array.
     */
    @Option
    public String[] hoverBackgroundColor$array;
    
    /**
     * The background color when hovered as a function.
     */
    @Option(convertTo = JavaScript.class)
    public String hoverBackgroundColor$function;

    /**
     * Arc offset when hovered (in pixels).
     * <p>
     * Default: 0
     */
    @Option
    public Integer hoverOffset;

    /**
     * Arc offset when hovered (in pixels).
     */
    @Option
    public int[] hoverOffset$array;

    /**
     * Arc offset when hovered (in pixels).
     */
    @Option(convertTo = JavaScript.class)
    public String hoverOffset$function;

    /**
     * Arc offset (in pixels).
     */
    @Option
    public Integer offset;

    /**
     * Arc offset (in pixels).
     */
    @Option
    public int[] offset$array;

    /**
     * Arc offset (in pixels).
     */
    @Option(convertTo = JavaScript.class)
    public String offset$function;

    /**
     * The outer radius of the chart in pixels.
     */
    @Option
    public Integer radius$pixels;

    /**
     * The outer radius of the chart as a percentage of the maximum radius.
     * <p>
     * Default: 100
     */
    @Option(convertUsing = "value + '%'")
    public Integer radius$percent;

    /**
     * Starting angle to draw arcs from.
     * <p>
     * Default: 0
     */
    @Option
    public Double rotation;

    /**
     * The relative thickness of the dataset. Providing a value for weight will cause the pie or doughnut dataset
     * to be drawn with a thickness relative to the sum of all the dataset weight values.
     * <p>
     * Default: 1
     */
    @Option
    public Integer weight;
}
