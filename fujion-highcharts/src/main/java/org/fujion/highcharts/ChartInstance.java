/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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

import java.util.ArrayList;
import java.util.List;

import org.fujion.ancillary.Options;

/**
 * Top level chart settings.
 */
public class ChartInstance extends Options {

    protected final ChartOptions chart = new ChartOptions();

    protected final List<String> colors = new ArrayList<>();

    protected final CreditsOptions credits = new CreditsOptions();

    protected final ExportingOptions exporting = new ExportingOptions();

    protected final LegendOptions legend = new LegendOptions();

    protected final LoadingOptions loading = new LoadingOptions();

    protected final NavigationOptions navigation = new NavigationOptions();

    protected final PaneOptions pane = new PaneOptions();

    protected PlotOptions plotOptions;

    protected final List<Series> series = new ArrayList<>();

    protected final TitleOptions subtitle = new TitleOptions();

    protected final TitleOptions title = new TitleOptions();

    protected final GlobalTooltipOptions tooltip = new GlobalTooltipOptions();

    protected final List<Axis> xAxis = new ArrayList<>();

    protected final List<Axis> yAxis = new ArrayList<>();

    public ChartOptions getChart() {
        return chart;
    }

    public List<String> getColors() {
        return colors;
    }

    public CreditsOptions getCredits() {
        return credits;
    }

    public ExportingOptions getExporting() {
        return exporting;
    }

    public LegendOptions getLegend() {
        return legend;
    }

    public LoadingOptions getLoading() {
        return loading;
    }

    public NavigationOptions getNavigation() {
        return navigation;
    }

    public PaneOptions getPane() {
        return pane;
    }

    public PlotOptions getPlotOptions() {
        return plotOptions;
    }

    protected void setPlotOptions(PlotOptions plotOptions) {
        this.plotOptions = plotOptions;
    }

    public List<Series> getSeries() {
        return series;
    }

    public TitleOptions getSubtitle() {
        return subtitle;
    }

    public TitleOptions getTitle() {
        return title;
    }

    public GlobalTooltipOptions getTooltip() {
        return tooltip;
    }

    public List<Axis> getxAxis() {
        return xAxis;
    }

    public List<Axis> getyAxis() {
        return yAxis;
    }

}
