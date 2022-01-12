/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.common.Assert;
import org.fujion.component.BaseUIComponent;

/**
 * Fujion wrapper for Leaflet.js interactive maps.
 */
@Component(
        tag = "lmap",
        widgetModule = "fujion-lmaps",
        widgetClass = "LeafletMap",
        parentTag = "*",
        description = "Fujion wrapper for leaflet.js interactive maps.")
public class LeafletMap extends BaseUIComponent {

    private final LeafletOptions options = new LeafletOptions();

    private boolean running;

    public LeafletMap() {
        super();
    }

    /**
     * Removes the current map.
     */
    public void clear() {
        running = false;
        invoke("reset");
    }

    /**
     * Display the map on the client using the current options.
     */
    public void run() {
        invoke("run", options);
        running = true;
    }

    /**
     * Returns true if a map is currently displayed on the client.
     *
     * @return True if a map is being displayed.
     */
    public boolean isRunning() {
        return running;
    }

    private void ensureRunning() {
        Assert.state(running, () -> "A map has not yet been created");
    }

    /**
     * Returns the option map.
     *
     * @return The option map.
     */
    public LeafletOptions getOptions() {
        return options;
    }

    /**
     * Invokes a function on the map object.  An exception is thrown if the map object
     * has not been instantiated.
     *
     * @param function The function to invoke.
     * @param args     The function arguments.
     */
    private void invokeMap(
            String function,
            Object... args) {
        ensureRunning();
        invoke("invoke", function, args);
    }

    /**
     * Get the center coordinates.
     *
     * @return The coordinates of the map's center.
     */
    @PropertyGetter(value = "center", description = "The coordinates of the map's center.")
    public LatLng getCenter() {
        return options.center;
    }

    /**
     * Set the center coordinates.
     *
     * @param center The center coordinates.
     */
    @PropertySetter(value = "center", description = "The coordinates of the map's center.")
    public void setCenter(String center) {
        setCenter(LatLng.parse(center));
    }

    /**
     * Sets the current center of the map.
     *
     * @param center The current center of the map.
     */
    public void setCenter(LatLng center) {
        if (propertyChange("center", options.center, options.center = center, false) && running) {
            panTo(center);
        }
    }

    /**
     * Get the map's zoom level.
     *
     * @return The map's zoom level.
     */
    @PropertyGetter(value = "zoom", description = "The map's current zoom level.")
    public int getZoom() {
        return options.zoom;
    }

    /**
     * Set the map's zoom level.
     *
     * @param zoom The map's zoom level.
     */
    @PropertySetter(value = "zoom", description = "The map's current zoom level.")
    public void setZoom(int zoom) {
        if (propertyChange("zoom", options.zoom, options.zoom = zoom, false) && running) {
            invokeMap("setZoom", zoom);
        }
    }

    /**
     * Gets the lower limit for the available zoom levels.
     *
     * @return The lower limit for the available zoom levels.
     */
    @PropertyGetter(value = "minZoom", description = "The lower limit for the available zoom levels.")
    public int getMinZoom() {
        return options.minZoom;
    }

    /**
     * Set the lower limit for the available zoom levels.
     *
     * @param minZoom The lower limit for the available zoom levels.
     */
    @PropertySetter(value = "minZoom", description = "The lower limit for the available zoom levels.")
    public void setMinZoom(int minZoom) {
        if (propertyChange("minZoom", options.minZoom, options.minZoom = minZoom, false) && running) {
            invokeMap("setMinZoom", minZoom);
        }
    }

    /**
     * Gets the upper limit for the available zoom levels.
     *
     * @return The upper limit for the available zoom levels.
     */
    @PropertyGetter(value = "maxZoom", description = "The upper limit for the available zoom levels.")
    public int getMaxZoom() {
        return options.maxZoom;
    }

    /**
     * Set the upper limit for the available zoom levels.
     *
     * @param maxZoom The upper limit for the available zoom levels.
     */
    @PropertySetter(value = "maxZoom", description = "The upper limit for the available zoom levels.")
    public void setMaxZoom(int maxZoom) {
        if (propertyChange("maxZoom", options.maxZoom, options.maxZoom = maxZoom, false) && running) {
            invokeMap("setMaxZoom", maxZoom);
        }
    }

    /**
     * Pans the map to a given center.
     *
     * @param x The x increment.
     * @param y The y increment.
     */
    public void panBy(
            double x,
            double y) {
        invokeMap("panBy", new Point(x, y));
    }

    /**
     * Changes the center of the map to the given LatLng. If the change is less than both the width
     * and height of the map, the transition will be smoothly animated.
     *
     * @param center The new map center.
     */
    public void panTo(LatLng center) {
        invokeMap("panTo", center);
    }

    /**
     * Pans the map to the closest view that would lie inside the given bounds (if it's not already).
     *
     * @param bounds The restricting bounds.
     */
    public void panInside(LatLngBounds bounds) {
        invoke("panInsideBounds", bounds);
    }

    /**
     * Pans the map the minimum amount to make the latlng visible. Use padding, paddingTopLeft and paddingTopRight
     * options to fit the display to more restricted bounds, like fitBounds. If latlng is already within the
     * (optionally padded) display bounds, the map will not be panned.
     *
     * @param latlng The location to make visible.
     */
    public void panInside(LatLng latlng) {
        invoke("panInside", latlng);
    }

    /**
     * Sets a map view that contains the given geographical bounds with the maximum zoom level possible.
     *
     * @param bounds The bounds.
     */
    public void fitBounds(LatLngBounds bounds) {
        invoke("fitBounds", bounds);
    }

    /**
     * Increments the zoom of the map by delta.  A positive value zooms in by that amount,
     * a negative value zooms out.
     *
     * @param delta The amount to increment.
     */
    public void zoom(int delta) {
        if (delta > 0) {
            invokeMap("zoomIn", delta);
        } else if (delta < 0) {
            invokeMap("zoomOut", -delta);
        }
    }

    /**
     * Zooms the map while keeping a specified geographical position on the map stationary.
     *
     * @param position The position to remain stationary.
     */
    public void zoomAround(LatLng position) {
        Assert.notNull(position, "Position must not be null");
        invokeMap("setZoomAround", position);
    }

    /**
     * Zooms the map while keeping a specified pixel on the map (relative to the top-left corner) stationary.
     *
     * @param offset The pixel offset.
     */
    public void zoomAround(Point offset) {
        Assert.notNull(offset, "Offset must not be null");
        invokeMap("setZoomAround", offset);
    }

    public void flyToBounds(LatLngBounds bounds) {
        Assert.notNull(bounds, "Bounds must not be null");
        invokeMap("flyToBounds", bounds);
    }

    /**
     * Tries to locate the user using the Geolocation API, firing a locationfound event with location data on success
     * or a locationerror event on failure. Note that, if your page doesn't use HTTPS, this method will fail in modern
     * browsers.
     */
    public void locate() {
        invokeMap("locate");
    }

    /**
     * Tries to locate the user using the Geolocation API, firing a locationfound event with location data on success
     * or a locationerror event on failure, and optionally sets the map view to the user's location with respect to
     * detection accuracy (or to the world view if geolocation failed). Note that, if your page doesn't use HTTPS,
     * this method will fail in modern browsers.
     *
     * @param options The locate options.
     */
    public void locate(LocateOptions options) {
        invokeMap("locate", options);
    }

    /**
     * Stops watching location previously initiated by map.locate({watch: true}) and aborts resetting the map view if
     * map.locate was called with {setView: true}.
     */
    public void stopLocate() {
        invokeMap("stopLocate");
    }
}
