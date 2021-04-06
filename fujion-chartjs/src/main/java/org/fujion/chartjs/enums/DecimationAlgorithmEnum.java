package org.fujion.chartjs.enums;

public enum DecimationAlgorithmEnum {
    LTTB, MIN_MAX;

    @Override
    public String toString() {
        return name().toLowerCase().replace("_", "-");
    }
}
