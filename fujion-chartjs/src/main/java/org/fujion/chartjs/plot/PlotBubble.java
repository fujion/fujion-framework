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

import org.fujion.ancillary.JavaScript;
import org.fujion.annotation.Option;
import org.fujion.chartjs.common.BubblePoint;

/**
 * Options for bubble plots.
 */
public class PlotBubble extends PlotOptions {
    
    /**
     * The points to plot.
     */
    @Option
    public BubblePoint[] data;
    
    /**
     * The bubble additional radius for hit detection as an array.
     */
    @Option
    public Integer[] hitRadius$array;
    
    /**
     * The bubble additional radius for hit detection as a function.
     */
    @Option(convertTo = JavaScript.class)
    public String hitRadius$function;
    
    /**
     * The bubble additional radius for hit detection as a single value.
     * <p>
     * Default: 1
     */
    @Option
    public Integer hitRadius$number;
    
    /**
     * The bubble background color when hovered as an array.
     */
    @Option
    public String[] hoverBackgroundColor$array;
    
    /**
     * The bubble background color when hovered as a function.
     */
    @Option(convertTo = JavaScript.class)
    public String hoverBackgroundColor$function;
    
    /**
     * The bubble background color when hovered as a single value.
     */
    @Option
    public String hoverBackgroundColor$string;
    
    /**
     * The bubble border color when hovered as an array.
     */
    @Option
    public String[] hoverBorderColor$array;
    
    /**
     * The bubble border color when hovered as a function.
     */
    @Option(convertTo = JavaScript.class)
    public String hoverBorderColor$function;
    
    /**
     * The bubble border color when hovered as a single value.
     */
    @Option
    public String hoverBorderColor$string;
    
    /**
     * The bubble border width when hovered as an array.
     */
    @Option
    public Integer[] hoverBorderWidth$array;
    
    /**
     * The bubble border width when hovered as a function.
     */
    @Option(convertTo = JavaScript.class)
    public String hoverBorderWidth$function;

    /**
     * The bubble border width when hovered as a single value.
     * <p>
     * Default: 1
     */
    @Option
    public Integer hoverBorderWidth$number;
    
    /**
     * The bubble additional radius when hovered as an array.
     */
    @Option
    public Integer[] hoverRadius$array;
    
    /**
     * The bubble additional radius when hovered as a function.
     */
    @Option(convertTo = JavaScript.class)
    public String hoverRadius$function;

    /**
     * The bubble additional radius when hovered as a single value.
     * <p>
     * Default: 4
     */
    @Option
    public Integer hoverRadius$number;
    
    /**
     * The label text.
     */
    @Option
    public String label;
    
    /**
     * The bubble shape style.
     */
    @Option
    public PointStyleEnum[] pointStyle$array;

    /**
     * The bubble shape style.
     */
    @Option
    public PointStyleEnum pointStyle$enum;

    /**
     * The bubble radius as an array.
     */
    @Option
    public Integer[] radius$array;

    /**
     * The bubble radius as a function.
     */
    @Option(convertTo = JavaScript.class)
    public String radius$function;

    /**
     * The bubble radius as a single value.
     * <p>
     * Default: 3
     */
    @Option
    public Integer radius$number;
}
