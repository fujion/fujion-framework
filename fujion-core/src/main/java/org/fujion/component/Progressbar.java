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
package org.fujion.component;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A progress bar component.
 */
@Component(
        tag = "progressbar",
        widgetClass = "Progressbar",
        parentTag = "*",
        description = "A progress bar.")
public class Progressbar extends BaseLabeledComponent<BaseLabeledComponent.LabelPositionNone> {

    private int value;

    private int maxvalue = 100;

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
        propertyChange("value", this.value, this.value = value, true);
    }

    /**
     * Returns the maximum value for the slider.
     *
     * @return The maximum value for the slider.
     */
    @PropertyGetter(value = "maxvalue", description = "The maximum value for the slider.")
    public int getMaxValue() {
        return maxvalue;
    }

    /**
     * Sets the maximum value for the slider.
     *
     * @param maxvalue The maximum value for the slider.
     */
    @PropertySetter(value = "maxvalue", defaultValue = "100", description = "The maximum value for the slider.")
    public void setMaxValue(int maxvalue) {
        propertyChange("maxvalue", this.maxvalue, this.maxvalue = maxvalue, true);
    }

}
