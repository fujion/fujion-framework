/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
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
package org.fujion.plotly.plot;

import org.fujion.ancillary.Options;

/**
 * Settings for a link in a Sankey plot.
 */
public class LinkOptions extends Options {
    
    /**
     * The "link" color as an array for specifying color for each "node". If "color" is omitted,
     * then by default, a translucent grey link will be used.
     */
    public String[] color$array;

    /**
     * The same color to all links. If "color" is omitted, then by default, a translucent grey link
     * will be used.
     */
    public String color$string;

    /**
     * The shown name of the link.
     */
    public String[] label;

    /**
     * The color of the "line" around each "link".
     */
    public String line_color;

    /**
     * The width (in px) of the "line" around each "link".
     */
    public int[] line_width$array;

    /**
     * The width (in px) of the "line" around each "link".
     * <p>
     * Default: 0
     */
    public Integer line_width$number;

    /**
     * An integer number "[0..nodes.length - 1]" that represents the source node.
     */
    public int[] source;
    
    /**
     * An integer number "[0..nodes.length - 1]" that represents the target node.
     */
    public int[] target;
    
    /**
     * A numeric value representing the flow volume value.
     */
    public double[] value;
}
