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
package org.fujion.sparkline;

import org.fujion.annotation.Option;

/**
 * Discrete charts provide a separated thin vertical line for each value.
 */
public class DiscretePlot extends AbstractPlot {

    /**
     * Height of each line in pixels.
     * <p>
     * Default: 30% of the graph height
     */
    @Option
    public Integer lineHeight;

    /**
     * Values less than this value will be drawn using thresholdColor instead of lineColor.
     */
    @Option
    public Double thresholdValue;

    /**
     * Color to use in combination with thresholdValue.
     */
    @Option
    public String thresholdColor;
    
    protected DiscretePlot() {
        super(SparklineType.DISCRETE);
    }

}
