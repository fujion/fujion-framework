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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.plotly.common.VHOrientationEnum;

/**
 * Layout options for layout axes.
 */
public class LayoutAxisOptions extends Options {

    /**
     * The domain of the axis (in plot fraction). Default is [0,1].
     */
    @Option
    public Integer[] domain;

    /**
     *
     */
    @Option
    public Integer endpadding;
    
    /**
     * Defines the start and end point of the axis.
     */
    @Option
    public double[] range;
    
    /**
     * Determines whether or not the line bounding the axis will be shown on the figure.
     */
    @Option
    public Boolean showline;
    
    /**
     * Determines whether or not the axis ticks will feature tick labels.
     */
    @Option
    public Boolean showticklabels;
    
    /**
     * The color of the tick lines on the axis.
     */
    @Option
    public String tickcolor;
    
    /**
     * The length of the tick lines on the axis.
     */
    @Option
    public Integer ticklen;
    
    /**
     * The orientation (from the paper perspective) of the axis tick labels.
     */
    @Option
    public VHOrientationEnum tickorientation;
    
    /**
     * The length of the tick lines on the axis.
     */
    @Option
    public String ticksuffix;
    
    /**
     * Determines whether or not the axis will be visible.
     */
    @Option
    public Boolean visible;
}
