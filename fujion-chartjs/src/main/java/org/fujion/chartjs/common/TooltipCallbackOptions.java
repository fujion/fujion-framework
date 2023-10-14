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
package org.fujion.chartjs.common;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

public class TooltipCallbackOptions extends Options {

    /**
     * Returns text to render after the body section
     */
    @Option(convertTo = JavaScript.class)
    public String afterBody;

    /**
     * Text to render after the footer section.
     */
    @Option(convertTo = JavaScript.class)
    public String afterFooter;

    /**
     * Returns text to render after an individual label.
     */
    @Option(convertTo = JavaScript.class)
    public String afterLabel;

    /**
     * Returns text to render after the title.
     */
    @Option(convertTo = JavaScript.class)
    public String afterTitle;

    /**
     * Returns text to render before the body section.
     */
    @Option(convertTo = JavaScript.class)
    public String beforeBody;

    /**
     * Returns text to render before the footer section.
     */
    @Option(convertTo = JavaScript.class)
    public String beforeFooter;

    /**
     * Returns text to render before an individual label. This will be called for each item in the
     * tooltip.
     */
    @Option(convertTo = JavaScript.class)
    public String beforeLabel;

    /**
     * Returns the text to render before the title.
     */
    @Option(convertTo = JavaScript.class)
    public String beforeTitle;

    /**
     * Returns text to render as the footer of the tooltip.
     */
    @Option(convertTo = JavaScript.class)
    public String footer;

    /**
     * Returns text to render for an individual item in the tooltip.
     */
    @Option(convertTo = JavaScript.class)
    public String label;

    /**
     * Returns the colors to render for the tooltip item.
     */
    @Option(convertTo = JavaScript.class)
    public String labelColor;

    /**
     * Returns the point style to use instead of color boxes if usePointStyle is true
     * (object with values pointStyle and rotation). Default implementation uses the point style
     * from the dataset points.
     */
    @Option(convertTo = JavaScript.class)
    public String labelPointStyle;

    /**
     * Returns the colors for the text of the label for the tooltip item.
     */
    @Option(convertTo = JavaScript.class)
    public String labelTextColor;

    /**
     * Returns text to render as the title of the tooltip.
     */
    @Option(convertTo = JavaScript.class)
    public String title;

}
