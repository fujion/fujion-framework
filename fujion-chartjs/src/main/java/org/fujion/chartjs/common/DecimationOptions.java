package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

public class DecimationOptions extends Options {

    public enum DecimationAlgorithmEnum {
        LTTB, MIN_MAX;

        @Override
        public String toString() {
            return name().toLowerCase().replace("_", "-");
        }
    }

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
