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
package org.fujion.chartjs.common;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for tooltips.
 */
public class TooltipOptions extends Options {
    
    /**
     * Position modes.
     */
    public enum PositionModeEnum {
        AVERAGE, NEAREST;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Background color of the tooltip.
     * <p>
     * Default: "rgba(0,0,0,0.8)"
     */
    @Option
    public String backgroundColor;

    /**
     * The body font color.
     * <p>
     * Default: "#FFF"
     */
    @Option
    public String bodyFontColor;

    /**
     * The body font family.
     * <p>
     * Default: "Helvetica Neue, Helvetica, Arial, sans-serif"
     */
    @Option
    public String bodyFontFamily;

    /**
     * The bodu font size.
     * <p>
     * Default: 12
     */
    @Option
    public Integer bodyFontSize;

    /**
     * The body font style.
     * <p>
     * Default: "normal"
     */
    @Option
    public String bodyFontStyle;

    /**
     * The spacing to add to top and bottom of each tooltip item.
     * <p>
     * Default: 2
     */
    @Option
    public Integer bodySpacing;
    
    /**
     * Color of the border.
     * <p>
     * Default: "rgba(0,0,0,0)"
     */
    @Option
    public String borderColor;
    
    /**
     * Size of the border.
     * <p>
     * Default: 0
     */
    @Option
    public Integer borderWidth;
    
    /**
     * Options for tooltip callbacks.
     */
    @Option
    public final TooltipCallbackOptions callbacks = new TooltipCallbackOptions();
    
    /**
     * Extra distance to move the end of the tooltip arrow away from the tooltip point.
     * <p>
     * Default: 2
     */
    @Option
    public Integer caretPadding;
    
    /**
     * Size, in px, of the tooltip arrow.
     * <p>
     * Default: 5
     */
    @Option
    public Integer caretSize;
    
    /**
     * Radius of tooltip corner curves.
     * <p>
     * Default: 6
     */
    @Option
    public Double cornerRadius;
    
    /**
     * Custom tooltip function/
     */
    @Option(convertTo = JavaScript.class)
    public String custom;
    
    /**
     * If true, color boxes are shown in the tooltip.
     * <p>
     * Default: true
     */
    @Option
    public Boolean displayColors;
    
    /**
     * If true, on-canvas tooltips are enabled.
     * <p>
     * Default: true
     */
    @Option
    public Boolean enabled;
    
    /**
     * Custom function for filtering tooltip items.
     */
    @Option(convertTo = JavaScript.class)
    public String filter;
    
    /**
     * The footer font color.
     * <p>
     * Default: "#FFF"
     */
    @Option
    public String footerFontColor;
    
    /**
     * The footer font family.
     * <p>
     * Default: "Helvetica Neue, Helvetica, Arial, sans-serif"
     */
    @Option
    public String footerFontFamily;
    
    /**
     * The footer font size.
     * <p>
     * Default: 12
     */
    @Option
    public Integer footerFontSize;
    
    /**
     * The footer font style.
     * <p>
     * Default: "bold"
     */
    @Option
    public String footerFontStyle;

    /**
     * Margin to add before drawing the footer.
     * <p>
     * Default: 6
     */
    @Option
    public Integer footerMarginTop;
    
    /**
     * The spacing to add to top and bottom of each footer line.
     * <p>
     * Default: 2
     */
    @Option
    public Integer footerSpacing;
    
    /**
     * If true, the tooltip mode applies only when the mouse position intersects with an element. If
     * false, the mode will be applied at all times.
     * <p>
     * Default: true
     */
    @Option
    public Boolean intersect;
    
    /**
     * Custom function for sorting tooltip items.
     */
    @Option(convertTo = JavaScript.class)
    public String itemSort;
    
    /**
     * Determines which elements appear in the tooltip.
     * <p>
     * Default: NEAREST
     */
    @Option
    public HoverModeEnum mode;
    
    /**
     * Color to draw behind the colored boxes when multiple items are in the tooltip.
     * <p>
     * Default: "#FFF"
     */
    @Option
    public String multiKeyBackground;
    
    /**
     * The mode for positioning the tooltip.
     * <p>
     * Default: AVERAGE
     */
    @Option
    public PositionModeEnum position;
    
    /**
     * The title font color.
     * <p>
     * Default: "#FFF"
     */
    @Option
    public String titleFontColor;
    
    /**
     * The title font family.
     * <p>
     * Default: "Helvetica Neue, Helvetica, Arial, sans-serif"
     */
    @Option
    public String titleFontFamily;
    
    /**
     * The title font size.
     * <p>
     * Default: 12
     */
    @Option
    public Integer titleFontSize;
    
    /**
     * The title font style.
     * <p>
     * Default: "bold"
     */
    @Option
    public String titleFontStyle;

    /**
     * The margin to add on bottom of title section.
     * <p>
     * Default: 6
     */
    @Option
    public Integer titleMarginBottom;

    /**
     * The spacing to add to top and bottom of each title line.
     * <p>
     * Default: 2
     */
    @Option
    public Integer titleSpacing;
    
    /**
     * Padding to add on left and right of tooltip.
     * <p>
     * Default: 6
     */
    @Option
    public Integer xPadding;
    
    /**
     * Padding to add on top and bottom of tooltip.
     * <p>
     * Default: 6
     */
    @Option
    public Integer yPadding;
}
