package org.fujion.chartjs.enums;

public enum DecimationAlgorithmEnum {
    /**
     * Reduces the number of data points significantly. This is most useful for showing trends in data
     * using only a few data points.
     */
    LTTB,
    /**
     * Preserves peaks in your data but could require up to 4 points for each pixel. This type of decimation
     * would work well for a very noisy signal where you need to see data peaks.
     */
    MIN_MAX;

    @Override
    public String toString() {
        return name().toLowerCase().replace("_", "-");
    }
}
