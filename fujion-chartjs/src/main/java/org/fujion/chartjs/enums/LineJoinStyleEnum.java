package org.fujion.chartjs.enums;

public enum LineJoinStyleEnum {
    BEVEL, ROUND, MITER;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
