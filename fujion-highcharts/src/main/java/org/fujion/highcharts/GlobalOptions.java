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
 * Global options that don't apply to each chart. These options, like the language options, must be
 * set using the Highcharts.setOptions method.
 */
public class GlobalOptions extends Options {

    /**
     * Path to the pattern image required by VML browsers in order to draw radial gradients.
     * Defaults to http://code.highcharts.com/{version}/gfx/vml-radial-gradient.png.
     */
    @Option
    public String VMLRadialGradientURL;

    /**
     * he URL to the additional file to lazy load for Android 2.x devices. These devices don't
     * support SVG, so we download a helper file that contains canvg, its dependency rbcolor, and
     * our own CanVG Renderer class. To avoid hot linking to our site, you can install
     * canvas-tools.js on your own server and change this option accordingly. Defaults to
     * "http://www.highcharts.com/js/canvas-tools.js".
     */
    @Option
    public String canvasToolsURL;

    /**
     * Whether to use UTC time for axis scaling, tickmark placement and time display in
     * Highcharts.dateFormat. Advantages of using UTC is that the time displays equally regardless
     * of the user agent's time zone settings. Local time can be used when the data is loaded in
     * real time or when correct Daylight Saving Time transitions are required. Defaults to false.
     */
    @Option
    public Boolean useUTC = false;
}
