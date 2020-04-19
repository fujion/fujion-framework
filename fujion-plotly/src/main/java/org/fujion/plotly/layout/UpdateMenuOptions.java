/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
import org.fujion.annotation.Option;
import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.common.HorizontalAlignEnum;
import org.fujion.plotly.common.VerticalAlignEnum;

/**
 * Layout options for update menus.
 */
public class UpdateMenuOptions extends Options {
    
    /**
     * Determines how buttons are laid out.
     */
    public enum DirectionEnum {
        DOWN, LEFT, RIGHT, UP;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * Type of update menu.
     */
    public enum TypeEnum {
        BUTTONS, DROPDOWN;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Determines which button (by index starting from 0) is considered active.
     * <p>
     * Constraints: &ge;-1
     * <p>
     * Default: 0
     */
    @Option
    public Integer active;

    /**
     * The background color of the update menu buttons.
     */
    @Option
    public String bgcolor;
    
    /**
     * The color of the border enclosing the update menu.
     * <p>
     * Default: "#BEC8D9"
     */
    @Option
    public String bordercolor;

    /**
     * The width (in px) of the border enclosing the update menu.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 1
     */
    @Option
    public Integer borderwidth;

    /**
     * Menu button options.
     */
    @Option
    public final ControlOptions buttons = new ControlOptions();
    
    /**
     * Determines the direction in which the buttons are laid out, whether in a dropdown menu or a
     * row/column of buttons. For LEFT and UP, the buttons will still appear in left-to-right or
     * top-to-bottom order respectively.
     * <p>
     * Default: DOWN
     */
    @Option
    public DirectionEnum direction;
    
    /**
     * The font of the update menu button text.
     */
    @Option
    public final FontOptions font = new FontOptions();
    
    /**
     * The amount of padding (in px) along the bottom of the component.
     * <p>
     * Default: 0
     */
    @Option("pad.b")
    public Integer pad_b;
    
    /**
     * The amount of padding (in px) along the left of the component.
     * <p>
     * Default: 0
     */
    @Option("pad.l")
    public Integer pad_l;
    
    /**
     * The amount of padding (in px) along the right of the component.
     * <p>
     * Default: 0
     */
    @Option("pad.r")
    public Integer pad_r;

    /**
     * The amount of padding (in px) along the top of the component.
     * <p>
     * Default: 0
     */
    @Option("pad.t")
    public Integer pad_t;

    /**
     * Highlights active dropdown item or active button if true.
     * <p>
     * Default: true
     */
    @Option
    public Boolean showactive;

    /**
     * Determines whether the buttons are accessible via a dropdown menu or whether the buttons are
     * stacked horizontally or vertically.
     * <p>
     * Default: DROPDOWN
     */
    @Option
    public TypeEnum type;

    /**
     * Determines whether or not the update menu is visible.
     */
    @Option
    public Boolean visible;
    
    /**
     * The x position (in normalized coordinates) of the update menu.
     * <p>
     * Constraints: &ge;-2 and &le;3
     * <p>
     * Default: -0.05
     */
    @Option
    public Double x;
    
    /**
     * The update menu's horizontal position anchor. This anchor binds the "x" position to the left,
     * center or right of the range selector.
     * <p>
     * Default: RIGHT
     */
    @Option
    public HorizontalAlignEnum xanchor;
    
    /**
     * The y position (in normalized coordinates) of the update menu.
     * <p>
     * Constraints: &ge;-2 and &le;3
     * <p>
     * Default: 1
     */
    @Option
    public Double y;
    
    /**
     * The update menu's vertical position anchor This anchor binds the "y" position to the top,
     * middle or bottom of the range selector.
     * <p>
     * Default: TOP
     */
    @Option
    public VerticalAlignEnum yanchor;
}
