/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
 * Options for scale title.
 */
public class ScaleLabelOptions extends Options {
    
    /**
     * If true, display the axis title.
     * <p>
     * Default: false
     */
    @Option
    public Boolean display;
    
    /**
     * Font color for scale title.
     * <p>
     * Default: "#666"
     */
    @Option
    public String fontColor;
    
    /**
     * Font family for the scale title, follows CSS font-family options.
     * <p>
     * Default: "Helvetica Neue, Helvetica, Arial, sans-serif"
     */
    @Option
    public String fontFamily;
    
    /**
     * Font size for scale title.
     * <p>
     * Default: 12
     */
    @Option
    public Integer fontSize;
    
    /**
     * Font style for the scale title, follows CSS font-style options (i.e. normal, italic, oblique,
     * initial, inherit).
     * <p>
     * Default: normal
     */
    @Option
    public String fontStyle;
    
    /**
     * The text for the title.
     */
    @Option
    public String labelString;
    
    /**
     * Height of an individual line of text. See
     * <a href="https://developer.mozilla.org/en-US/docs/Web/CSS/line-height">MDN</a>.
     * <p>
     * Default: 1.2
     */
    @Option
    public String lineHeight;
    
    /**
     * Padding to apply around scale labels. Only top and bottom are implemented.
     * <p>
     * Default: 4
     */
    @Option
    public Integer padding;
}
