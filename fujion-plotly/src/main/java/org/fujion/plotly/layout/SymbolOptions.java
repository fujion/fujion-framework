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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.common.TextPositionEnum;

/**
 * Layout options for layer symbols.
 */
public class SymbolOptions extends Options {

    /**
     * The symbol icon image. For full list see
     * <a href="https://www.mapbox.com/maki-icons/">here.</a>
     * <p>
     * Default: "marker"
     */
    @Option
    public String icon;

    /**
     * The symbol icon size.
     */
    @Option
    public Integer iconsize;

    /**
     * The symbol text.
     */
    @Option
    public String text;

    /**
     * The icon text font.
     */
    @Option
    public final FontOptions textfont = new FontOptions();

    /**
     * The positions of the "text" elements with respects to the (x,y) coordinates.
     * <p>
     * Default: MIDDLE_CENTER
     */
    @Option
    public TextPositionEnum textposition;
}
