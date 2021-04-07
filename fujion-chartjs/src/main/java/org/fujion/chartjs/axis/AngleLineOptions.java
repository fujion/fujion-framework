package org.fujion.chartjs.axis;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

public class AngleLineOptions extends Options {

    /**
     * If true, angle lines are shown.
     * <p>
     * Default: true
     */
    @Option
    public Boolean display;

    /**
     * Color of angled lines.
     */
    @Option
    public String color;

    /**
     * Color of angled lines.
     */
    @Option(convertTo = JavaScript.class)
    public String color$function;

    /**
     * Width of angled lines.
     * <p>
     * Default: 1
     */
    @Option
    public Integer lineWidth;

    /**
     * Width of angled lines.
     */
    @Option(convertTo = JavaScript.class)
    public String lineWidth$function;

    /**
     * Length and spacing of dashes on angled lines.
     */
    @Option
    public int[] borderDash;

    /**
     * Length and spacing of dashes on angled lines.
     */
    @Option(convertTo = JavaScript.class)
    public String borderDash$function;

    /**
     * Offset for line dashes.
     * <p>
     * Default: 0.0
     */
    @Option
    public Double borderDashOffset;

    /**
     * Offset for line dashes.
     */
    @Option(convertTo = JavaScript.class)
    public String borderDashOffset$function;

}
