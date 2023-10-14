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
package org.fujion.gmaps.event;

import org.fujion.ancillary.IEnumWithValue;
import org.fujion.annotation.EventType;
import org.fujion.annotation.EventType.EventParameter;
import org.fujion.annotation.OnFailure;
import org.fujion.event.Event;
import org.fujion.gmaps.TiltAngle;

/**
 * Tilt change event
 */
@EventType(TiltChangeEvent.TYPE)
public class TiltChangeEvent extends Event {
    
    /**
     * The event type.
     */
    public static final String TYPE = "gmap_tilt_changed";

    @EventParameter(onFailure = OnFailure.EXCEPTION)
    private int value;
    
    private TiltAngle tilt;
    
    public TiltAngle getTilt() {
        return tilt == null ? tilt = IEnumWithValue.fromValue(TiltAngle.class, value) : tilt;
    }
}
