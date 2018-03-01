package org.fujion.plotly.layout;

/**
 * Layout options for a, b, and c axes.
 */
public class ABCAxisOptions extends AxisOptions {

    public enum CheaterTypeEnum {

        INDEX, VALUE;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }

    }
    
    /**
     * The stride between grid lines along the axis.
     * <p>
     * Default: 1
     */
    public Integer arraydtick;
    
    /**
     * The starting index of grid lines along the axis.
     * <p>
     * Default: 0
     */
    public Integer arraytick0;
    
    /**
     * The cheater type.
     */
    public CheaterTypeEnum cheatertype;
    
    /**
     * Determines whether or not a line is drawn at along the final value of this axis. If true, the
     * end line is drawn on top of the grid lines.
     */
    public Boolean endline;
    
    /**
     * The line color of the end line.
     */
    public String endlinecolor;
    
    /**
     * The width (in px) of the end line.
     * <p>
     * Default: 1
     */
    public Integer endlinewidth;
    
    /**
     * Extra padding between label and the axis.
     * <p>
     * Default: 10
     */
    public Integer labelpadding;
    
    /**
     * Axis label prefix.
     */
    public String labelprefix;
    
    /**
     * Axis label suffix.
     */
    public String labelsuffix;
    
    /**
     * The color of the grid lines.
     * <p>
     * Default: "#eee"
     */
    public String minorgridcolor;
    
    /**
     * The number of minor grid ticks per major grid tick.
     * <p>
     * Default: 0
     */
    public Integer minorgridcount;
    
    /**
     * The width (in px) of the grid lines.
     * <p>
     * Default: 1
     */
    public Integer minorgridwidth;
    
    /**
     * Smoothing factor.
     * <p>
     * Constraints: &ge;0 and &le;1.3
     * <p>
     * Default: 1
     */
    public Double smoothing;
    
    /**
     * Determines whether or not a line is drawn at along the starting value of this axis. If true,
     * the start line is drawn on top of the grid lines.
     */
    public Boolean startline;
    
    /**
     * The line color of the start line.
     */
    public String startlinecolor;
    
    /**
     * The width (in px) of the start line.
     * <p>
     * Default: 1
     */
    public Integer startlinewidth;
    
    /**
     * An additional amount by which to offset the title from the tick labels, given in pixels.
     * <p>
     * Default: 10
     */
    public Integer titleoffset;
    
}
