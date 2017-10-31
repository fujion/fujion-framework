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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fujion.ancillary.ConvertUtil;
import org.fujion.ancillary.OptionMap;
import org.fujion.ancillary.OptionMap.IOptionMapConverter;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.BaseUIComponent;
import org.fujion.component.Page;

/**
 * Fujion wrapper for HighCharts component.
 */
@Component(tag = "hchart", widgetModule = "fujion-hchart", widgetClass = "HChart", parentTag = "*", description = "Fujion wrapper for HighCharts component.")
public class Chart extends BaseUIComponent implements IOptionMapConverter {

    private static final String GLOBAL_SETTINGS = Chart.class.getName() + ".global";

    /**
     * Top level chart settings.
     */
    public static class ChartSettings extends Options {

        public final ChartOptions chart = new ChartOptions();

        public final List<String> colors = new ArrayList<>();

        public final CreditsOptions credits = new CreditsOptions();

        public final ExportingOptions exporting = new ExportingOptions();

        public final LegendOptions legend = new LegendOptions();

        public final LoadingOptions loading = new LoadingOptions();

        public final NavigationOptions navigation = new NavigationOptions();

        public final PaneOptions pane = new PaneOptions();

        public final PlotOptions plotOptions = null;

        public final List<Series> series = new ArrayList<>();

        public final TitleOptions subtitle = new TitleOptions();

        public final TitleOptions title = new TitleOptions();

        public final TooltipOptions tooltip = new TooltipOptions();

        public final List<Axis> xAxis = new ArrayList<>();

        public final List<Axis> yAxis = new ArrayList<>();

    }

    public final ChartSettings options = new ChartSettings();

    private boolean running;

    /**
     * Create default chart (line plot, single x- and y-axis).
     */
    public Chart() {
        super();
        addXAxis();
        addYAxis();
        setType("line");
    }

    /**
     * Sets the default colors for the chart's series. When all colors are used, new colors are
     * pulled from the start again.
     *
     * @param colors List of default colors. If null or empty, the Highcharts defaults are used.
     */
    public void setDefaultColors(String... colors) {
        options.colors.clear();

        if (colors != null) {
            options.colors.addAll(Arrays.asList(colors));
        }
    }

    /**
     * Returns the chart type.
     *
     * @return The chart type.
     */
    @PropertyGetter(value = "type", description = "The chart type.")
    public String getType() {
        return options.chart.type;
    }

    /**
     * Sets the chart type. This will remove any existing series.
     *
     * @param type One of the supported chart types.
     */
    @PropertySetter(value = "type", defaultValue = "line", description = "The chart type.")
    public void setType(String type) {
        try {
            Field field = ChartSettings.class.getField("plotOptions");
            field.setAccessible(true);
            PlotOptions plotOptions = Util.getPlotType(type);
            plotOptions.type = type;
            field.set(options, plotOptions);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        options.chart.type = type;
        options.series.clear();
    }

    /**
     * Convenience method for returning the x-axis. If there are no x-axes, returns null. If there
     * are multiple x-axes, returns the first only.
     *
     * @return The x-axis.
     */
    public Axis getXAxis() {
        return options.xAxis.isEmpty() ? null : options.xAxis.get(0);
    }

    /**
     * Convenience method for returning the y-axis. If there are no y-axes, returns null. If there
     * are multiple y-axes, returns the first only.
     *
     * @return The y-axis.
     */
    public Axis getYAxis() {
        return options.yAxis.isEmpty() ? null : options.yAxis.get(0);
    }

    /**
     * Adds a new series to the chart using the chart's default type.
     *
     * @return The newly created series.
     */
    public Series addSeries() {
        return addSeries(options.chart.type);
    }

    /**
     * Adds a new series to the chart using the specified type.
     *
     * @param type The plot type.
     * @return The newly created series.
     */
    public Series addSeries(String type) {
        Series series = new Series(type);
        options.series.add(series);
        return series;
    }

    /**
     * Adds an additional x axis.
     *
     * @return The newly added axis.
     */
    public Axis addXAxis() {
        return new Axis(options.xAxis);
    }

    /**
     * Adds an additional y axis.
     *
     * @return The newly added axis.
     */
    public Axis addYAxis() {
        return new Axis(options.yAxis);
    }

    /**
     * Build the graph on the client.
     */
    public void run() {
        init();
        invoke("_run", toMap());
        running = true;
    }

    /**
     * Returns true if a chart is currently running on the client.
     *
     * @return True if a chart is running.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Removes all series and data points and destroys the client graph.
     */
    public void clear() {
        running = false;
        options.series.clear();
        invoke("_reset");
    }

    /**
     * Force a redraw of the chart.
     */
    public void redraw() {
        if (running) {
            invoke("_redraw");
        } else {
            run();
        }
    }

    /**
     * Send global settings to client if necessary.
     */
    private void init() {
        if (shouldInitialize()) {
            invoke("_global", new GlobalSettings().toMap());
        }
    }

    /**
     * Returns true if global settings need to be sent to client. This occurs once per page.
     *
     * @return True if global settings need to be sent.
     */
    private boolean shouldInitialize() {
        Page pg = getPage();

        if (pg != null && !pg.hasAttribute(GLOBAL_SETTINGS)) {
            pg.setAttribute(GLOBAL_SETTINGS, true);
            return true;
        }

        return false;
    }

    /**
     * Converts all options to map for sending to client.
     */
    @Override
    public OptionMap toMap() {
        options.chart.renderTo = getId();
        return options.toMap();
    }

    /**
     * Convenience method for getting title.
     *
     * @return Title text
     */
    @PropertyGetter(value = "title", description = "The title text.")
    public String getTitle() {
        return options.title.text;
    }

    /**
     * Convenience method for setting title.
     *
     * @param text Title text
     */
    @PropertySetter(value = "title", description = "The title text.")
    public void setTitle(String text) {
        options.title.text = text;
        updateTitle();
    }

    /**
     * Convenience method for getting subtitle.
     *
     * @return Subtitle text
     */
    @PropertyGetter(value = "subtitle", description = "The subtitle text.")
    public String getSubtitle() {
        return options.subtitle.text;
    }

    /**
     * Convenience method for setting subtitle.
     *
     * @param text Subtitle text
     */
    @PropertySetter(value = "subtitle", description = "The subtitle text.")
    public void setSubtitle(String text) {
        options.subtitle.text = text;
        updateTitle();
    }

    /**
     * Calls the exportChart function on the chart.
     */
    public void export() {
        ensureRunning("Exporting");
        invokeJS("_export", options.exporting.buttons_exportButton.onclick);
    }

    /**
     * Calls the print function on the chart.
     */
    public void print() {
        ensureRunning("Printing");
        invokeJS("_print", options.exporting.buttons_printButton.onclick);
    }

    /**
     * Invokes the specified widget function, passing the JavaScript snippet as its argument.
     *
     * @param func Widget function name.
     * @param js JavaScript snippet.
     */
    private void invokeJS(String func, String js) {
        invoke(func, ConvertUtil.convertToJS(js));
    }

    /**
     * If the chart is active, dynamically update the title and subtitle.
     */
    private void updateTitle() {
        if (running) {
            OptionMap map = new OptionMap();
            map.put("title", options.title);
            map.put("subtitle", options.subtitle);
            invoke("_title", map);
        }
    }

    /**
     * Throws an exception if a chart is not currently running.
     *
     * @param operation The operation to be invoked.
     */
    private void ensureRunning(String operation) {
        if (!running) {
            throw new RuntimeException(operation + " requires an active chart.");
        }
    }

}
