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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Layout options for range sliders.
 */
public class RangeSliderOptions extends Options {

    /**
     * Determines whether or not the range slider range is computed in relation to the input data.
     * <p>
     * Default: true (If "range" is provided, then "autorange" is set to false)
     */
    @Option
    public Boolean autorange;

    /**
     * The background color of the range slider.
     * <p>
     * Default: "#fff"
     */
    @Option
    public String bgcolor;

    /**
     * The border color of the range slider.
     * <p>
     * Default: "#444"
     */
    @Option
    public String bordercolor;

    /**
     * The border width of the range slider.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 0
     */
    @Option
    public Integer borderwidth;

    /**
     * The range of the range slider. If not set, defaults to the full xaxis range. If the axis
     * "type" is "log", then you must take the log of your desired range. If the axis "type" is
     * "date", it should be date strings, like date data, though Date objects and unix milliseconds
     * will be accepted and converted to strings. If the axis "type" is "category", it should be
     * numbers, using the scale where each category is assigned a serial number from zero in the
     * order it appears.
     */
    @Option("range")
    public Object[] range$array;

    /**
     * The range of the range slider as a numeric array. If not set, defaults to the full xaxis
     * range. If the axis "type" is "log", then you must take the log of your desired range. If the
     * axis "type" is "date", it should be date strings, like date data, though Date objects and
     * unix milliseconds will be accepted and converted to strings. If the axis "type" is
     * "category", it should be numbers, using the scale where each category is assigned a serial
     * number from zero in the order it appears.
     */
    @Option("range")
    public double[] range$number;

    /**
     * The height of the range slider as a fraction of the total plot area height.
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 0.15
     */
    @Option
    public Double thickness;

    /**
     * Determines whether or not the range slider will be visible. If visible, perpendicular axes
     * will be set to "fixedrange".
     * <p>
     * Default: true
     */
    @Option
    public Boolean visible;
}
