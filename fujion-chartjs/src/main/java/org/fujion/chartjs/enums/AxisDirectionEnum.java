package org.fujion.chartjs.enums;

/**
 * Determines which directions are used in calculating distances.
 */
public enum AxisDirectionEnum {
    X, XY, Y;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
