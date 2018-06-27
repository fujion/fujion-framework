/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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
import org.fujion.plotly.Chart;
import org.fujion.plotly.plot.PlotScatter;

/**
 * Sample controller to demonstrate simple Plotly graph.
 */
public class PlotlyController extends BaseChartController {
    
    @WiredComponent
    private Chart plotly;
    
    @Override
    public void afterInitialized(BaseComponent root) {
        plotly.getSettings().displaylogo = false;
        plotly.setTitle("Plotly: Monthly Average Temperature");
        plotly.getLayout().yaxis.title = "Temperature (°C)";
        plotly.getLayout().autosize = true;
        addSeries(TOKYO, "Tokyo");
        addSeries(NEW_YORK, "New York");
        addSeries(BERLIN, "Berlin");
        addSeries(LONDON, "London");
        plotly.run();
    }

    private void addSeries(double[] data, String name) {
        PlotScatter series = plotly.addSeries(PlotScatter.class);
        series.x = CATEGORIES;
        series.y$number = data;
        series.name = name;
        series.hoverInfo = "y+text";
        series.hovertext = "°C";
    }
    
}
