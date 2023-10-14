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

/**
 * Options for parallel coordinates plot.
 * <p>
 * Parallel coordinates are a common way of visualizing high-dimensional geometry and analyzing
 * multivariate data. To show a set of points in an n-dimensional space, a backdrop is drawn
 * consisting of n parallel lines, typically vertical and equally spaced. A point in n-dimensional
 * space is represented as a polyline with vertices on the parallel axes; the position of the vertex
 * on the i-th axis corresponds to the i-th coordinate of the point. This visualization is closely
 * related to time series visualization, except that it is applied to data where the axes do not
 * correspond to points in time, and therefore do not have a natural order. Therefore, different
 * axis arrangements may be of interest.
 */
public class PlotParallelCoords extends PlotOptions {

    /**
     * Options for display of dimensions.
     */
    @Option
    public final DimensionOptions dimensions = new DimensionOptions();
    
    /**
     * The horizontal domain of this parcoords trace (in plot fraction).
     */
    @Option("domain.x")
    public double[] domain_x;
    
    /**
     * The vertical domain of this parcoords trace (in plot fraction).
     */
    @Option("domain.y")
    public double[] domain_y;
    
    /**
     * The font for the "dimension" labels.
     */
    @Option
    public final FontOptions labelfont = new FontOptions();
    
    /**
     * Line options.
     */
    @Option
    public final ParcoordsLineOptions line = new ParcoordsLineOptions();

    /**
     * The font for the "dimension" range values.
     */
    @Option
    public final FontOptions rangefont = new FontOptions();
    
    /**
     * The font for the "tick" values.
     */
    @Option
    public final FontOptions tickfont = new FontOptions();
}
