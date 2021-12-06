/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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
package org.fujion.common;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.ZoneOffset.UTC;

/**
 * Utility methods for managing dates.
 */
public class DateUtil {

    /**
     * Represents time units in order of increasing precision.
     */
    public enum TimeUnit {
        YEARS, MONTHS, WEEKS, DAYS, HOURS, MINUTES, SECONDS, MILLISECONDS
    }

    /**
     * Enum representing common date formats.
     */
    public enum Format {
        //@formatter:off
        WITH_TZ("dd-MMM-yyyy HH:mm zzz"),
        WITHOUT_TZ("dd-MMM-yyyy HH:mm"),
        WITHOUT_TIME("dd-MMM-yyyy"),
        WITHOUT_DATE("HH:mm"),
        HL7(HL7_DATE_TIME_PATTERN),
        HL7_WITHOUT_TIME(HL7_DATE_ONLY_PATTERN),
        JS_WITH_TZ("yyyy-MM-dd HH:mm zzz"),
        JS_WITHOUT_TZ("yyyy-MM-dd HH:mm"),
        JS_WITHOUT_TIME("yyyy-MM-dd"),
        TO_STRING("EEE MMM dd HH:mm:ss zzz yyyy");
        //@formatter:on

        private static final Map<ZoneId, DateTimeFormatter> formatterCache = new ConcurrentHashMap<>();

        private final boolean noTime;

        private final String pattern;

        private final DateTimeFormatter temporalFormatter;

        Format(String pattern) {
            this.pattern = pattern;
            this.noTime = !pattern.contains(":");
            temporalFormatter = DateTimeFormatter.ofPattern(pattern);
        }

        private TimeZone getTimeZone() {
            return noTime ? TimeZone.getDefault() : getLocalTimeZone();
        }

        /**
         * Returns the format pattern.
         *
         * @return The format pattern.
         */
        public String getPattern() {
            return pattern;
        }

        /**
         * Formats an input date.
         *
         * @param date The date to format.
         * @return The formatted date.
         */
        public String formatDate(Date date) {
            return date == null ? "" : FastDateFormat.getInstance(pattern, getTimeZone()).format(date);
        }

        public String formatDate(Temporal date) {
            if (date == null) {
                return "";
            }

            ZoneId zoneId = getTimeZone().toZoneId();
            DateTimeFormatter formatter = formatterCache.get(zoneId);

            if (formatter == null) {
                formatterCache.put(zoneId, formatter = temporalFormatter.withZone(zoneId));
            }

            return formatter.format(date);
        }

        /**
         * Parses an input value.
         *
         * @param value The value to parse.
         * @return The resulting date value if successful.
         * @throws ParseException Date parsing exception.
         */
        public Date parseAsDate(String value) throws ParseException {
            return parseDate(value, pattern);
        }

        /**
         * Parses an input value.
         *
         * @param value The value to parse.
         * @return The resulting date value if successful.
         * @throws ParseException Date parsing exception.
         */
        public LocalDate parseAsLocalDate(String value) throws ParseException {
            return toLocalDate(parseDate(value, pattern));
        }

        /**
         * Parses an input value.
         *
         * @param value The value to parse.
         * @return The resulting date value if successful.
         * @throws ParseException Date parsing exception.
         */
        public LocalDateTime parseAsLocalDateTime(String value) throws ParseException {
            return toLocalDateTime(parseDate(value, pattern));
        }

        /**
         * Parses an input value.
         *
         * @param value The value to parse.
         * @return The resulting date value if successful.
         * @throws ParseException Date parsing exception.
         */
        public OffsetDateTime parseAsOffsetDateTime(String value) throws ParseException {
            return toOffsetDateTime(parseDate(value, pattern));
        }
    }

    private static final String HL7_DATE_ONLY_PATTERN = "yyyyMMdd";

    private static final String HL7_DATE_TIME_PATTERN = HL7_DATE_ONLY_PATTERN + "HHmmssz";

    private static final String UNKNOWN = "Unknown";

