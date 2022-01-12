package org.fujion.lmaps;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Some geolocation methods take this options parameter.
 */
public class LocateOptions extends Options {

    /**
     * If true, starts continuous watching of location changes (instead of detecting it once) using W3C watchPosition
     * method. You can later stop watching using map.stopLocate() method.
     * <p>
     * Default: false
     */
    @Option
    public Boolean watch;

    /**
     * If true, automatically sets the map view to the user location with respect to detection accuracy, or to world
     * view if geolocation failed.
     * <p>
     * Default: false
     */
    @Option
    public Boolean setView;

    /**
     * The maximum zoom for automatic view setting when using setView option.
     * <p>
     * Default: no maximum
     */
    @Option
    public Integer maxZoom;

    /**
     * Number of milliseconds to wait for a response from geolocation before firing a locationerror event.
     * <p>
     * Default: 10000
     */
    @Option
    public Integer timeout;

    /**
     * Maximum age of detected location. If less than this amount of milliseconds passed since last geolocation response,
     * locate will return a cached location.
     * <p>
     * Default: 0
     */
    @Option
    public Integer maximumAge;

    /**
     * Enables high accuracy, see description in the W3C spec.
     * <p>
     * Default: false
     */
    @Option
    public Boolean enableHighAccuracy;

}
