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
package org.fujion.chartjs;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.chartjs.common.AnimationOptions;
import org.fujion.chartjs.common.HoverOptions;
import org.fujion.chartjs.common.LayoutOptions;
import org.fujion.chartjs.common.LegendOptions;
import org.fujion.chartjs.common.ScaleOptions;
import org.fujion.chartjs.common.TitleOptions;
import org.fujion.chartjs.common.TooltipOptions;

public class ChartOptions extends Options {
    
    @Option
    public final AnimationOptions animation = new AnimationOptions();
    
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
     * Default: ["mousemove", "mouseout", "click", "touchstart", "touchmove", "touchend"]
     */
    @Option
    public String[] events;
    
    /**
     * Hover options.
     */
    @Option
    public final HoverOptions hover = new HoverOptions();
    
    /**
     * Layout options.
     */
    @Option
    public final LayoutOptions layout = new LayoutOptions();

    /**
     * Legend options.
     */
    @Option
    public final LegendOptions legend = new LegendOptions();
    
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
     * Resizes the chart canvas when its container does.
     * <p>
     * Default: true
     */
    @Option
    public Boolean responsive;

    /**
     * Duration in milliseconds it takes to animate to new size after a resize event.
     * <p>
     * Default: 0
     */
    @Option
    public Integer responsiveAnimationDuration;
    
    /**
     * Scale options.
     */
    @Option
    public final ScaleOptions scales = new ScaleOptions();
    
    /**
     * If true, show plot lines.
     */
    @Option
    public Boolean showLines;
    
    /**
     * Title display options.
     */
    @Option
    public final TitleOptions title = new TitleOptions();

    /**
     * Tooltip display options.
     */
    @Option
    public final TooltipOptions tooltips = new TooltipOptions();
    
}
