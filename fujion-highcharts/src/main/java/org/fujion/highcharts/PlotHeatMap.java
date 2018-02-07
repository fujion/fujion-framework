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
 * Options for heat map plot.
 * <p>
 * A heat map is a graphical representation of data where the individual values contained in a
 * matrix are represented as colors.
 */
public class PlotHeatMap extends PlotOptions {
    
    /**
     * The column size - how many X axis units each column in the heatmap should span. Defaults to
     * 1.
     */
    public Integer colsize;
    
    /**
     * The color applied to null points. In styled mode, a general CSS class is applied instead.
     * Defaults to #f7f7f7.
     */
    public String nullColor;

    /**
     * Padding between the points in the heatmap. Defaults to 0.
     */
    public Integer pointPadding;
    
    /**
     * The row size - how many Y axis units each heatmap row should span. Defaults to 1.
     */
    public Integer rowsize;
}
