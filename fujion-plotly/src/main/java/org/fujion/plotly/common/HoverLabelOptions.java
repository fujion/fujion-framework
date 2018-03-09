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
package org.fujion.plotly.common;

import org.fujion.ancillary.Options;

public class HoverLabelOptions extends Options {
    
    /**
     * The background color of the hover labels for the trace.
     */
    public String bgcolor;

    /**
     * The border color of the hover labels for the trace.
     */
    public String bordercolor;

    /**
     * The font used in hover labels.
     */
    public final FontOptions font = new FontOptions();
    
    /**
     * The length (in number of characters) of the trace name in the hover labels for this trace. -1
     * shows the whole name regardless of length. 0-3 shows the first 0-3 characters, and an integer
     * >3 will show the whole name if it is less than that many characters, but if it is longer,
     * will truncate to "namelength - 3" characters and add an ellipsis.
     */
    public int[] namelength$array;

    /**
     * Alternative to specifying an array of integers for namelength, if specified this value will
     * be applied to all labels.
     */
    public Integer namelength$number;
}
