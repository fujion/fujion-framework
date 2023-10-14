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
 * Settings for a node in a Sankey plot.
 */
public class NodeOptions extends Options {

    /**
     * The "node" color as an array for specifying color for each "node". If "color" is omitted,
     * then the default "Plotly" color palette will be cycled through to have a variety of colors.
     * These defaults are not fully opaque, to allow some visibility of what is beneath the node.
     */
    @Option("color")
    public String[] color$array;
    
    /**
     * The same color to all nodes. If "node.color" is omitted, then the default "Plotly" color
     * palette will be cycled through to have a variety of colors. These defaults are not fully
     * opaque, to allow some visibility of what is beneath the node.
     */
    @Option("color")
    public String color$string;
    
    /**
     * The shown name of the node.
     */
    @Option
    public String[] label;
    
    /**
     * The color of the "line" around each "node".
     */
    @Option("line.color")
    public String line_color;
    
    /**
     * The width (in px) of the "line" around each "node".
     */
    @Option("line.width")
    public int[] line_width$array;
    
    /**
     * The width (in px) of the "line" around each "node".
     * <p>
     * Default: 0.5
     */
    @Option("line.width")
    public Integer line_width$number;
    
    /**
     * The padding (in px) between the "nodes".
     * <p>
     * Default: 20
     */
    @Option
    public Integer pad;
    
    /**
     * The thickness (in px) of the "nodes".
     * <p>
     * Default: 20
     */
    @Option
    public Integer thickness;
}
