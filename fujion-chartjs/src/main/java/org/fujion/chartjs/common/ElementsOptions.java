package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

public class ElementsOptions extends Options {

    /**
     * Point options for all chart types.
     */
    @Option
    public final PointElementOptions point = new PointElementOptions();

    /**
     * Line options for all chart types.
     */
    @Option
    public final LineElementOptions line = new LineElementOptions();

    /**
     * Bar options for bar charts.
     */
    @Option
    public final BarElementOptions bar = new BarElementOptions();

    /**
     * Arc options for polar area, doughnut and pie charts.
     */
    @Option
    public final ArcElementOptions arc = new ArcElementOptions();
}
