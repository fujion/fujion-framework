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

import org.fujion.annotation.Option;

/**
 * Options for linear radial axes.
 */
public class RadialAxisOptions extends BaseAxisOptions {

    /**
     * Angle line options.
     */
    @Option
    public final AngleLineOptions angleLines = new AngleLineOptions();

    /**
     * Whether to animate scaling the chart from the center.
     */
    @Option
    public Boolean animate;

    /**
     * If true, scale will include 0 if it is not already included.
     *
     * Default: false
     */
    @Option
    public Boolean beginAtZero;

    /**
     * Point label options.
     */
    @Option
    public final PointLabelOptions pointLabels = new PointLabelOptions();

    /**
     * Starting angle of the scale. In degrees, 0 is at top.
     * <p>
     * Default: 0
     */
    @Option
    public Double startAngle;

    /**
     * Tick configuration.
     */
    @Option
    public final RadialTickOptions ticks = new RadialTickOptions();

}
