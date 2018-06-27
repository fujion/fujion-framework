/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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

/**
 * Controls how the API handles gestures on the map.
 */
public enum GestureHandlingType {
    
    /**
     * Gesture handling is either cooperative or greedy, depending on whether the page is scrollable
     * or in an iframe.
     */
    AUTO,
    /**
     * Scroll events and one-finger touch gestures scroll the page, and do not zoom or pan the map.
     * Two-finger touch gestures pan and zoom the map. Scroll events with a ctrl key or ⌘ key
     * pressed zoom the map. In this mode the map cooperates with the page.
     */
    COOPERATIVE,
    /**
     * All touch gestures and scroll events pan or zoom the map.
     */
    GREEDY,
    /**
     * The map cannot be panned or zoomed by user gestures.
     */
    NONE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
