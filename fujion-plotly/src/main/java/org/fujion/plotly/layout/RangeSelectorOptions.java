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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;
import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.common.HorizontalAlignEnum;

/**
 * Layout options for range selectors.
 */
public class RangeSelectorOptions extends Options {
    
    /**
     * The background color of the active range selector button.
     */
    public String activecolor;

    /**
     * The background color of the range selector buttons.
     * <p>
     * Default: "#eee"
     */
    public String bgcolor;

    /**
     * The color of the border enclosing the range selector.
     * <p>
     * Default: "#444"
     */
    public String bordercolor;

    /**
     * The width (in px) of the border enclosing the range selector.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 0
     */
    public Integer borderwidth;
    
    /**
     * Options for buttons.
     */
    public final RangeSelectorButtonOptions buttons = new RangeSelectorButtonOptions();

    /**
     * The font of the range selector button text.
     */
    public final FontOptions font = new FontOptions();
    
    /**
     * Determines whether or not this range selector is visible. Note that range selectors are only
     * available for x axes of "type" set to or auto-typed to "date".
     */
    public Boolean visible;
    
    /**
     * The x position (in normalized coordinates) of the range selector.
     * <p>
     * Constraints: &ge;-2 and &le;3
     */
    public Double x;
    
    /**
     * The range selector's horizontal position anchor. This anchor binds the "x" position to the
     * "left", "center" or "right" of the range selector.
     * <p>
     * Default: LEFT
     */
    public HorizontalAlignEnum xanchor;
    
    /**
     * The y position (in normalized coordinates) of the range selector.
     * <p>
     * Constraints: &ge;-2 and &le;3
     */
    public Double y;
    
    /**
     * The range selector's vertical position anchor This anchor binds the "y" position to the
     * "top", "middle" or "bottom" of the range selector.
     * <p>
     * Default: BOTTOM
     */
    public HorizontalAlignEnum yanchor;
}
