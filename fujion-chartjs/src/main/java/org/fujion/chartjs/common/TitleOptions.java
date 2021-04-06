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
package org.fujion.chartjs.common;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Title options.
 */
public class TitleOptions extends Options {

    /**
     * The horizontal alignment of the title.
     *
     * Default: CENTER
     */
    public AlignmentEnum align;

    /**
     * Font color.
     * <p>
     * Default: "#666"
     */
    @Option
    public String color;

    /**
     * If true, the is title shown.
     * <p>
     * Default: false
     */
    @Option
    public Boolean display;

    /**
     * Font.
     * <p>
     * Default: font.style = bold
     */
    @Option
    public final FontOptions font = new FontOptions();

    /**
     * Marks that this box should take the full width/height of the canvas.
     * If false, the box is sized and placed above/beside the chart area.
     *
     * Default: true
     */
    @Option
    public Boolean fullSize;

    /**
     * Number of pixels to add above and below the title text.
     *
     * Default: 10
     */
    @Option
    public final Padding padding = new Padding();
    
    /**
     * Position of title.
     * <p>
     * Default: TOP
     */
    @Option
    public PositionEnum position;

    /**
     * The text of the title.
     */
    @Option
    public String text;

    /**
     * The text of the title.
     */
    @Option
    public String[] text$array;
    
}
