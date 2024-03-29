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
 * Options for a histogram bin.
 */
public class BinOptions extends Options {
    
    /**
     * The ending value for the axis bins as a number.
     */
    @Option("end")
    public Double end$number;

    /**
     * The ending value for the axis bins as a categorical coordinate string.
     */
    @Option("end")
    public String end$string;

    /**
     * The step in-between value each axis bin as a number.
     */
    @Option("size")
    public Double size$number;

    /**
     * The step in-between value each axis bin as a categorical coordinate string.
     */
    @Option("size")
    public String size$string;

    /**
     * The starting value for the axis bins as a number.
     */
    @Option("start")
    public Double start$number;

    /**
     * The starting value for the axis bins as a categorical coordinate string.
     */
    @Option("start")
    public String start$string;
    
}
