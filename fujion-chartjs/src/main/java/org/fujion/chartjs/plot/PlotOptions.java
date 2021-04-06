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
package org.fujion.chartjs.plot;

import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Options common to all plots.
 */
public abstract class PlotOptions extends Options {

    /**
     * The fill color under the line.
     */
    @Option
    public String backgroundColor;

    /**
     * The fill alternating colors under the line.
     */
    @Option
    public String[] backgroundColor$array;

    /**
     * The fill alternating colors under the line.
     */
    @Option(convertTo = JavaScript.class)
    public String backgroundColor$function;

    /**
     * The color of the line.
     */
    @Option
    public String borderColor;

    /**
     * The color of the line.
     */
    @Option
    public String[] borderColor$array;

    /**
     * The color of the line.
     */
    @Option(convertTo = JavaScript.class)
    public String borderColor$function;

    /**
     * The width of the line in pixels.
     */
    @Option
    public String borderWidth;

    /**
     * The width of the line in pixels.
     */
    @Option
    public String[] borderWidth$array;

    /**
     * The width of the line in pixels.
     */
    @Option(convertTo = JavaScript.class)
    public String borderWidth$function;

    /**
     * How to clip relative to chartArea. Positive value allows overflow, negative value clips
     * that many pixels inside chart area.
     *
     * TODO: allow clip per side.
     */
    @Option
    public Integer clip;

    /**
     * The background color when hovered as a single value.
     */
    @Option
    public String hoverBackgroundColor;

    /**
     * The background color when hovered as an array.
     */
    @Option
    public String[] hoverBackgroundColor$array;

    /**
     * The background color when hovered as a function.
     */
    @Option(convertTo = JavaScript.class)
    public String hoverBackgroundColor$function;

    /**
     * The border color when hovered as a single value.
     */
    @Option
    public String hoverBorderColor;

    /**
     * The border color when hovered as an array.
     */
    @Option
    public String[] hoverBorderColor$array;

    /**
     * The border color when hovered as a function.
     */
    @Option(convertTo = JavaScript.class)
    public String hoverBorderColor$function;

    /**
     * The border width when hovered as a single value.
     * <p>
     * Default: 1
     */
    @Option
    public Integer hoverBorderWidth;

    /**
     * The border width when hovered as an array.
     */
    @Option
    public int[] hoverBorderWidth$array;

    /**
     * The border width when hovered as a function.
     */
    @Option(convertTo = JavaScript.class)
    public String hoverBorderWidth$function;

    /**
     * The plot type.
     */
    protected PlotType type;
    
}
