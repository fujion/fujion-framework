/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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
 * Options for a single contour.
 */
public class ContourOptions extends Options {
    
    /**
     * The color of the contour lines.
     * <p>
     * Default: "#444"
     */
    @Option
    public String color;
    
    /**
     * Determines whether or not contour lines about the x dimension are highlighted on hover.
     * <p>
     * Default: true
     */
    @Option
    public Boolean highlight;

    /**
     * The color of the highlighted contour lines.
     * <p>
     * Default: "#444"
     */
    @Option
    public String highlightcolor;

    /**
     * The width of the highlighted contour lines.
     * <p>
     * Constraints: &ge;1 and &le;16
     * <p>
     * Default: 2
     */
    @Option
    public Integer highlightwidth;

    /**
     * Determines whether or not these contour lines are projected on the x plane. If "highlight" is
     * set to "true" (the default), the projected lines are shown on hover. If "show" is set to
     * "true", the projected lines are shown in permanence.
     */
    @Option("project.x")
    public Boolean project_x;

    /**
     * Determines whether or not these contour lines are projected on the y plane. If "highlight" is
     * set to "true" (the default), the projected lines are shown on hover. If "show" is set to
     * "true", the projected lines are shown in permanence.
     */
    @Option("project.y")
    public Boolean project_y;
    
    /**
     * Determines whether or not these contour lines are projected on the z plane. If "highlight" is
     * set to "true" (the default), the projected lines are shown on hover. If "show" is set to
     * "true", the projected lines are shown in permanence.
     */
    @Option("project.z")
    public Boolean project_z;
    
    /**
     * Determines whether or not contour lines about a dimension are drawn.
     */
    @Option
    public Boolean show;

    /**
     * An alternate to "color". Determines whether or not the contour lines are colored using the
     * trace "colorscale".
     */
    @Option
    public Boolean usecolormap;

    /**
     * The width of the contour lines.
     * <p>
     * Constraints: &ge;1 and &le;16
     * <p>
     * Default: 2.
     */
    @Option
    public Integer width;
    
}
