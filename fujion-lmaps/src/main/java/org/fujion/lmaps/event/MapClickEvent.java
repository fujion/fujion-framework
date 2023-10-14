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
package org.fujion.lmaps.event;

import org.fujion.annotation.EventType;
import org.fujion.annotation.EventType.EventParameter;
import org.fujion.annotation.OnFailure;
import org.fujion.event.Event;
import org.fujion.lmaps.LatLng;
import org.fujion.lmaps.Point;

import java.util.Map;

/**
 * Map click event
 */
@EventType(MapClickEvent.TYPE)
public class MapClickEvent extends Event {

    /**
     * The event type.
     */
    public static final String TYPE = "lmap_click";

    private LatLng location;

    private Point layerPoint;

    private Point containerPoint;

    @EventParameter(value = "latlng", onFailure = OnFailure.EXCEPTION)
    private Map<String, Number> _location;

    @EventParameter(value="layerPoint", onFailure = OnFailure.EXCEPTION)
    private Map<String, Number> _layerPoint;

    @EventParameter(value="containerPoint", onFailure = OnFailure.EXCEPTION)
    private Map<String, Number> _containerPoint;

    public LatLng getLocation() {
        return location;
    }

    public Point getLayerPoint() {
        return layerPoint;
    }

    public Point getContainerPoint() {
        return containerPoint;
    }

    @Override
    protected void afterInitialized() {
        super.afterInitialized();
        location = new LatLng(_location);
        layerPoint = new Point(_layerPoint);
        containerPoint = new Point(_containerPoint);
    }

}
