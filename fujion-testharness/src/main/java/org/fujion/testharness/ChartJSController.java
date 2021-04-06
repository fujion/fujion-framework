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
package org.fujion.testharness;

import org.fujion.annotation.WiredComponent;
import org.fujion.chartjs.Chart;
import org.fujion.chartjs.axis.LinearAxisOptions;
import org.fujion.chartjs.plot.PlotLine;
import org.fujion.component.BaseComponent;

/**
 * Sample controller to demonstrate simple chart.js graph.
 */
public class ChartJSController extends BaseChartController {

    @WiredComponent
    private Chart chartjs;

    private final LinearAxisOptions yaxis = new LinearAxisOptions();

    @Override
    public void afterInitialized(BaseComponent root) {
        chartjs.setTitle("ChartJS: Monthly Average Temperature");
        chartjs.setSubtitle("Source: WorldClimate.com");
        chartjs.setLabels(CATEGORIES);
        yaxis.title.text = "Temperature (°C)";
        yaxis.title.display = true;
        yaxis.id = "y";
        chartjs.getOptions().plugins.tooltip.callbacks.label = "function(item, data) {return item.formattedValue + '°C';}";
        chartjs.addYAxis(yaxis);
        addSeries(TOKYO, "Tokyo");
        addSeries(NEW_YORK, "New York");
        addSeries(BERLIN, "Berlin");
        addSeries(LONDON, "London");
        chartjs.run();
    }
    
    private void addSeries(double[] data, String name) {
        PlotLine series = chartjs.addSeries(PlotLine.class);
        series.data$number = data;
        series.label = name;
        series.yAxisID = yaxis.id;
    }

}
