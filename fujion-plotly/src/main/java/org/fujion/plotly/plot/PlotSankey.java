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

import org.fujion.annotation.Option;
import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.common.VHOrientationEnum;

/**
 * Options for sankey plot.
 * <p>
 * A sankey plot is a type of flow diagram, in which the width of the link between two nodes is
 * shown proportionally to the flow quantity.
 */
public class PlotSankey extends PlotOptions {
    
    /**
     * Determines how nodes are arranged in a Sankey plot.
     */
    public enum ArrangementEnum {

        /**
         * The nodes are stationary.
         */
        FIXED,
        /**
         * The nodes can freely move on the plane.
         */
        FREEFORM,
        /**
         * The nodes can only move along a line perpendicular to the flow.
         */
        PERPENDICULAR,
        /**
         * The node arrangement is assisted by automatic snapping of elements to preserve space
         * between nodes specified via "nodepad".
         */
        SNAP;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * Determines how nodes are arranged.
     * <p>
     * Default: SNAP
     */
    @Option
    public ArrangementEnum arrangement;

    /**
     * The horizontal domain of this sankey trace (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    @Option("domain.x")
    public double[] domain_x;
    
    /**
     * The vertical domain of this sankey trace (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    @Option("domain.y")
    public double[] domain_y;

    /**
     * The links of the Sankey plot.
     */
    @Option
    public final LinkOptions link = new LinkOptions();

    /**
     * The nodes of the Sankey plot.
     */
    @Option
    public final NodeOptions node = new NodeOptions();

    /**
     * The orientation of the Sankey diagram.
     */
    @Option
    public VHOrientationEnum orientation;

    /**
     * The font for node labels
     */
    @Option
    public final FontOptions textfont = new FontOptions();

    /**
     * The value formatting rule using d3 formatting mini-language which is similar to those of
     * Python. See <a href="https://github.com/d3/d3-format/blob/master/README.md#locale_format">3d
     * formatting reference.</a>
     * <p>
     * Default: ".3s"
     */
    @Option
    public String valueformat;
    
    /**
     * Adds a unit to follow the value in the hover tooltip. Add a space if a separation is
     * necessary from the value.
     */
    @Option
    public String valuesuffix;
    
}
