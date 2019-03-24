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
package org.fujion.chartjs.common;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Time-related options.
 */
public class TimeOptions extends Options {
    
    public enum TimeUnitEnum {
        DAY, HOUR, MILLISECOND, MINUTE, MONTH, QUARTER, SECOND, WEEK, YEAR;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * Determines how different time units are displayed. See
     * <a href="http://momentjs.com/docs/#/displaying/format/">moment.js</a> for the allowable
     * format strings.
     * <p>
     * Default:
     * <table>
     * <caption>Time Unit Defaults</caption>
     * <tr>
     * <th>Unit</th>
     * <th>Default</th>
     * <th>Example</th>
     * </tr>
     * <tr>
     * <td>MILLISECOND</td>
     * <td>'h:mm:ss.SSS a'</td>
     * <td>11:20:01.123 AM</td>
     * </tr>
     * <tr>
     * <td>SECOND</td>
     * <td>'h:mm:ss a'</td>
     * <td>11:20:01 AM</td>
     * </tr>
     * <tr>
     * <td>MINUTE</td>
     * <td>'h:mm a'</td>
     * <td>11:20 AM</td>
     * </tr>
     * <tr>
     * <td>HOUR</td>
     * <td>'hA'</td>
     * <td>11AM</td>
     * </tr>
     * <tr>
     * <td>DAY</td>
     * <td>'MMM D'</td>
     * <td>Sep 4</td>
     * </tr>
     * <tr>
     * <td>WEEK</td>
     * <td>'ll'</td>
     * <td>Sep 4 2015</td>
     * </tr>
     * <tr>
     * <td>MONTH</td>
     * <td>'MMM YYYY'</td>
     * <td>Sep 2015</td>
     * </tr>
     * <tr>
     * <td>QUARTER</td>
     * <td>'[Q]Q - YYYY'</td>
     * <td>Q3 - 2015</td>
     * </tr>
     * <tr>
     * <td>YEAR</td>
     * <td>'YYYY'</td>
     * <td>2015</td>
     * </tr>
     * </table>
     */
    @Option
    public String displayFormats;

    /**
     * If true and the unit is set to 'week', then the first day of the week will be Monday.
     * Otherwise, it will be Sunday.
     * <p>
     * Default: false
     */
    @Option
    public Boolean isoWeekday;

    /**
     * The data maximum. For supported formats, see
     * <a href="http://momentjs.com/docs/#/parsing/">moment.js</a>.
     */
    @Option
    public String max;

    /**
     * The data minimum. For supported formats, see
     * <a href="http://momentjs.com/docs/#/parsing/">moment.js</a>.
     */
    @Option
    public String min;

    /**
     * The minimum display format to be used for a time unit.
     * <p>
     * Default: MILLISECOND
     */
    @Option
    public TimeUnitEnum minUnit;

    /**
     * Custom function for parsing dates.
     */
    @Option(value="parser", convertTo = JavaScript.class)
    public String parser$function;

    /**
     * Custom format for parsing dates.
     */
    @Option("parser")
    public String parser$string;

    /**
     * Dates will be rounded to the start of this unit.
     */
    @Option
    public TimeUnitEnum round;

    /**
     * The number of units between grid lines.
     * <p>
     * Default: 1
     */
    @Option
    public Integer stepSize;

    /**
     * The momentjs format string to use for the tooltip.
     */
    @Option
    public String tooltipFormat;

    /**
     * Force the unit to be a certain type.
     */
    @Option
    public TimeUnitEnum unit;

}
