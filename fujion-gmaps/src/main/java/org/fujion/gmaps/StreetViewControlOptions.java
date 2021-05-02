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
package org.fujion.gmaps;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for the rendering of the Street View pegman control on the map.
 */
public class StreetViewControlOptions extends Options {
    
    /**
     * Position id. Used to specify the position of the control on the map. The default position is
     * embedded within the navigation (zoom and pan) controls. If this position is empty or the same
     * as that specified in the zoomControlOptions or panControlOptions, the Street View control
     * will be displayed as part of the navigation controls. Otherwise, it will be displayed
     * separately.
     */
    @Option
    public ControlPosition position;
}
