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
package org.fujion.chartjs;

import org.fujion.ancillary.Options;
import org.fujion.annotation.JavaScript;
import org.fujion.chartjs.common.AnimationOptions;
import org.fujion.chartjs.common.HoverOptions;
import org.fujion.chartjs.common.LayoutOptions;
import org.fujion.chartjs.common.LegendOptions;
import org.fujion.chartjs.common.ScaleOptions;
import org.fujion.chartjs.common.TitleOptions;
import org.fujion.chartjs.common.TooltipOptions;

public class ChartOptions extends Options {

    public final AnimationOptions animation = new AnimationOptions();

    /**
     * Override the window's default devicePixelRatio.
     * <p>
     * Default: window.devicePixelRatio
     */
    public Double devicePixelRatio;

    /**
     * The events option defines the browser events that the chart should listen to for tooltips and
     * hovering.
     * <p>
     * Default: ["mousemove", "mouseout", "click", "touchstart", "touchmove", "touchend"]
     */
    public String[] events;

    /**
     * Hover options.
     */
    public final HoverOptions hover = new HoverOptions();

    /**
     * Layout options.
     */
    public final LayoutOptions layout = new LayoutOptions();
    
    /**
     * Legend options.
     */
    public final LegendOptions legend = new LegendOptions();

    /**
     * Maintain the original canvas aspect ratio (width / height) when resizing.
     * <p>
     * Default: true
     */
    public Boolean maintainAspectRatio;

    /**
     * Called if the event is of type 'mouseup' or 'click'. Called in the context of the chart and
     * passed the event and an array of active elements.
     */
    @JavaScript
    public String onClick;

    /**
     * Called when any of the events fire. Called in the context of the chart and passed the event
     * and an array of active elements (bars, points, etc).
     */
    @JavaScript
    public String onHover;

    /**
     * Called when a resize occurs. Gets passed two arguments: the chart instance and the new size.
     */
    @JavaScript
    public String onResize;

    /**
     * Resizes the chart canvas when its container does.
     * <p>
     * Default: true
     */
    public Boolean responsive;
    
    /**
     * Duration in milliseconds it takes to animate to new size after a resize event.
     * <p>
     * Default: 0
     */
    public Integer responsiveAnimationDuration;

    /**
     * Scale options.
     */
    public final ScaleOptions scales = new ScaleOptions();

    /**
     * If true, show plot lines.
     */
    public Boolean showLines;

    /**
     * Title display options.
     */
    public final TitleOptions title = new TitleOptions();
    
    /**
     * Tooltip display options.
     */
    public final TooltipOptions tooltips = new TooltipOptions();

}
