package org.fujion.chartjs.enums;

public enum SteppedLineEnum {
    AFTER, BEFORE, MIDDLE, FALSE, TRUE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
