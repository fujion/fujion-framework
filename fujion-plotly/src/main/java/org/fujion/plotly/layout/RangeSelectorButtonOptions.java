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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Layout options for range selector buttons.
 */
public class RangeSelectorButtonOptions extends Options {
    
    /**
     * Units for step operation.
     */
    public enum StepEnum {
        ALL, DAY, HOUR, MINUTE, MONTH, SECOND, YEAR;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * The range update mode. For example, with "step" set to "year" and "count" set to "1" the
     * range update shifts the start of the range back to January 01 of the current year. Month and
     * year "todate" are currently available only for the built-in (Gregorian) calendar.
     */
    public enum StepModeEnum {
        /**
         * The range update shifts the start of range back "count" times "step" milliseconds.
         */
        BACKWARD,
        /**
         * The range update shifts the start of range back to the first timestamp from "count" times
         * "step" milliseconds back.
         */
        TODATE;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * The number of steps to take to update the range. Use with "step" to specify the update
     * interval.
     * <p>
     * Constraints: &ge;0
     * <p>
     * Default: 1
     */
    @Option
    public Integer count;
    
    /**
     * The text label to appear on the button.
     */
    @Option
    public String label;
    
    /**
     * The unit of measurement that the "count" value will set the range by.
     * <p>
     * Default: MONTH
     */
    @Option
    public StepEnum step;

    /**
     * The range update mode.
     */
    @Option
    public StepModeEnum stepmode;
}
