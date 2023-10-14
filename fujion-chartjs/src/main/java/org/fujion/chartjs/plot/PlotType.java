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
package org.fujion.chartjs.plot;

import org.fujion.common.MiscUtil;
import org.fujion.common.StrUtil;

import java.util.Arrays;

/**
 * Supported plot types.
 */
public enum PlotType {

    // @formatter:off
    BAR(PlotBar.class),
    BUBBLE(PlotBubble.class),
    DOUGHNUT(PlotDoughnut.class),
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
        return Arrays.stream(PlotType.values())
                .filter(type -> type.plotClass == plotClass)
                .findAny()
                .orElse(null);
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
        PlotOptions plotOptions = MiscUtil.newInstance(plotClass);
        plotOptions.type = this;
        return plotOptions;
    }

    @Override
    public String toString() {
        return StrUtil.toCamelCaseLower(name());
    }
}
