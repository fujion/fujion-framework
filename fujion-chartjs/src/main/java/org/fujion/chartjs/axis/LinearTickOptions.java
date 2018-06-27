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

import org.fujion.annotation.Option;
import org.fujion.chartjs.common.TickOptions;

/**
 * Tick options for linear plots.
 */
public class LinearTickOptions extends TickOptions {
    
    /**
     * If true, scale will include 0 if it is not already included.
     */
    @Option
    public Boolean beginAtZero;
    
    /**
     * Maximum number for the scale, overrides maximum value from data.
     */
    @Option
    public Double max;
    
    /**
     * Maximum number of ticks and gridlines to show.
     * <p>
     * Default: 11
     */
    @Option
    public Integer maxTicksLimit;
    
    /**
     * Minimum number for the scale, overrides minimum value from data.
     */
    @Option
    public Double min;
    
    /**
     * Fixed step size for the scale.
     */
    @Option
    public Double stepSize;
    
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
    
}
