package org.fujion.chartjs.enums;

/**
 * Position modes.
 */
public enum TooltipPositionModeEnum {
    AVERAGE, NEAREST;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
