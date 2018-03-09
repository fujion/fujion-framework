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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;
import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.common.HorizontalAlignEnum;
import org.fujion.plotly.common.VHOrientationEnum;
import org.fujion.plotly.common.VerticalAlignEnum;

/**
 * Options for legends.
 */
public class LegendOptions extends Options {
    
    /**
     * The legend background color.
     */
    public String bgcolor;

    /**
     * The color of the border enclosing the legend.
     * <p>
     * Default: "#444"
     */
    public String bordercolor;

    /**
     * The width (in px) of the border enclosing the legend.
     * <p>
     * Default: 0
     */
    public Integer borderwidth;

    /**
     * The font used to text the legend items.
     */
    public final FontOptions font = new FontOptions();

    /**
     * The orientation of the legend.
     * <p>
     * Default: VERTICAL
     */
    public VHOrientationEnum orientation;

    /**
     * The amount of vertical space (in px) between legend groups.
     * <p>
     * Default: 10
     */
    public Integer tracegroupgap;
    
    /**
     * Any combination of "reversed", "grouped" joined with a "+" OR "normal". examples: "reversed",
     * "grouped", "reversed+grouped", "normal" Determines the order at which the legend items are
     * displayed. If "normal", the items are displayed top-to-bottom in the same order as the input
     * data. If "reversed", the items are displayed in the opposite order as "normal". If "grouped",
     * the items are displayed in groups (when a trace "legendgroup" is provided). if
     * "grouped+reversed", the items are displayed in the opposite order as "grouped".
     */
    public String traceorder;
    
    /**
     * The x position (in normalized coordinates) of the legend.
     * <p>
     * Constraints: &le;-2 and &ge;3
     * <p>
     * Default: 1.02
     */
    public Double x;

    /**
     * The legend's horizontal position anchor. This anchor binds the "x" position to the "left"
     * (the default), "center" or "right" of the legend.
     */
    public HorizontalAlignEnum xanchor;
    
    /**
     * The y position (in normalized coordinates) of the legend.
     * <p>
     * Constraints: &ge;-2 and &le;3
     * <p>
     * Default: 1
     */
    public Double y;

    /**
     * The legend's vertical position anchor This anchor binds the "y" position to the "top",
     * "middle" or "bottom" of the legend.
     * <p>
     * Default: AUTO
     */
    public VerticalAlignEnum yanchor;

}
