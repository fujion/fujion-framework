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
package org.fujion.chartjs;

import org.apache.commons.lang3.StringUtils;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.chartjs.axis.BaseAxisOptions;
import org.fujion.chartjs.common.AnimationsOptions;
import org.fujion.chartjs.common.LegendOptions;
import org.fujion.chartjs.common.TitleOptions;
import org.fujion.chartjs.common.TooltipOptions;
import org.fujion.chartjs.plot.PlotOptions;
import org.fujion.chartjs.plot.PlotType;
import org.fujion.common.Assert;
import org.fujion.common.CyclicIterator;
import org.fujion.common.MiscUtil;
import org.fujion.component.BaseUIComponent;

/**
 * Fujion wrapper for Chart.js component.
 */
@Component(
        tag = "chartjs",
        widgetModule = "fujion-chartjs",
        widgetClass = "ChartJS",
        parentTag = "*",
        description = "Fujion wrapper for chart.js component.")
public class Chart extends BaseUIComponent {

    private static final String[] DEFAULT_COLORS = new String[]{"#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9",
            "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"};

    private boolean running;

    private final CyclicIterator<String> colorIterator = new CyclicIterator<>(DEFAULT_COLORS);

    private final ChartInstance instance = new ChartInstance();

    private final String[] titles = new String[]{"", ""};

    public Chart() {
        super();
        instance.options.plugins.title.text$array = titles;
    }

    /**
     * Removes all series and data points and destroys the client graph.
     */
    public void clear() {
        running = false;
        colorIterator.reset();
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
     * Get the title text.
     *
     * @return Title text
     */
    @PropertyGetter(value = "title", description = "The title text.")
    public String getTitle() {
        return titles[0];
    }

    /**
     * Set the title text.
     *
     * @param title Title text
     */
    @PropertySetter(value = "title", description = "The title text.")
    public void setTitle(String title) {
        title = StringUtils.trimToEmpty(title);

        if (propertyChange("title", titles[0], titles[0] = title, false)) {
            updateTitleVisibility();
        }
    }

    /**
     * Get the subtitle text.
     *
     * @return Subtitle text
     */
    @PropertyGetter(value = "subtitle", description = "The subtitle text.")
    public String getSubtitle() {
        return titles[1];
    }

    /**
     * Set the subtitle text.
     *
     * @param subtitle Subtitle text
     */
    @PropertySetter(value = "subtitle", description = "The subtitle text.")
    public void setSubtitle(String subtitle) {
        subtitle = StringUtils.trimToEmpty(subtitle);

        if (propertyChange("subtitle", titles[1], titles[1] = subtitle, false)) {
            updateTitleVisibility();
        }
    }

    /**
     * Sets the title to visible if either a title or a subtitle is present.
     */
    private void updateTitleVisibility() {
        instance.options.plugins.title.display = !StringUtils.isEmpty(getTitle()) || !StringUtils.isEmpty(getSubtitle());
    }

    /**
     * Returns the default plot type. Defaults to scatter.
     *
     * @return The plot type.
     */
    @PropertyGetter(value = "type", description = "The default plot type.")
    public PlotType getType() {
        return instance.type;
    }

    /**
     * The default plot type. Defaults to scatter.
     *
     * @param type The plot type.
     */
    @PropertySetter(value = "type", defaultValue = "scatter", description = "The default plot type.")
    public void setType(PlotType type) {
        propertyChange("type", instance.type, instance.type = defaultify(type, PlotType.LINE), false);
    }

    /**
     * Adds a new series of the default type.
     *
     * @return The new series.
     */
    public PlotOptions addSeries() {
        return addSeries(instance.type);
    }

    /**
     * Adds a new series of the specified type.
     *
     * @param type The plot type.
     * @return The new series.
     */
    public PlotOptions addSeries(PlotType type) {
        PlotOptions plot = type.newInstance();
        instance.data.datasets.add(plot);
        plot.borderColor = colorIterator.next();
        plot.backgroundColor = "transparent";
        return plot;
    }

    /**
     * Adds a new series of the specified type.
     *
     * @param <T>       The plot implementation class.
     * @param plotClass The plot implementation class.
     * @return The new series.
     */
    @SuppressWarnings("unchecked")
    public <T extends PlotOptions> T addSeries(Class<T> plotClass) {
        PlotType type = PlotType.fromPlotClass(plotClass);
        Assert.notNull(type, () -> "Unrecognized plot class: " + plotClass);
        return (T) addSeries(type);
    }

    /**
     * Adds an axis.
     *
     * @param id   The unique id for the axis.
     * @param axis The axis to add.
     * @param <T>  The axis type.
     */
    public <T extends BaseAxisOptions> T addAxis(
            String id,
            T axis) {
        instance.options.addAxis(id, axis);
        return axis;
    }

    /**
     * Creates and adds an axis.
     *
     * @param id   The unique id for the axis.
     * @param type The type of axis to create.
     * @param <T>  The axis type.
     */
    public <T extends BaseAxisOptions> T addAxis(
            String id,
            Class<T> type) {
        return addAxis(id, newAxis(type));
    }

    /**
     * Removes an axis with the specified id.
     *
     * @param id The unique id for the axis.
     */
    public void removeAxis(String id) {
        instance.options.removeAxis(id);
    }

    /**
     * Creates an axis of the given type.
     *
     * @param <T>      The axis type.
     * @param axisType The axis type.
     * @return An instance of the given axis type.
     */
    private <T extends BaseAxisOptions> T newAxis(Class<T> axisType) {
        return MiscUtil.newInstance(axisType);
    }

    public void setLabels(String[] labels) {
        instance.data.labels = labels;
    }

    /**
     * Return configuration options for chart.
     *
     * @return Configuration options for chart.
     */
    public ChartOptions getOptions() {
        return instance.options;
    }

    /**
     * Return title configuration options.
     *
     * @return Title configuration options.
     */
    public TitleOptions getTitleOptions() {
        return instance.options.plugins.title;
    }

    /**
     * Return tooltip configuration options.
     *
     * @return Tooltip configuration options.
     */
    public TooltipOptions getTooltipOptions() {
        return instance.options.plugins.tooltip;
    }

    /**
     * Return legend configuration options.
     *
     * @return Legend configuration options.
     */
    public LegendOptions getLegendOptions() {
        return instance.options.plugins.legend;
    }

    /**
     * Adds an animation option.
     *
     * @param name    The group or property name affected by the animation options.
     * @param options The animation options.  If null, any existing animation is removed.
     */
    public void addAnimation(
            String name,
            AnimationsOptions options) {
        instance.options.addAnimation(name, options);
    }

    /**
     * Removes an animation option.
     *
     * @param name The group or property name affected by the animation options.
     */
    public void removeAnimation(String name) {
        instance.options.removeAnimation(name);
    }

}
