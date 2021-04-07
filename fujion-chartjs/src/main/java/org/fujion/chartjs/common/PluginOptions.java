package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

public class PluginOptions extends Options {

    /**
     * Title display options.
     */
    @Option
    public final TitleOptions title = new TitleOptions();

    /**
     * Tooltip display options.
     */
    @Option
    public final TooltipOptions tooltip = new TooltipOptions();

    /**
     * Legend options.
     */
    @Option
    public final LegendOptions legend = new LegendOptions();

    /**
     * Decimation options.
     */
    @Option
    public final DecimationOptions decimation = new DecimationOptions();
}