    /*
     * Defines a regular expression pattern representing a permissible
     * extended style date that can be converted into a traditional date.
     *
     * Such a value starts with either 't' or 'n', and then optionally plus or
     * minus a numeric value of 'd' (days), 'm' (months), or 'y' (years).
     */
    private static final Pattern PATTERN_EXT_DATE = Pattern
            .compile("^\\s*[tn]\\s*([+-]\\s*[\\d]*\\s*[snhdmy]?)?\\s*$");

    /*
     * Defines a regular expression pattern representing a value ending in one
     * of the acceptable extended style date units (d, m, or y).
     */
    private static final Pattern PATTERN_SPECIFIES_UNITS = Pattern.compile("^.*[snhdmy]$");

    /*
     * Defines a regular expression pattern for extracting a numeric prefix from a string.
     */
    private static final Pattern PATTERN_NUMERIC_PREFIX = Pattern.compile("^-?[\\d.]+");

    private static final double[] MS_FP = new double[]{31557600000.0, 2592000000.0, 604800000.0, 86400000.0, 3600000.0,
            60000.0, 1000.0, 1.0};

    private static final long[] MS_LG = new long[]{31557600000L, 2592000000L, 604800000L, 86400000L, 3600000L, 60000L,
            1000L, 1L};

    /**
     * Labels for time units. TODO: externalize these for localization support.
     */
    public static String[][] TIME_UNIT = new String[][]{{"year", "years", "yr", "yrs"},
            {"month", "months", "mo", "mos"}, {"week", "weeks", "wk", "wks"}, {"day", "days", "day", "days"},
            {"hour", "hours", "hr", "hrs"}, {"minute", "minutes", "min", "mins"}, {"second", "seconds", "sec", "secs"},
            {"millisecond", "milliseconds", "ms", "ms"}};

    private static final ThreadLocal<DecimalFormat> decimalFormat = ThreadLocal.withInitial(() -> new DecimalFormat("##0.##"));

    // =============================== General Methods ===============================

    /**
     * Return elapsed time in ms to displayable format with units.
     *
     * @param elapsed Elapsed time in ms.
     * @return Elapsed time in displayable format.
     */
    public static String formatElapsed(double elapsed) {
        return formatElapsed(elapsed, true, false, false);
    }

    /**
     * Return elapsed time in ms to displayable format with units.
     *
     * @param elapsed  Elapsed time in ms.
     * @param minUnits Minimum units for return value (null = ms).
     * @return Elapsed time in displayable format.
     */
    public static String formatElapsed(
            double elapsed,
            TimeUnit minUnits) {
        return formatElapsed(elapsed, true, false, false, minUnits);
    }

    /**
     * Return elapsed time in ms to displayable format with units.
     *
     * @param elapsed     Elapsed time in ms.
     * @param pluralize   If true, pluralize units when appropriate.
     * @param abbreviated If true, use abbreviated form of units.
     * @param round       If true, round result to an integer.
     * @return Elapsed time in displayable format.
     */
    public static String formatElapsed(
            double elapsed,
            boolean pluralize,
            boolean abbreviated,
            boolean round) {
        return formatElapsed(elapsed, pluralize, abbreviated, round, null);
    }

    /**
     * Return elapsed time in ms to displayable format with units.
     *
     * @param elapsed     Elapsed time in ms.
     * @param pluralize   If true, pluralize units when appropriate.
     * @param abbreviated If true, use abbreviated form of units.
     * @param round       If true, round result to an integer.
     * @param minUnits    Minimum units for return value (null = ms).
     * @return Elapsed time in displayable format.
     */
    public static String formatElapsed(
            double elapsed,
            boolean pluralize,
            boolean abbreviated,
            boolean round,
            TimeUnit minUnits) {
        int index = (minUnits == null ? TimeUnit.MILLISECONDS : minUnits).ordinal();
        String prefix = "";

        if (elapsed < 0) {
            elapsed = -elapsed;
            prefix = "-";
        }

        for (int i = 0; i <= index; i++) {
            if (elapsed >= MS_FP[i] || i == index) {
                elapsed /= MS_FP[i];
                index = i;
                break;
            }
        }

        if (round) {
            elapsed = Math.floor(elapsed);
        }

        return prefix + decimalFormat.get().format(elapsed) + " "
                + getDurationUnits(index, pluralize && elapsed != 1.0, abbreviated);
    }

