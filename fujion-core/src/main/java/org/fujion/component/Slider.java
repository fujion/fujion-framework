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
package org.fujion.component;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.common.Assert;
import org.fujion.event.ChangeEvent;

/**
 * A slider component.
 */
@Component(
        tag = "slider",
        widgetClass = "Slider",
        parentTag = "*",
        description = "A slider component.")
public class Slider extends BaseUIComponent {

    /**
     * Orientation of slider component.
     */
    public enum Orientation {
        /**
         * The slider is oriented horizontally.
         */
        HORIZONTAL,
        /**
         * The slider is oriented vertically.
         */
        VERTICAL
    }
    
    private Orientation orientation = Orientation.HORIZONTAL;
    
    private int value;
    
    private int maxvalue = 100;
    
    private int minvalue;

    private int step = 1;

    private boolean synced;

    /**
     * Returns the current value of the slider.
     *
     * @return The current value of the slider.
     */
    @PropertyGetter(value = "value", description = "The current value of the slider.")
    public int getValue() {
        return value;
    }
    
    /**
     * Sets the current value of the slider.
     *
     * @param value The current value of the slider.
     */
    @PropertySetter(value = "value", defaultValue = "0", description = "The current value of the slider.")
    public void setValue(int value) {
        _setValue(value, true);
    }
    
    /**
     * Set the new value of the slider, optionally notifying the client.
     *
     * @param value The new slider value.
     * @param notifyClient If true, notify the client.
     */
    private void _setValue(int value, boolean notifyClient) {
        propertyChange("value", this.value, this.value = value, notifyClient);
    }
    
    /**
     * Returns the maximum allowable value. Defaults to 100.
     *
     * @return The maximum allowable value. Defaults to 100.
     */
    @PropertyGetter(value = "maxvalue", description = "The maximum allowable value.")
    public int getMaxValue() {
        return maxvalue;
    }
    
    /**
     * Sets the maximum allowable value.
     *
     * @param maxvalue The maximum allowable value.
     */
    @PropertySetter(value = "maxvalue", defaultValue = "100", description = "The maximum allowable value.")
    public void setMaxValue(int maxvalue) {
        propertyChange("maxvalue", this.maxvalue, this.maxvalue = maxvalue, true);
    }
    
    /**
     * Returns the minimum allowable value. Defaults to 0.
     *
     * @return The minimum allowable value. Defaults to 0.
     */
    @PropertyGetter(value = "minvalue", description = "The minimum allowable value.")
    public int getMinValue() {
        return minvalue;
    }

    /**
     * Sets the minimum allowable value.
     *
     * @param minvalue The minimum allowable value.
     */
    @PropertySetter(value = "minvalue", defaultValue = "0", description = "The minimum allowable value.")
    public void setMinValue(int minvalue) {
        propertyChange("minvalue", this.minvalue, this.minvalue = minvalue, true);
    }
    
    /**
     * Returns the {@link Orientation orientation} of the component.
     *
     * @return The {@link Orientation orientation} of the component.
     */
    @PropertyGetter(value = "orientation", description = "The orientation of the component.")
    public Orientation getOrientation() {
        return orientation;
    }
    
    /**
     * Sets the {@link Orientation orientation} of the component.
     *
     * @param orientation The {@link Orientation orientation} of the component.
     */
    @PropertySetter(value = "orientation", defaultValue = "horizontal", description = "The orientation of the component.")
    public void setOrientation(Orientation orientation) {
        propertyChange("orientation", this.orientation, this.orientation = defaultify(orientation, Orientation.HORIZONTAL),
                true);
    }
    
    /**
     * Returns the amount of change in the current value when an arrow button is clicked. Default is
     * 1.
     *
     * @return The amount of change in the current value when an arrow button is clicked. Default is
     *         1.
     */
    @PropertyGetter(value = "step", description = "The amount of change in the current value when an arrow button is clicked.")
    public int getStep() {
        return step;
    }
    
    /**
     * Sets the amount of change in the current value when an arrow button is clicked.
     *
     * @param step The amount of change in the current value when an arrow button is clicked.
     */
    @PropertySetter(value = "step", defaultValue = "1", description = "The amount of change in the current value when an arrow button is clicked.")
    public void setStep(int step) {
        Assert.isTrue(step > 0, () -> "Step value must be greater than zero");
        propertyChange("step", this.step, this.step = step, true);
    }

    /**
     * Returns the synchronized setting. If set to true, every change to the slider's value will be
     * sent to the server. If false, only the final value will be sent.
     *
     * @return The synchronized setting.
     */
    @PropertyGetter(value = "synchronized", description = "If set to true, every change to the slider's value will be "
            + "sent to the server. If false, only the final value will be sent.")
    public boolean getSynchronized() {
        return synced;
    }

    /**
     * Sets the synchronized setting. If set to true, every change to the slider's value will be
     * sent to the server. If false, only the final value will be sent.
     *
     * @param synced The synchronized setting.
     */
    @PropertySetter(value = "synchronized", defaultValue = "false", description = "If set to true, every change to the slider's value will be "
            + "sent to the server. If false, only the final value will be sent.")
    protected void setSynchronized(boolean synced) {
        propertyChange("synced", this.synced, this.synced = synced, true);
    }

    /**
     * Handles change events from the client.
     *
     * @param event A change event.
     */
    @EventHandler(value = "change", syncToClient = false, mode = "init")
    @SuppressWarnings("unused")
    private void _onChange(ChangeEvent event) {
        _setValue(defaultify(event.getValue(Integer.class), value), false);
    }
}
