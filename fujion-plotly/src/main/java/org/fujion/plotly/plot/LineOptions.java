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
package org.fujion.plotly.plot;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.plotly.common.DashStyleEnum;

public class LineOptions extends Options {
    
    /**
     * Determines the line shape. With "spline" the lines are drawn using spline interpolation. The
     * other available values correspond to step-wise line shapes.
     */
    public enum ShapeEnum {

        HV, HVH, LINEAR, SPLINE, VH, VHV;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * The line color.
     */
    @Option
    public String color;

    /**
     * The dash style of lines.
     * <p>
     * Default: SOLID
     */
    @Option
    public DashStyleEnum dash$enum;

    /**
     * An alternative in the form of a dash length list in px (eg "5px,10px,2px,2px").
     */
    @Option
    public String dash$string;
    
    /**
     * Determines the line shape. With "spline" the lines are drawn using spline interpolation. The
     * other available values correspond to step-wise line shapes.
     */
    @Option
    public ShapeEnum shape;

    /**
     * Simplifies lines by removing nearly-collinear points. When transitioning lines, it may be
     * desirable to disable this so that the number of points along the resulting SVG path is
     * unaffected.
     * <p>
     * Default: true
     */
    @Option
    public Boolean simplify;

    /**
     * The amount of smoothing. 0 corresponds to no smoothing (equivalent to a "linear" shape).
     * <p>
     * Default: 1
     */
    @Option
    public Double smoothing;
    
    /**
     * The line width (in px).
     * <p>
     * Default: 2
     */
    @Option
    public Integer width;
}
