/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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
import org.fujion.annotation.Option;
import org.fujion.chartjs.enums.HorizontalAlignmentEnum;
import org.fujion.chartjs.enums.InteractionModeEnum;
import org.fujion.chartjs.enums.TooltipPositionModeEnum;

/**
 * Options for tooltips.
 */
public class TooltipOptions extends InteractionOptions {

    /**
     * Background color of the tooltip.
     * <p>
     * Default: "rgba(0,0,0,0.8)"
     */
    @Option
    public String backgroundColor;

    /**
     * The body font.
     */
    @Option
    public final FontOptions bodyFont = new FontOptions();

    /**
     * The footer font.
     */
    @Option
    public final FontOptions footerFont = new FontOptions();

    /**
     * Padding inside the tooltip.
     */
    @Option
    public final Padding padding = new Padding();

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
     * The title font.
     */
    @Option
    public final FontOptions titleFont = new FontOptions();

    /**
     * Horizontal alignment of the body text lines.
     * <p>
     * Default: LEFT
     */
    @Option
    public HorizontalAlignmentEnum bodyAlign;

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
     * The body text color.
     * <p>
     * Default: "#FFF"
     */
    @Option
    public String bodyColor;
    
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
     * Height of the color box if displayColors is true.
     */
    @Option
    public Integer boxHeight;

    /**
     * Width of the color box if displayColors is true.
     */
    @Option
    public Integer boxWidth;

    /**
     * Custom tooltip function/
     */
    @Option(convertTo = JavaScript.class)
    public String external;

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
     * Horizontal alignment of the footer text lines.
     * <p>
     * Default: LEFT
     */
    @Option
    public HorizontalAlignmentEnum footerAlign;

    /**
     * Custom function for sorting tooltip items.
     */
    @Option(convertTo = JavaScript.class)
    public String itemSort;

    /**
     * The footer color.
     * <p>
     * Default: "#FFF"
     */
    @Option
    public String footerColor;

    /**
     * Color to draw behind the colored boxes when multiple items are in the tooltip.
     * <p>
     * Default: "#FFF"
     */
    @Option
    public String multiKeyBackground;

    /**
     * If true, the tooltip mode applies only when the mouse position intersects with an element.
     * If false, the mode will be applied at all times.
     */
    @Option
    public Boolean intersect;

    /**
     * The mode for positioning the tooltip.
     * <p>
     * Default: AVERAGE
     */
    @Option
    public TooltipPositionModeEnum position;

    /**
     * Set to true for rendering the tooltip from right to left.
     * <p>
     * Default: false
     */
    @Option
    public Boolean rtl;

    /**
     * Sets which elements appear in the tooltip.
     */
    @Option
    public InteractionModeEnum mode;

    /**
     * Horizontal alignment of the title text lines.
     * <p>
     * Default: LEFT
     */
    @Option
    public HorizontalAlignmentEnum titleAlign;

    /**
     * Color of title text.
     * <p>
     * Default: "#FFF"
     */
    @Option
    public String titleColor;

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
     * Use the corresponding point style (from dataset options) instead of color boxes, ex: star, triangle etc.
     * Size is based on the minimum value between boxWidth and boxHeight.
     * <p>
     * Default: false
     */
    @Option
    public Boolean usePointStyle;

}
