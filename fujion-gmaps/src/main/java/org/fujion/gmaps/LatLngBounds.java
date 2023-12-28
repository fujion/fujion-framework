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
package org.fujion.gmaps;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

import java.util.Objects;

/**
 * Represents a rectangle in geographical coordinates, including one that crosses the 180 degrees
 * longitudinal meridian.
 */
public class LatLngBounds extends Options {

    /**
     * The southwest corner of the rectangle.
     */
    @Option
    protected LatLng sw;
    
    /**
     * The northeast corner of the rectangle.
     */
    @Option
    protected LatLng ne;
    
    /**
     * Create a new bounds given SW and NE coordinates.
     *
     * @param sw The southwest coordinate.
     * @param ne The northeast coordinate.
     */
    public LatLngBounds(LatLng sw, LatLng ne) {
        this.sw = sw;
        this.ne = ne;
    }
    
    /**
     * Create a new bounds given individual latitudes and longitudes.
     *
     * @param north The north latitude.
     * @param south The south latitude.
     * @param east The east latitude.
     * @param west The west latitude.
     */
    public LatLngBounds(double north, double south, double east, double west) {
        this.sw = new LatLng(south, west);
        this.ne = new LatLng(north, east);
    }
    
    /**
     * Returns true if the given point is in this bounds.
     *
     * @param point The point to check.
     * @return True if the point is within the bounds.
     */
    public boolean contains(LatLng point) {
        return point.lat >= sw.lat && point.lat <= ne.lat && point.lng >= sw.lng && point.lng <= ne.lng;
    }

    /**
     * Extends this bounds to contain the given point.
     *
     * @param point The reference point.
     */
    public void extend(LatLng point) {
        if (point.lat < sw.lat) {
            sw.lat = point.lat;
        } else if (point.lat > ne.lat) {
            ne.lat = point.lat;
        }

        if (point.lng < sw.lng) {
            sw.lng = point.lng;
        } else if (point.lng > ne.lng) {
            ne.lng = point.lng;
        }
    }

    /**
     * Computes the center of this LatLngBounds.
     *
     * @return The center point.
     */
    public LatLng getCenter() {
        double lat = (ne.lat + sw.lat) / 2.0;
        double lng = (ne.lng + sw.lng) / 2.0;
        return new LatLng(lat, lng);
    }

    /**
     * Returns the north-east corner of this bounds.
     *
     * @return The north-east corner of this bounds.
     */
    public LatLng getNortheast() {
        return ne;
    }
    
    /**
     * Returns the south-west corner of this bounds.
     *
     * @return The south-west corner of this bounds.
     */
    public LatLng getSouthwest() {
        return sw;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else if (o instanceof LatLngBounds b) {
            return Objects.equals(sw, b.sw) && Objects.equals(ne, b.ne);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "(" + ne + ", " + sw + ")";
    }
}
