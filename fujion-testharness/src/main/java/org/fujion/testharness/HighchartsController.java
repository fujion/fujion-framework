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
package org.fujion.testharness;

import org.fujion.annotation.WiredComponent;
import org.fujion.component.BaseComponent;
import org.fujion.highcharts.*;

import java.util.Arrays;

/**
 * Sample controller to reproduce HighCharts demo from its web site.
 */
public class HighchartsController extends BaseChartController {
    
    @WiredComponent
    private Chart hchart;

    @Override
    public void afterInitialized(BaseComponent root) {
        hchart.setTitle("Highcharts: Monthly Average Temperature");
        hchart.setSubtitle("Source: WorldClimate.com");
        Axis xAxis = hchart.getXAxis();
        xAxis.categories.addAll(Arrays.asList(CATEGORIES));
        Axis yAxis = hchart.getYAxis();
        yAxis.title.text = "Temperature (°C)";
        PlotLineOptions plo = new PlotLineOptions();
        yAxis.plotLines.add(plo);
        plo.value = 0.0;
        plo.width = 1;
        plo.color = "#808080";
        hchart.instance.getTooltip().valueSuffix = "°C";
        hchart.instance.getLegend().layout = Orientation.vertical;
        hchart.instance.getLegend().align = AlignHorizontal.right;
        hchart.instance.getLegend().verticalAlign = AlignVertical.middle;
        hchart.instance.getLegend().borderWidth = 0;
        addSeries("Tokyo", TOKYO);
        addSeries("New York", NEW_YORK);
        addSeries("Berlin", BERLIN);
        addSeries("London", LONDON);
        hchart.run();
    }

    private void addSeries(String name, double[] data) {
        Series series = hchart.addSeries();
        series.name = name;

        for (double value : data) {
            series.addDataPoint(value);
        }
    }

}
