package org.fujion.chartjs.enums;

public enum TimeUnitEnum {
    DAY, HOUR, MILLISECOND, MINUTE, MONTH, QUARTER, SECOND, WEEK, YEAR;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
