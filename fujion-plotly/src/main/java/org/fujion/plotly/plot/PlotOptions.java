/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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
package org.fujion.plotly.plot;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.plotly.common.HoverLabelOptions;

/**
 * Base class for all plot types.
 */
public abstract class PlotOptions extends Options {

    /**
     * Determines whether or not a trace is visible.
     */
    public enum VisibileEnum {
        
        /**
         * The trace is not visible.
         */
        FALSE,
        /**
         * The trace is not drawn, but can appear as a legend item (provided that the legend itself
         * is visible).
         */
        LEGEND_ONLY,
        /**
         * The trace is visible.
         */
        TRUE;
        
        @Override
        public String toString() {
            return name().toLowerCase().replace("_", "");
        }
    }
    
    /**
     * Assigns extra data to each datum. This may be useful when listening to hover, click and
     * selection events. Note that, "scatter" traces also appends customdata items in the markers
     * DOM elements.
     */
    @Option
    public Object[] customdata;
    
    /**
     * Any combination of "x", "y", "z", "text", "name" joined with a "+" OR "all" or "none" or
     * "skip". examples: "x", "y", "x+y", "x+y+z", "all" default: "all" Determines which trace
     * information appear on hover. If "none" or "skip" are set, no information is displayed upon
     * hovering. But, if "none" is set, click and hover events are still fired.
     */
    @Option
    public String hoverInfo;

    /**
     * Style options for hover labels.
     */
    @Option
    public final HoverLabelOptions hoverlabel = new HoverLabelOptions();

    /**
     * Assigns id labels to each datum. These ids for object constancy of data points during
     * animation.
     */
    @Option
    public String[] ids;
    
    /**
     * Traces that are part of the same legend group hide/show at the same time when toggling legend
     * items.
     */
    @Option
    public String legendgroup;

    /**
     * The trace name appears as the legend item and on hover.
     */
    @Option
    public String name;
    
    /**
     * The opacity of the trace.
     * <p>
     * Constraints: &ge;0 and &le;1
     */
    @Option
    public Double opacity;
    
    /**
     * Array containing integer indices of selected points. Has an effect only for traces that
     * support selections. Note that an empty array means an empty selection where the "unselected"
     * are turned on for all points, whereas, any other non-array values means no selection all
     * where the "selected" and "unselected" styles have no effect.
     */
    @Option
    public int[] selectedpoints;
    
    /**
     * Determines whether or not an item corresponding to this trace is shown in the legend.
     * <p>
     * Default: true
     */
    @Option
    public Boolean showlegend;

    /**
     * Options for stream settings.
     */
    @Option
    public final StreamOptions stream = new StreamOptions();

    /**
     * The plot type.
     */
    protected PlotType type;

    /**
     * Determines whether or not this trace is visible. If "legendonly", the trace is not drawn, but
     * can appear as a legend item (provided that the legend itself is visible). value.
     */
    @Option
    public VisibileEnum visible;
}
