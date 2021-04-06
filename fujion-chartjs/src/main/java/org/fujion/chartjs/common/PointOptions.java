package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.chartjs.enums.PointStyleEnum;

/**
 * Point rendering options across all chart types.
 */
public class PointOptions extends Options {

    /**
     * Point radius.
     * <p>
     * Default: 3
     */
    @Option
    public Integer radius;

    /**
     * Point style.
     * <p>
     * Default: CIRCLE
     */
    @Option
    public PointStyleEnum pointStyleEnum;

    /**
     * Point rotation (in degrees).
     * <p>
     * Default: 0
     */
    @Option
    public Integer rotation;

    /**
     * Point fill color.
     */
    @Option
    public String backgroundColor;

    /**
     * Point stroke width.
     * <p>
     * Default: 1
     */
    @Option
    public Integer borderWidth;

    /**
     * Point stroke color.
     */
    @Option
    public String borderColor;

    /**
     * Extra radius added to point radius for hit detection.
     * <p>
     * Default: 1
     */
    @Option
    public Integer hitRadius;

    /**
     * Point radius when hovered.
     * <p>
     * Default: 4
     */
    @Option
    public Integer hoverRadius;

    /**
     * Stroke width when hovered.
     * <p>
     * Default: 1
     */
    @Option
    public Integer hoverBorderWidth;

}
