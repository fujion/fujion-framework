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
package org.fujion.chartjs;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.OptionMap;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.chartjs.axis.BaseAxisOptions;
import org.fujion.chartjs.common.*;

import static org.fujion.chartjs.common.AnimationsOptions.PropertyTypeEnum;

public class ChartOptions extends Options {

    @Option
    public final AnimationOptions animation = new AnimationOptions();

    /**
     * Set to true to disable animations.
     *
     * Default: false
     */
    @Option(value = "animation", convertUsing = "value == false ? false : animation")
    public Boolean disableAnimation;

    /**
     * Configures which element properties are animated and how.
     */
    @Option("animations.numbers")
    public final AnimationsOptions animationsOptionsNumbers = new AnimationsOptions(PropertyTypeEnum.NUMBER);

    /**
     * Configures which element properties are animated and how.
     */
    @Option("animations.colors")
    public final AnimationsOptions animationsOptionsColors = new AnimationsOptions(PropertyTypeEnum.COLOR);

    /**
     * Configures which element properties are animated and how.
     */
    @Option("animations.booleans")
    public final AnimationsOptions animationsOptionsBoolean = new AnimationsOptions(PropertyTypeEnum.BOOLEAN);

    /**
     * Canvas aspect ratio (i.e. width / height, a value of 1 representing a square canvas).
     * Note that this option is ignored if the height is explicitly defined either as attribute or via the style.
     *
     * Default: 2
     */
    @Option
    public Integer aspectRatio;

    /**
     * Override the window's default devicePixelRatio.
     * <p>
     * Default: window.devicePixelRatio
     */
    @Option
    public Double devicePixelRatio;
    
    /**
     * The events option defines the browser events that the chart should listen to for tooltips and
     * hovering.
     * <p>
     * Default: ["mousemove", "mouseout", "click", "touchstart", "touchmove"]
     */
    @Option
    public String[] events;
    
    /**
     * Hover options.
     */
    @Option // ?
    public final HoverOptions hover = new HoverOptions();

    /**
     * Interaction options.
     */
    @Option
    public final InteractionOptions interaction = new InteractionOptions();

    /**
     * A BCP 47 language tag.
     */
    @Option
    public String locale;

    /**
     * Layout options.
     */
    @Option
    public final LayoutOptions layout = new LayoutOptions();

    /**
     * Maintain the original canvas aspect ratio (width / height) when resizing.
     * <p>
     * Default: true
     */
    @Option
    public Boolean maintainAspectRatio;
    
    /**
     * Called if the event is of type 'mouseup' or 'click'. Called in the context of the chart and
     * passed the event and an array of active elements.
     */
    @Option(convertTo = JavaScript.class)
    public String onClick;
    
    /**
     * Called when any of the events fire. Called in the context of the chart and passed the event
     * and an array of active elements (bars, points, etc).
     */
    @Option(convertTo = JavaScript.class)
    public String onHover;
    
    /**
     * Called when a resize occurs. Gets passed two arguments: the chart instance and the new size.
     */
    @Option(convertTo = JavaScript.class) // ?
    public String onResize;

    /**
     * Plugin options.
     */
    @Option
    public final PluginOptions plugins = new PluginOptions();

    /**
     * Element configuration for all chart types.
     */
    @Option
    public final ElementsOptions elements = new ElementsOptions();

    /**
     * Resizes the chart canvas when its container does.
     * <p>
     * Default: true
     */
    @Option // ?
    public Boolean responsive;

    /**
     * Delay the resize update by give amount of milliseconds.
     * This can ease the resize process by debouncing update of the elements.
     *
     * Default: 0
     */
    @Option
    public Integer resizeDelay;

    /**
     * Duration in milliseconds it takes to animate to new size after a resize event.
     * <p>
     * Default: 0
     */
    @Option("animation.resize.duration") // ?
    public Integer responsiveAnimationDuration;
    
    /**
     * Scale options.
     */
    @Option
    private final OptionMap scales = new OptionMap();
    
    /**
     * If true, show plot lines.
     */
    @Option // ?
    public Boolean showLine;

    public void addAxis(BaseAxisOptions axis) {
        scales.put(axis.id, axis);
    }
}
