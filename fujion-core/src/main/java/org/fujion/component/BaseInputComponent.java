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

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.event.ChangeEvent;

/**
 * The base class for all components that support some mode of character-based user input.
 *
 * @param <T> The type of the input value.
 */
public abstract class BaseInputComponent<T> extends BaseUIComponent {

    private T value;

    /**
     * Returns the input value.
     *
     * @return The input value.
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the input value.
     *
     * @param value The input value.
     */
    public void setValue(T value) {
        _setValue(value, true);
    }

    /**
     * Clears the input value.
     */
    public void clear() {
        setValue(null);
    }

    /**
     * Returns the input value as a string. Uses the _toString method to do the conversion.
     *
     * @return The input value as a string.
     */
    @PropertyGetter(value = "value", description = "The input value as a string.")
    protected String _getValue() {
        return value == null ? null : _toString(value);
    }

    /**
     * Sets the input value as a string. Uses the _toValue method to do the conversion.
     *
     * @param value The input value as a string.
     */
    @PropertySetter(value = "value", description = "The input value as a string.")
    protected void _setValue(String value) {
        setValue(_toValue(value));
    }

    /**
     * Sets the input value.
     *
     * @param value The input value.
     * @param notifyClient If true, notify the client of the change.
     */
    protected void _setValue(T value, boolean notifyClient) {
        if (propertyChange("value", this.value, this.value = value, false) && notifyClient) {
            sync("value", value == null ? null : _toClient(value));
        }
    }

    /**
     * Converts a string value to this component's value type.
     *
     * @param value String value to convert.
     * @return The converted value.
     */
    protected abstract T _toValue(String value);

    /**
     * Converts a value to a string.
     *
     * @param value The value to convert.
     * @return The converted value.
     */
    protected abstract String _toString(T value);

    /**
     * Override to provide alternate serialization format for sending to client. Default action is
     * to serialize to string.
     *
     * @param value The raw value.
     * @return The serialized value.
     */
    protected Object _toClient(T value) {
        return _toString(value);
    }

    /**
     * Event handler for change events sent by the client.
     *
     * @param event The change event.
     */
    @EventHandler(value = "change", syncToClient = false, mode = "init")
    protected void _onChange(ChangeEvent event) {
        try {
            _setValue(_toValue(event.getValue(String.class)), false);
        } catch (Exception e) {
            setBalloon(ExceptionUtils.getRootCauseMessage(e));
        }
    }

}
