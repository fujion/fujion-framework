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
package org.fujion.ancillary;

import org.fujion.annotation.Option;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Text input formats supported by Textbox component.
 */
public abstract class TextInputOptions extends Options {

    /**
     * Specifies how input is converted or restricted.
     */
    public enum InputMode {
        /**
         * Only numeric input is allowed.
         */
        NUMERIC_ONLY("numericOnly"),
        /**
         * All text is converted to upper case.
         */
        UPPERCASE("uppercase"),
        /**
         * All text is converted to lower case.
         */
        LOWERCASE("lowercase");

        public final String option;

        InputMode(String option) {
            this.option = option;
        }

    }

    @Option
    private boolean swapHiddenInput = true;

    /**
     * Value to be prepended to the input. It can't be removed or changed in the input field.
     * The default is no prefix.
     */
    @Option
    public String prefix;

    /**
     * If true, will only add the prefix once the user enters values. Useful if you need to use placeholders.
     * The default is false;
     */
    @Option
    public Boolean noImmediatePrefix;

    /**
     * Indicates the delimiter to be used in formatting.  The default depends on the option type.
     */
    @Option
    public String delimiter;

    /**
     * If true, will add the delimiter only when the user starts typing the next group section.
     * Default is false;
     */
    @Option
    public Boolean delimiterLazyShow;

    /**
     * Determines how text input is converted or restricted.
     *
     * Default is no action.
     */
    @Option(
            value = "${value.option}",
            convertUsing = "true"
    )
    public InputMode mode;

    public static class TextOptions extends TextInputOptions {

        /**
         * Indicates the sizes of input blocks.  Delimiters are inserted between each block.
         */
        @Option
        public int[] blocks;

        /**
         * Delimiters to be used in formatting for each block.  If specified, the single delimiter property
         * value is ignored.
         */
        @Option
        public String[] delimiters;

    }

    /**
     * Options for formatting credit card numbers.
     */
    public static class CreditCardOptions extends TextInputOptions {

        @Option
        private final boolean creditCard = true;

        @Option
        public Boolean creditCardStrictMode;

    }

    /**
     * Options for formatting phone numbers.
     */
    public static class PhoneNumberOptions extends TextInputOptions {

        @Option
        private final boolean phone = true;

        @Option
        public String phoneRegionCode;

    }

    /**
     * Options for formatting dates.
     */
    public static class DateOptions extends TextInputOptions {

        @Option
        private final boolean date = true;

        /**
         * A list representing the date pattern.
         *
         * Note: a leading 0 before date and month is required. To indicate what patterns it should apply, you can use: 'Y', 'y', 'm' and 'd'.
         *
         * Default value: ['d', 'm', 'Y']
         */
        @Option
        public final List<String> datePattern = new ArrayList<>();

        /**
         * The lower date boundary.  Default is no lower boundary.
         */
        @Option(convertUsing = "DateUtil.toISODate(value)")
        public Date dateMin;

        /**
         * The upper date boundary.  Default is no upper boundary.
         */
        @Option(convertUsing = "DateUtil.toISODate(value)")
        public Date dateMax;

    }

    public static class TimeOptions extends TextInputOptions {

        @Option
        private boolean time = true;

        /**
         * Indicates the time pattern. Since it's an input field, a leading 0 before hour, minute and second
         * is required. To indicate what patterns it should apply, you can use: 'h', 'm' and 's'.
         */
        @Option
        public String[] timePattern;

        /**
         * If true (the default), use military time.
         */
        @Option(
                value = "timeFormat",
                convertUsing = "value ? '24' : '12'")
        public Boolean militaryTime;

    }

    public static class NumericOptions extends TextInputOptions {

        /**
         * Specifies how thousands are grouped.
         */
        public enum ThousandsGroupStyle {
            /**
             * Thousand numbering group style. It groups numbers in thousands and the delimiter occurs every 3 digits.
             * For example: 1,234,567.89
             */
            THOUSAND,
            /**
             * Indian numbering group style. It groups the rightmost 3 digits in a similar manner to regular way
             * but then groups every 2 digits thereafter. For example: 12,34,567.89
             */
            LAKH,
            /**
             * Chinese numbering group style. It groups numbers in 10-thousand(万, 萬) and the delimiter occurs
             * every 4 digits. For example: 123,4567.89
             */
            WAN,
            /**
             * Does not group thousands. For example, 1234567.89
             */
            NONE;

            @Override
            public String toString() {
                return super.toString().toLowerCase();
            }
        };

        @Option
        private boolean numeral = true;


        /**
         * Indicates the thousands separator grouping style.
         * Default is THOUSAND.
         */
        @Option("numeralThousandsGroupStyle")
        public ThousandsGroupStyle thousandsGroupStyle;

        /**
         * Indicates the integer scale.
         */
        @Option("numeralIntegerScale")
        public Integer integerScale;

        /**
         * Indicates the decimal scale.
         */
        @Option("numeralDecimalScale")
        public Integer decimalScale;

        /**
         * The numeral decimal mark. Can be different in handwriting, and for delimiter as well.
         * Default value is ".".
         */
        @Option("numeralDecimalMark")
        public String decimalMark;

        /**
         * If true, only positive values are allowed.
         * Default is false;
         */
        @Option("numeralPositiveOnly")
        public Boolean positiveOnly;

        /**
         * Indicates if the sign of the number should appear before the prefix.
         * Default is false.
         */
        @Option
        public Boolean signBeforePrefix;

        /**
         * If true, the prefix should appear after the number.
         * Default is false.
         */
        @Option
        public Boolean tailPrefix;

        /**
         * If true, leading zeroes are removed.
         * Default is false;
         */
        @Option
        public Boolean stripLeadingZeroes;
    }
}
