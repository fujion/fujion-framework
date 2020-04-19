/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
package org.fujion.gmaps.event;

import java.util.Map;

import org.fujion.annotation.EventType;
import org.fujion.annotation.EventType.EventParameter;
import org.fujion.annotation.OnFailure;
import org.fujion.event.Event;
import org.fujion.gmaps.LatLng;

/**
 * A map location event.
 */
@EventType(MapLocationEvent.TYPE)
public class MapLocationEvent extends Event {
    
    /**
     * The event type.
     */
    public static final String TYPE = "gmap_location";

    @EventParameter(onFailure = OnFailure.EXCEPTION)
    private Map<String, Number> value;
    
    public LatLng getLocation() {
        return new LatLng(get("lat"), get("lng"));
    }
    
    private double get(String key) {
        return value.get(key).doubleValue();
    }

}
