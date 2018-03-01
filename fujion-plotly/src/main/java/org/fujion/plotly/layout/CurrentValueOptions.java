package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;
import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.common.HorizontalAlignEnum;

/**
 * Layout options displaying the current value.
 */
public class CurrentValueOptions extends Options {

    /**
     * The font of the current value label text.
     */
    public final FontOptions font = new FontOptions();
    
    /**
     * The amount of space, in pixels, between the current value label and the slider.
     * <p>
     * Default: 10
     */
    public Integer offset;
    
    /**
     * When currentvalue.visible is true, this sets the prefix of the label.
     */
    public String prefix;
    
    /**
     * When currentvalue.visible is true, this sets the suffix of the label.
     */
    public String suffix;
    
    /**
     * Shows the currently-selected value above the slider.
     * <p>
     * Default: true
     */
    public Boolean visible;
    
    /**
     * The alignment of the value readout relative to the length of the slider.
     * <p>
     * Default: LEFT
     */
    public HorizontalAlignEnum xanchor;

}
