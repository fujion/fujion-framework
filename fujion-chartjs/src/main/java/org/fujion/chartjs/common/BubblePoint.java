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
package org.fujion.chartjs.common;

import org.fujion.annotation.Option;

/**
 * A single point for a bubble plot.
 */
public class BubblePoint extends Point {

    /**
     * The bubble radius in pixels (not scaled; it is the raw radius in pixels of the bubble that is
     * drawn on the canvas).
     */
    @Option
    protected final int r;

    public BubblePoint(double x, double y, int r) {
        super(x, y);
        this.r = r;
    }
    
}
