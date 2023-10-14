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
package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.plotly.common.FontOptions;
import org.fujion.plotly.common.HorizontalAlignEnum;

/**
 * Layout options displaying the current value.
 */
public class CurrentValueOptions extends Options {
    
    /**
     * The font of the current value label text.
     */
    @Option
    public final FontOptions font = new FontOptions();

    /**
     * The amount of space, in pixels, between the current value label and the slider.
     * <p>
     * Default: 10
     */
    @Option
    public Integer offset;

    /**
     * When currentvalue.visible is true, this sets the prefix of the label.
     */
    @Option
    public String prefix;

    /**
     * When currentvalue.visible is true, this sets the suffix of the label.
     */
    @Option
    public String suffix;

    /**
     * Shows the currently-selected value above the slider.
     * <p>
     * Default: true
     */
    @Option
    public Boolean visible;

    /**
     * The alignment of the value readout relative to the length of the slider.
     * <p>
     * Default: LEFT
     */
    @Option
    public HorizontalAlignEnum xanchor;
    
}
