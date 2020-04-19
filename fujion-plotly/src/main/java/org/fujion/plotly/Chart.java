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
package org.fujion.plotly;

import java.util.Map;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.BaseUIComponent;
import org.fujion.plotly.layout.LayoutOptions;
import org.fujion.plotly.plot.PlotOptions;
import org.fujion.plotly.plot.PlotType;
import org.springframework.util.Assert;

/**
 * Fujion wrapper for Plotly component.
 */
@Component(
        tag = "plotly",
        widgetModule = "fujion-plotly",
        widgetClass = "Plotly",
        parentTag = "*",
        description = "Fujion wrapper for Plotly component.")
public class Chart extends BaseUIComponent {

    private final ChartInstance instance = new ChartInstance();

    private boolean running;

    private PlotType type = PlotType.SCATTER;

    /**
     * Create default chart (line plot, single x- and y-axis).
     */
    public Chart() {
        super();
    }

    /**
     * Removes all series and data points and destroys the client graph.
     */
    public void clear() {
        running = false;
        instance.data.clear();
        invoke("reset");
    }

    /**
     * Build the graph on the client.
     */
    public void run() {
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
     * Need to track rendering for proper sizing of graph.
     *
     * @see org.fujion.component.BaseComponent#_initProps(java.util.Map)
     */
    @Override
    protected void _initProps(Map<String, Object> props) {
        super._initProps(props);
        props.put("trackrender", true);
    }
    
    /**
     * Convenience method for getting title.
     *
     * @return Title text
     */
    @PropertyGetter(value = "title", description = "The title text.")
    public String getTitle() {
        return instance.layout.title;
    }

    /**
     * Convenience method for setting title.
     *
     * @param title Title text
     */
    @PropertySetter(value = "title", description = "The title text.")
    public void setTitle(String title) {
        instance.layout.title = title;
    }

    /**
     * Returns the default plot type. Defaults to scatter.
     *
     * @return The plot type.
     */
    @PropertyGetter(value = "type", description = "The default plot type.")
    public PlotType getType() {
        return type;
    }

    /**
     * The default plot type. Defaults to scatter.
     *
     * @param type The plot type.
     */
    @PropertySetter(value = "type", defaultValue = "scatter", description = "The default plot type.")
    public void setType(PlotType type) {
        propertyChange("type", this.type, this.type = defaultify(type, PlotType.SCATTER), false);
    }

    /**
     * Adds a new series of the default type.
     *
     * @return The new series.
     */
    public PlotOptions addSeries() {
        return addSeries(type);
    }

    /**
     * Adds a new series of the specified type.
     *
     * @param type The plot type.
     * @return The new series.
     */
    public PlotOptions addSeries(PlotType type) {
        PlotOptions plot = type.newInstance();
        instance.data.add(plot);
        return plot;
    }

    /**
     * Adds a new series of the specified type.
     *
     * @param <T> The plot implementation class.
     * @param plotClass The plot implementation class.
     * @return The new series.
     */
    @SuppressWarnings("unchecked")
    public <T extends PlotOptions> T addSeries(Class<T> plotClass) {
        PlotType type = PlotType.fromPlotClass(plotClass);
        Assert.notNull(type, () -> "Unrecognized plot class: " + plotClass);
        return (T) addSeries(type);
    }

    public LayoutOptions getLayout() {
        return instance.layout;
    }
    
    public ChartOptions getSettings() {
        return instance.config;
    }
}
