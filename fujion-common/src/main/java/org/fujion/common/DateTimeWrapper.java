package org.fujion.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Date;

/**
 * Wrapper for local date/time and legacy date.
 */
public class DateTimeWrapper implements Comparable<DateTimeWrapper> {

    private final Temporal temporal;

    /**
     * Returns a wrapper for the current date and time.
     *
     * @return Current date and time.
     */
    public static DateTimeWrapper now() {
        return new DateTimeWrapper(LocalDateTime.now());
    }

    /**
     * Returns a wrapper for the current date.
     *
     * @return Current date.
     */
    public static DateTimeWrapper today() {
        return new DateTimeWrapper(LocalDate.now());
    }

    /**
     * Returns a wrapper for the current time.
     *
     * @return The current time.
     */
    public static DateTimeWrapper time() {
        return new DateTimeWrapper(LocalTime.now());
    }

    public static DateTimeWrapper parse(String value) {
        Date date = DateUtil.parseDate(value);
        MiscUtil.assertTrue(date != null, () -> "Unable to parse input '" + value + "'.");
        return new DateTimeWrapper(date);
    }

    public DateTimeWrapper(LocalDate date) {
        this.temporal = validateTemporal(date);
    }

    public DateTimeWrapper(LocalDateTime datetime) {
        this.temporal = validateTemporal(datetime);
    }

    public DateTimeWrapper(LocalTime time) {
        this.temporal = validateTemporal(time);
    }

    public DateTimeWrapper(Date date) {
        this.temporal = validateTemporal(DateUtil.hasTime(date) ? DateUtil.toLocalDateTime(date) : DateUtil.toLocalDate(date));
    }

    public boolean hasDate() {
        return temporal instanceof LocalDate || temporal instanceof LocalDateTime;
    }

    public boolean hasTime() {
        return temporal instanceof LocalDateTime || temporal instanceof LocalTime;
    }

    public boolean hasDateAndTime() {
        return temporal instanceof LocalDateTime;
    }

    public Date getLegacyDate() {
        validateDate();
        return DateUtil.toDate(temporal);
    }

    public LocalDateTime getDateTime() {
        validateDate();
        validateTime();
        return (LocalDateTime) temporal;
    }

    public LocalDate getDate() {
        validateDate();
        return temporal instanceof LocalDateTime ? ((LocalDateTime) temporal).toLocalDate() : (LocalDate) temporal;
    }

    public LocalTime getTime() {
        validateTime();
        return temporal instanceof LocalDateTime ? ((LocalDateTime) temporal).toLocalTime() : (LocalTime) temporal;
    }

    private Temporal validateTemporal(Temporal temporal) {
        MiscUtil.assertTrue(temporal != null, "Date/time value must not be null.");
        return temporal;
    }

    private void validateDate() {
        MiscUtil.assertState(hasDate(), "No date component available.");
    }

    private void validateTime() {
        MiscUtil.assertState(hasTime(), "No time component available.");
    }

    @Override
    public int compareTo(DateTimeWrapper w) {
        Temporal temporal2 = w.temporal;

        if (temporal instanceof LocalTime && temporal2 instanceof LocalTime) {
            return ((LocalTime) temporal).compareTo((LocalTime) temporal2);
        }

        if (temporal instanceof LocalDate && temporal2 instanceof LocalDate) {
            return ((LocalDate) temporal).compareTo((LocalDate) temporal2);
        }

        if (temporal instanceof LocalDateTime && temporal2 instanceof LocalDateTime) {
            return ((LocalDateTime) temporal).compareTo((LocalDateTime) temporal2);
        }

        if (temporal instanceof LocalDateTime && temporal2 instanceof LocalDate) {
            return ((LocalDateTime) temporal).compareTo(((LocalDate) temporal2).atStartOfDay());
        }

        if (temporal instanceof LocalDate && temporal2 instanceof LocalDateTime) {
            return ((LocalDate) temporal).atStartOfDay().compareTo(((LocalDateTime) temporal2));
        }

        throw new IllegalArgumentException("Incompatible date/time components for comparison.");
    }

    public String toISOString() {
        return hasDate() && hasTime() ? DateTimeFormatter.ISO_DATE_TIME.format(temporal) :
                hasDate() ? DateTimeFormatter.ISO_DATE.format(temporal) :
                        hasTime() ? DateTimeFormatter.ISO_TIME.format(temporal) :
                                null;
    }

    @Override
    public String toString() {
        return DateUtil.formatDate(temporal);
    }

}
