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
package org.fujion.highcharts;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Set options on specific levels. Takes precedence over series options, but not point options.
 */
public class LevelOptions extends Options {
    
    /**
     * Color on all points which lies on the same level.
     */
    @Option
    public String borderColor;
    
    /**
     * Border dash style on all points which lies on the same level.
     */
    @Option
    public DashStyle borderDashStyle;
    
    /**
     * Border width on all points which lies on the same level.
     */
    @Option
    public Integer borderWidth;
    
    /**
     * Color on all points which lies on the same level.
     */
    @Option
    public String color;
    
    /**
     * The key of a color variation. Currently supports brightness only.
     */
    @Option("colorVariation.key")
    public String colorVariation_key;
    
    /**
     * The ending value of a color variation. The last sibling will receive this value.
     */
    @Option("colorVariation.to")
    public Double colorVariation_to;
    
    /**
     * Data labels on all points which lies on the same level.
     */
    @Option
    public Object dataLabels;

    /**
     * Level size on all points which lies on the same level.
     */
    @Option
    public Object levelSize;

    /**
     * Rotation on all points which lies on the same level.
     */
    @Option
    public Double rotation;
    
    /**
     * Rotation mode on all points which lies on the same level.
     */
    @Option
    public String rotationMode;
}
