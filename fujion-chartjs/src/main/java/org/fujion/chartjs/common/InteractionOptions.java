package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * By default, these options apply to both the hover and tooltip interactions. The same options can be set in the
 * options.hover namespace, in which case they will only affect the hover interaction. Similarly, the options can
 * be set in the options.plugins.tooltip namespace to independently configure the tooltip interactions.
 */
public class InteractionOptions extends Options {

    /**
     * Determines which directions are used in calculating distances.
     */
    public enum AxisDirectionEnum {
        X, XY, Y;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    /**
     * Determines which directions are used in calculating distances.
     */
    @Option
    public AxisDirectionEnum axis;

    /**
     * If true, the interaction mode only applies when the mouse position intersects an item on the chart.
     *
     * Default: true
     */
    @Option
    public Boolean intersect;

    /**
     * Sets which elements appear in the interaction.
     *
     * Default: NEAREST
     */
    @Option
    public InteractionModeEnum mode;

}
