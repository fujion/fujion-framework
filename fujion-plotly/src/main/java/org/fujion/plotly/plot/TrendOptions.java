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
import org.fujion.plotly.common.DashStyleEnum;

/**
 * Controls how upward or downward trend information is displayed.
 */
public class TrendOptions extends Options {

    /**
     * The fill color.
     * <p>
     * Default: A half-transparent variant of the line color, marker color, or marker line color,
     * whichever is available.
     */
    public String fillcolor;
    
    /**
     * The color of line bounding the box(es).
     * <p>
     * Default: "#3D9970"
     */
    public String line_color;
    
    /**
     * The dash style of lines.
     * <p>
     * Default: SOLID
     */
    public DashStyleEnum line_dash$enum;
    
    /**
     * An alternative in the form of a dash length list in px (eg "5px,10px,2px,2px").
     */
    public String line_dash$string;

    /**
     * The width (in px) of line bounding the box(es).
     * <p>
     * Default: 2
     */
    public Integer line_width;
    
    /**
     * The segment name. The segment name appear as the legend item and on hover.
     */
    public String name;
    
    /**
     * Determines whether or not an item corresponding to this segment is shown in the legend.
     * <p>
     * Default: true
     */
    public Boolean showlegend;
}