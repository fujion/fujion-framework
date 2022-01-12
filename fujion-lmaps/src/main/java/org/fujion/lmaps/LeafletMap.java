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
        options.center = center == null ? null : LatLng.parse(center);
    }

}
