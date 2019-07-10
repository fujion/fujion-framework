/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.component.BaseUIComponent;
import org.fujion.gmaps.event.CenterChangeEvent;
import org.fujion.gmaps.event.HeadingChangeEvent;
import org.fujion.gmaps.event.MapTypeIdChangeEvent;
import org.fujion.gmaps.event.TiltChangeEvent;
import org.fujion.gmaps.event.ZoomChangeEvent;
import org.springframework.util.Assert;

/**
 * Fujion wrapper for Google Maps library.
 */
@Component(
        tag = "gmap",
        widgetModule = "fujion-gmaps",
        widgetClass = "GMap",
        parentTag = "*",
        description = "Google Maps component.")
public class GoogleMap extends BaseUIComponent {
    
    private boolean running;

    private final MapOptions options = new MapOptions();
    
    public void run() {
        invoke("run", options, LoaderOptions.getInstance().validate());
        running = true;
    }

    public void clear() {
        invoke("clear");
        running = false;
    }
    
    private void ensureRunning() {
        Assert.state(running, () -> "A map has not yet been created");
    }

    public MapOptions getOptions() {
        return options;
    }

    /**
     * Changes the center of the map by the given distance in pixels. If the distance is less than
     * both the width and height of the map, the transition will be smoothly animated. Note that the
     * map coordinate system increases from west to east (for x values) and north to south (for y
     * values).
     *
     * @param x The x increment.
     * @param y The y increment.
     */
    public void panBy(double x, double y) {
        invokeMap("panBy", x, y);
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
     * Pans the map by the minimum amount necessary to contain the given LatLngBounds. It makes no
     * guarantee where on the map the bounds will be, except that as much of the bounds as possible
     * will be visible. The bounds will be positioned inside the area bounded by the map type and
     * navigation (pan, zoom, and Street View) controls, if they are present on the map. If the
     * bounds is larger than the map, the map will be shifted to include the northwest corner of the
     * bounds. If the change in the map's position is less than both the width and height of the
     * map, the transition will be smoothly animated.
     *
     * @param bounds The new bounds.
     */
    public void panToBounds(LatLngBounds bounds) {
        invokeMap("panToBounds", bounds);
    }

    private void invokeMap(String function, Object... args) {
        ensureRunning();
        invoke("invoke", function, args);
    }

    /**
     * Returns the current center of the map.
     *
     * @return The current center of the map.
     */
    @PropertyGetter(value = "center", description = "The latitude and longitude of the current center of the map.")
    public LatLng getCenter() {
        return options.center;
    }
    
    /**
     * Sets the current center of the map.
     *
     * @param center The current center of the map.
     */
    public void setCenter(LatLng center) {
        propertyChange("center", options.center, options.center = center, running);
    }

    @PropertySetter(value = "center", description = "The latitude and longitude of the current center of the map.")
    private void setCenter(String center) {
        setCenter(LatLng.parse(center));
    }
    
    /**
     * Returns the tilt setting, which controls the automatic switching behavior for the angle of
     * incidence of the map.
     *
     * @return The tilt setting
     */
    @PropertyGetter(value = "tilt", description = "Controls the automatic switching behavior for the angle of incidence of the map.")
    public TiltAngle getTilt() {
        return options.tilt;
    }
    
    /**
     * Set the current tilt value, which controls the automatic switching behavior for the angle of
     * incidence of the map.
     *
     * @param tilt The new tilt setting.
     */
    @PropertySetter(value = "tilt", description = "Controls the automatic switching behavior for the angle of incidence of the map.")
    public void setTilt(TiltAngle tilt) {
        propertyChange("tilt", options.tilt, options.tilt = tilt, running);
    }

    /**
     * Returns the {@link MapOptions#zoom zoom value}.
     *
     * @return The zoom value.
     */
    @PropertyGetter(value = "zoom", description = "The zoom value.")
    public int getZoom() {
        return options.zoom;
    }

    /**
     * Sets the current {@link MapOptions#zoom zoom value}.
     *
     * @param zoom The new zoom value.
     */
    @PropertySetter(value = "zoom", description = "The zoom value.")
    public void setZoom(int zoom) {
        propertyChange("zoom", options.zoom, options.zoom = zoom, running);
    }

    @EventHandler(value=TiltChangeEvent.TYPE, mode = "init")
    private void _onTiltChanged(TiltChangeEvent event) {
        propertyChange("tilt", options.tilt, options.tilt = event.getTilt(), false);
    }
    
    @EventHandler(value=ZoomChangeEvent.TYPE, mode = "init")
    private void _onZoomChanged(ZoomChangeEvent event) {
        propertyChange("zoom", options.zoom, options.zoom = event.getZoom(), false);
    }

    @EventHandler(value=CenterChangeEvent.TYPE, mode = "init")
    private void _onCenterChanged(CenterChangeEvent event) {
        options.center = event.getLocation();
    }

    @EventHandler(value=HeadingChangeEvent.TYPE, mode = "init")
    private void _onHeadingChanged(HeadingChangeEvent event) {
        options.heading = event.getHeading();
    }

    @EventHandler(value=MapTypeIdChangeEvent.TYPE, mode = "init")
    private void _onMapTypeIdChanged(MapTypeIdChangeEvent event) {
        options.mapTypeId$enum = event.getMapTypeIdEnum();
        options.mapTypeId$string = options.mapTypeId$enum == null ? event.getMapTypeIdString() : null;
    }
}
