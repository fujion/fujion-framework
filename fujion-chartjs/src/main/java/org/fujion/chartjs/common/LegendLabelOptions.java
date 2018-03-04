/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
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
import org.fujion.annotation.JavaScript;

/**
 * Options for legend labels.
 */
public class LegendLabelOptions extends Options {
    
    /**
     * Width of colored box.
     * <p>
     * Default: 40
     */
    public Integer boxWidth;

    /**
     * Function that filters items out of the legend. Receives 2 parameters, a Legend Item and the
     * chart data.
     */
    @JavaScript
    public String filter;

    /**
     * Font color.
     * <p>
     * Default: "#666"
     */
    public String fontColor;

    /**
     * Font family.
     * <p>
     * Default: "Helvetica Neue, Helvetica, Arial, sans-serif"
     */
    public String fontFamily;

    /**
     * Font size.
     * <p>
     * Default: 12
     */
    public Integer fontSize;

    /**
     * Font style.
     * <p>
     * Default: "normal"
     */
    public String fontStyle;

    /**
     * Function to generate legend items for each item in the legend. Default implementation returns
     * the text + styling for the color box.
     */
    @JavaScript
    public String generateLabels;

    /**
     * Padding between rows of colored boxes.
     * <p>
     * Default: 10
     */
    public Integer padding;

    /**
     * Label style will match corresponding point style (size is based on fontSize, boxWidth is not
     * used in this case).
     * <p>
     * Default: false
     */
    public Boolean usePointStyle;

}
