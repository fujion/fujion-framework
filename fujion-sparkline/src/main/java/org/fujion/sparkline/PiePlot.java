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
package org.fujion.sparkline;

import org.fujion.annotation.Option;

public class PiePlot extends AbstractPlot {
    
    /**
     * An array of CSS colors to use for pie slices.
     */
    @Option
    public String[] sliceColors;
    
    /**
     * Angle in degrees to offset the first slice (e.g., -90 or +90);
     */
    @Option
    public Double offset;
    
    /**
     * Width of the border to draw around the whole pie chart, in pixels.
     * <p>
     * Default: 0
     */
    @Option
    public Integer borderWidth;
    
    /**
     * CSS color to use to draw the pie border.
     * <p>
     * Default: "#000"
     */
    @Option
    public String borderColor;
    
    protected PiePlot() {
        super(SparklineType.PIE);
    }

}
