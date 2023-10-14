/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2023 Fujion Framework
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
package org.fujion.lmaps;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

public class LeafletOptions extends Options {

    /**
     * Whether Paths should be rendered on a Canvas renderer. By default, all Paths are rendered in a SVG renderer.
     * <p>
     * Default: false
     */
    @Option
    public Boolean preferCanvas;

    /**
     * Whether an attribution control is added to the map by default.
     * <p>
     * Default: true
     */
    @Option
    public Boolean attributionControl;

    /**
     * Whether a zoom control is added to the map by default.
     * <p>
     * Default: true
     */
    @Option
    public Boolean zoomControl;

    /**
     * Initial map zoom level.
     */
    @Option
    public Integer zoom = 13;

    /**
     * Minimum zoom level of the map. If not specified and at least one GridLayer or TileLayer is in the map,
     * the lowest of their minZoom options will be used instead.
     */
    @Option
    public Integer minZoom;

    /**
     * Maximum zoom level of the map. If not specified and at least one GridLayer or TileLayer is in the map,
     * the highest of their maxZoom options will be used instead.
     */
    @Option
    public Integer maxZoom;

    /**
     * Initial geographic center of the map.
     */
    @Option(required = true)
    public LatLng center;

    /**
     * Whether the map zoom animation is enabled. By default, it's enabled in all browsers that support CSS3 Transitions except Android.
     * <p>
     * Default: true
     */
    @Option
    public Boolean zoomAnimation;

    /**
     * Won't animate zoom if the zoom difference exceeds this value.
     * <p>
     * Default: 4
     */
    @Option
    public Double zoomAnimationThreshold;

    /**
     * Whether the tile fade animation is enabled. By default, it's enabled in all browsers that support CSS3 Transitions except Android.
     * <p>
     * Default: true
     */
    @Option
    public Boolean fadeAnimation;

    /**
     * Whether markers animate their zoom with the zoom animation, if disabled they will disappear for the length of
     * the animation. By default, it's enabled in all browsers that support CSS3 Transitions except Android.
     * <p>
     * Default: true
     */
    @Option
    public Boolean markerZoomAnimation;

    /**
     * Defines the maximum size of a CSS translation transform. The default value should not be changed unless a web
     * browser positions layers in the wrong place after doing a large panBy.
     * <p>
     * Default: 2^23
     */
    @Option
    public Double transform3DLimit;

    /**
     * If enabled, panning of the map will have an inertia effect where the map builds momentum while dragging and
     * continues moving in the same direction for some time. Feels especially nice on touch devices. Enabled by default
     * unless running on old Android devices.
     */
    @Option
    public Boolean inertia;

    /**
     * Max speed of the inertial movement, in pixels/second.
     * <p>
     * Default: no limit
     */
    @Option
    public Integer inertiaDeceleration;

    /**
     * No information.
     * <p>
     * Default: 0.2
     */
    @Option
    public Double easeLinearity;

    /**
     * With this option enabled, the map tracks when you pan to another "copy" of the world and seamlessly jumps to the
     * original one so that all overlays like markers and vector layers are still visible.
     * <p>
     * Default: false
     */
    @Option
    public Boolean worldCopyJump;

    /**
     * If maxBounds is set, this option will control how solid the bounds are when dragging the map around. The default
     * value of 0.0 allows the user to drag outside the bounds at normal speed, higher values will slow down map
     * dragging outside bounds, and 1.0 makes the bounds fully solid, preventing the user from dragging outside the bounds.
     * <p>
     * Default: 0.0
     */
    @Option
    public Double maxBoundsViscosity;

    /**
     * Makes the map focusable and allows users to navigate the map with keyboard arrows and +/- keys.
     * <p>
     * Default: true
     */
    @Option
    public Boolean keyboard;

    /**
     * Amount of pixels to pan when pressing an arrow key.
     * <p>
     * Default: 80
     */
    @Option
    public Integer keyboardPanDelta;

    /**
     * Whether the map can be zoomed by using the mouse wheel.
     * <p>
     * Default: true
     */
    @Option("scrollWheelZoom")
    public Boolean scrollWheelZoomBoolean;

    /**
     * Whether the map can be zoomed by using the mouse wheel. If passed 'center', it will zoom to the center of the
     * view regardless of where the mouse was.
     */
    @Option("scrollWheelZoom")
    public String scrollWheelZoomString;

    /**
     * Limits the rate at which a wheel can fire (in milliseconds). By default, user can't zoom via wheel more often
     * than once per 40 ms.
     * <p>
     * Default: 40
     */
    @Option
    public Long wheelDebounceTime;

    /**
     * How many scroll pixels (as reported by L.DomEvent.getWheelDelta) mean a change of one full zoom level.
     * Smaller values will make wheel-zooming faster (and vice versa).
     * <p>
     * Default: 60
     */
    @Option
    public Integer wheelPxPerZoomLevel;

    /**
     * Enables mobile hacks for supporting instant taps (fixing 200ms click delay on iOS/Android) and touch holds
     * (fired as contextmenu events).
     * <p>
     * Default: true
     */
    @Option
    public Boolean tap;

    /**
     * The max number of pixels a user can shift his finger during touch for it to be considered a valid tap.
     * <p>
     * Default: 15
     */
    @Option
    public Integer tapTolerance;

    /**
     * Whether the map can be zoomed by touch-dragging with two fingers. Enabled for touch-capable web browsers except
     * for old Androids.
     * <p>
     * Default: *
     */
    @Option("touchZoom")
    public Boolean touchZoomBoolean;

    /**
     * Whether the map can be zoomed by touch-dragging with two fingers. If passed 'center', it will zoom to the center
     * of the view regardless of where the touch events (fingers) were. Enabled for touch-capable web browsers except
     * for old Androids.
     * <p>
     * Default: *
     */
    @Option("touchZoom")
    public String touchZoomString;

    /**
     * Set it to false if you don't want the map to zoom beyond min/max zoom and then bounce back when pinch-zooming.
     * <p>
     * Default: true
     */
    @Option
    public Boolean bounceAtZoomLimits;


}
