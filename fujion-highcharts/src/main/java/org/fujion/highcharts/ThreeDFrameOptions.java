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
package org.fujion.highcharts;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for a 3D frame.
 */
public class ThreeDFrameOptions extends Options {

    /**
     * The color of the panel. Defaults to transparent.
     */
    @Option
    public String color;
    
    /**
     * The thickness of the panel. Defaults to 1.
     */
    @Option
    public Integer size;
    
    /**
     * Whether to display the frame. Possible values are true, false, "auto" to display only the
     * frames behind the data, and "default" to display faces behind the data based on the axis
     * layout, ignoring the point of view. Defaults to default.
     */
    @Option
    public Boolean visible;
}
