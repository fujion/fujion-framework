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
package org.fujion.plotly.plot;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Dimension options.
 */
public class DimensionOptions extends Options {

    /**
     * The domain range to which the filter on the dimension is constrained. Must be an array of
     * "[fromValue, toValue]" with finite numbers as elements.
     */
    @Option
    public double[] constraintrange;
    
    /**
     * The shown name of the dimension.
     */
    @Option
    public String label;

    /**
     * The domain range that represents the full, shown axis extent. Defaults to the "values"
     * extent. Must be an array of "[fromValue, toValue]" with finite numbers as elements.
     */
    @Option
    public double[] range;

    /**
     * The tick label formatting rule using d3 formatting mini-language which is similar to those of
     * Python. See <a href="https://github.com/d3/d3-format/blob/master/README.md#locale_format">d3
     * format reference.</a>
     */
    @Option
    public String tickformat;

    /**
     * The text displayed at the ticks position via "tickvals". Only has an effect if "tickmode" is
     * set to "array". Used with "tickvals".
     */
    @Option
    public String[] ticktext;

    /**
     * The values at which ticks on this axis appear. Only has an effect if "tickmode" is set to
     * "array". Used with "ticktext".
     */
    @Option
    public double[] tickvals;
    
    /**
     * Dimension values. "values[n]" represents the value of the "n"th point in the dataset,
     * therefore the "values" vector for all dimensions must be the same (longer vectors will be
     * truncated). Each value must be a finite number.
     */
    @Option
    public double[] values;

    /**
     * Shows the dimension when set to true. Hides the dimension for false.
     * <p>
     * Default: true
     */
    @Option
    public Boolean visible;
    
}
