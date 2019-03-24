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
package org.fujion.highcharts;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * For a date/time axis, the scale will automatically adjust to the appropriate unit. This class
 * gives the default string representations used for each unit.
 *
 * <pre>
 * Supported format specifiers are:
 *
 * %a: Short weekday, like 'Mon'.
 * %A: Long weekday, like 'Monday'.
 * %d: Two digit day of the month, 01 to 31.
 * %e: Day of the month, 1 through 31.
 * %b: Short month, like 'Jan'.
 * %B: Long month, like 'January'.
 * %m: Two digit month number, 01 through 12.
 * %y: Two digits year, like 09 for 2009.
 * %Y: Four digits year, like 2009.
 * %H: Two digits hours in 24h format, 00 through 23.
 * %I: Two digits hours in 12h format, 00 through 11.
 * %l  Hours in 12h format, 1 through 11. (Lower case L)
 * %M: Two digits minutes, 00 through 59.
 * %p: Upper case AM or PM.
 * %P: Lower case AM or PM.
 * %S: Two digits seconds, 00 through 59
 * </pre>
 */
public class DateTimeFormatOptions extends Options {

    /**
     * Axis defaults: "%H:%M:%S.%L" <br>
     * Tooltip defaults: "%A, %b %e, %H:%M:%S.%L"
     */
    @Option
    public String millisecond;

    /**
     * Axis defaults: "%H:%M:%S" <br>
     * Tooltip defaults: "%A, %b %e, %H:%M:%S"
     */
    @Option
    public String second;

    /**
     * Axis defaults: "%H:%M" <br>
     * Tooltip defaults: "%A, %b %e, %H:%M"
     */
    @Option
    public String minute;

    /**
     * Axis defaults: "%H:%M" <br>
     * Tooltip defaults: "%A, %b %e, %H:%M"
     */
    @Option
    public String hour;

    /**
     * Axis defaults: "%e. %b" <br>
     * Tooltip defaults: "%A, %b %e, %Y"
     */
    @Option
    public String day;

    /**
     * Axis defaults: "%e. %b" <br>
     * Tooltip defaults: "Week from %A, %b %e, %Y"
     */
    @Option
    public String week;

    /**
     * Axis defaults: "%b \'%y" <br>
     * Tooltip defaults: "%B %Y"
     */
    @Option
    public String month;

    /**
     * Axis defaults: "%Y" <br>
     * Tooltip defaults: "%Y"
     */
    @Option
    public String year;

    /**
     * Sets all formats to the specified value.
     *
     * @param value Format value.
     */
    public void setAllFormats(String value) {
        setDateFormats(value);
        setTimeFormats(value);
    }

    /**
     * Sets all date formats to specified value.
     *
     * @param value Date format value.
     */
    public void setDateFormats(String value) {
        day = value;
        month = value;
        week = value;
        year = value;
    }

    /**
     * Sets all time formats to specified value.
     *
     * @param value Time format value.
     */
    public void setTimeFormats(String value) {
        hour = value;
        millisecond = value;
        minute = value;
        second = value;
    }

}
