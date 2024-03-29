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
package org.fujion.plotly.plot;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Settings for a link in a Sankey plot.
 */
public class LinkOptions extends Options {
    
    /**
     * The "link" color as an array for specifying color for each "node". If "color" is omitted,
     * then by default, a translucent grey link will be used.
     */
    @Option("color")
    public String[] color$array;

    /**
     * The same color to all links. If "color" is omitted, then by default, a translucent grey link
     * will be used.
     */
    @Option("color")
    public String color$string;

    /**
     * The shown name of the link.
     */
    @Option
    public String[] label;

    /**
     * The color of the "line" around each "link".
     */
    @Option("line.color")
    public String line_color;

    /**
     * The width (in px) of the "line" around each "link".
     */
    @Option("line.width")
    public int[] line_width$array;

    /**
     * The width (in px) of the "line" around each "link".
     * <p>
     * Default: 0
     */
    @Option("line.width")
    public Integer line_width$number;

    /**
     * An integer number "[0..nodes.length - 1]" that represents the source node.
     */
    @Option
    public int[] source;
    
    /**
     * An integer number "[0..nodes.length - 1]" that represents the target node.
     */
    @Option
    public int[] target;
    
    /**
     * A numeric value representing the flow volume value.
     */
    @Option
    public double[] value;
}
