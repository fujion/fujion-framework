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

/**
 * Options for an axis title.
 */
public class AxisTitleOptions extends Options {
    
    /**
     * Alignment of the title relative to the axis values. Possible values are "low", "middle" or
     * "high". Defaults to "middle".
     */
    public String align;

    /**
     * The pixel distance between the axis labels or line and the title. Defaults to 0 for
     * horizontal axes, 10 for vertical.
     */
    public Integer margin;

    /**
     * The distance of the axis title from the axis line. By default, this distance is computed from
     * the offset width of the labels, the labels' distance from the axis and the title's margin.
     * However when the offset option is set, it overrides all this. Defaults to undefined.
     */
    public Integer offset;

    /**
     * The rotation of the text in degrees. 0 is horizontal, 270 is vertical reading from bottom to
     * top. Defaults to 0.
     */
    public Integer rotation;

    /**
     * CSS styles for the title. When titles are rotated they are rendered using vector graphic
     * techniques and not all styles are applicable. Most noteworthy, a bug in IE8 renders all
     * rotated strings bold and italic. Defaults to:
     *
     * <pre>
     *     color: '#6D869F'
     *     fontWeight: 'bold'
     * </pre>
     */
    public final StyleOptions style = new StyleOptions();

    /**
     * The actual text of the axis title. It can contain basic HTML text markup like &lt;b&gt;,
     * &lt;i&gt; and spans with style. Defaults to null.
     */
    public String text;
}
