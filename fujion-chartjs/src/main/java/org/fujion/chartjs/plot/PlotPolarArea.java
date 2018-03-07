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
package org.fujion.chartjs.plot;

/**
 * Options for polar area plots.
 */
public class PlotPolarArea extends PlotOptions {

    /**
     * If true, the chart will animate in with a rotation animation.
     * <p>
     * Default: true
     */
    public Boolean animation_animateRotate;
    
    /**
     * If true, will animate scaling the chart from the center outwards.
     * <p>
     * Default: true
     */
    public Boolean animation_animateScale;
    
    /**
     * The data points.
     */
    public double[] data;
    
    /**
     * The fill color of individual bars when hovered.
     */
    public String[] hoverBackgroundColor$array;
    
    /**
     * The fill color of all bars when hovered.
     */
    public String hoverBackgroundColor$string;
    
    /**
     * The stroke color of individual bars when hovered.
     */
    public String[] hoverBorderColor$array;
    
    /**
     * The stroke color of all bars when hovered.
     */
    public String hoverBorderColor$string;
    
    /**
     * The stroke width of individual bars when hovered.
     */
    public int[] hoverBorderWidth$array;
    
    /**
     * The stroke width of all bars when hovered.
     */
    public Integer hoverBorderWidth$number;
    
    /**
     * Starting angle to draw arcs for the first item in a dataset.
     * <p>
     * Default: 0.5 * pi
     */
    public Double startAngle;
}