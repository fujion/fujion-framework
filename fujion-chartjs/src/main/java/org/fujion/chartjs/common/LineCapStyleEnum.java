package org.fujion.chartjs.common;

public enum LineCapStyleEnum {
    BUTT, ROUND, SQUARE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
