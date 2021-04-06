package org.fujion.chartjs.enums;

public enum ScaleBoundsEnum {
    DATA,   // Makes sure data are fully visible, labels outside are removed.
    TICKS;  // Makes sure ticks are fully visible, data outside are truncated.

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
