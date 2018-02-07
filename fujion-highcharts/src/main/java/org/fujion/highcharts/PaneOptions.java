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
package org.fujion.highcharts;

import java.util.ArrayList;
import java.util.List;

import org.fujion.ancillary.Options;

/**
 * Applies only to polar charts and angular gauges. This configuration object holds general options
 * for the combined X and Y axes set. Each xAxis or yAxis can reference the pane by index.
 */
public class PaneOptions extends Options {
    
    /**
     * An object, or array of objects, for backgrounds. Sub options include backgroundColor (which
     * can be solid or gradient), innerWidth, outerWidth, borderWidth, borderColor.
     */
    public final List<Object> background = new ArrayList<>();
    
    /**
     * The center of a polar chart or angular gauge, given as an array of [x, y] positions.
     * Positions can be given as integers that transform to pixels, or as percentages of the plot
     * area size. Defaults to ['50%', '50%'].
     */
    public final List<String> center = new ArrayList<>();
    
    /**
     * The end angle of the polar X axis or gauge value axis, given in degrees where 0 is north.
     * Defaults to startAngle + 360. Defaults to undefined.
     */
    public Integer endAngle;
    
    /**
     * The size of the pane, either as a number defining pixels, or a percentage defining a
     * percentage of the plot are. Defaults to 85%.
     */
    public String size;
    
    /**
     * The start angle of the polar X axis or gauge axis, given in degrees where 0 is north.
     * Defaults to 0. Defaults to 0.
     */
    public Integer startAngle;
}
