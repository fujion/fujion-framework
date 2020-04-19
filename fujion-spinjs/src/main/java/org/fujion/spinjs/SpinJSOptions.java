/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
package org.fujion.spinjs;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

public class SpinJSOptions extends Options {

    /**
     * The number of lines to draw.
     * <p>
     * Default: 12
     */
    @Option
    public Integer lines;
    
    /**
     * The length of each line.
     * <p>
     * Default: 7
     */
    @Option
    public Integer length;
    
    /**
     * The line thickness.
     * <p>
     * Default: 5
     */
    @Option
    public Integer width;
    
    /**
     * The radius of the inner circle.
     * <p>
     * Default: 10
     */
    @Option
    public Integer radius;
    
    /**
     * Scales overall size of the spinner.
     * <p>
     * Default: 1
     */
    @Option
    public Double scale;
    
    /**
     * Corner roundness.
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 1
     */
    @Option
    public Double corners;
    
    /**
     * Line colors.
     * <p>
     * Default: "#000"
     */
    @Option("color")
    public String[] color$array;
    
    /**
     * Line color.
     * <p>
     * Default: "#000"
     */
    @Option("color")
    public String color$string;
    
    /**
     * Colors for fade effect.
     * <p>
     * Default: "transparent"
     */
    @Option("fadeColor")
    public String[] fadeColor$array;
    
    /**
     * Colors for fade effect.
     * <p>
     * Default: "transparent"
     */
    @Option("fadeColor")
    public String fadeColor$string;
    
    /**
     * Opacity of the lines.
     * <p>
     * Constraints: &ge;0 and &le;1
     * <p>
     * Default: 0.25
     */
    @Option
    public Double opacity;
    
    /**
     * The rotation offset.
     * <p>
     * Default: 0
     */
    @Option
    public Integer rotate;
    
    /**
     * 1: clockwise, -1: counterclockwise
     * <p>
     * Default: 1
     */
    @Option
    public Integer direction;
    
    /**
     * Rounds per second.
     * <p>
     * Default: 1
     */
    @Option
    public Integer speed;
    
    /**
     * After-glow percentage.
     * <p>
     * Constraints: &ge;0 and &le;100
     * <p>
     * Default: 100
     */
    @Option
    public Integer trail;
    
    /**
     * Top position relative to parent.
     * <p>
     * Default: "50%"
     */
    @Option
    public String top;
    
    /**
     * Left position relative to parent.
     * <p>
     * Default: "50%"
     */
    @Option
    public String left;
    
    /**
     * Box-shadow for the lines.
     * <p>
     * Default: "none"
     */
    @Option
    public String shadow;
    
    /**
     * Element positioning.
     */
    @Option
    public String position = "relative";
}
