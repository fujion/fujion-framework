package org.fujion.sparkline;

import org.fujion.ancillary.Options;

/**
 * Base class for sparkline plots.
 */
public class AbstractPlot extends Options {
    
    private final SparklineType type;

    protected AbstractPlot(SparklineType type) {
        this.type = type;
    }

    public SparklineType getType() {
        return type;
    }
}
