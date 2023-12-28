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

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.chartjs.common.GridLineOptions;

/**
 * Options common to all axis types.
 */
public abstract class BaseAxisOptions extends Options {

    /**
     * Callback functions.
     */
    @Option
    public final AxisCallbackOptions callbacks = new AxisCallbackOptions();

    /**
     * Grid line configuration.
     */
    @Option
    public final GridLineOptions grid = new GridLineOptions();

    /**
     * Align pixel values to device pixels.
     * <p>
     * Default: false
     */
    @Option
    public Boolean alignToPixels;

    /**
     * If set to false the axis is hidden from view.
     * <p>
     * Default: true
     */
    @Option
    public Boolean display;

    /**
     * Background color of the scale area.
     */
    @Option
    public String backgroundColor;

    /**
     * User defined minimum number for the scale, overrides minimum value from data.
     */
    @Option
    public Double min;

    /**
     * User defined maximum number for the scale, overrides maximum value from data.
     */
    @Option
    public Double max;

    /**
     * Reverse the scale.
     * <p>
     * Default: false
     */
    @Option
    public Boolean reverse;

    /**
     * Should the data be stacked.
     * <p>
     * Default: false
     */
    @Option
    public Boolean stacked;

    /**
     * Adjustment used when calculating the maximum data value.
     */
    @Option
    public Double suggestedMax;

    /**
     * Adjustment used when calculating the minimum data value.
     */
    @Option
    public Double suggestedMin;

    /**
     * Type of scale being employed. Custom scales can be created and registered with a string key.
     * This allows changing the type of axis for a chart.
     */
    @Option
    public String type;

    /**
     * The weight used to sort the axis. Higher weights are further away from the chart area.
     * <p>
     * Default: 0
     */
    @Option
    public Double weight;

}
