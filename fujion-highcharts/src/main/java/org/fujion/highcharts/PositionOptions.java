/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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
 * Position configuration for visual elements.
 */
public class PositionOptions extends Options {
    
    /**
     * The horizontal alignment. Defaults to "right".
     */
    @Option
    public AlignHorizontal align;
    
    /**
     * The vertical alignment. Defaults to "bottom".
     */
    @Option
    public AlignVertical verticalAlign;
    
    /**
     * The x offset. Defaults to -10.
     */
    @Option
    public Integer x;
    
    /**
     * The y offset. Defaults to -5.
     */
    @Option
    public Integer y;
}
