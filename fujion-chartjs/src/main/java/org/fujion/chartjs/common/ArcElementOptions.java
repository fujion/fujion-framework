package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.chartjs.enums.BorderAlignEnum;

/**
 * Arcs are used in the polar area, doughnut and pie charts.
 */
public class ArcElementOptions extends Options {

    /**
     * Arc angle to cover.  For polar only.
     */
    @Option
    public Integer angle;

    /**
     * Arc fill color.
     */
    @Option
    public String backgroundColor;

    /**
     * Arc stroke alignment.
     * <p>
     * Default: CENTER
     */
    @Option
    public BorderAlignEnum borderAlignEnum;

    /**
     * Arc stroke color.
     * <p>
     * Default: #FFF
     */
    @Option
    public String borderColor;

    /**
     * Arc stroke width.
     * <p>
     * Default: 2
     */
    @Option
    public Integer borderWidth;

}
