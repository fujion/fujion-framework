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
package org.fujion.component;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * A progress bar component.
 */
@Component(tag = "progressbar", widgetClass = "Progressbar", parentTag = "*")
public class Progressbar extends BaseLabeledComponent<BaseLabeledComponent.LabelPositionNone> {

    private int value;

    private int maxValue = 100;

    @PropertyGetter("value")
    public int getValue() {
        return value;
    }

    @PropertySetter("value")
    public void setValue(int value) {
        if (value != this.value) {
            sync("value", this.value = value);
        }
    }

    @PropertyGetter("maxValue")
    public int getMaxValue() {
        return maxValue;
    }

    @PropertySetter("maxValue")
    public void setMaxValue(int maxValue) {
        if (maxValue != this.maxValue) {
            sync("maxValue", this.maxValue = maxValue);
        }
    }

}
