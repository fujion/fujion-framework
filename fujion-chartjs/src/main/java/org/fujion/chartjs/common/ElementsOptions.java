package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

public class ElementsOptions extends Options {

    /**
     * Point options for all chart types.
     */
    @Option
    public final PointOptions point = new PointOptions();

    /**
     * Line options for all chart types.
     */
    @Option
    public final LineOptions line = new LineOptions();

    // TODO: BarOptions, ArcOptions
}
