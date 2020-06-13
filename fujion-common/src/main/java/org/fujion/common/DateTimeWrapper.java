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

    private final Date date;

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

    public static DateTimeWrapper parse(String value) {
        Date date = DateUtil.parseDate(value);
        MiscUtil.assertTrue(date != null, () -> "Unable to parse input '" + value + "'.");
        return new DateTimeWrapper(date);
    }

    public DateTimeWrapper(LocalDate date) {
        this.temporal = validateTemporal(date);
        this.date = DateUtil.toDate(temporal);
    }

    public DateTimeWrapper(LocalDateTime datetime) {
        this.temporal = validateTemporal(datetime);
        this.date = DateUtil.toDate(temporal);
    }

    public DateTimeWrapper(Date date) {
        this.temporal = validateTemporal(DateUtil.hasTime(date) ? DateUtil.toLocalDateTime(date) : DateUtil.toLocalDate(date));
        this.date = date;
    }

    public boolean hasTime() {
        return temporal instanceof LocalDateTime;
    }

    public Date getLegacyDate() {
        return date;
    }

    public LocalDateTime getDateTime() {
        validateTime();
        return (LocalDateTime) temporal;
    }

    public LocalDate getDate() {
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

    private void validateTime() {
        MiscUtil.assertState(hasTime(), "No time component available.");
    }

    @Override
    public int compareTo(DateTimeWrapper w) {
        Temporal temporal2 = w.temporal;

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

        throw new IllegalArgumentException("Incompatible date components for comparison.");
    }

    public String toISOString() {
        return hasTime() ? DateTimeFormatter.ISO_DATE_TIME.format(temporal) : DateTimeFormatter.ISO_DATE.format(temporal);
    }

    @Override
    public String toString() {
        return DateUtil.formatDate(temporal);
    }

}
