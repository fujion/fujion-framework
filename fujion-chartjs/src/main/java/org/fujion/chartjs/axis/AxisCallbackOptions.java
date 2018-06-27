/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Callback options for axes.
 */
public class AxisCallbackOptions extends Options {
    
    /**
     * Callback that runs after ticks are created. Useful for filtering ticks.
     */
    @Option(convertTo = JavaScript.class)
    public String afterBuildTicks;
    
    /**
     * Callback that runs after tick rotation is determined.
     */
    @Option(convertTo = JavaScript.class)
    public String afterCalculateTickRotation;
    
    /**
     * Callback that runs after data limits are determined.
     */
    @Option(convertTo = JavaScript.class)
    public String afterDataLimits;
    
    /**
     * Callback that runs after the scale fits to the canvas.
     */
    @Option(convertTo = JavaScript.class)
    public String afterFit;
    
    /**
     * Callback that runs after dimensions are set.
     */
    @Option(convertTo = JavaScript.class)
    public String afterSetDimensions;
    
    /**
     * Callback that runs after ticks are converted into strings.
     */
    @Option(convertTo = JavaScript.class)
    public String afterTickToLabelConversion;
    
    /**
     * Callback that runs at the end of the update process.
     */
    @Option(convertTo = JavaScript.class)
    public String afterUpdate;
    
    /**
     * Callback that runs before ticks are created.
     */
    @Option(convertTo = JavaScript.class)
    public String beforeBuildTicks;
    
    /**
     * Callback that runs before tick rotation is determined.
     */
    @Option(convertTo = JavaScript.class)
    public String beforeCalculateTickRotation;
    
    /**
     * Callback that runs before data limits are determined.
     */
    @Option(convertTo = JavaScript.class)
    public String beforeDataLimits;
    
    /**
     * Callback that runs before the scale fits to the canvas.
     */
    @Option(convertTo = JavaScript.class)
    public String beforeFit;
    
    /**
     * Callback that runs before dimensions are set.
     */
    @Option(convertTo = JavaScript.class)
    public String beforeSetDimensions;
    
    /**
     * Callback that runs before ticks are converted into strings.
     */
    @Option(convertTo = JavaScript.class)
    public String beforeTickToLabelConversion;
    
    /**
     * Callback that runs before the update process starts.
     */
    @Option(convertTo = JavaScript.class)
    public String beforeUpdate;
    
}
