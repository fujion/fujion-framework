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
package org.fujion.chartjs.common;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for tick marks.
 */
public abstract class TickOptions extends Options {

    /**
     * Returns the string representation of the tick value as it should be displayed on the chart.
     */
    @Option(convertTo = JavaScript.class)
    public String callback;

    /**
     * Color of ticks.
     */
    @Option
    public String color;

    /**
     * Color of ticks.
     */
    @Option(convertTo = JavaScript.class)
    public String color$function;

    /**
     * If true, show tick labels.
     * <p>
     * Default: true
     */
    @Option
    public Boolean display;

    /**
     * Font for ticks.
     */
    @Option
    public final FontOptions font = new FontOptions();

    /**
     * Font for ticks.
     */
    @Option(convertTo = JavaScript.class)
    public String font$function;

    /**
     * If true, major ticks are generated. A major tick will affect autoskipping and major will be
     * defined on ticks in the scriptable options context.
     */
    @Option("major.enabled")
    public Boolean major_enabled;

    /**
     * Sets the offset of the tick labels from the axis.
     * <p>
     * Default: 3
     */
    @Option
    public Integer padding;

    /**
     * The color of the stroke around the text.
     */
    @Option
    public String textStrokeColor;

    /**
     * The color of the stroke around the text.
     */
    @Option(convertTo = JavaScript.class)
    public String textStrokeColor$function;

    /**
     * Stroke width around the text.
     * <p>
     * Default: 0
     */
    @Option
    public Integer textStrokeWidth;

    /**
     * Stroke width around the text.
     */
    @Option(convertTo = JavaScript.class)
    public String textStrokeWidth$function;

    /**
     * z-index of tick layer. Useful when ticks are drawn on chart area. Values &le; 0 are drawn under datasets, &gt; 0 on top.
     */
    @Option
    public Integer z;

}