    /**
     * Parses an elapsed time string, returning time in milliseconds.
     *
     * @param value The string value to parse.
     * @return The elapsed time value in milliseconds.
     */
    public static double parseElapsed(String value) {
        return parseElapsed(value, TimeUnit.MILLISECONDS);
    }

    /**
     * Parses an elapsed time string, returning time in specified units.
     *
     * @param value The string value to parse.
     * @param units The units of the returned value (defaults to ms).
     * @return The elapsed time value in the requested units.
     */
    public static double parseElapsed(
            String value,
            TimeUnit units) {
        Matcher matcher = PATTERN_NUMERIC_PREFIX.matcher(value);

        if (!matcher.find()) {
            return 0;
        }

        int i = matcher.end();
        double result;

        try {
            result = Double.parseDouble(value.substring(0, i));
        } catch (NumberFormatException e) {
            return 0;
        }
        value = value.substring(i).trim().toLowerCase();

        for (TimeUnit tu : TimeUnit.values()) {
            for (String unit : TIME_UNIT[tu.ordinal()]) {
                if (unit.equals(value)) {
                    result *= MS_FP[tu.ordinal()];

                    if (units != null && units != TimeUnit.MILLISECONDS) {
                        result /= MS_FP[units.ordinal()];
                    }

                    return result;
                }
            }
        }

        return 0;
    }

    /**
     * Formats a duration in ms.
     *
     * @param duration Duration in ms.
     * @return Formatted duration.
     */
    public static String formatDuration(long duration) {
        return formatDuration(duration, null);
    }

    /**
     * Formats a duration in ms to the specified accuracy.
     *
     * @param duration Duration in ms.
     * @param accuracy Accuracy of output.
     * @return Formatted duration.
     */
    public static String formatDuration(
            long duration,
            TimeUnit accuracy) {
        return formatDuration(duration, accuracy, true, false);
    }

    /**
     * Formats a duration in ms to the specified accuracy.
     *
     * @param duration    Duration in ms.
     * @param accuracy    Accuracy of output.
     * @param pluralize   If true, pluralize units when appropriate.
     * @param abbreviated If true, use abbreviated form of units.
     * @return Formatted duration.
     */
    public static String formatDuration(
            long duration,
            TimeUnit accuracy,
            boolean pluralize,
            boolean abbreviated) {
        StringBuilder sb = new StringBuilder();

        if (duration < 0) {
            duration = -duration;
            sb.append('-');
        }

        accuracy = accuracy == null ? TimeUnit.MILLISECONDS : accuracy;
        int last = accuracy.ordinal();
        boolean empty = true;

        for (int i = 0; i <= last; i++) {
            long val = duration / MS_LG[i];
            duration -= val * MS_LG[i];

            if (val != 0 || (empty && i == last)) {
                if (!empty) {
                    sb.append(' ');
                } else {
                    empty = false;
                }

                sb.append(val).append(' ').append(getDurationUnits(i, pluralize && val != 1, abbreviated));
            }
        }

        return sb.toString();
    }

    private static String getDurationUnits(
            TimeUnit accuracy,
            boolean plural,
            boolean abbreviated) {
        return getDurationUnits(accuracy.ordinal(), plural, abbreviated);
    }

    private static String getDurationUnits(
            int index,
            boolean plural,
            boolean abbreviated) {
        int which = (plural ? 1 : 0) + (abbreviated ? 2 : 0);
        return TIME_UNIT[index][which];
    }

