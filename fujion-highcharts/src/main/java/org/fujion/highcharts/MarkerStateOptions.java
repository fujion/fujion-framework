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
package org.fujion.highcharts;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for marker hover and select states.
 */
public class MarkerStateOptions extends Options {

    /**
     * Enable or disable the point marker. Defaults to true.
     */
    @Option
    public Boolean enabled;

    /**
     * The fill color of the point marker. When null, the series' or point's color is used. Defaults
     * to null.
     */
    @Option
    public String fillColor;

    /**
     * The color of the point marker's outline. When null, the series' or point's color is used.
     * Defaults to "#FFFFFF".
     */
    @Option
    public String lineColor;

    /**
     * The width of the point marker's outline. Defaults to 0.
     */
    @Option
    public Integer lineWidth;

    /**
     * The radius of the point marker. In hover state, it defaults to the normal state's radius + 2.
     */
    @Option
    public Integer radius;

}
