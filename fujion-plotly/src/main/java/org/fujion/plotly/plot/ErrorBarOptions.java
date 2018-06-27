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
package org.fujion.plotly.plot;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

public class ErrorBarOptions extends Options {
    
    /**
     * Determines the rule used to generate the error bars.
     */
    public enum TypeEnum {
        /**
         * The bar lengths are of a constant value.
         */
        CONSTANT,
        /**
         * The bar lengths are set with data set "array".
         */
        DATA,
        /**
         * The bar lengths correspond to a percentage of underlying data. Set this percentage in
         * "value".
         */
        PERCENT,
        /**
         * The bar lengths correspond to the square root of the underlying data.
         */
        SQRT;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * The data corresponding the length of each error bar. Values are plotted relative to the
     * underlying data.
     */
    @Option
    public int[] array;
    
    /**
     * The data corresponding the length of each error bar in the bottom (left) direction for
     * vertical (horizontal) bars Values are plotted relative to the underlying data.
     */
    @Option
    public int[] arrayminus;
    
    /**
     * The stroke color of the error bars.
     */
    @Option
    public String color;
    
    /**
     *
     */
    @Option
    public Boolean copy_ystyle;
    
    /**
     *
     */
    @Option
    public Boolean copy_zstyle;
    
    /**
     * Determines whether or not the error bars have the same length in both direction (top/bottom
     * for vertical bars, left/right for horizontal bars.
     */
    @Option
    public Boolean symmetric;
    
    /**
     * The thickness (in px) of the error bars.
     * <p>
     * Default: 2
     */
    @Option
    public Integer thickness;
    
    /**
     * Constraints: &ge;0
     * <p>
     * Default: 0
     */
    @Option
    public Integer traceref;
    
    /**
     * Constraints: &ge;0
     * <p>
     * Default: 0
     */
    @Option
    public Integer tracerefminus;

    /**
     * Determines the rule used to generate the error bars.
     */
    @Option
    public TypeEnum type;

    /**
     * The value of either the percentage (if "type" is set to "percent") or the constant (if "type"
     * is set to "constant") corresponding to the lengths of the error bars.
     * <p>
     * Default: 10
     */
    @Option
    public Integer value;

    /**
     * The value of either the percentage (if "type" is set to "percent") or the constant (if "type"
     * is set to "constant") corresponding to the lengths of the error bars in the bottom (left)
     * direction for vertical (horizontal) bars.
     * <p>
     * Default: 10
     */
    @Option
    public Integer valueminus;

    /**
     * Determines whether or not this set of error bars is visible.
     */
    @Option
    public Boolean visible;

    /**
     * The width (in px) of the cross-bar at both ends of the error bars.
     */
    @Option
    public Integer width;

}
