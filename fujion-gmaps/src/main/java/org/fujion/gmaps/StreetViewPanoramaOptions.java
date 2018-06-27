/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * #L%
 */
package org.fujion.gmaps;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for creating a street view panorama.
 */
public class StreetViewPanoramaOptions extends Options {
    
    /**
     * The enabled/disabled state of the address control.
     */
    @Option
    public Boolean addressControl;

    /**
     * The display options for the address control.
     */
    @Option
    public final StreetViewAddressControlOptions addressControlOptions = new StreetViewAddressControlOptions();

    /**
     * The enabled/disabled state of click-to-go.
     */
    @Option
    public Boolean clickToGo;

    /**
     * Enables/disables all default UI. May be overridden individually.
     */
    @Option
    public Boolean disableDefaultUI;
    
    /**
     * Enables/disables zoom on double click. Disabled by default.
     */
    @Option
    public Boolean disableDoubleClickZoom;

    /**
     * If true, the close button is displayed. Disabled by default.
     */
    @Option
    public Boolean enableCloseButton;

    /**
     * The enabled/disabled state of the fullscreen control.
     */
    @Option
    public Boolean fullscreenControl;
    
    /**
     * The display options for the fullscreen control.
     */
    @Option
    public final FullscreenControlOptions fullscreenControlOptions = new FullscreenControlOptions();
    
    /**
     * The enabled/disabled state of the imagery acquisition date control. Disabled by default.
     */
    @Option
    public Boolean imageDateControl;

    /**
     * The enabled/disabled state of the links control.
     */
    @Option
    public Boolean linksControl;

    /**
     * Whether motion tracking is on or off. Enabled by default when the motion tracking control is
     * present, so that the POV (point of view) follows the orientation of the device. This is
     * primarily applicable to mobile devices. If motionTracking is set to false while
     * motionTrackingControl is enabled, the motion tracking control appears but tracking is off.
     * The user can tap the motion tracking control to toggle this option.
     */
    @Option
    public Boolean motionTracking;
    
    /**
     * The enabled/disabled state of the motion tracking control. Enabled by default when the device
     * has motion data, so that the control appears on the map. This is primarily applicable to
     * mobile devices.
     */
    @Option
    public Boolean motionTrackingControl;

    /**
     * The display options for the motion tracking control.
     */
    @Option
    public final MotionTrackingControlOptions motionTrackingControlOptions = new MotionTrackingControlOptions();

    /**
     * The enabled/disabled state of the pan control.
     */
    @Option
    public Boolean panControl;

    /**
     * The display options for the pan control.
     */
    @Option
    public final PanControlOptions panControlOptions = new PanControlOptions();

    /**
     * The panorama ID, which should be set when specifying a custom panorama.
     */
    @Option
    public String pano;

    /**
     * The LatLng position of the Street View panorama.
     */
    @Option
    public LatLng position;

    /**
     * The camera orientation, specified as heading and pitch, for the panorama.
     */
    @Option
    public final StreetViewPov pov = new StreetViewPov();

    /**
     * If false, disables scrollwheel zooming in Street View. The scrollwheel is enabled by default.
     */
    @Option
    public Boolean scrollwheel;

    /**
     * The display of street names on the panorama. If this value is not specified, or is set to
     * true, street names are displayed on the panorama. If set to false, street names are not
     * displayed.
     */
    @Option
    public Boolean showRoadLabels;
    
    /**
     * If true, the Street View panorama is visible on load.
     */
    @Option
    public Boolean visible;
    
    /**
     * The zoom of the panorama, specified as a number. A zoom of 0 gives a 180 degrees Field of
     * View.
     */
    @Option
    public Double zoom;
    
    /**
     * The enabled/disabled state of the zoom control.
     */
    @Option
    public Boolean zoomControl;
    
    /**
     * The display options for the zoom control.
     */
    @Option
    public final ZoomControlOptions zoomControlOptions = new ZoomControlOptions();

}
