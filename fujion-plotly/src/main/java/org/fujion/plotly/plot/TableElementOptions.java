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
import org.fujion.plotly.common.HorizontalAlignEnum;

/**
 * Options for a table header.
 */
public class TableElementOptions extends Options {

    /**
     * The horizontal alignment of the "text" within the box. Has an effect only if "text" spans
     * more two or more lines (i.e. "text" contains one or more HTML tags) or if an explicit width
     * is set to override the text width.
     */
    public HorizontalAlignEnum align;

    /**
     * The cell value formatting rule using d3 formatting mini-language which is similar to those of
     * Python. See <a href="https://github.com/d3/d3-format/blob/master/README.md#locale_format">d3
     * formatting reference.</a>
     */
    public String[] format;

    /**
     * The height of cells.
     * <p>
     * Default: 28
     */
    public Integer height;

    /**
     * Line options.
     */
    public final LineOptions line = new LineOptions();

    /**
     * Prefix for cell values.
     */
    public String[] prefix$array;

    /**
     * Prefix for cell values.
     */
    public String prefix$string;
    
    /**
     * Suffix for cell values.
     */
    public String[] suffix$array;
    
    /**
     * Suffix for cell values.
     */
    public String suffix$string;
    
    /**
     * Header cell values. "values[m][n]" represents the value of the nth point in column "m",
     * therefore the "values[m]" vector length for all columns must be the same (longer vectors will
     * be truncated). Each value must be a finite number or a string.
     */
    public double[][] values;

}
