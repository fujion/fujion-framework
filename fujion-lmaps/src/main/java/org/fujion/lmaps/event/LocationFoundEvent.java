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
import org.fujion.lmaps.LatLngBounds;

import java.util.Map;

/**
 * Fired when geolocation (using the locate method) completed successfully.
 */
@EventType(LocationFoundEvent.TYPE)
public class LocationFoundEvent extends Event {

    /**
     * The event type.
     */
    public static final String TYPE = "lmap_locationfound";

    private LatLng location;

    private LatLngBounds bounds;

    @EventParameter(value = "latlng", onFailure = OnFailure.EXCEPTION)
    private Map<String, Number> _location;

    @EventParameter(value = "bounds", onFailure = OnFailure.EXCEPTION)
    private Map<String, Map<String, Number>> _bounds;

    @EventParameter(onFailure = OnFailure.EXCEPTION)
    private Integer accuracy;

    @EventParameter(onFailure = OnFailure.EXCEPTION)
    private Long timestamp;

    @EventParameter
    private Double altitude;

    @EventParameter
    private Integer altitudeAccuracy;

    @EventParameter
    private Integer heading;

    @EventParameter
    private Integer speed;

    public LatLng getLocation() {
        return location;
    }

    public LatLngBounds getBounds() {
        return bounds;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public Double getAltitude() {
        return altitude;
    }

    public Integer getAltitudeAccuracy() {
        return altitudeAccuracy;
    }

    public Integer getHeading() {
        return heading;
    }

    public Integer getSpeed() {
        return speed;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    protected void afterInitialized() {
        super.afterInitialized();
        location = new LatLng(_location);
        bounds = new LatLngBounds(_bounds);
    }

}
