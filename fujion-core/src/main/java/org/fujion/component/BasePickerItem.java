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

import org.fujion.annotation.Component.PropertySetter;

/**
 * The base class for components that represent a single choice for a picker component.
 *
 * @param <T> The item type.
 */
public abstract class BasePickerItem<T> extends BaseComponent {

    private T value;

    protected BasePickerItem() {

    }

    protected BasePickerItem(T item) {
        setRawValue(item);
    }

    /**
     * Returns the value associated with the item.
     *
     * @return Value associated with the item.
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the value associated with the item.
     *
     * @param text Text representation of the item value. Uses _toValue to convert to the raw value.
     */
    @PropertySetter(value = "value", description = "Text representation of the item value.")
    public void setValue(String text) {
        setRawValue(_toValue(text));
    }

    /**
     * Sets the raw value associated with the item.
     *
     * @param value The new value.
     */
    public void setRawValue(T value) {
        if (propertyChange("value", this.value, this.value = value, false)) {
            sync("value", _toString(value));
        }
    }

    /**
     * Converts the raw value to its text representation.
     *
     * @param value The raw value.
     * @return The text representation.
     */
    protected abstract String _toString(T value);

    /**
     * Converts the text representation to its raw value.
     *
     * @param text The text representation.
     * @return The raw value.
     */
    protected abstract T _toValue(String text);
}
