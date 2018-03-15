/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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
 * Options for a Google Map.
 */
public class MapOptions extends Options {

    /**
     * Color used for the background of the Map div. This color will be visible when tiles have not
     * yet loaded as the user pans. This option can only be set when the map is initialized.
     */
    @Option
    public String backgroundColor;

    /**
     * The initial Map center. Required.
     */
    @Option
    public LatLng center;
    
    /**
     * When false, map icons are not clickable. A map icon represents a point of interest, also
     * known as a POI.
     * <p>
     * Default: true
     */
    @Option
    public Boolean clickableIcons;
    
    /**
     * Enables/disables all default UI. May be overridden individually.
     */
    @Option
    public Boolean disableDefaultUI;

    /**
     * Enables/disables zoom and center on double click. Note: This property is not recommended. To
     * disable zooming on double click, you can use the gestureHandling property, and set it to
     * "none".
     * <p>
     * Default: false
     */
    @Option
    public Boolean disableDoubleClickZoom;

    /**
     * The name or url of the cursor to display when mousing over a draggable map. This property
     * uses the css cursor attribute to change the icon. As with the css property, you must specify
     * at least one fallback cursor that is not a URL. For example: draggableCursor:
     * 'url(http://www.example.com/icon.png), auto;'.
     */
    @Option
    public String draggableCursor;

    /**
     * The name or url of the cursor to display when the map is being dragged. This property uses
     * the css cursor attribute to change the icon. As with the css property, you must specify at
     * least one fallback cursor that is not a URL. For example: draggingCursor:
     * 'url(http://www.example.com/icon.png), auto;'.
     */
    @Option
    public String draggingCursor;

    /**
     * The enabled/disabled state of the Fullscreen control.
     */
    @Option
    public Boolean fullscreenControl;
    
    /**
     * Options for Fullscreen control.
     */
    @Option
    public FullscreenControlOptions fullscreenControlOptions = new FullscreenControlOptions();
    
    /**
     * Controls how the API handles gestures on the map.
     * <p>
     * Default: AUTO
     */
    @Option
    public GestureHandlingType gestureHandling;

    /**
     * The heading for aerial imagery in degrees measured clockwise from cardinal direction North.
     * Headings are snapped to the nearest available angle for which imagery is available.
     */
    @Option
    public Double heading;
    
    /**
     * If false, prevents the map from being controlled by the keyboard.
     * <p>
     * Default: true
     */
    @Option
    public Boolean keyboardShortcuts;

    /**
     * The initial enabled/disabled state of the Map type control.
     */
    @Option
    public Boolean mapTypeControl;
    
    /**
     * Map type control options.
     */
    @Option
    public final MapTypeControlOptions mapTypeControlOptions = new MapTypeControlOptions();
    
    /**
     * The initial Map mapTypeId as an enum.
     * <p>
     * Default: ROADMAP
     */
    @Option
    public MapTypeId mapTypeId$enum;
    
    /**
     * The initial Map mapTypeId as a string.
     */
    @Option
    public String mapTypeId$string;
    
    /**
     * The maximum zoom level which will be displayed on the map. If omitted, or set to null, the
     * maximum zoom from the current map type is used instead. Valid values: Integers between zero,
     * and up to the supported maximum zoom level.
     */
    @Option
    public Integer maxZoom;
    
    /**
     * The minimum zoom level which will be displayed on the map. If omitted, or set to null, the
     * minimum zoom from the current map type is used instead. Valid values: Integers between zero,
     * and up to the supported maximum zoom level.
     */
    @Option
    public Integer minZoom;

    /**
     * If true, do not clear the contents of the Map div.
     */
    @Option
    public Boolean noClear;
    
    /**
     * The enabled/disabled state of the Rotate control.
     */
    @Option
    public Boolean rotateControl;

    /**
     * The display options for the Rotate control.
     */
    @Option
    public final RotateControlOptions rotateControlOptions = new RotateControlOptions();

    /**
     * The initial enabled/disabled state of the Scale control.
     */
    @Option
    public Boolean scaleControl;

    /**
     * The initial display options for the Scale control.
     */
    @Option
    public final ScaleControlOptions scaleControlOptions = new ScaleControlOptions();
    
    /**
     * If false, disables zooming on the map using a mouse scroll wheel. Note: This property is not
     * recommended. To disable zooming using scrollwheel, you can use the gestureHandling property,
     * and set it to either "cooperative" or "none".
     * <p>
     * Default: true
     */
    @Option
    public Boolean scrollWheel;
    
    /**
     * A StreetViewPanorama to display when the Street View pegman is dropped on the map. If no
     * panorama is specified, a default StreetViewPanorama will be displayed in the map's div when
     * the pegman is dropped.
     */
    @Option
    public final StreetViewPanorama streetView = new StreetViewPanorama();
    
    /**
     * The initial enabled/disabled state of the Street View Pegman control. This control is part of
     * the default UI, and should be set to false when displaying a map type on which the Street
     * View road overlay should not appear (e.g. a non-Earth map type).
     */
    @Option
    public Boolean streetViewControl;

    /**
     * The initial display options for the Street View Pegman control.
     */
    @Option
    public final StreetViewControlOptions streetViewControlOptions = new StreetViewControlOptions();
    
    /**
     * Styles to apply to each of the default map types. Note that for satellite/hybrid and terrain
     * modes, these styles will only apply to labels and geometry.
     */
    @Option
    public final MapTypeStyleOptions styles = new MapTypeStyleOptions();
    
    /**
     * Controls the automatic switching behavior for the angle of incidence of the map. The only
     * allowed values are 0 and 45. The value 0 causes the map to always use a 0° overhead view
     * regardless of the zoom level and viewport. The value 45 causes the tilt angle to
     * automatically switch to 45 whenever 45° imagery is available for the current zoom level and
     * viewport, and switch back to 0 whenever 45° imagery is not available (this is the default
     * behavior). 45° imagery is only available for satellite and hybrid map types, within some
     * locations, and at some zoom levels. Note: getTilt returns the current tilt angle, not the
     * value specified by this option. Because getTilt and this option refer to different things, do
     * not bind() the tilt property; doing so may yield unpredictable effects.
     */
    @Option
    public TiltAngle tilt;

    /**
     * The initial Map zoom level. Required. Valid values: Integers between zero, and up to the
     * supported maximum zoom level.
     * <p>
     * Default: 8
     */
    @Option
    public int zoom = 8;

    /**
     * The enabled/disabled state of the Zoom control.
     */
    @Option
    public Boolean zoomControl;
    
    /**
     * The display options for the Zoom control.
     */
    @Option
    public final ZoomControlOptions zoomControlOptions = new ZoomControlOptions();
}
