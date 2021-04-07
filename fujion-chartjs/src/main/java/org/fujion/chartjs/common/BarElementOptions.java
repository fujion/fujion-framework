package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.chartjs.enums.BorderSkippedEnum;
import org.fujion.chartjs.enums.PointStyleEnum;

/**
 * Bar elements are used to represent the bars in a bar chart.
 */
public class BarElementOptions extends Options {

    /**
     * Bar fill color.
     */
    @Option
    public String backgroundColor;

    /**
     * Bar stroke width.
     * <p>
     * Default: 0
     */
    @Option
    public Integer borderWidth;

    /**
     * Bar stroke color.
     */
    @Option
    public String borderColor;

    /**
     * Skipped (excluded) border.
     * <p>
     * Default: START
     */
    @Option
    public BorderSkippedEnum borderSkipped;

    /**
     * The bar border radius (in pixels).
     * <p>
     * Default: 0
     */
    @Option
    public Integer borderRadius;

    /**
     * Style of the point for legend.
     * <p>
     * Default: CIRCLE
     */
    @Option
    public PointStyleEnum pointStyle;

}
