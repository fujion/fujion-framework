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
import org.fujion.common.StrUtil;

/**
 * Latitude/longitude coordinates.
 */
public class LatLng extends Options {

    /**
     * Parse the string into a LatLng instance.
     *
     * @param value Value to parse.
     * @return Parse result.
     */
    public static LatLng parse(String value) {
        if (value == null) {
            return null;
        }
        
        value = StrUtil.xlate(value, "+[]()° ", "").toUpperCase();
        String[] pcs = value.split(",");
        
        if (pcs.length != 2) {
            return null;
        }
        
        return new LatLng(parse(pcs[0], "NS"), parse(pcs[1], "EW"));
    }
    
    private static double parse(String value, String dir) {
        int i = value.isEmpty() ? -1 : dir.indexOf(value.charAt(value.length() - 1));
        
        if (i > -1) {
            value = value.substring(0, value.length() - 1);
        }
        
        double result = Double.parseDouble(value);
        return i == -1 ? result : i == 1 ? -result : result;
    }

    /**
     * Latitude specified in degrees within the range [-90, 90].
     */
    @Option
    protected double lat;

    /**
     * Longitude specified in degrees within the range [-180, 180].
     */
    @Option
    protected double lng;

    /**
     * Set noWrap to true to enable values outside of this range. Note the ordering of latitude and
     * longitude.
     */
    @Option
    public Boolean noWrap;

    public LatLng(double lat, double lng) {
        this(lat, lng, null);
    }
    
    public LatLng(double lat, double lng, Boolean noWrap) {
        this.lat = lat;
        this.lng = lng;
        this.noWrap = noWrap;
    }

    /**
     * Returns the latitude component.
     *
     * @return The latitude component.
     */
    public double getLatitude() {
        return lat;
    }

    /**
     * Returns the longitude component.
     *
     * @return The longitude component.
     */
    public double getLongitude() {
        return lng;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else if (o instanceof LatLng p) {
            return p.lat == lat && p.lng == lng;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        toString(sb, lat, "N", "S").append(", ");
        toString(sb, lng, "E", "W").append("]");
        return sb.toString();
    }
    
    private StringBuilder toString(StringBuilder sb, double value, String dir1, String dir2) {
        String dir = value >= 0.0 ? dir1 : dir2;
        sb.append(Math.abs(value)).append("°").append(dir);
        return sb;
    }
}
