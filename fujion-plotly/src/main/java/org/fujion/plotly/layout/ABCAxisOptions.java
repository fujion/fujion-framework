/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2023 Fujion Framework
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

/**
 * Layout options for a, b, and c axes.
 */
public class ABCAxisOptions extends AxisOptions {
    
    public enum CheaterTypeEnum {
        
        INDEX, VALUE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
        
    }

    /**
     * The stride between grid lines along the axis.
     * <p>
     * Default: 1
     */
    @Option
    public Integer arraydtick;

    /**
     * The starting index of grid lines along the axis.
     * <p>
     * Default: 0
     */
    @Option
    public Integer arraytick0;

    /**
     * The cheater type.
     */
    @Option
    public CheaterTypeEnum cheatertype;

    /**
     * Determines whether a line is drawn at along the final value of this axis. If true, the
     * end line is drawn on top of the grid lines.
     */
    @Option
    public Boolean endline;

    /**
     * The line color of the end line.
     */
    @Option
    public String endlinecolor;

    /**
     * The width (in px) of the end line.
     * <p>
     * Default: 1
     */
    @Option
    public Integer endlinewidth;

    /**
     * Extra padding between label and the axis.
     * <p>
     * Default: 10
     */
    @Option
    public Integer labelpadding;

    /**
     * Axis label prefix.
     */
    @Option
    public String labelprefix;

    /**
     * Axis label suffix.
     */
    @Option
    public String labelsuffix;

    /**
     * The color of the grid lines.
     * <p>
     * Default: "#eee"
     */
    @Option
    public String minorgridcolor;

    /**
     * The number of minor grid ticks per major grid tick.
     * <p>
     * Default: 0
     */
    @Option
    public Integer minorgridcount;

    /**
     * The width (in px) of the grid lines.
     * <p>
     * Default: 1
     */
    @Option
    public Integer minorgridwidth;

    /**
     * Smoothing factor.
     * <p>
     * Constraints: &ge;0 and &le;1.3
     * <p>
     * Default: 1
     */
    @Option
    public Double smoothing;

    /**
     * Determines whether or not a line is drawn at along the starting value of this axis. If true,
     * the start line is drawn on top of the grid lines.
     */
    @Option
    public Boolean startline;

    /**
     * The line color of the start line.
     */
    @Option
    public String startlinecolor;

    /**
     * The width (in px) of the start line.
     * <p>
     * Default: 1
     */
    @Option
    public Integer startlinewidth;

    /**
     * An additional amount by which to offset the title from the tick labels, given in pixels.
     * <p>
     * Default: 10
     */
    @Option
    public Integer titleoffset;

}
