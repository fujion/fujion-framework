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
package org.fujion.sparkline;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.common.MiscUtil;
import org.fujion.component.BaseUIComponent;

import java.util.Map;

/**
 * Fujion wrapper for jquery.sparkline component.
 */
@Component(
        tag = "sparkline",
        widgetModule = "fujion-sparkline",
        widgetClass = "Sparkline",
        parentTag = "*",
        description = "Sparkline component.")
public class Sparkline extends BaseUIComponent {

    private final CommonOptions commonOptions = new CommonOptions();

    private AbstractPlot plotOptions;

    private Object data;

    public void run() {
        invoke("run", data, commonOptions, getPlot());
    }

    public void clear() {
        invoke("clear");
    }

    @PropertyGetter(value = "type", description = "The type of sparkline.")
    public SparklineType getType() {
        return commonOptions.type;
    }

    @PropertySetter(value = "type", defaultValue = "line", description = "The type of sparkline.")
    public void setType(SparklineType type) {
        type = defaultify(type, SparklineType.LINE);

        if (propertyChange("type", commonOptions.type, commonOptions.type = type, false)) {
            plotOptions = null;
        }
    }

    public AbstractPlot getPlot() {
        return plotOptions != null ? plotOptions : (plotOptions = commonOptions.type.createPlot());
    }

    public <T extends AbstractPlot> T newPlot(Class<T> clazz) {
        T plot = MiscUtil.newInstance(clazz);
        setType(plot.getType());
        plotOptions = plot;
        return plot;
    }

    public void setData(double[] data) {
        this.data = data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    /**
     * Need to track rendering for proper rendering of sparkline.
     *
     * @see org.fujion.component.BaseComponent#_initProps(java.util.Map)
     */
    @Override
    protected void _initProps(Map<String, Object> props) {
        super._initProps(props);
        props.put("trackrender", true);
    }

    @PropertySetter(value = "data", defer = true, description = "Comma-delimited list of numeric values.")
    private void setData(String data) {
        String[] pcs = data.split(",");
        double[] values = new double[pcs.length];

        for (int i = 0; i < values.length; i++) {
            values[i] = Double.parseDouble(pcs[i].trim());
        }

        this.data = values;
        run();
    }

    @PropertyGetter(value = "chartRangeMax", description = "The maximum value to use for the range of Y values of the chart. Defaults to the maximum value supplied.")
    public Double getChartRangeMax() {
        return commonOptions.chartRangeMax;
    }

    @PropertySetter(value = "chartRangeMax", description = "The maximum value to use for the range of Y values of the chart. Defaults to the maximum value supplied.")
    public void setChartRangeMax(Double chartRangeMax) {
        propertyChange("chartRangeMax", commonOptions.chartRangeMax, commonOptions.chartRangeMax = chartRangeMax, false);
    }

    @PropertyGetter(value = "chartRangeMin", description = "The minimum value to use for the range of Y values of the chart. Defaults to the minimum value supplied.")
    public Double getChartRangeMin() {
        return commonOptions.chartRangeMin;
    }

    @PropertySetter(value = "chartRangeMin", description = "The minimum value to use for the range of Y values of the chart. Defaults to the minimum value supplied.")
    public void setChartRangeMin(Double chartRangeMin) {
        propertyChange("chartRangeMin", commonOptions.chartRangeMin, commonOptions.chartRangeMin = chartRangeMin, false);
    }

    @PropertyGetter(value = "fillColor", description = "The color used to fill the area under the graph as a CSS value.")
    public String getFillColor() {
        return commonOptions.fillColor;
    }

    @PropertySetter(value = "fillColor", description = "The color used to fill the area under the graph as a CSS value.")
    public void setFillColor(String fillColor) {
        propertyChange("fillColor", commonOptions.fillColor, commonOptions.fillColor = fillColor, false);
    }

    @PropertyGetter(value = "lineColor", description = "Used by line and discrete charts to specify the color of the line drawn as a CSS values string.")
    public String getLineColor() {
        return commonOptions.lineColor;
    }

    @PropertySetter(value = "lineColor", description = "Used by line and discrete charts to specify the color of the line drawn as a CSS values string.")
    public void setLineColor(String lineColor) {
        propertyChange("lineColor", commonOptions.lineColor, commonOptions.lineColor = lineColor, false);
    }
}
