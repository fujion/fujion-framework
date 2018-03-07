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

import org.fujion.annotation.JavaScript;
import org.fujion.chartjs.common.BubblePoint;

/**
 * Options for bubble plots.
 */
public class PlotBubble extends PlotOptions {

    /**
     * The points to plot.
     */
    public BubblePoint[] data;

    /**
     * The bubble additional radius for hit detection as an array.
     */
    public Integer[] hitRadius$array;

    /**
     * The bubble additional radius for hit detection as a function.
     */
    @JavaScript
    public String hitRadius$function;

    /**
     * The bubble additional radius for hit detection as a single value.
     * <p>
     * Default: 1
     */
    public Integer hitRadius$number;

    /**
     * The bubble background color when hovered as an array.
     */
    public String[] hoverBackgroundColor$array;

    /**
     * The bubble background color when hovered as a function.
     */
    @JavaScript
    public String hoverBackgroundColor$function;

    /**
     * The bubble background color when hovered as a single value.
     */
    public String hoverBackgroundColor$string;

    /**
     * The bubble border color when hovered as an array.
     */
    public String[] hoverBorderColor$array;

    /**
     * The bubble border color when hovered as a function.
     */
    @JavaScript
    public String hoverBorderColor$function;

    /**
     * The bubble border color when hovered as a single value.
     */
    public String hoverBorderColor$string;

    /**
     * The bubble border width when hovered as an array.
     */
    public Integer[] hoverBorderWidth$array;

    /**
     * The bubble border width when hovered as a function.
     */
    @JavaScript
    public String hoverBorderWidth$function;
    
    /**
     * The bubble border width when hovered as a single value.
     * <p>
     * Default: 1
     */
    public Integer hoverBorderWidth$number;

    /**
     * The bubble additional radius when hovered as an array.
     */
    public Integer[] hoverRadius$array;

    /**
     * The bubble additional radius when hovered as a function.
     */
    @JavaScript
    public String hoverRadius$function;
    
    /**
     * The bubble additional radius when hovered as a single value.
     * <p>
     * Default: 4
     */
    public Integer hoverRadius$number;

    /**
     * The label text.
     */
    public String label;

    /**
     * The bubble shape style.
     */
    public PointStyleEnum[] pointStyle$array;
    
    /**
     * The bubble shape style.
     */
    public PointStyleEnum pointStyle$enum;
    
    /**
     * The bubble radius as an array.
     */
    public Integer[] radius$array;
    
    /**
     * The bubble radius as a function.
     */
    @JavaScript
    public String radius$function;
    
    /**
     * The bubble radius as a single value.
     * <p>
     * Default: 3
     */
    public Integer radius$number;
}