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

import org.fujion.ancillary.ConvertUtil;
import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.OptionMap;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.common.Assert;
import org.fujion.component.BaseUIComponent;
import org.fujion.component.Page;

import java.util.Arrays;

/**
 * Fujion wrapper for HighCharts component.
 */
@Component(
        tag = "hchart",
        widgetModule = "fujion-hchart",
        widgetClass = "HChart",
        parentTag = "*",
        description = "Fujion wrapper for HighCharts component.")
public class Chart extends BaseUIComponent {
    
    private static final String GLOBAL_SETTINGS = Chart.class.getName() + ".global";
    
    public final ChartInstance instance = new ChartInstance();
    
    private boolean running;
    
    /**
     * Create default chart (line plot, single x- and y-axis).
     */
    public Chart() {
        super();
        addXAxis();
        addYAxis();
        setType(PlotType.LINE);
    }
    
    /**
     * Sets the default colors for the chart's series. When all colors are used, new colors are
     * pulled from the start again.
     *
     * @param colors List of default colors. If null or empty, the Highcharts defaults are used.
     */
    public void setDefaultColors(String... colors) {
        instance.getColors().clear();
        
        if (colors != null) {
            instance.getColors().addAll(Arrays.asList(colors));
        }
    }
    
    /**
     * Returns the chart type.
     *
     * @return The chart type.
     */
    @PropertyGetter(value = "type", description = "The chart type.")
    public PlotType getType() {
        return instance.getChart().type;
    }
    
    /**
     * Sets the chart type. This will remove any existing series.
     *
     * @param type One of the supported chart types.
     */
    @PropertySetter(value = "type", defaultValue = "line", description = "The chart type.")
    public void setType(PlotType type) {
        type = type == null ? PlotType.LINE : type;
        instance.setPlotOptions(type.newInstance());
        instance.getChart().type = type;
        instance.getSeries().clear();
    }
    
    /**
     * Convenience method for returning the x-axis. If there are no x-axes, returns null. If there
     * are multiple x-axes, returns the first only.
     *
     * @return The x-axis.
     */
    public Axis getXAxis() {
        return instance.getxAxis().isEmpty() ? null : instance.getxAxis().get(0);
    }
    
    /**
     * Convenience method for returning the y-axis. If there are no y-axes, returns null. If there
     * are multiple y-axes, returns the first only.
     *
     * @return The y-axis.
     */
    public Axis getYAxis() {
        return instance.getyAxis().isEmpty() ? null : instance.getyAxis().get(0);
    }
    
    /**
     * Adds a new series to the chart using the chart's default type.
     *
     * @return The newly created series.
     */
    public Series addSeries() {
        return addSeries(instance.getChart().type);
    }
    
    /**
     * Adds a new series to the chart using the specified type.
     *
     * @param type The plot type.
     * @return The newly created series.
     */
    public Series addSeries(PlotType type) {
        Series series = new Series(type);
        instance.getSeries().add(series);
        return series;
    }
    
    /**
     * Adds an additional x axis.
     *
     * @return The newly added axis.
     */
    public Axis addXAxis() {
        return new Axis(instance.getxAxis());
    }
    
    /**
     * Adds an additional y axis.
     *
     * @return The newly added axis.
     */
    public Axis addYAxis() {
        return new Axis(instance.getyAxis());
    }
    
    /**
     * Build the graph on the client.
     */
    public void run() {
        init();
        invoke("run", instance);
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
        instance.getSeries().clear();
        invoke("reset");
    }
    
    /**
     * Force a redraw of the chart.
     */
    public void redraw() {
        if (running) {
            invoke("redraw");
        } else {
            run();
        }
    }
    
    /**
     * Send global settings to client if necessary.
     */
    private void init() {
        if (shouldInitialize()) {
            invoke("global", new GlobalSettings());
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
     * Convenience method for getting title.
     *
     * @return Title text
     */
    @PropertyGetter(value = "title", description = "The title text.")
    public String getTitle() {
        return instance.getTitle().text;
    }
    
    /**
     * Convenience method for setting title.
     *
     * @param text Title text
     */
    @PropertySetter(value = "title", description = "The title text.")
    public void setTitle(String text) {
        instance.getTitle().text = text;
        updateTitle();
    }
    
    /**
     * Convenience method for getting subtitle.
     *
     * @return Subtitle text
     */
    @PropertyGetter(value = "subtitle", description = "The subtitle text.")
    public String getSubtitle() {
        return instance.getSubtitle().text;
    }
    
    /**
     * Convenience method for setting subtitle.
     *
     * @param text Subtitle text
     */
    @PropertySetter(value = "subtitle", description = "The subtitle text.")
    public void setSubtitle(String text) {
        instance.getSubtitle().text = text;
        updateTitle();
    }
    
    /**
     * Calls the exportChart function on the chart.
     */
    public void export() {
        ensureRunning("Exporting");
        invokeJS("export", instance.getExporting().buttons_exportButton.onclick);
    }
    
    /**
     * Calls the print function on the chart.
     */
    public void print() {
        ensureRunning("Printing");
        invokeJS("print", instance.getExporting().buttons_printButton.onclick);
    }
    
    /**
     * Invokes the specified widget function, passing the JavaScript snippet as its argument.
     *
     * @param func Widget function name.
     * @param js JavaScript snippet.
     */
    private void invokeJS(String func, String js) {
        invoke(func, ConvertUtil.convert(js, JavaScript.class));
    }
    
    /**
     * If the chart is active, dynamically update the title and subtitle.
     */
    private void updateTitle() {
        if (running) {
            OptionMap map = new OptionMap();
            map.put("title", instance.getTitle());
            map.put("subtitle", instance.getSubtitle());
            invoke("title", map);
        }
    }
    
    /**
     * Throws an exception if a chart is not currently running.
     *
     * @param operation The operation to be invoked.
     */
    private void ensureRunning(String operation) {
        Assert.state(running, () -> operation + " requires an active chart.");
    }
    
}
