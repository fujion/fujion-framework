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
package org.fujion.chartjs.plot;

import org.fujion.chartjs.common.Point;
import org.fujion.chartjs.common.PositionEnum;

/**
 * Options for bar plots.
 */
public class PlotBar extends PlotOptions {

    /**
     * Which edge to skip drawing the border for.
     */
    public PositionEnum borderSkipped;

    /**
     * The plot data (as numbers).
     */
    public double[] data$number;
    
    /**
     * The plot data (as points).
     */
    public Point[] data$points;

    /**
     * The label for the dataset which appears in the legend and tooltips.
     */
    public String label;
    
    /**
     * The ID of the x axis to plot this dataset on. If not specified, this defaults to the ID of
     * the first found x axis
     */
    public String xAxisID;

    /**
     * The ID of the y axis to plot this dataset on. If not specified, this defaults to the ID of
     * the first found y axis.
     */
    public String yAxisID;

}
