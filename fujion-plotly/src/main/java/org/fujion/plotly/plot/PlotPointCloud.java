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

/**
 * Options for a point cloud plot.
 */
public class PlotPointCloud extends PlotOptions {

    /**
     * Options for marker.
     */
    @Option
    public final MarkerOptions marker = new MarkerOptions();
    
    /**
     * Text elements associated with each (x,y) pair. The items are mapped in order to the trace's
     * (x,y) coordinates. If trace "hoverinfo" contains a "text" flag and "hovertext" is not set,
     * these elements will be seen in the hover labels.
     */
    @Option("text")
    public String[] text$array;

    /**
     * Text elements associated with each (x,y) pair. Alternate form where the same string appears
     * over all the data points. If trace "hoverinfo" contains a "text" flag and "hovertext" is not
     * set, these elements will be seen in the hover labels.
     */
    @Option("text")
    public String text$string;

    /**
     * Data points for x-axis.
     */
    @Option
    public Object[] x;

    /**
     * Data points for x-axis. Alternate form for numeric data.
     */
    @Option("x")
    public double[] x$number;

    /**
     * Reference between this trace's x coordinates and a 2D Cartesian x axis. If "x" (the default
     * value), the x coordinates refer to "layout.xaxis". If "x2", the x coordinates refer to
     * "layout.xaxis2", and so on.
     */
    @Option
    public String xaxis;
    
    /**
     * Data points for y-axis.
     */
    @Option
    public Object[] y;

    /**
     * Data points for y-axis. Alternate form for numeric data.
     */
    @Option("y")
    public double[] y$number;

    /**
     * Reference between this trace's y coordinates and a 2D Cartesian y axis. If "y" (the default
     * value), the y coordinates refer to "layout.yaxis". If "y2", the y coordinates refer to
     * "layout.xaxis2", and so on.
     */
    @Option
    public String yaxis;

}
