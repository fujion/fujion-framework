package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;

/**
 * TODO: Layout options for ternary plots.
 */
public class TernaryOptions extends Options {
    
    /**
     * Options for a axis.
     */
    public final ABCAxisOptions aaxis = new ABCAxisOptions();
    
    /**
     * Options for b axis.
     */
    public final ABCAxisOptions baxis = new ABCAxisOptions();
    
    /**
     * Options for c axis.
     */
    public final ABCAxisOptions caxis = new ABCAxisOptions();
    
    public String bgcolor;
    
    public int[] domain_x;
    
    public int[] domain_y;
    
    public Object sum;
}
