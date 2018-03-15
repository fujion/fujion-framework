/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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

public class SelectedOptions extends Options {

    /**
     * The marker color of selected points.
     */
    @Option("marker.color")
    public String marker_color;
    
    /**
     * The marker opacity of selected points.
     * <p>
     * Constraints: &ge;0 and &le;1.
     */
    @Option("marker.opacity")
    public Double marker_opacity;
    
    /**
     * The marker size of selected points.
     * <p>
     * Constraints: &ge;0.
     */
    @Option("marker.size")
    public Integer marker_size;
    
    /**
     * The text font color of selected points.
     */
    @Option("textfont.color")
    public String textfont_color;

}
