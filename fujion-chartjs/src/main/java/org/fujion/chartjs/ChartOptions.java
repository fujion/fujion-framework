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
    @Option
    private final OptionMap animations = new OptionMap();

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
     * By default the chart's canvas will use a 1:1 pixel ratio, unless the physical display has a higher pixel ratio
     * (e.g. Retina displays).  For applications where a chart will be converted to a bitmap, or printed to a higher
     * DPI medium it can be desirable to render the chart at a higher resolution than the default.
     * <p>
     * Setting devicePixelRatio to a value other than 1 will force the canvas size to be scaled by that amount,
     * relative to the container size. There should be no visible difference on screen; the difference will only
     * be visible when the image is zoomed or printed.
     *
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
    @Option
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
    @Option(convertTo = JavaScript.class)
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
    @Option
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
     * Scale options.
     */
    @Option
    private final OptionMap scales = new OptionMap();
    
    /**
     * If true, show plot lines.
     */
    @Option
    public Boolean showLine;

    /**
     *
     * Adds an axis.
     *
     * @param id The unique id for the axis.
     * @param axis The axis to add.
     */
    protected void addAxis(String id, BaseAxisOptions axis) {
        scales.put(id, axis);
    }

    /**
     * Removes an axis
     *
     * @param id The unique id for the axis.
     */
    protected void removeAxis(String id) {
        scales.remove(id);
    }

    /**
     * Adds an animation option.
     *
     * @param name The group or property name affected by the animation options.
     * @param options The animation options.  If null, any existing animation is removed.
     */
    protected void addAnimation(String name, AnimationsOptions options) {
        animations.put(name, options);
    }

    /**
     * Removes an animation option.
     *
     * @param name The group or property name affected by the animation options.
     */
    protected void removeAnimation(String name) {
        animations.remove(name);
    }
}
