/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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
 * Options for the paging or navigation appearing when the legend overflows.
 */
public class LegendNavigationOptions extends Options {

    /**
     * The color for the active up or down arrow in the legend page navigation. Defaults to #003399.
     */
    @Option
    public String activeColor;

    /**
     * How to animate the pages when navigating up or down. A value of true applies the default
     * navigation given in the chart.animation option.
     */
    @Option
    public AnimationOptions animation;

    /**
     * The pixel size of the up and down arrows in the legend paging navigation. Defaults to 12.
     */
    @Option
    public Integer arrowSize;

    /**
     * Whether to enable the legend navigation. In most cases, disabling the navigation results in
     * an unwanted overflow. See also the adapt chart to legend plugin for a solution to extend the
     * chart height to make room for the legend, optionally in exported charts only. Defaults to
     * true.
     */
    @Option
    public Boolean enabled;

    /**
     * The color of the inactive up or down arrow in the legend page navigation. Defaults to
     * #cccccc.
     */
    @Option
    public String inactiveColor;

    /**
     * Text styles for the legend page navigation.
     */
    @Option
    public final StyleOptions style = new StyleOptions();
}
