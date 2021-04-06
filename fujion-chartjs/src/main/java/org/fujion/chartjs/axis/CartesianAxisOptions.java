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
package org.fujion.chartjs.axis;

import org.fujion.annotation.Option;
import org.fujion.chartjs.common.CartesianAxisEnum;
import org.fujion.chartjs.common.PositionEnum;
import org.fujion.chartjs.common.TitleOptions;

/**
 * Options common to all Cartesian axes.
 */
public abstract class CartesianAxisOptions extends BaseAxisOptions {

    public enum Bounds {
        DATA,   // Makes sure data are fully visible, labels outside are removed.
        TICKS;  // Makes sure ticks are fully visible, data outside are truncated.

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Controls the scale boundary strategy (bypassed by min/max options).
     * <p>
     * Default: TICKS
     */
    @Option
    public Bounds bounds;

    /**
     * Which type of axis this is. If not set, this is inferred from the first character of the ID
     * which should be 'x' or 'y'.
     */
    @Option
    public CartesianAxisEnum axis;

    /**
     * If true, extra space is added to the both edges and the axis is scaled to fit into the chart
     * area. This is set to true in the bar chart by default.
     * <p>
     * Default: true for bar charts; false otherwise.
     */
    @Option
    public Boolean offset;

    /**
     * Position of the axis in the chart.
     */
    @Option
    public PositionEnum position;

    /**
     * Title configuration.
     */
    @Option
    public final TitleOptions title = new TitleOptions();
}
