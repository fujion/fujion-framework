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

/**
 * Options for the rendering of the map type control.
 */
public class MapTypeControlOptions extends Options {

    /**
     * IDs of map types to show in the control.
     */
    @Option
    public MapTypeId[] mapTypeIds;

    /**
     * Position id. Used to specify the position of the control on the map.
     * <p>
     * Default: RIGHT_TOP
     */
    @Option
    public ControlPosition position;

    /**
     * Style id. Used to select what style of map type control to display.
     */
    @Option
    public MapTypeControlStyle style;
}
