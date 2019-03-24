/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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
 * Options for table plot.
 */
public class PlotTable extends PlotOptions {
    
    /**
     * Options for table cells.
     */
    @Option
    public final TableElementOptions cells = new TableElementOptions();

    /**
     * Specifies the rendered order of the data columns; for example, a value "2" at position "0"
     * means that column index "0" in the data will be rendered as the third column, as columns have
     * an index base of zero.
     */
    @Option
    public int[] columnorder;

    /**
     * The width of columns expressed as a ratio. Columns fill the available width in proportion of
     * their specified column widths.
     */
    @Option("columnwidth")
    public double[] columnwidth$array;
    
    /**
     * The width of columns expressed as a ratio. Columns fill the available width in proportion of
     * their specified column widths.
     */
    @Option("columnwidth")
    public Double columnwidth$number;

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
     * Options for the table header.
     */
    @Option
    public final TableElementOptions header = new TableElementOptions();
}
