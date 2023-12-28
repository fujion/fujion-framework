package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.chartjs.enums.AxisDirectionEnum;
import org.fujion.chartjs.enums.InteractionModeEnum;

/**
 * By default, these options apply to both the hover and tooltip interactions. The same options can be set in the
 * <code>options.hover</code> namespace, in which case they will only affect the hover interaction.
 * Similarly, the options can be set in the <code>options.plugins.tooltip</code> namespace to independently configure
 * the tooltip interactions.
 */
public class InteractionOptions extends Options {

    /**
     * Determines which directions are used in calculating distances.
     * <p>
     * Default: X
     */
    @Option
    public AxisDirectionEnum axis;

    /**
     * If true, the interaction mode only applies when the mouse position intersects an item on the chart.
     * <p>
     * Default: true
     */
    @Option
    public Boolean intersect;

    /**
     * Sets which elements appear in the interaction.
     * <p>
     * Default: NEAREST
     */
    @Option
    public InteractionModeEnum mode;

}
