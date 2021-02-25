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
package org.fujion.highcharts;

import org.fujion.annotation.Option;

/**
 * Options for gauge plot.
 * <p>
 * Gauges are circular plots displaying one or more values with a dial pointing to values along the
 * perimeter.
 */
public class PlotGauge extends PlotOptions {

    /**
     * Options for the dial or arrow pointer of the gauge.
     */
    @Option
    public final DialOptions dial = new DialOptions();

    /**
     * Allow the dial to overshoot the end of the perimeter axis by this many degrees. Say if the
     * gauge axis goes from 0 to 60, a value of 100, or 1000, will show 5 degrees beyond the end of
     * the axis. Defaults to 0.
     */
    @Option
    public Integer overshoot;
    
    /**
     * Options for the pivot or the center point of the gauge.
     */
    @Option
    public final PivotOptions pivot = new PivotOptions();

}
