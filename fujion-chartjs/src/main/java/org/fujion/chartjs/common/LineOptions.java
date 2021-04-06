package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

public class LineOptions extends Options {

    /**
     * Bézier curve tension (0 for no Bézier curves).
     * <p>
     * Default: 0
     */
    @Option
    public Integer tension;

    /**
     * Line fill color.
     */
    @Option
    public String backgroundColor;

    /**
     * Line stroke width.
     * <p>
     * Default: 3
     */
    @Option
    public Integer borderWidth;

    /**
     * Line stroke color.
     */
    @Option
    public String borderColor;

    /**
     * Line cap style.
     * <p>
     * Default: BUTT
     */
    @Option
    public LineCapStyleEnum borderCapStyle;

    /**
     * An Array of numbers that specify distances to alternately draw a line and a gap (in coordinate space units).
     */
    @Option
    public Integer[] borderDash;

    /**
     * Line dash offset.
     * <p>
     * Default: 0.0
     */
    @Option
    public Double borderDashOffset;

    /**
     * Line join style.
     * <p>
     * Default: MITER
     */
    @Option
    public LineJoinStyleEnum borderJoinStyle;

    /**
     * Set to true to keep Bézier control inside the chart, false for no restriction.
     * <p>
     * Default: true
     */
    @Option
    public Boolean capBezierPoints;

    /**
     * Interpolation mode to apply.
     * <p>
     * Default: 'default'
     */
    @Option
    public String cubicInterpolationMode;

    /**
     * How to fill the area under the line.
     * <p>
     * Default: false
     */
    @Option
    public Boolean fill$boolean;

    /**
     * How to fill the area under the line.
     */
    @Option
    public String fill$string;

    /**
     * Set to true to show the line as a stepped line (tension will be ignored).
     * <p>
     * Default: false
     */
    @Option
    public Boolean stepped;

}
