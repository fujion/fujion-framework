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
package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for layout.
 */
public class LayoutOptions extends Options {

    /**
     * Bottom padding in pixels.
     * <p>
     * Default: 0
     */
    @Option
    public Integer padding_bottom;
    
    /**
     * Left padding in pixels.
     * <p>
     * Default: 0
     */
    @Option
    public Integer padding_left;
    
    /**
     * Right padding in pixels.
     * <p>
     * Default: 0
     */
    @Option
    public Integer padding_right;
    
    /**
     * Top padding in pixels.
     * <p>
     * Default: 0
     */
    @Option
    public Integer padding_top;
    
}
