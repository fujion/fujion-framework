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
package org.fujion.highcharts;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * A zone within a series.
 */
public class Zone extends Options {
    
    /**
     * Styled mode only. A custom CSS class name for the zone. Defaults to undefined.
     */
    @Option
    public String className;

    /**
     * Defines the color of the series. Defaults to undefined.
     */
    @Option
    public String color;
    
    /**
     * A name for the dash style to use for the graph. Defaults to undefined.
     */
    @Option
    public DashStyle dashStyle;

    /**
     * Defines the fill color for the series (in area type series) Defaults to undefined.
     */
    @Option
    public String fillColor;
    
    /**
     * The value up to where the zone extends, if undefined the zones stretches to the last value in
     * the series. Defaults to undefined.
     */
    @Option
    public Double value;
}
