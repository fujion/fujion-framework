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
package org.fujion.testharness;

import org.fujion.ancillary.IAutoWired;
import org.fujion.annotation.WiredComponent;
import org.fujion.component.BaseComponent;
import org.fujion.plotly.Chart;
import org.fujion.plotly.plot.PlotScatter;

/**
 * Sample controller to demonstrate simple Plotly graph.
 */
public class PlotlyController implements IAutoWired {

    private static final String[] CATEGORIES = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov",
            "Dec" };

    private static final double[] TOKYO = { 7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6 };

    private static final double[] NEW_YORK = { -0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5 };

    private static final double[] BERLIN = { -0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0 };

    private static final double[] LONDON = { 3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8 };

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
