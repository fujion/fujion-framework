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

import org.fujion.common.MiscUtil;

/**
 * Supported plot types.
 */
public enum PlotType {

    // @formatter:off
    AREA(PlotArea.class),
    AREA_RANGE(PlotAreaRange.class),
    AREA_SPLINE(PlotAreaSpline.class),
    AREA_SPLINE_RANGE(PlotAreaSplineRange.class),
    BAR(PlotBar.class),
    BOX_PLOT(PlotBox.class),
    BUBBLE(PlotBubble.class),
    COLUMN(PlotColumn.class),
    COLUMN_RANGE(PlotColumnRange.class),
    ERROR_BAR(PlotErrorBar.class),
    FUNNEL(PlotFunnel.class),
    GAUGE(PlotGauge.class),
    LINE(PlotLine.class),
    PIE(PlotPie.class),
    SCATTER(PlotScatter.class),
    SOLID_GAUGE(PlotSolidGauge.class),
    SPLINE(PlotSpline.class),
    WATERFALL(PlotWaterfall.class);
    // @formatter:on

    private final Class<? extends PlotOptions> optionClass;

    PlotType(Class<? extends PlotOptions> optionClass) {
        this.optionClass = optionClass;
    }

    public PlotOptions newInstance() {
        try {
            PlotOptions plotOptions = optionClass.newInstance();
            plotOptions.type = toString();
            return plotOptions;
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    @Override
    public String toString() {
        return name().toLowerCase().replace("_", "");
    }
}
