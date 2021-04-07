package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.chartjs.enums.DecimationAlgorithmEnum;

/**
 * The decimation plugin can be used with line charts to automatically decimate data at the
 * start of the chart lifecycle. Before enabling this plugin, review the requirements to ensure
 * that it will work with the chart you want to create.
 */
public class DecimationOptions extends Options {

    /**
     * Is decimation enabled?
     * <p>
     * Default: false
     */
    @Option
    public Boolean enabled;

    /**
     * Decimation algorithm to use.
     * <p>
     * Default: MIN_MAX
     */
    @Option
    public DecimationAlgorithmEnum algorithm;

    /**
     * If the 'lttb' algorithm is used, this is the number of samples in the output dataset. Defaults to the canvas width to pick 1 sample per pixel.
     * <p>
     * Default: the canvas width to pick 1 sample per pixel.
     */
    @Option
    public Integer samples;

}
