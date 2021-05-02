package org.fujion.chartjs.enums;

public enum CubicInterpolationModeEnum {
    /**
     * Uses a custom weighted cubic interpolation, which produces pleasant curves for all types
     * of datasets.
     */
    DEFAULT,
    /**
     * More suited to y = f(x) datasets : it preserves monotonicity (or piecewise monotonicity)
     * of the dataset being interpolated, and ensures local extremums (if any) stay at input
     * data points.
     */
    MONOTONE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
