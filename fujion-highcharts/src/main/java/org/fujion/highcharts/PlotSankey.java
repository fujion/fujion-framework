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
package org.fujion.highcharts;

import org.fujion.annotation.Option;

/**
 * Options for sankey diagram.
 * <p>
 * A sankey diagram is a type of flow diagram, in which the width of the link between two nodes is
 * shown proportionally to the flow quantity.
 */
public class PlotSankey extends PlotOptions {

    /**
     * Higher numbers makes the links in a sankey diagram render more curved. A curveFactor of 0
     * makes the lines straight. Defaults to 0.33.
     */
    @Option
    public Double curveFactor;

    /**
     * Opacity for the links between nodes in the sankey diagram. Defaults to 0.5.
     */
    @Option
    public Double linkOpacity;
    
    /**
     * The padding between nodes in a sankey diagram, in pixels. Defaults to 10.
     */
    @Option
    public Integer nodePadding;
    
    /**
     * The pixel width of each node in a sankey diagram, or the height in case the chart is
     * inverted. Defaults to 20.
     */
    @Option
    public Integer nodeWidth;
}
