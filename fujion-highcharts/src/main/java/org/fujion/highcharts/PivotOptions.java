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
 * Options for a pivot.
 */
public class PivotOptions extends Options {
    
    /**
     * The background color or fill of the pivot. Defaults to black.
     */
    @Option
    public String backgroundColor;
    
    /**
     * The border or stroke color of the pivot. In able to change this, the borderWidth must also be
     * set to something other than the default 0. Defaults to silver.
     */
    @Option
    public String borderColor;
    
    /**
     * The border or stroke width of the pivot. Defaults to 0.
     */
    @Option
    public Integer borderWidth;
    
    /**
     * The pixel radius of the pivot. Defaults to 5.
     */
    @Option
    public Integer radius;
    
}
