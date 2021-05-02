package org.fujion.chartjs.axis;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.chartjs.common.FontOptions;

/**
 * Used to configure the point labels that are shown on the perimeter of the scale.
 */
public class PointLabelOptions extends Options {

    /**
     * Font to use.
     */
    @Option
    public final FontOptions font = new FontOptions();

    /**
     * Background color of the point label.
     */
    @Option
    public String backdropColor;

    /**
     * Background color of the point label.
     */
    @Option(convertTo = JavaScript.class)
    public String backdropColor$function;

    /**
     * Padding of label backdrop.
     * <p>
     * Default: 2
     */
    @Option
    public Integer backdropPadding;

    /**
     * If true, point labels are shown.
     * <p>
     * Default: true
     */
    @Option
    public Boolean display;

    /**
     * Callback function to transform data labels to point labels. The default implementation simply returns
     * the current string.
     */
    @Option(convertTo = JavaScript.class)
    public String callback;

    /**
     * Color of label.
     */
    @Option
    public String color;

    /**
     * Color of label.
     */
    @Option(convertTo = JavaScript.class)
    public String color$function;

    /**
     * Font to use.
     */
    @Option(convertTo = JavaScript.class)
    public String font$function;

    /**
     * Padding between chart and point labels.
     * <p>
     * Default: 5
     */
    @Option
    public Integer padding;

    /**
     * Padding between chart and point labels.
     */
    @Option(convertTo = JavaScript.class)
    public String padding$function;

}
