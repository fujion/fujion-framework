/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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

import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.common.NumUtil;

/**
 * The base class for all input box components.
 *
 * @param <T> The value type for the input box.
 */
public abstract class BaseInputboxComponent<T> extends BaseInputComponent<T> {

    private T minvalue;

    private T maxvalue;

    private String pattern;

    private String placeholder;

    private int maxLength;

    private boolean readonly;

    private boolean required;

    private boolean synced;

    /**
     * Returns the synchronization flag. A true value means that the client will notify the server
     * as the value of the input box changes. A false value means that the client will notify the
     * server of the new value only when the input element loses focus.
     *
     * @return The synchronization flag.
     */
    protected boolean getSynchronized() {
        return synced;
    }

    /**
     * Sets the synchronization flag. A true value means that the client will notify the server as
     * the value of the input box changes. A false value means that the client will notify the
     * server of the new value only when the input element loses focus.
     *
     * @param synced The synchronization flag.
     */
    protected void setSynchronized(boolean synced) {
        propertyChange("synced", this.synced, this.synced = synced, true);
    }

    /**
     * Returns the minimum allowable value, if any.
     *
     * @return The minimum allowable value. Null indicates no minimum.
     */
    @PropertyGetter(value = "minvalue", description = "The minimum allowable value, if any.")
    public T getMinValue() {
        return minvalue;
    }

    @PropertySetter(value = "minvalue", description = "The minimum allowable value, if any.")
    private void _setMinValue(String minvalue) {
        setMinValue(_toValue(minvalue));
    }

    /**
     * Sets the minimum allowable value.
     *
     * @param minvalue The minimum allowable value. Null indicates no minimum.
     */
    public void setMinValue(T minvalue) {
        if (propertyChange("minvalue", this.minvalue, this.minvalue = minvalue, false)) {
            sync("minvalue", _toString(minvalue));
        }
    }

    /**
     * Returns the maximum allowable value, if any.
     *
     * @return The maximum allowable value. Null indicates no maximum.
     */
    @PropertyGetter(value = "maxvalue", description = "The maximum allowable value, if any.")
    public T getMaxValue() {
        return maxvalue;
    }

    @PropertySetter(value = "maxvalue", description = "The maximum allowable value, if any.")
    private void _setMaxValue(String maxvalue) {
        setMaxValue(_toValue(maxvalue));
    }

    /**
     * Sets the maximum allowable value.
     *
     * @param maxvalue The maximum allowable value. Null indicates no maximum.
     */
    public void setMaxValue(T maxvalue) {
        if (propertyChange("maxvalue", this.maxvalue, this.maxvalue = maxvalue, false)) {
            sync("maxvalue", _toString(maxvalue));
        }
    }

    /**
     * Returns the regular expression that constrains the input format.
     *
     * @return Regular expression that constrains the input format.
     */
    @PropertyGetter(value = "pattern", description = "Regular expression that constrains the input format.")
    public String getPattern() {
        return pattern;
    }

    /**
     * Sets the regular expression that constrains the input format.
     *
     * @param pattern Regular expression that constrains the input format.
     */
    @PropertySetter(value = "pattern", description = "Regular expression that constrains the input format.")
    public void setPattern(String pattern) {
        propertyChange("pattern", this.pattern, this.pattern = nullify(pattern), true);
    }

    /**
     * Returns the placeholder message that is displayed when the input box is empty.
     *
     * @return The placeholder message.
     */
    @PropertyGetter(value = "placeholder", description = "The placeholder message that is displayed when the input box is empty.")
    public String getPlaceholder() {
        return placeholder;
    }

    /**
     * Sets the placeholder message that is displayed when the input box is empty.
     *
     * @param placeholder The placeholder message.
     */
    @PropertySetter(value = "placeholder", description = "The placeholder message that is displayed when the input box is empty.")
    public void setPlaceholder(String placeholder) {
        propertyChange("placeholder", this.placeholder, this.placeholder = nullify(placeholder), true);
    }

    /**
     * Returns the maximum character length of input.
     *
     * @return The maximum character length of input.
     */
    @PropertyGetter(value = "maxlength", description = "The maximum character length of input.")
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * Sets the maximum character length of input.
     *
     * @param maxLength The maximum character length of input.
     */
    @PropertySetter(value = "maxlength", description = "The maximum character length of input.")
    public void setMaxLength(int maxLength) {
        propertyChange("maxlength", this.maxLength, this.maxLength = NumUtil.enforceRange(maxLength, 0, 524288), true);
    }

    /**
     * Returns true if the input box is read-only.
     *
     * @return True if the input box is read-only.
     */
    @PropertyGetter(value = "readonly", description = "True if the input box is read-only.")
    public boolean isReadonly() {
        return readonly;
    }

    /**
     * Sets the read-only state of the input box.
     *
     * @param readonly If true, the contents of the input box may not be changed by the user.
     */
    @PropertySetter(value = "readonly", defaultValue = "false", description = "True if the input box is read-only.")
    public void setReadonly(boolean readonly) {
        propertyChange("readonly", this.readonly, this.readonly = readonly, true);
    }

    /**
     * Returns true if input is required for this component.
     *
     * @return True if input is required for this component.
     */
    @PropertyGetter(value = "required", description = "True if input is required for this component.")
    public boolean isRequired() {
        return required;
    }

    /**
     * Sets the required state of the input box.
     *
     * @param required True if input is required for this component.
     */
    @PropertySetter(value = "required", defaultValue = "false", description = "True if input is required for this component.")
    public void setRequired(boolean required) {
        propertyChange("required", this.required, this.required = required, true);
    }

    /**
     * Selects the entire contents of the input box.
     */
    public void selectAll() {
        invoke("selectAll");
    }

    /**
     * Selects a range of characters in the input box.
     *
     * @param start Start of range.
     * @param end End of range.
     */
    public void selectRange(int start, int end) {
        invoke("selectRange", start, end);
    }
}
