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
package org.fujion.plotly.plot;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.plotly.common.FontOptions;

/**
 *
 */
public class ContoursOptions extends Options {
    
    /**
     * Determines the coloring method for showing the contour values.
     */
    public enum ColoringEnum {

        /**
         * Coloring is done evenly between each contour level.
         */
        FILL,
        /**
         * A heatmap gradient coloring is applied between each contour level.
         */
        HEATMAP,
        /**
         * Coloring is done on the contour lines.
         */
        LINES,
        /**
         * No coloring is applied on this trace.
         */
        NONE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * Specifies the constraint operation.
     */
    public enum ConstraintOperationEnum {
        
        /**
         * Equal to.
         */
        EQ("="),
        /**
         * Greater than or equal to.
         */
        GE(">="),
        /**
         * Greater than.
         */
        GT(">"),
        /**
         * Inside, exclusive boundaries.
         */
        IEE("()"),
        /**
         * Inside, exclusive low, inclusive high.
         */
        IEI("(]"),
        /**
         * Inside, inclusive low, exclusive high.
         */
        IIE("[)"),
        /**
         * Inside, inclusive boundaries.
         */
        III("[]"),
        /**
         * Less than or equal to.
         */
        LE("<="),
        /**
         * Less than.
         */
        LT("<"),
        /**
         * Outside, exclusive boundaries.
         */
        OEE(")("),
        /**
         * Outside, exclusive low, inclusive high.
         */
        OEI(")["),
        /**
         * Outside, inclusive low, exclusive high.
         */
        OIE("]("),
        /**
         * Outside, inclusive boundaries.
         */
        OII("][");
        
        private final String value;

        ConstraintOperationEnum(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * Controls how data are represented within a contour plot.
     */
    public enum TypeEnum {

        /**
         * The data are represented as constraints with the invalid region shaded as specified by
         * "operation" and "value" parameters.
         */
        CONSTRAINT,
        /**
         * The data are represented as a contour plot with multiple levels displayed.
         */
        LEVELS;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * Determines the coloring method showing the contour values. Defaults to FILL.
     */
    @Option
    public ColoringEnum coloring;
    
    /**
     * The ending contour level value. Must be more than "start".
     */
    @Option
    public Integer end;
    
    /**
     * The font used for labeling the contour levels. The default color comes from the lines, if
     * shown. The default family and size come from "layout.font".
     */
    @Option
    public final FontOptions labelfont = new FontOptions();
    
    /**
     * The contour label formatting rule using d3 formatting mini-language which is very similar to
     * Python, see <a href="https://github.com/d3/d3-format/blob/master/README.md#locale_format">d3
     * formatting reference.</a>
     */
    @Option
    public String labelformat;
    
    /**
     * The constraint operation. Open vs closed intervals make no difference to constraint display,
     * but all versions are allowed for consistency with filter transforms.
     * <p>
     * Default: EQ
     */
    @Option
    public ConstraintOperationEnum operation;
    
    /**
     * Determines whether to label the contour lines with their values.
     */
    @Option
    public Boolean showlabels;
    
    /**
     * Determines whether or not the contour lines are drawn. Has an effect only if "coloring" is
     * set to FILL.
     */
    @Option
    public Boolean showlines;
    
    /**
     * The step between each contour level.
     * <p>
     * Constraints: &gt;0
     */
    @Option
    public Integer size;
    
    /**
     * The starting contour level value.
     * <p>
     * Constraints: Must be less than "end"
     */
    @Option
    public Integer start;
    
    /**
     * Controls how data are represented within a contour plot.
     */
    @Option
    public TypeEnum type;
    
    /**
     * The values of the constraint range as a an array of two numbers where the first is the lower
     * bound and the second is the upper bound. This form requires that "operation" be set to one of
     * the interval values.
     */
    @Option("value")
    public double[] value$array;
    
    /**
     * The value of the constraint boundary. This form requires that "operation" be set to one of
     * the comparison values.
     */
    @Option("value")
    public Double value$number;
    
    /**
     * Alternate form where value is a categorical coordinate string.
     */
    @Option("value")
    public String value$string;
    
}
