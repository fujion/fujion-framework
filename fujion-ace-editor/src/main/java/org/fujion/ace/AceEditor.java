/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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
package org.fujion.ace;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.BaseInputComponent;

import java.util.Map;

/**
 * Base class for Ace JavaScript editor components.
 */
@Component(tag = "ace", widgetModule = "fujion-ace", widgetClass = "AceEditor", parentTag = "*", description = "Fujion wrapper for Ace JavaScript editor.")
public class AceEditor extends BaseInputComponent<String> {

    private boolean readonly;

    private String mode;

    private boolean lineNumbers;

    /**
     * Returns true if the editor is set to read-only.
     *
     * @return True if read-only.
     */
    @PropertyGetter(value = "readonly", description = "True if read-only.")
    public boolean isReadonly() {
        return readonly;
    }

    /**
     * Set the read-only state of the editor.
     *
     * @param readonly The read-only state.
     */
    @PropertySetter(value = "readonly", defaultValue = "false", description = "True if read-only.")
    public void setReadonly(boolean readonly) {
        propertyChange("readonly", this.readonly, this.readonly = readonly, true);
    }

    /**
     * Returns the Ace Editor mode parameter.
     *
     * @return The Ace Editor mode parameter.
     */
    @PropertyGetter(value = "mode", description = "The edit mode.")
    protected String getMode() {
        return mode;
    }

    /**
     * Sets the Ace Editor mode parameter.
     *
     * @param mode The Ace Editor mode parameter.
     */
    @PropertySetter(value = "mode", description = "The edit mode.")
    protected void setMode(String mode) {
        propertyChange("mode", this.mode, this.mode = trimify(mode), true);
    }

    /**
     * Returns the Ace Editor lineNumbers parameter.
     *
     * @return The Ace Editor lineNumbers parameter.
     */
    @PropertyGetter(value = "lineNumbers", description = "Controls display of line numbers.")
    public boolean getLineNumbers() {
        return lineNumbers;
    }

    /**
     * Sets the Ace Editor lineNumbers parameter.
     *
     * @param lineNumbers The Ace Editor lineNumbers parameter.
     */
    @PropertySetter(value = "lineNumbers", defaultValue = "false", description = "Controls display of line numbers.")
    public void setLineNumbers(boolean lineNumbers) {
        propertyChange("lineNumbers", this.lineNumbers, this.lineNumbers = lineNumbers, true);
    }

    @Override
    protected void _initProps(Map<String, Object> props) {
        super._initProps(props);
        props.put("wclazz", "fujion_ace");
    }

    @Override
    protected String _toValue(String value) {
        return value;
    }

    @Override
    protected String _toString(String value) {
        return value;
    }

}
