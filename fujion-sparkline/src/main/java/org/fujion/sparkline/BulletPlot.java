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
package org.fujion.sparkline;

import org.fujion.annotation.Option;

public class BulletPlot extends AbstractPlot {

    /**
     * The CSS color of the vertical target marker.
     */
    @Option
    public String targetColor;
    
    /**
     * The width of the target marker in pixels.
     */
    @Option
    public Integer targetWidth;
    
    /**
     * The CSS color of the performance measure horizontal bar.
     */
    @Option
    public String performanceColor;
    
    /**
     * Colors to use for each qualitative range background color. For example, ['red','green',
     * '#22f'].
     */
    @Option
    public String[] rangeColors;

    protected BulletPlot() {
        super(SparklineType.BULLET);
    }

}
