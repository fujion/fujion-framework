package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;
import org.fujion.plotly.common.DashStyleEnum;

/**
 * Layout options for shapes.
 */
public class ShapeOptions extends Options {
    
    /**
     * Type of shape.
     */
    public enum TypeEnum {
        /**
         * A circle is drawn from (("x0"+"x1")/2, ("y0"+"y1")/2)) with radius (|("x0"+"x1")/2 -
         * "x0"|, |("y0"+"y1")/2 -"y0")|)
         */
        CIRCLE,
        /**
         * A line is drawn from ("x0","y0") to ("x1","y1")
         */
        LINE,
        /**
         * Draw a custom SVG path using "path".
         */
        PATH,
        /**
         * A rectangle is drawn linking ("x0","y0"), ("x1","y0"), ("x1","y1"), ("x0","y1"),
         * ("x0","y0")
         */
        RECT;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    
    /**
     * The color filling the shape's interior.
     * <p>
     * Default: "rgba(0,0,0,0)"
     */
    public String fillcolor;
    
    /**
     * Specifies whether shapes are drawn below or above traces.
     * <p>
     * Default: ABOVE
     */
    public LayerEnum layer;
    
    /**
     * The line color.
     */
    public String line_color;

    /**
     * The dash style of lines.
     * <p>
     * Default: SOLID
     */
    public DashStyleEnum line_dash;
    
    /**
     * The line width (in px).
     * <p>
     * Default: 2
     */
    public Integer line_width;
    
    /**
     * The opacity of the shape.
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 1
     */
    public Double opacity;
    
    /**
     * For "type" PATH - a valid SVG path but with the pixel values replaced by data values. There
     * are a few restrictions / quirks only absolute instructions, not relative. So the allowed
     * segments are: M, L, H, V, Q, C, T, S, and Z arcs (A) are not allowed because radius rx and ry
     * are relative. In the future we could consider supporting relative commands, but we would have
     * to decide on how to handle date and log axes. Note that even as is, Q and C Bezier paths that
     * are smooth on linear axes may not be smooth on log, and vice versa. no chained "polybezier"
     * commands - specify the segment type for each one. On category axes, values are numbers scaled
     * to the serial numbers of categories because using the categories themselves there would be no
     * way to describe fractional positions On data axes: because space and T are both normal
     * components of path strings, we can't use either to separate date from time parts. Therefore
     * we'll use underscore for this purpose: 2015-02-21_13:45:56.789.
     */
    public String path;

    /**
     * Specifies the shape type to be drawn.
     */
    public TypeEnum type;
    
    /**
     * Determines whether or not a shape is visible.
     * <p>
     * Default: true.
     */
    public Boolean visible;
    
    /**
     * The shape's starting x position.
     */
    public Integer x0;

    /**
     * The shape's ending x position.
     */
    public Integer x1;
    
    /**
     * The shape's x coordinate axis. If set to an x axis id (e.g. "x" or "x2"), the "x" position
     * refers to an x coordinate If set to "paper", the "x" position refers to the distance from the
     * left side of the plotting area in normalized coordinates where "0" ("1") corresponds to the
     * left (right) side. If the axis "type" is "log", then you must take the log of your desired
     * range. If the axis "type" is "date", then you must convert the date to unix time in
     * milliseconds.
     */
    public String xref;
    
    /**
     * The shape's starting y position.
     */
    public Integer y0;
    
    /**
     * The shape's ending y position.
     */
    public Integer y1;
    
    /**
     * The annotation's y coordinate axis. If set to an y axis id (e.g. "y" or "y2"), the "y"
     * position refers to an y coordinate If set to "paper", the "y" position refers to the distance
     * from the bottom of the plotting area in normalized coordinates where "0" ("1") corresponds to
     * the bottom (top).
     */
    public String yref;
}
