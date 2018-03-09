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
package org.fujion.chartjs.plot;

import org.fujion.annotation.JavaScript;

/**
 * Options for pie plots.
 */
public class PlotPie extends PlotOptions {

    /**
     * If true, the chart will animate in with a rotation animation.
     * <p>
     * Default: true
     */
    public Boolean animation_animateRotate;
    
    /**
     * If true, will animate scaling the chart from the center outwards.
     * <p>
     * Default: false
     */
    public Boolean animation_animateScale;
    
    /**
     * Sweep to allow arcs to cover.
     * <p>
     * Default: 2 * pi
     */
    public Double circumference;
    
    /**
     * The percentage of the chart that is cut out of the middle.
     * <p>
     * Default: 0 for pie chart, 50 for doughnut.
     */
    public Integer cutoutPercentage;
    
    /**
     * The data points.
     */
    public double[] data;
    
    /**
     * The background color when hovered as an array.
     */
    public String[] hoverBackgroundColor$array;
    
    /**
     * The background color when hovered as a function.
     */
    @JavaScript
    public String hoverBackgroundColor$function;
    
    /**
     * The background color when hovered as a single value.
     */
    public String hoverBackgroundColor$string;

    /**
     * The border color when hovered as an array.
     */
    public String[] hoverBorderColor$array;
    
    /**
     * The border color when hovered as a function.
     */
    @JavaScript
    public String hoverBorderColor$function;
    
    /**
     * The border color when hovered as a single value.
     */
    public String hoverBorderColor$string;
    
    /**
     * The border width when hovered as an array.
     */
    public Integer[] hoverBorderWidth$array;
    
    /**
     * The border width when hovered as a function.
     */
    @JavaScript
    public String hoverBorderWidth$function;
    
    /**
     * The border width when hovered as a single value.
     * <p>
     * Default: 1
     */
    public Integer hoverBorderWidth$number;

    /**
     * Starting angle to draw arcs from.
     * <p>
     * Default: -0.5 * pi
     */
    public Double rotation;
}
