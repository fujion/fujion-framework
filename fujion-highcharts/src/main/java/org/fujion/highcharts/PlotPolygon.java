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

import org.fujion.annotation.Option;

/**
 * Options for polygon series.
 * <p>
 * A polygon series can be used to draw any free-form shape in the Cartesian coordinate system. A
 * fill is applied with the color option, and stroke is applied through lineWidth and lineColor
 * options. Requires the highcharts-more.js file.
 */
public class PlotPolygon extends PlotOptions {
    
    /**
     * Defaults to true.
     */
    @Option
    public Boolean trackByArea;
}
