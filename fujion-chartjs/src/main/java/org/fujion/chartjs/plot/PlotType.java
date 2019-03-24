/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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

import org.fujion.common.MiscUtil;
import org.fujion.common.StrUtil;

/**
 * Supported plot types.
 */
public enum PlotType {

    // @formatter:off
    BAR(PlotBar.class),
    BUBBLE(PlotBubble.class),
    DOUGHNUT(PlotDoughnut.class),
    HORIZONTAL_BAR(PlotHorizBar.class),
    LINE(PlotLine.class),
    PIE(PlotPie.class),
    POLAR_AREA(PlotPolarArea.class),
    RADAR(PlotRadar.class),
    SCATTER(PlotScatter.class);
    // @formatter:on

    /**
     * Returns the plot type given the implementation class.
     *
     * @param plotClass The implementation class.
     * @return The corresponding plot type, or null if none.
     */
    public static PlotType fromPlotClass(Class<? extends PlotOptions> plotClass) {
        for (PlotType type : PlotType.values()) {
            if (type.plotClass == plotClass) {
                return type;
            }
        }
        
        return null;
    }

    private final Class<? extends PlotOptions> plotClass;

    PlotType(Class<? extends PlotOptions> plotClass) {
        this.plotClass = plotClass;
    }

    /**
     * Creates a new instance of a plot of this type.
     *
     * @return New plot instance.
     */
    public PlotOptions newInstance() {
        try {
            PlotOptions plotOptions = plotClass.newInstance();
            plotOptions.type = this;
            return plotOptions;
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    @Override
    public String toString() {
        return StrUtil.toCamelCaseLower(name());
    }
}
