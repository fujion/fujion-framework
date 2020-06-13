package org.fujion.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Date;

/**
 * Common wrapper for LocalDate, LocalDateTime, and Date instances.
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

    /**
     * Converts a string value to a DateTimeWrapper.
     *
     * @param value The value to parse.
     * @return A DateTimeWrapper instance.
     * @throws IllegalArgumentException If the input value could not be parsed.
     */
    public static DateTimeWrapper parse(String value) {
        Date date = DateUtil.parseDate(value);
        MiscUtil.assertTrue(date != null, () -> "Unable to parse input '" + value + "'.");
        return new DateTimeWrapper(date);
    }

    /**
     * Wrap a LocalDate value.
     *
     * @param date A LocalDate value.
     */
    public DateTimeWrapper(LocalDate date) {
        this.temporal = validateTemporal(date);
        this.date = DateUtil.toDate(temporal);
    }

    /**
     * Wrap a LocalDateTime value.
     *
     * @param datetime A LocalDateTime value.
     */
    public DateTimeWrapper(LocalDateTime datetime) {
        this.temporal = validateTemporal(datetime);
        this.date = DateUtil.toDate(temporal);
    }

    /**
     * Wrap a legacy Date value.
     *
     * @param date A legacy Date value.
     */
    public DateTimeWrapper(Date date) {
        this.temporal = validateTemporal(DateUtil.hasTime(date) ? DateUtil.toLocalDateTime(date) : DateUtil.toLocalDate(date));
        this.date = date;
    }

    /**
     * Returns true if the wrapped date/time has a time component.
     *
     * @return True if the wrapped date/time has a time component.
     */
    public boolean hasTime() {
        return temporal instanceof LocalDateTime;
    }

    /**
     * Returns the wrapped date/time as a legacy Date value.  If the wrapped value has no time component,
     * the returned value will have a time set to midnight.
     *
     * @return The wrapped value as a legacy date value.
     */
    public Date getLegacyDate() {
        return date;
    }

    /**
     * Returns the wrapped date/time as a LocalDateTime value.  If the wrapped value contains no time component,
     * it will be coerced to one with a time beginning at the start of the day.
     *
     * @return The wrapped value as a LocalDateTime.
     */
    public LocalDateTime getDateTime() {
        return hasTime() ? (LocalDateTime) temporal : ((LocalDate) temporal).atStartOfDay();
    }

    /**
     * Returns the wrapped date/time as a LocalDate value.  If the wrapped value contains a time component,
     * it will be coerced to a value with the time component removed.
     *
     * @return The wrapped value as a LocalDateTime.
     */
   public LocalDate getDate() {
        return hasTime() ? ((LocalDateTime) temporal).toLocalDate() : (LocalDate) temporal;
    }

    /**
     * Returns the time component of the wrapped value.  If there is no time component, null is returned.
     *
     * @return The time component of the wrapped value (possibly null).
     */
    public LocalTime getTime() {
        return hasTime() ? ((LocalDateTime) temporal).toLocalTime() : null;
    }

    private Temporal validateTemporal(Temporal temporal) {
        MiscUtil.assertTrue(temporal != null, "Date/time value must not be null.");
        return temporal;
    }

    /**
     * Compares two DateTimeWrapper instances.  Wrapped values are coerced as necessary.
     *
     * @param dtw A DateTimeWrapper against which to compare.
     * @return The result of the comparison.
     */
    @Override
    public int compareTo(DateTimeWrapper dtw) {
        if (!hasTime() && !dtw.hasTime()) {
            return getDate().compareTo(dtw.getDate());
        } else {
            return getDateTime().compareTo(dtw.getDateTime());
        }
    }

    /**
     * Returns the wrapped date/time as an ISO-formatted date or date/time.
     *
     * @return An ISO-formatted date or date/time.
     */
    public String toISOString() {
        return hasTime() ? DateTimeFormatter.ISO_DATE_TIME.format(temporal) : DateTimeFormatter.ISO_DATE.format(temporal);
    }

    /**
     * Returns the wrapped date/time in a form suitable for display.
     *
     * @return Text version of the wrapped date/time.
     */
    @Override
    public String toString() {
        return DateUtil.formatDate(temporal);
    }

}
