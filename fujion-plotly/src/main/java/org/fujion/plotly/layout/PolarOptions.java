package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;

/**
 * Layout options for polar plots.
 */
public class PolarOptions extends Options {

    /**
     * The horizontal domain of the polar subplot (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    public int[] domain_x;
    
    /**
     * The vertical domain of the polar subplot (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    public int[] domain_y;
    
    /**
     * angular span of this polar subplot with two angles (in degrees). Sector are assumed to be
     * spanned in the counterclockwise direction with "0" corresponding to rightmost limit of the
     * polar subplot.
     * <p>
     * Default: [0,360]
     */
    public int[] sector;

    /**
     * The background color of the subplot.
     * <p>
     * Default: "#fff"
     */
    public String bgcolor;

    /**
     * Options for polar angular axis.
     */
    public final AngularAxisOptions angularaxis = new AngularAxisOptions();
    
    /**
     * Options for polar radial axis.
     */
    public final RadialAxisOptions radialaxis = new RadialAxisOptions();
    
}
