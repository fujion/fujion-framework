/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Layout options for projections.
 */
public class ProjectionOptions extends Options {
    
    public enum TypeEnum {
        EQUIRECTANGULAR, MERCATOR, ORTHOGRAPHIC, NATURAL_EARTH, KAVRAYSKIY7, MILLER, ROBINSON, ECKERT4, AZIMUTHAL_EQUAL_AREA, AZIMUTHAL_EQUIDISTANT, CONIC_EQUAL_AREA, CONIC_CONFORMAL, CONIC_EQUIDISTANT, GNOMONIC, STEREOGRAPHIC, MOLLWEIDE, HAMMER, TRANSVERSE_MERCATOR, ALBERS_USA, WINKEL_TRIPEL, AITOFF, SINUSOIDAL;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * The projection type.
     */
    @Option
    public TypeEnum type;

    /**
     * Rotates the map along parallels (in degrees East). Defaults to the center of the
     * "lonaxis.range" values.
     */
    @Option
    public Double rotation_lon;

    /**
     * Rotates the map along meridians (in degrees North).
     */
    @Option
    public Double rotation_lat;

    /**
     * Roll the map (in degrees) For example, a roll of 180 makes the map appear upside down.
     */
    @Option
    public Integer rotation_roll;
    
    /**
     * For conic projection types only. Sets the parallels (tangent, secant) where the cone
     * intersects the sphere.
     */
    @Option
    public double[] parallels;

    /**
     * Zooms in or out on the map view. A scale of 1 corresponds to the largest zoom level that fits
     * the map's lon and lat ranges.
     * <p>
     * Default: 1
     */
    @Option
    public Integer scale;
}
