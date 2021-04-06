package org.fujion.chartjs.common;

public enum LineJoinStyleEnum {
    BEVEL, ROUND, MITER;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
