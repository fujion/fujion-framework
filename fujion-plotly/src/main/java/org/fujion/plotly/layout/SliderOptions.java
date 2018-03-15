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
import org.fujion.annotation.Option;
import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.common.HorizontalAlignEnum;
import org.fujion.plotly.common.MeasurementEnum;
import org.fujion.plotly.common.VerticalAlignEnum;

/**
 * Layout options for sliders.
 */
public class SliderOptions extends Options {

    /**
     * The easing function of the slider transition
     */
    public enum EasingEnum {
        BACK, BACK_IN, BACK_IN_OUT, BACK_OUT, BOUNCE, BOUNCE_IN, BOUNCE_IN_OUT, BOUNCE_OUT, CIRCLE, CIRCLE_IN, CIRCLE_IN_OUT, CIRCLE_OUT, CUBIC, CUBIC_IN, CUBIC_IN_OUT, CUBIC_OUT, ELASTIC, ELASTIC_IN, ELASTIC_IN_OUT, ELASTIC_OUT, EXP, EXP_IN, EXP_IN_OUT, EXP_OUT, LINEAR, LINEAR_IN, LINEAR_IN_OUT, LINEAR_OUT, QUAD, QUAD_IN, QUAD_IN_OUT, QUAD_OUT, SIN, SIN_IN, SIN_IN_OUT, SIN_OUT;
        
        @Override
        public String toString() {
            return name().toLowerCase().replace("_", "-");
        }
    }
    
    /**
     * Determines which button (by index starting from 0) is considered active.
     * <p>
     * Default: 0
     */
    @Option
    public Integer active;

    /**
     * The background color of the slider grip while dragging.
     * <p>
     * Default: "#dbdde0"
     */
    @Option
    public String activebgcolor;
    
    /**
     * The background color of the slider.
     * <p>
     * Default: "#f8fafc"
     */
    @Option
    public String bgcolor;

    /**
     * The color of the border enclosing the slider.
     * <p>
     * Default: "#bec8d9"
     */
    @Option
    public String bordercolor;
    
    /**
     * The width (in px) of the border enclosing the slider.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 1
     */
    @Option
    public Integer borderwidth;

    /**
     * Options for displaying the current value.
     */
    @Option
    public final CurrentValueOptions currentvalue = new CurrentValueOptions();

    /**
     * The font of the slider step labels.
     */
    @Option
    public final FontOptions font = new FontOptions();

    /**
     * The length of the slider This measure excludes the padding of both ends. That is, the
     * slider's length is this length minus the padding on both ends.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 1
     */
    @Option
    public Integer len;

    /**
     * Determines whether this slider length is set in units of plot fraction or in pixels. Use
     * "len" to set the value.
     * <p>
     * Default: FRACTION
     */
    @Option
    public MeasurementEnum lenmode;
    
    /**
     * The length in pixels of minor step tick marks.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 4
     */
    @Option
    public Integer minorticklen;
    
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
     * Options for step controls.
     */
    @Option
    public final ControlOptions steps = new ControlOptions();
    
    /**
     * The color of the border enclosing the slider.
     * <p>
     * Default: "#333"
     */
    @Option
    public String tickcolor;
    
    /**
     * The length in pixels of step tick marks the length in pixels of step tick marks.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 7
     */
    @Option
    public Integer ticklen;
    
    /**
     * The tick width (in px).
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 1
     */
    @Option
    public Integer tickwidth;
    
    /**
     * The duration of the slider transition.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 150
     */
    @Option("transition.duration")
    public Integer transition_duration;
    
    /**
     * The easing function of the slider transition.
     */
    @Option("transition.easing")
    public EasingEnum transition_easing;
    
    /**
     * Determines whether or not the slider is visible.
     * <p>
     * Default: true
     */
    @Option
    public Boolean visible;
    
    /**
     * The x position (in normalized coordinates) of the slider.
     * <p>
     * Constraints: &ge;-2 and &le;3
     * <p>
     * Default: 0
     */
    @Option
    public Double x;
    
    /**
     * The slider's horizontal position anchor. This anchor binds the "x" position to the left,
     * center or right of the range selector.
     * <p>
     * Default: LEFT
     */
    @Option
    public HorizontalAlignEnum xanchor;
    
    /**
     * The y position (in normalized coordinates) of the slider.
     * <p>
     * Constraints: &ge;-2 and &le;3
     * <p>
     * Default: 0
     */
    @Option
    public Double y;
    
    /**
     * The slider's vertical position anchor This anchor binds the "y" position to the top, middle
     * or bottom of the range selector.
     * <p>
     * Default: TOP
     */
    @Option
    public VerticalAlignEnum yanchor;
    
}
