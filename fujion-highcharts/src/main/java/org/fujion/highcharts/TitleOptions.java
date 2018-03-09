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
package org.fujion.highcharts;

import org.fujion.ancillary.Options;

/**
 * Display options for a chart title.
 */
public class TitleOptions extends Options {

    /**
     * The horizontal alignment of the title or subtitle. Can be one of "left", "center" and
     * "right". Defaults to "center".
     */
    public AlignHorizontal align;

    /**
     * When the title or subtitle is floating, the plot area will not move to make space for it.
     * Defaults to false.
     */
    public Boolean floating;

    /**
     * The margin between the title and the plot area, or if a subtitle is present, the margin
     * between the subtitle and the plot area. Defaults to 15. Ignored for subtitles.
     */
    public Integer margin;

    /**
     * CSS styles for the title or subtitle. Use this for font styling, but use align, x and y for
     * text alignment. Defaults to:
     *
     * <pre>
     *     color: '#3E576F'
     *     fontSize: '16px' (title only)
     * </pre>
     */
    public final StyleOptions style = new StyleOptions();

    /**
     * The title or subtitle of the chart. To disable, set the text to null. Defaults to "Chart
     * title" for title and "" for subtitle.
     */
    public String text;

    /**
     * The vertical alignment of the title or subtitle. Can be one of "top", "middle" and "bottom".
     * Defaults to "top".
     */
    public AlignVertical verticalAlign;

    /**
     * The x position of the title or subtitle relative to the alignment within chart.spacingLeft
     * and chart.spacingRight. Defaults to 0.
     */
    public Integer x;

    /**
     * The y position of the subtitle relative to the alignment within chart.spacingTop and
     * chart.spacingBottom. Defaults to 15 for title and 30 for subtitle.
     */
    public Integer y;
}
