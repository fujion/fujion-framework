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
package org.fujion.highcharts;

import org.fujion.annotation.Option;

/**
 * Options for tile map series.
 * <p>
 * A tile map series is a type of heat map where the tile shapes are configurable.
 */
public class PlotTileMap extends PlotOptions {
    
    /**
     * The column size - how many X axis units each column in the heatmap should span. Defaults to
     * 1.
     */
    @Option
    public Integer colsize;
    
    /**
     * The color applied to null points. In styled mode, a general CSS class is applied instead.
     * Defaults to #f7f7f7.
     */
    @Option
    public String nullColor;

    /**
     * The padding between points in the tilemap. Defaults to 2.
     */
    @Option
    public Integer pointPadding;

    /**
     * The row size - how many Y axis units each heatmap row should span. Defaults to 1.
     */
    @Option
    public Integer rowsize;
    
    /**
     * The shape of the tiles in the tilemap. Possible values are hexagon, circle, diamond, and
     * square. Defaults to hexagon.
     */
    @Option
    public String tileShape;
}
