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

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Display options for box in violin plot.
 */
public class ViolinBoxOptions extends Options {
    
    /**
     * The inner box plot fill color.
     */
    @Option
    public String fillcolor;

    /**
     * The inner box plot bounding line color.
     */
    @Option("line.color")
    public String line_color;
    
    /**
     * The inner box plot bounding line width.
     */
    @Option("line.width")
    public Integer line_width;
    
    /**
     * Determines if an miniature box plot is drawn inside the violins.
     */
    @Option
    public Boolean visible;
    
    /**
     * The width of the inner box plots relative to the violins" width. For example, with 1, the
     * inner box plots are as wide as the violins.
     * <p>
     * Default: 0.25
     */
    @Option
    public Double width;

}
