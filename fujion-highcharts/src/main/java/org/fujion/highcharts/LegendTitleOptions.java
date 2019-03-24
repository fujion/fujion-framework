/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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
 * Options for a legend title.
 */
public class LegendTitleOptions extends Options {
    
    /**
     * Generic CSS styles for the legend title. Defaults to:
     *
     * <pre>
     * style: {
     *    fontWeight: 'bold'
     * }
     * </pre>
     */
    @Option
    public final StyleOptions style = new StyleOptions();
    
    /**
     * A text or HTML string for the title. Defaults to null.
     */
    @Option
    public String text;
    
}
