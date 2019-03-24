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
package org.fujion.chartjs.common;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options for legend labels.
 */
public class LegendLabelOptions extends Options {

    /**
     * Width of colored box.
     * <p>
     * Default: 40
     */
    @Option
    public Integer boxWidth;
    
    /**
     * Function that filters items out of the legend. Receives 2 parameters, a Legend Item and the
     * chart data.
     */
    @Option(convertTo = JavaScript.class)
    public String filter;
    
    /**
     * Font color.
     * <p>
     * Default: "#666"
     */
    @Option
    public String fontColor;
    
    /**
     * Font family.
     * <p>
     * Default: "Helvetica Neue, Helvetica, Arial, sans-serif"
     */
    @Option
    public String fontFamily;
    
    /**
     * Font size.
     * <p>
     * Default: 12
     */
    @Option
    public Integer fontSize;
    
    /**
     * Font style.
     * <p>
     * Default: "normal"
     */
    @Option
    public String fontStyle;
    
    /**
     * Function to generate legend items for each item in the legend. Default implementation returns
     * the text + styling for the color box.
     */
    @Option(convertTo = JavaScript.class)
    public String generateLabels;
    
    /**
     * Padding between rows of colored boxes.
     * <p>
     * Default: 10
     */
    @Option
    public Integer padding;
    
    /**
     * Label style will match corresponding point style (size is based on fontSize, boxWidth is not
     * used in this case).
     * <p>
     * Default: false
     */
    @Option
    public Boolean usePointStyle;
    
}
