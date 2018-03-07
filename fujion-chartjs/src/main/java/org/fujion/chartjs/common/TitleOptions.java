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

/**
 * Title options.
 */
public class TitleOptions extends Options {

    /**
     * If true, the is title shown.
     * <p>
     * Default: false
     */
    public Boolean display;

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
     * Default: "bold"
     */
    public String fontStyle;

    /**
     * Height of an individual line of text. See
     * <a href="https://developer.mozilla.org/en-US/docs/Web/CSS/line-height">MDN</a>.
     * <p>
     * Default: 1.2
     */
    public String lineHeight;

    /**
     * Number of pixels to add above and below the title text.
     * <p>
     * Default: 10
     */
    public Integer padding;

    /**
     * Position of title.
     * <p>
     * Default: TOP
     */
    public PositionEnum position;

    /**
     * The text of the title.
     */
    public String[] text$array;

    /**
     * The text of the title.
     */
    public String text$string;
    
}