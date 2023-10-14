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
package org.fujion.chartjs.axis;

import org.fujion.ancillary.JavaScript;
import org.fujion.annotation.Option;

/**
 * Tick options for linear radial plots.
 */
public class RadialTickOptions extends LinearTickOptions {

    /**
     * Color of label backdrops.
     * <p>
     * Default: "rgba(255, 255, 255, 0.75)"
     */
    @Option
    public String backdropColor;

    /**
     * Color of label backdrops.
     */
    @Option(convertTo = JavaScript.class)
    public String backdropColor$function;

    /**
     * Padding of label backdrop.
     * <p>
     * Default: 2
     */
    @Option
    public Integer backdropPadding;

    /**
     * Padding of label backdrop.
     */
    @Option(convertTo = JavaScript.class)
    public String backdropPadding$function;

    /**
     * The number of ticks to generate. If specified, this overrides the automatic generation.
     */
    @Option
    public Integer count;

    /**
     * The number of ticks to generate. If specified, this overrides the automatic generation.
     */
    @Option(convertTo = JavaScript.class)
    public String count$function;

    /**
     * The Intl.NumberFormat options used by the default label formatter.
     */
    @Option
    public Object format;

    /**
     * The Intl.NumberFormat options used by the default label formatter.
     */
    @Option(convertTo = JavaScript.class)
    public String format$function;

    /**
     * Maximum number of ticks and gridlines to show.
     * <p>
     * Default: 11
     */
    @Option
    public Integer maxTicksLimit;

    /**
     * Maximum number of ticks and gridlines to show.
     */
    @Option(convertTo = JavaScript.class)
    public String maxTicksLimit$function;

    /**
     * If defined and stepSize is not specified, the step size will be rounded to this many decimal places.
     */
    @Option
    public Integer precision;

    /**
     * If defined and stepSize is not specified, the step size will be rounded to this many decimal places.
     */
    @Option(convertTo = JavaScript.class)
    public String precision$function;

    /**
     * User defined fixed step size for the scale.
     */
    @Option
    public Double stepSize;

    /**
     * User defined fixed step size for the scale.
     */
    @Option(convertTo = JavaScript.class)
    public String stepSize$function;

    /**
     * If true, draw a background behind the tick labels.
     * <p>
     * Default: true
     */
    @Option
    public Boolean showLabelBackdrop;

    /**
     * If true, draw a background behind the tick labels.
     */
    @Option(convertTo = JavaScript.class)
    public String showLabelBackdrop$function;

}
