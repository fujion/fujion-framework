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
package org.fujion.highcharts;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for a gauge dial.
 */
public class DialOptions extends Options {
    
    /**
     * The background or fill color of the gauge's dial. Defaults to black.
     */
    @Option
    public String backgroundColor;
    
    /**
     * The length of the dial's base part, relative to the total radius or length of the dial.
     * Defaults to 70%.
     */
    @Option
    public String baseLength;
    
    /**
     * The pixel width of the base of the gauge dial. The base is the part closest to the pivot,
     * defined by baseLength. Defaults to 3.
     */
    @Option
    public Integer baseWidth;
    
    /**
     * The border color or stroke of the gauge's dial. By default, the borderWidth is 0, so this
     * must be set in addition to a custom border color. Defaults to silver.
     */
    @Option
    public String borderColor;
    
    /**
     * The width of the gauge dial border in pixels. Defaults to 0.
     */
    @Option
    public Integer borderWidth;
    
    /**
     * The radius or length of the dial, in percentages relative to the radius of the gauge itself.
     * Defaults to 80%.
     */
    @Option
    public String radius;
    
    /**
     * The length of the dial's rear end, the part that extends out on the other side of the pivot.
     * Relative to the dial's length. Defaults to 10%.
     */
    @Option
    public String rearLength;
    
    /**
     * The width of the top of the dial, closest to the perimeter. The pivot narrows in from the
     * base to the top. Defaults to 1.
     */
    @Option
    public String topWidth;
}
