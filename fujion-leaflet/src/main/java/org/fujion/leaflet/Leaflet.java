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
package org.fujion.leaflet;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.BaseUIComponent;

/**
 * Fujion wrapper for Chart.js component.
 */
@Component(
        tag = "leaflet",
        widgetModule = "fujion-leaflet",
        widgetClass = "Leaflet",
        parentTag = "*",
        description = "Fujion wrapper for leaflet.js component.")
public class Leaflet extends BaseUIComponent {

    private final LeafletOptions options = new LeafletOptions();

    private boolean running;

    public Leaflet() {
        super();
    }

    /**
     * Removes all series and data points and destroys the client graph.
     */
    public void clear() {
        running = false;
        invoke("reset");
    }

    /**
     * Build the graph on the client.
     */
    public void run() {
        invoke("run", options);
        running = true;
    }

    /**
     * Returns true if a chart is currently running on the client.
     *
     * @return True if a chart is running.
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
