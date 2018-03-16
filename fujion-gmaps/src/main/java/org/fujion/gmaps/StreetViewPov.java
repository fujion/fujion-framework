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
package org.fujion.gmaps;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * A point of view object which specifies the camera's orientation at the Street View panorama's
 * position.
 */
public class StreetViewPov extends Options {

    /**
     * The camera heading in degrees relative to true north. True north is 0°, east is 90°, south is
     * 180°, west is 270°.
     */
    @Option
    public Double heading;
    
    /**
     * The camera pitch in degrees, relative to the street view vehicle. Ranges from 90° (directly
     * upwards) to -90° (directly downwards).
     */
    @Option
    public Double pitch;
}
