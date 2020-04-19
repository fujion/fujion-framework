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

import org.fujion.common.MiscUtil;

/**
 * Supported plot types.
 */
public enum PlotType {
    
    // @formatter:off
    AREA(PlotArea.class),
    AREARANGE(PlotAreaRange.class),
    AREASPLINE(PlotAreaSpline.class),
    AREASPLINERANGE(PlotAreaSplineRange.class),
    BAR(PlotBar.class),
    BELLCURVE(PlotBellCurve.class),
    BOXPLOT(PlotBox.class),
    BUBBLE(PlotBubble.class),
    BULLET(PlotBullet.class),
    COLUMN(PlotColumn.class),
    COLUMNRANGE(PlotColumnRange.class),
    ERRORBAR(PlotErrorBar.class),
    FUNNEL(PlotFunnel.class),
    GAUGE(PlotGauge.class),
    HEATMAP(PlotHeatMap.class),
    HISTOGRAM(PlotHistogram.class),
    LINE(PlotLine.class),
    PARETO(PlotPareto.class),
    PIE(PlotPie.class),
    POLYGON(PlotPolygon.class),
    PYRAMID(PlotPyramid.class),
    SANKEY(PlotSankey.class),
    SCATTER(PlotScatter.class),
    SCATTER3D(PlotScatter3D.class),
    SOLIDGAUGE(PlotSolidGauge.class),
    SPLINE(PlotSpline.class),
    STREAMGRAPH(PlotStreamGraph.class),
    SUNBURST(PlotSunburst.class),
    TILEMAP(PlotTileMap.class),
    TREEMAP(PlotTreeMap.class),
    VARIABLEPIE(PlotVariablePie.class),
    VARIWIDE(PlotVariwide.class),
    VECTOR(PlotVector.class),
    WATERFALL(PlotWaterfall.class),
    WINDBARB(PlotWindbarb.class),
    WORDCLOUD(PlotWordCloud.class),
    XRANGE(PlotXRange.class);
    // @formatter:on
    
    private final Class<? extends PlotOptions> optionClass;

    /**
     * Returns the plot type given the implementation class.
     *
     * @param optionClass The implementation class.
     * @return The corresponding plot type, or null if none.
     */
    public static PlotType fromPlotClass(Class<? extends PlotOptions> optionClass) {
        for (PlotType type : PlotType.values()) {
            if (type.optionClass == optionClass) {
                return type;
            }
        }
        
        return null;
    }
    
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
        return name().toLowerCase();
    }
}
