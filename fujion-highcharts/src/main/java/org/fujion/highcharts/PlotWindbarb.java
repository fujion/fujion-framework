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
package org.fujion.highcharts;

/**
 * Options for windbarb plot.
 * <p>
 * Wind barbs are a convenient way to represent wind speed and direction in one graphical form. Wind
 * direction is given by the stem direction, and wind speed by the number and shape of barbs.
 */
public class PlotWindbarb extends PlotOptions {

    /**
     * 3D columns only. The color of the edges. Similar to borderColor, except it defaults to the
     * same color as the column. Defaults to undefined.
     */
    public String edgeColor;

    /**
     * 3D columns only. The width of the colored edges. Defaults to 1.
     */
    public Integer edgeWidth;
    
    /**
     * Whether to group non-stacked columns or to let them render independent of each other.
     * Non-grouped columns will be laid out individually and overlap each other. Defaults to true.
     */
    public Boolean grouping;

    /**
     * The spacing between columns on the Z Axis in a 3D chart. Requires highcharts-3d.js. Defaults
     * to 1.
     */
    public Integer groupZPadding;
    
    /**
     * The maximum allowed pixel width for a column, translated to the height of a bar in a bar
     * chart. This prevents the columns from becoming too wide when there is a small number of
     * points in the chart. Defaults to null.
     */
    public Integer maxPointWidth;

    /**
     * The id of another series in the chart that the wind barbs are projected on. When null, the
     * wind symbols are drawn on the X axis, but offset up or down by the yOffset setting. Defaults
     * to null.
     */
    public String onSeries;
    
    /**
     * A pixel value specifying a fixed width for each column or bar. When null, the width is
     * calculated from the pointPadding and groupPadding. Defaults to null.
     */
    public Integer pointWidth;

    /**
     * Pixel length of the stems. Defaults to 20.
     */
    public Integer vectorLength;
    
    /**
     * Vertical offset from the cartesian position, in pixels. The default value makes sure the
     * symbols don't overlap the X axis when onSeries is null, and that they don't overlap the
     * linked series when onSeries is given. Defaults to -20.
     */
    public Integer yOffset;
}
