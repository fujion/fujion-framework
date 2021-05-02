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
import org.fujion.chartjs.common.BubblePoint;
import org.fujion.chartjs.enums.PointStyleEnum;

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
     * The bubble additional radius for hit detection as a single value.
     * <p>
     * Default: 1
     */
    @Option
    public Integer hitRadius;

    /**
     * The bubble additional radius for hit detection as an array.
     */
    @Option
    public int[] hitRadius$array;
    
    /**
     * The bubble additional radius for hit detection as a function.
     */
    @Option(convertTo = JavaScript.class)
    public String hitRadius$function;

    /**
     * The bubble additional radius when hovered as a single value.
     * <p>
     * Default: 4
     */
    @Option
    public Integer hoverRadius;

    /**
     * The bubble additional radius when hovered as an array.
     */
    @Option
    public int[] hoverRadius$array;
    
    /**
     * The bubble additional radius when hovered as a function.
     */
    @Option(convertTo = JavaScript.class)
    public String hoverRadius$function;

    /**
     * The label text.
     */
    @Option
    public String label;

    /**
     * The drawing order of dataset.
     */
    @Option
    public Integer order;

    /**
     * Style of the point.
     */
    @Option
    public PointStyleEnum pointStyle;

    /**
     * Style of the point.
     */
    @Option
    public PointStyleEnum[] pointStyle$array;

    /**
     * Style of the point.
     */
    @Option(convertTo = JavaScript.class)
    public String pointStyle$function;


    /**
     * The bubble radius as a single value.
     * <p>
     * Default: 3
     */
    @Option
    public Integer radius;

    /**
     * The bubble radius as an array.
     */
    @Option
    public int[] radius$array;

    /**
     * The bubble radius as a function.
     */
    @Option(convertTo = JavaScript.class)
    public String radius$function;

    /**
     * Starting angle to draw arcs from.
     * <p>
     * Default: 0
     */
    @Option
    public Double rotation;

    /**
     * Starting angle to draw arcs from.
     */
    @Option
    public double[] rotation$array;

    /**
     * Starting angle to draw arcs from.
     */
    @Option(convertTo = JavaScript.class)
    public String rotation$function;

}
