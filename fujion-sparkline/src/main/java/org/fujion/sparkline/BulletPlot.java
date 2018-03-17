package org.fujion.sparkline;

import org.fujion.annotation.Option;

public class BulletPlot extends AbstractPlot {

    /**
     * The CSS color of the vertical target marker.
     */
    @Option
    public String targetColor;
    
    /**
     * The width of the target marker in pixels.
     */
    @Option
    public Integer targetWidth;
    
    /**
     * The CSS color of the performance measure horizontal bar.
     */
    @Option
    public String performanceColor;
    
    /**
     * Colors to use for each qualitative range background color. For example, ['red','green',
     * '#22f'].
     */
    @Option
    public String[] rangeColors;

    protected BulletPlot() {
        super(SparklineType.BULLET);
    }

}
