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
package org.fujion.plotly.plot;

import org.fujion.common.MiscUtil;

/**
 * Supported plot types.
 */
public enum PlotType {

    // @formatter:off
    AREA(PlotArea.class),
    BAR(PlotBar.class),
    BOX(PlotBox.class),
    CANDLESTICK(PlotCandlestick.class),
    CARPET(PlotCarpet.class),
    CHLOROPLETH(PlotChoropleth.class),
    CONTOUR(PlotContour.class),
    CONTOURCARPET(PlotContourCarpet.class),
    HEATMAP(PlotHeatMap.class),
    HEATMAPGL(PlotHeatMapGL.class),
    HISTOGRAM(PlotHistogram.class),
    HISTOGRAM2D(PlotHistogram2D.class),
    HISTOGRAM2DCONTOUR(PlotHistogram2DContour.class),
    MESH3D(PlotMesh3D.class),
    OHLC(PlotOHLC.class),
    PARCOORDS(PlotParallelCoords.class),
    PIE(PlotPie.class),
    POINTCLOUD(PlotPointCloud.class),
    SANKEY(PlotSankey.class),
    SCATTER(PlotScatter.class),
    SCATTER3D(PlotScatter3D.class),
    SCATTERCARPET(PlotScatterCarpet.class),
    SCATTERGEO(PlotScatterGeo.class),
    SCATTERGL(PlotScatterGL.class),
    SCATTERMAPBOX(PlotScatterMapBox.class),
    SCATTERPOLAR(PlotScatterPolar.class),
    SCATTERPOLARGL(PlotScatterPolarGL.class),
    SCATTERTERNARY(PlotScatterTernary.class),
    SURFACE(PlotSurface.class),
    TABLE(PlotTable.class),
    VIOLIN(PlotViolin.class);
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
        PlotOptions plotOptions = MiscUtil.newInstance(plotClass);
        plotOptions.type = this;
        return plotOptions;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
