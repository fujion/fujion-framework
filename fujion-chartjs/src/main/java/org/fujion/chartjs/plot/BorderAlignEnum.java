package org.fujion.chartjs.plot;

public enum BorderAlignEnum {
    CENTER, // The borders of arcs next to each other will overlap.
    INNER;  // Guarantees that all borders will not overlap.

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
