package org.fujion.chartjs.common;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Configures which element properties are animated and how.
 */
public class AnimationsOptions extends Options {

    /**
     * Type of property, determines the interpolator used.
     * Only really needed for COLOR, because typeof does not get that right.
     */
    public enum PropertyTypeEnum {
        NUMBER, COLOR, BOOLEAN;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * The property names this configuration applies to. Defaults to the key name of this object.
     */
    @Option
    public String[] properties;

    /**
     * Type of property, determines the interpolator used.
     */
    @Option
    private final PropertyTypeEnum type;

    /**
     * Start value for the animation.
     */
    @Option
    public Integer from$number;

    /**
     * Start value for the animation.
     */
    @Option
    public Integer from$string;

    /**
     * Start value for the animation.
     */
    @Option
    public Boolean from$boolean;

    /**
     * End value for the animation.
     */
    @Option
    public Integer to$number;

    /**
     * End value for the animation.
     */
    @Option
    public Integer to$string;

    /**
     * End value for the animation.
     */
    @Option
    public Boolean to$boolean;

    /**
     * Optional custom interpolator, instead of using a predefined interpolator from type
     */
    @Option(convertTo = JavaScript.class)
    public String fn;

    public AnimationsOptions(PropertyTypeEnum type) {
        this.type = type;
    }
}
