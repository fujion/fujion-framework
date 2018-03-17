package org.fujion.sparkline;

import org.fujion.annotation.Option;

/**
 * Discrete charts provide a separated thin vertical line for each value.
 */
public class DiscretePlot extends AbstractPlot {

    /**
     * Height of each line in pixels.
     * <p>
     * Default: 30% of the graph height
     */
    @Option
    public Integer lineHeight;

    /**
     * Values less than this value will be drawn using thresholdColor instead of lineColor.
     */
    @Option
    public Double thresholdValue;

    /**
     * Color to use in combination with thresholdValue.
     */
    @Option
    public String thresholdColor;
    
    protected DiscretePlot() {
        super(SparklineType.DISCRETE);
    }

}
