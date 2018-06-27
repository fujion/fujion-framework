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
package org.fujion.plotly.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for font settings.
 */
public class FontOptions extends Options {
    
    /**
     * The font color.
     */
    @Option
    public String color;
    
    /**
     * The typeface that will be applied by the web browser. The web browser will only be able to
     * apply a font if it is available on the system which it operates. Provide multiple font
     * families, separated by commas, to indicate the preference in which to apply fonts if they
     * aren't available on the system. The plotly service (at https://plot.ly or on-premise)
     * generates images on a server, where only a select number of fonts are installed and
     * supported. These include "Arial", "Balto", "Courier New", "Droid Sans",, "Droid Serif",
     * "Droid Sans Mono", "Gravitas One", "Old Standard TT", "Open Sans", "Overpass", "PT Sans
     * Narrow", "Raleway", "Times New Roman".
     */
    @Option
    public String family;

    /**
     * The font size.
     * <p>
     * Constraints: &ge;1
     */
    @Option
    public Integer size;
}