    private static int getAgeInYears(
            int birthYear,
            int birthMonth,
            int birthDay,
            int refYear,
            int refMonth,
            int refDay) {
        // If person has had a birthday already this year
        if (refMonth > birthMonth || (refMonth == birthMonth && refDay >= birthDay)) {
            return refYear - birthYear;
        }

        // If person has not yet had a birthday this year, subtract 1
        return refYear - birthYear - 1;
    }

    private static String formatUnits(
            long value,
            TimeUnit accuracy,
            boolean pluralize) {
        return value + " " + getDurationUnits(accuracy, pluralize && value != 1, true);
    }

    /**
     * Returns the user's time zone.
     *
     * @return The user's time zone.
     */
    public static TimeZone getLocalTimeZone() {
        return Localizer.getTimeZone();
    }

    /**
     * Returns zone id of the user's time zone.
     *
     * @return The zone id of the user's time zone.
     */
    public static ZoneId getLocalZoneId() {
        return getLocalTimeZone().toZoneId();
    }

    // =============================== java.util.Date Methods ===============================

    /**
     * Clones a date.
     *
     * @param date Date to clone.
     * @return A clone of the original date, or null if the original date was null.
     */
    public static Date cloneDate(Date date) {
        return date == null ? null : new Date(date.getTime());
    }

