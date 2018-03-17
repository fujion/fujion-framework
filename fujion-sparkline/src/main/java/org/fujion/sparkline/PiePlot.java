package org.fujion.sparkline;

import org.fujion.annotation.Option;

public class PiePlot extends AbstractPlot {
    
    /**
     * An array of CSS colors to use for pie slices.
     */
    @Option
    public String[] sliceColors;
    
    /**
     * Angle in degrees to offset the first slice (e.g., -90 or +90);
     */
    @Option
    public Double offset;
    
    /**
     * Width of the border to draw around the whole pie chart, in pixels.
     * <p>
     * Default: 0
     */
    @Option
    public Integer borderWidth;
    
    /**
     * CSS color to use to draw the pie border.
     * <p>
     * Default: "#000"
     */
    @Option
    public String borderColor;
    
    protected PiePlot() {
        super(SparklineType.PIE);
    }

}
