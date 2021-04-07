package org.fujion.chartjs.enums;

/**
 * Boundary-based fill mode.
 */
public enum FillEnum {
    END, ORIGIN, START;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