    /**
     * Adds specified number of days to date and, optionally strips the time component.
     *
     * @param date       Date value to process.
     * @param daysOffset # of days to add.
     * @param stripTime  If true, strip the time component.
     * @return Input value the specified operations applied.
     */
    public static Date addDays(
            Date date,
            int daysOffset,
            boolean stripTime) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false); // Make sure the calendar will not perform
        // automatic correction.
        calendar.setTime(date); // Set the time of the calendar to the given
        // date.

        if (stripTime) { // Remove the hours, minutes, seconds and milliseconds.
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }

        calendar.add(Calendar.DAY_OF_MONTH, daysOffset);
        return calendar.getTime();

    }

    /**
     * Strips the time component from a date.
     *
     * @param date Original date.
     * @return Date without the time component.
     */
    public static Date stripTime(Date date) {
        return addDays(date, 0, true);
    }

    /**
     * Returns the input date with the time set to the end of the day.
     *
     * @param date Original date.
     * @return Date with time set to end of day.
     */
    public static Date endOfDay(Date date) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * Returns a date with the current time.
     *
     * @return Current date and time.
     */
    public static Date now() {
        return new Date();
    }

    /**
     * Returns a date with the current day (no time).
     *
     * @return Current date.
     */
    public static Date today() {
        return stripTime(now());
    }

    /**
     * Compares two dates. Allows nulls.
     *
     * @param date1 First date to compare.
     * @param date2 Second date to compare.
     * @return Result of comparison.
     */
    public static int compare(
            Date date1,
            Date date2) {
        long diff = date1 == date2 ? 0 : date1 == null ? -1 : date2 == null ? 1 : date1.getTime() - date2.getTime();
        return diff < 0 ? -1 : diff > 0 ? 1 : 0;
    }

    /**
     * Converts a date/time value to a string, using the format dd-mmm-yyyy hh:mm. Because we cannot
     * determine the absence of a time from a time of 24:00, we must assume a time of 24:00 means
     * that no time is present and strip that from the return value.
     *
     * @param date Date value to convert.
     * @return Formatted string representation of the specified date, or an empty string if date is
     *         null.
     */
    public static String formatDate(Date date) {
        return formatDate(date, false, false);
    }

    /**
     * Converts a date/time value to a string, using the format dd-mmm-yyyy hh:mm. Because we cannot
     * determine the absence of a time from a time of 24:00, we must assume a time of 24:00 means
     * that no time is present and strip that from the return value.
     *
     * @param date         Date value to convert.
     * @param showTimezone If true, time zone information is also appended.
     * @return Formatted string representation of the specified date, or an empty string if date is
     *         null.
     */
    public static String formatDate(
            Date date,
            boolean showTimezone) {
        return formatDate(date, showTimezone, false);
    }

    /**
     * Converts a date/time value to a string, using the format dd-mmm-yyyy hh:mm. Because we cannot
     * determine the absence of a time from a time of 24:00, we must assume a time of 24:00 means
     * that no time is present and strip that from the return value.
     *
     * @param date         Date value to convert
     * @param showTimezone If true, time zone information is also appended.
     * @param ignoreTime   If true, the time component is ignored.
     * @return Formatted string representation of the specified date, or an empty string if date is
     *         null.
     */
    public static String formatDate(
            Date date,
            boolean showTimezone,
            boolean ignoreTime) {
        ignoreTime = ignoreTime || !hasTime(date);
        Format format = ignoreTime ? Format.WITHOUT_TIME : showTimezone ? Format.WITH_TZ : Format.WITHOUT_TZ;
        return format.formatDate(date);
    }

    /**
     * Same as formatDate(Date, boolean) except replaces the time separator with the specified
     * string.
     *
     * @param date          Date value to convert
     * @param timeSeparator String to use in place of default time separator
     * @return Formatted string representation of the specified date using the specified time
     *         separator.
     */
    public static String formatDate(
            Date date,
            String timeSeparator) {
        return formatDate(date).replaceFirst(" ", timeSeparator);
    }

    /**
     * Convert a date to HL7 format.
     *
     * @param date Date to convert.
     * @return The HL7-formatted date.
     */
    public static String toHL7(Date date) {
        Format format = hasTime(date) ? Format.HL7_WITHOUT_TIME : Format.HL7;
        return format.formatDate(date);
    }

    /**
     * Convert a date to ISO format.
     *
     * @param date Date to convert.
     * @return The ISO-formatted date.
     */
    public static String toISODate(Date date) {
        return DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(date);
    }

    /**
     * Convert a date to ISO format.
     *
     * @param date Date to convert.
     * @return The ISO-formatted date.
     */
    public static String toISODateTime(Date date) {
        return DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.format(date);
    }

    /**
     * Returns true if the date has an associated time.
     *
     * @param date Date value to check.
     * @return True if the date has a time component.
     */
    public static boolean hasTime(Date date) {
        if (date == null) {
            return false;
        }

        long time1 = date.getTime();
        long time2 = stripTime(date).getTime();
        return time1 != time2; // Do not use "Date.equals" since date may be of type Timestamp.
    }

    /**
     * <p>
     * Convert a string value to a date/time. Attempts to convert using the four locale-specific
     * date formats (FULL, LONG, MEDIUM, SHORT). If these fail, looks to see if T+/-offset or
     * N+/-offset is used.
     * </p>
     * <p>
     * TODO: probably we can make the "Java parse" portion a bit smarter by using a better variety
     * of formats, maybe to catch Euro-style input as well.
     * </p>
     * <p>
     * TODO: probably we can add something like "t+d" or "t-y" as valid cases; in these scenarios,
     * the coefficient was omitted and could be defaulted to 1.
     * </p>
     *
     * @param s <code>String</code> containing value to be converted.
     * @return <code>Date</code> object corresponding to the input value, or <code>null</code> if
     *         the parsing failed to resolve a valid Date.
     */
    public static Date parseDate(String s) {
        Date result = null;

        if (s != null && !s.isEmpty()) {
            s = s.toLowerCase(); // make lc

            if ((PATTERN_EXT_DATE.matcher(s)).matches()) { // is an extended date?
                try {
                    s = s.replaceAll("\\s+", ""); // strip space since they not
                    // delim
                    String _k = s.substring(1); // _k will ultimately be the multiplier value
                    char k = 'd'; // k = s, n, h, d (default), m, or y

                    if (1 == s.length()) {
                        _k = "0";
                    } else {
                        if ((PATTERN_SPECIFIES_UNITS.matcher(s)).matches()) {
                            _k = s.substring(1, s.length() - 1);
                            k = s.charAt(s.length() - 1);
                        }
                    }

                    if ('+' == _k.charAt(0)) { // clip positive coefficient...
                        _k = _k.substring(1);
                    }

                    int field = Calendar.DAY_OF_YEAR;
                    int offset = Integer.parseInt(_k);
                    Calendar c = Calendar.getInstance();
                    c.setLenient(false);

                    if (s.charAt(0) == 't') {
                        c.setTime(DateUtil.today());
                    }

                    switch (k) {
                        case 'y': // years
                            field = Calendar.YEAR;
                            break;
                        case 'm': // months
                            field = Calendar.MONTH;
                            break;
                        case 'h': // hours
                            field = Calendar.HOUR_OF_DAY;
                            break;
                        case 'n': // minutes
                            field = Calendar.MINUTE;
                            break;
                        case 's': // seconds
                            field = Calendar.SECOND;
                            break;
                    }

                    c.add(field, offset);
                    result = c.getTime();
                    // format
                } catch (Exception e) {
                    return null; // found unparseable date (e.g. t-y)
                }
            } else {
                result = tryParse(s);

                if (result != null) {
                    return result;
                }

                s = s.replaceAll("[.-]", "/"); // dots, dashes to slashes
                result = tryParse(s);

                if (result != null) {
                    return result;
                }

                s = s.replaceAll("\\s", "/"); // last chance to parse: spaces to
                result = tryParse(s); // slashes!
            }
        }

        return result;
    }

    /**
     * Attempts to parse an input value using one of several patterns.
     *
     * @param value    String to parse.
     * @param patterns Patterns to be tried in succession until parsing succeeds.
     * @return The resulting date value.
     * @throws ParseException Date parsing exception.
     */
    public static Date parseDate(
            String value,
            String... patterns) throws ParseException {
        return DateUtils.parseDate(value, patterns);
    }

    /**
     * Attempts to parse a string containing a date representation using several different date
     * patterns.
     *
     * @param value String to parse
     * @return If the parsing was successful, returns the date value represented by the input value.
     *         Otherwise, returns null.
     */
    private static Date tryParse(String value) {
        for (Format format : Format.values()) {
            try {
                return format.parseAsDate(value);
            } catch (Exception e) {
                // NOP
            }
        }

        for (int i = 3; i >= 0; i--) {
            try {
                return DateFormat.getDateInstance(i).parse(value);
            } catch (Exception e) {
                // NOP
            }
        }

        return null;
    }

    /**
     * <p>
     * Returns age as a formatted string expressed in days, months, or years, depending on whether
     * person is an infant (&lt; 2 mos), toddler (&gt; 2 mos, &lt; 2 yrs), or more than 2 years old.
     * </p>
     *
     * @param dob Date of person's birth
     * @return the age display string
     */
    public static String formatAge(Date dob) {
        return formatAge(dob, true, null);
    }

    /**
     * <p>
     * Returns age as a formatted string expressed in days, months, or years, depending on whether
     * person is an infant (&lt; 2 mos), toddler (&gt; 2 mos, &lt; 2 yrs), or more than 2 years old.
     * </p>
     * <p>
     * Allows the caller to specify an &quot;as-of&quot; date. The calculated age will be as-of the
     * provided date, rather than as-of the current date.
     * </p>
     * <p>
     * Allows the caller to specify whether or not to pluralize the age units in the age display
     * string.
     * </p>
     *
     * @param dob       Date of person's birth
     * @param pluralize If true, pluralize the age units in the age display string.
     * @param refDate   The date as of which to calculate the Person's age (null means today).
     * @return the age display string
     */
    public static String formatAge(
            Date dob,
            boolean pluralize,
            Date refDate) {
        if (dob == null) {
            return UNKNOWN;
        }

        Calendar asOf = Calendar.getInstance();
        asOf.setTimeInMillis(refDate == null ? System.currentTimeMillis() : refDate.getTime());
        Calendar bd = Calendar.getInstance();
        bd.setTime(dob);
        long birthDateInDays = (asOf.getTimeInMillis() - bd.getTimeInMillis()) / 1000 / 60 / 60 / 24;

        if (birthDateInDays < 0) {
            return UNKNOWN;
        }

        if (birthDateInDays <= 1) {
            return "newborn";
        }

        // If person is less than 2 months old, then display age in days
        if (birthDateInDays <= 60) {
            return formatUnits(birthDateInDays, TimeUnit.DAYS, pluralize);
        }

        int birthYear = bd.get(Calendar.YEAR);
        int birthMonth = bd.get(Calendar.MONTH);
        int birthDay = bd.get(Calendar.DATE);
        int refYear = asOf.get(Calendar.YEAR);
        int refMonth = asOf.get(Calendar.MONTH);
        int refDay = asOf.get(Calendar.DATE);

        if (birthDateInDays <= 730) {
            // If person is more than 2 months but less than 2 years then display age in months
            if (refMonth >= birthMonth && refDay >= birthDay) {
                // If person has had a birthday already this year
                return formatUnits((refYear - birthYear) * 12L + refMonth - birthMonth, TimeUnit.MONTHS, pluralize);
            }
            // then age in months = # years old * 12 + months so far this year
            // If person has not yet had a birthday this year, subtract 1 month
            return formatUnits((refYear - birthYear) * 12L + refMonth - birthMonth - 1, TimeUnit.MONTHS, pluralize);
        }
        // If person is more than 2 years old then display age in years
        return formatUnits(getAgeInYears(birthYear, birthMonth, birthDay, refYear, refMonth, refDay), TimeUnit.YEARS,
                pluralize);
    }

    /**
     * Converts day, month, and year to a date.
     *
     * @param day   Day of month.
     * @param month Month (1=January, etc.)
     * @param year  Year (4 digit).
     * @return Date instance.
     */
    public static Date toDate(
            int day,
            int month,
            int year) {
        return toDate(day, month, year, 0, 0, 0);
    }

    /**
     * Converts day, month, year and time parameters to a date.
     *
     * @param day   Day of month.
     * @param month Month (1=January, etc.)
     * @param year  Year (4 digit).
     * @param hr    Hour of day.
     * @param min   Minutes past the hour.
     * @param sec   Seconds past the minute.
     * @return Date instance.
     */
    public static Date toDate(
            int day,
            int month,
            int year,
            int hr,
            int min,
            int sec) {
        Calendar cal = Calendar.getInstance(Localizer.getTimeZone());
        cal.set(year, month - 1, day, hr, min, sec);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // =============================== java.time.Temporal Methods ===============================

    /**
     * Converts a date/time value to a string, using the format dd-mmm-yyyy hh:mm.
     *
     * @param temporal Date/time value to convert.
     * @return Formatted string representation of the specified date/time, or an empty string if input is
     *         null or of an unsupported type..
     */
    public static String formatDate(Temporal temporal) {
        Format format = null;

        if (temporal instanceof LocalDate) {
            format = Format.WITHOUT_TIME;
        } else if (temporal instanceof LocalDateTime) {
            format = Format.WITHOUT_TZ;
        } else if (temporal instanceof LocalTime) {
            format = Format.WITHOUT_DATE;
        } else if (temporal instanceof OffsetDateTime) {
            format = Format.WITHOUT_TZ;
        }

        return format == null ? "" : format.formatDate(temporal);
    }

    /**
     * Convert a date to ISO format.
     *
     * @param date Date to convert.
     * @return The ISO-formatted date.
     */
    public static String toISODate(Temporal date) {
        return DateTimeFormatter.ISO_LOCAL_DATE.format(date);
    }

    /**
     * Convert a date/time to ISO format.
     *
     * @param date Date to convert.
     * @return The ISO-formatted date.
     */
    public static String toISODateTime(Temporal date) {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(date);
    }

    /**
     * Converts a java.util.Date to a LocalDate. Uses the system's default time zone.
     *
     * @param date The java.util.Date to convert.
     * @return The equivalent LocalDate.
     */
    public static LocalDate toLocalDate(Date date) {
        return date == null ? null : date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * Converts a java.util.Date to a LocalDateTime using the system's default time zone.
     *
     * @param date The java.util.Date to convert.
     * @return The equivalent LocalDateTime.
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return date == null ? null : date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * Converts a java.util.Date to an OffsetDateTime.
     *
     * @param date The java.util.Date to convert.
     * @return The equivalent OffsetDateTime.
     */
    public static OffsetDateTime toOffsetDateTime(Date date) {
        return date == null ? null : OffsetDateTime.ofInstant(date.toInstant(), UTC);
    }

    /**
     * Converts a Temporal value to a java.util.Date.
     *
     * @param temporal The Temporal to convert.
     * @return The equivalent java.util.Date.
     */
    public static Date toDate(Temporal temporal) {
        if (temporal == null) {
            return null;
        } else if (temporal instanceof LocalDateTime) {
            return toDate((LocalDateTime) temporal);
        } else if (temporal instanceof LocalDate) {
            return toDate((LocalDate) temporal);
        } else if (temporal instanceof OffsetDateTime) {
            return toDate((OffsetDateTime) temporal);
        }

        return Assert.fail("Unsupported date type: %s", temporal.getClass());
    }

    /**
     * Converts a LocalDate to a java.util.Date using the local timezone.
     *
     * @param date The date to convert.
     * @return The equivalent java.util.Date.
     */
    public static Date toDate(LocalDate date) {
        return date == null ? null : Date.from(date.atStartOfDay(getLocalZoneId()).toInstant());
    }

    /**
     * Converts a LocalDateTime to a java.util.Date using the local timezone.
     *
     * @param date The date to convert.
     * @return The equivalent java.util.Date.
     */
    public static Date toDate(LocalDateTime date) {
        return date == null ? null : Date.from(date.atZone(getLocalZoneId()).toInstant());
    }

    /**
     * Converts an OffsetDateTime to a java.util.Date using the local timezone.
     *
     * @param date The date to convert.
     * @return The equivalent java.util.Date.
     */
    public static Date toDate(OffsetDateTime date) {
        return date == null ? null : Date.from(date.toInstant());
    }

    /**
     * <p>
     * Returns age as a formatted string expressed in days, months, or years, depending on whether
     * person is an infant (&lt; 2 mos), toddler (&gt; 2 mos, &lt; 2 yrs), or more than 2 years old.
     * </p>
     *
     * @param dob Date of person's birth
     * @return the age display string
     */
    public static String formatAge(Temporal dob) {
        return formatAge(dob, true, null);
    }

    /**
     * <p>
     * Returns age as a formatted string expressed in days, months, or years, depending on whether
     * person is an infant (&lt; 2 mos), toddler (&gt; 2 mos, &lt; 2 yrs), or more than 2 years old.
     * </p>
     * <p>
     * Allows the caller to specify an &quot;as-of&quot; date. The calculated age will be as-of the
     * provided date, rather than as-of the current date.
     * </p>
     * <p>
     * Allows the caller to specify whether or not to pluralize the age units in the age display
     * string.
     * </p>
     *
     * @param dob       Date of person's birth
     * @param pluralize If true, pluralize the age units in the age display string.
     * @param refDate   The date as of which to calculate the Person's age (null means today).
     * @return the age display string
     */
    public static String formatAge(
            Temporal dob,
            boolean pluralize,
            Temporal refDate) {
        return formatAge(toDate(dob), pluralize, toDate(refDate));
    }

    /**
     * Convert a string value to a LocalDateTime.
     *
     * @param s The value to be converted.
     * @return A LocalDateTime corresponding to the input value, or <code>null</code> if parsing failed.
     */
    public static LocalDateTime parseLocalDate(String s) {
        return toLocalDateTime(parseDate(s));
    }

    /**
     * Convert a string value to an OffsetDateTime.
     *
     * @param s The value to be converted.
     * @return An OffsetDateTime corresponding to the input value, or <code>null</code> if parsing failed.
     */
    public static OffsetDateTime parseOffsetDateTime(String s) {
        return toOffsetDateTime(parseDate(s));
    }

    /**
     * Enforce static class.
     */
    private DateUtil() {
    }

}
