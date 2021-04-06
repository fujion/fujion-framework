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

/**
 * Options for linear axes.
 */
public class LinearAxisOptions extends CartesianAxisOptions {

    /**
     * If true, scale will include 0 if it is not already included.
     */
    @Option
    public Boolean beginAtZero;

    /**
     * Amount for added room in the scale range above and below data.
     */
    @Option
    public Integer grace$number;

    /**
     * Percentage for added room in the scale range above and below data.
     */
    @Option(convertUsing = "value + '%'")
    public Integer grace$percentage;

    /**
     * Tick options.
     */
    public final LinearTickOptions ticks = new LinearTickOptions();

}
