package org.fujion.chartjs.enums;

/**
 * A skipped (excluded) border.
 */
public enum BorderSkippedEnum {
    START, END, BOTTOM, LEFT, TOP, RIGHT;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
