package org.fujion.sparkline;

import java.util.Map;

import org.fujion.annotation.Option;

/**
 * Options for tri-state plots. Tri-state charts are useful to show win-lose-draw information, such
 * as recent sports scores at the top of the page. You can also use the colorMap option to use
 * different colors for different values, or for arbitrary positions in the chart.
 */
public class TristatePlot extends AbstractPlot {

    /**
     * CSS color for positive (win) values.
     */
    @Option
    public String posBarColor;
    
    /**
     * CSS color for negative (lose) values.
     */
    @Option
    public String negBarColor;
    
    /**
     * CSS color for zero (draw) values.
     */
    @Option
    public String zeroBarColor;
    
    /**
     * Width of each bar, in pixels.
     */
    @Option
    public Integer barWidth;
    
    /**
     * Space between each bar, in pixels.
     */
    @Option
    public Integer barSpacing;
    
    /**
     * A range map to map specific values to selected colors. For example if you want all values of
     * -2 to appear yellow, set colorMap: { '-2': '#ff0' }.
     */
    @Option("colorMap")
    public Map<String, String> colorMap$map;
    
    /**
     * An array of values to specify a color for each individual bar. For example if your chart has
     * three values 1,3,1 you can set colorMap$array=["red", "green", "blue"]
     */
    @Option("colorMap")
    public String[] colorMap$array;
    
    protected TristatePlot() {
        super(SparklineType.TRISTATE);
    }

}
