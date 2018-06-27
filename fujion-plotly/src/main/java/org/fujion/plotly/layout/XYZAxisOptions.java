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
package org.fujion.plotly.layout;

import org.fujion.annotation.Option;
import org.fujion.plotly.common.DashStyleEnum;
import org.fujion.plotly.common.HorizontalAlignEnum;
import org.fujion.plotly.common.VerticalAlignEnum;

/**
 * Layout options for x, y, and z axes.
 */
public class XYZAxisOptions extends AxisOptions {

    /**
     * If this axis needs to be compressed (either due to its own scaleanchor and scaleratio or
     * those of the other axis), this setting determines how that happens.
     * <p>
     * Default: RANGE
     */
    public enum ConstrainEnum {
        
        /**
         * Decrease the domain.
         */
        DOMAIN,
        /**
         * Increase the range.
         */
        RANGE;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * The layer on which this axis is displayed. Useful when used together with scatter-like traces
     * with "cliponaxis" set to "false" to show markers and/or text nodes above this axis.
     */
    public enum LayerEnum {
        /**
         * Axis is displayed above all the subplot's traces
         */
        ABOVE_TRACES,
        /**
         * Axis is displayed below all the subplot's traces, but above the grid lines.
         */
        BELOW_TRACES;
        
        @Override
        public String toString() {
            return name().toLowerCase().replace("_", " ");
        }
    }

    /**
     * Determines if the axis lines or/and ticks are mirrored to the opposite side of the plotting
     * area.
     */
    public enum MirrorEnum {
        /**
         * The axis lines are mirrored.
         */
        TRUE,
        /**
         * The axis lines and ticks are mirrored.
         */
        TICKS,
        /**
         * Mirroring is disabled.
         */
        FALSE,
        /**
         * Axis lines are mirrored on all shared-axes subplots.
         */
        ALL,
        /**
         * Axis lines and ticks are mirrored on all shared-axes subplots.
         */
        ALLTICKS;
        
        @Override
        public String toString() {
            return name().toLowerCase().replace("_", " ");
        }
    }
    
    /**
     * Determines where an axis is positioned in the plotting area.
     */
    public enum SideEnum {

        TOP, BOTTOM, LEFT, RIGHT;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Determines the spike snap behavior.
     */
    public enum SpikeSnapEnum {
        
        DATA, CURSOR;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
        
    }

    /**
     * If set to an opposite-letter axis id (e.g. "x2", "y"), this axis is bound to the
     * corresponding opposite-letter axis. If set to "free", this axis' position is determined by
     * "position".
     */
    @Option
    public String anchor;
    
    /**
     * Determines how axis compression occurs.
     * <p>
     * Default: RANGE
     */
    @Option
    public ConstrainEnum constrain;

    /**
     * When axis compression occurs, determines which direction we push the originally specified
     * plot area.
     */
    @Option("constraintoward")
    public HorizontalAlignEnum constraintoward$horizontal;

    /**
     * When axis compression occurs, determines which direction we push the originally specified
     * plot area.
     */
    @Option("constraintoward")
    public VerticalAlignEnum constraintoward$vertical;
    
    /**
     * The domain of this axis (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    @Option
    public Integer[] domain;

    /**
     * The hover text formatting rule using d3 formatting mini-languages which are very similar to
     * those in Python. For numbers, see
     * <a href="https://github.com/d3/d3-format/blob/master/README.md#locale_format">here</a>, and
     * for dates see
     * <a href="https://github.com/d3/d3-time-format/blob/master/README.md#locale_format">here.</a>
     * We add one item to d3's date formatter: "%{n}f" for fractional seconds with n digits. For
     * example, "2016-10-13 09:15:23.456" with tickformat "%H~%M~%S.%2f" would display "09~15~23.46"
     */
    @Option
    public String hoverformat;
    
    /**
     * If set to another axis id (e.g. "x2", "y"), the range of this axis changes together with the
     * range of the corresponding axis such that the scale of pixels per unit is in a constant
     * ratio. Both axes are still zoomable, but when you zoom one, the other will zoom the same
     * amount, keeping a fixed midpoint. "constrain" and "constraintoward" determine how we enforce
     * the constraint. You can chain these, ie "yaxis: {scaleanchor: "x"}, xaxis2: {scaleanchor:
     * "y"}" but you can only link axes of the same "type". The linked axis can have the opposite
     * letter (to constrain the aspect ratio) or the same letter (to match scales across subplots).
     * Loops ("yaxis: {scaleanchor: "x"}, xaxis: {scaleanchor: "y"}" or longer) are redundant and
     * the last constraint encountered will be ignored to avoid possible inconsistent constraints
     * via "scaleratio".
     */
    @Option
    public String scaleAnchor;
    
