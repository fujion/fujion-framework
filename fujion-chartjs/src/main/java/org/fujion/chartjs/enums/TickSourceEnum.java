package org.fujion.chartjs.enums;

/**
 * Tick source options.
 */
public enum TickSourceEnum {
    AUTO, DATA, LABELS;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
