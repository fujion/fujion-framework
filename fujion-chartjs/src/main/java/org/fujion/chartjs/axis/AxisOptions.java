/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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

/**
 * Options common to all axis types.
 */
public abstract class AxisOptions extends Options {
    
    /**
     * Callback functions.
     */
    @Option
    public final AxisCallbackOptions callbacks = new AxisCallbackOptions();
    
    /**
     * If set to false the axis is hidden from view.
     * <p>
     * Default: true
     */
    @Option
    public Boolean display;

    /**
     * The ID is used to link datasets and scale axes together.
     */
    @Option
    public String id;

    /**
     * The weight used to sort the axis. Higher weights are further away from the chart area.
     * <p>
     * Default: 0
     */
    @Option
    public Double weight;
}