    /**
     * The layer on which this axis is displayed.
     */
    @Option
    public LayerEnum layer;

    /**
     * Determines if the axis lines or/and ticks are mirrored to the opposite side of the plotting
     * area.
     */
    @Option
    public MirrorEnum mirror;
    
    /**
     * If set a same-letter axis id, this axis is overlaid on top of the corresponding same-letter
     * axis. If "false", this axis does not overlay any same-letter axes.
     */
    @Option
    public String overlaying;

    /**
     * The position of this axis in the plotting space (in normalized coordinates). Only has an
     * effect if "anchor" is set to "free".
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 0
     */
    @Option
    public Double position;
    
    /**
     * Options for range selectors. (x-axis only).
     */
    @Option
    public final RangeSelectorOptions rangeselector = new RangeSelectorOptions();
    
    /**
     * Options for range sliders. (x-axis only).
     */
    @Option
    public final RangeSliderOptions rangeslider = new RangeSliderOptions();

    /**
     * If set to another axis id (e.g. "x2", "y"), the range of this axis changes together with the
     * range of the corresponding axis such that the scale of pixels per unit is in a constant
     * ratio. Both axes are still zoomable, but when you zoom one, the other will zoom the same
     * amount, keeping a fixed midpoint. "constrain" and "constraintoward" determine how we enforce
     * the constraint. You can chain these, ie "yaxis: {scaleanchor: "x"}, xaxis2: {scaleanchor:
     * "y"}" but you can only link axes of the same "type". The linked axis can have the opposite
     * letter (to constrain the aspect ratio) or the same letter (to match scales across subplots).
     * Loops ("yaxis: {scaleanchor: "x"}, xaxis: {scaleanchor: "y"}" or longer) are redundant and
     * the last constraint encountered will be ignored to avoid possible inconsistent constraints
     * via "scaleratio".
     */
    @Option
    public String scaleanchor;
    
    /**
     * If this axis is linked to another by "scaleanchor", this determines the pixel to unit scale
     * ratio. For example, if this value is 10, then every unit on this axis spans 10 times the
     * number of pixels as a unit on the linked axis. Use this for example to create an elevation
     * profile where the vertical scale is exaggerated a fixed amount with respect to the
     * horizontal.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 1
     */
    @Option
    public Double scaleratio;
    
    /**
     * Determines whether or not spikes (aka droplines) are drawn for this axis. Note: This only
     * takes affect when hovermode = closest.
     */
    @Option
    public Boolean showspikes;

    /**
     * Determines whether a x (y) axis is positioned at the "bottom" ("left") or "top" ("right") of
     * the plotting area.
     */
    @Option
    public SideEnum side;

    /**
     * The spike color. If undefined, will use the series color.
     */
    @Option
    public String spikecolor;

    /**
     * The dash style of lines.
     */
    @Option("spikedash")
    public DashStyleEnum spikedash$enum;

    /**
     * Alternate form using a dash length list in px (eg "5px,10px,2px,2px").
     */
    @Option("spikedash")
    public String spikedash$string;

    /**
     * Any combination of "toaxis", "across", "marker" joined with a "+" examples: "toaxis",
     * "across", "toaxis+across", "toaxis+across+marker" default: "toaxis" Determines the drawing
     * mode for the spike line If "toaxis", the line is drawn from the data point to the axis the
     * series is plotted on. If "across", the line is drawn across the entire plot area, and
     * supercedes "toaxis". If "marker", then a marker dot is drawn on the axis the series is
     * plotted on.
     */
    @Option
    public String spikemode;

    /**
     * Determines whether spikelines are stuck to the cursor or to the closest datapoints.
     */
    @Option
    public SpikeSnapEnum spikesnap;

    /**
     * The width (in px) of the zero line.
     * <p>
     * Default: 3
     */
    @Option
    public Integer spikethickness;

    /**
     * The tick length (in px).
     * <p>
     * Default: 5
     */
    @Option
    public Integer ticklen;
    
    /**
     * Determines if and where ticks are drawn.
     */
    @Option
    public TicksEnum ticks;

    /**
     * A single toggle to hide the axis while preserving interaction like dragging. Default is true
     * when a cheater plot is present on the axis, otherwise false.
     */
    @Option
    public Boolean visible;

    /**
     * Determines whether or not a line is drawn at along the 0 value of this axis. If true, the
     * zero line is drawn on top of the grid lines.
     */
    @Option
    public Boolean zeroline;
    
    /**
     * The line color of the zero line.
     * <p>
     * Default: "#444"
     */
    @Option
    public String zerolinecolor;
    
    /**
     * The width (in px) of the zero line.
     * <p>
     * Default: 1
     */
    @Option
    public Integer zerolinewidth;
}
