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
package org.fujion.codemirror;

import org.apache.commons.lang3.BooleanUtils;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.component.BaseInputComponent;
import org.fujion.event.EventUtil;

import java.util.Map;

/**
 * Base class for CodeMirror JavaScript editor components.
 *
 * @param <T> Options class for the CodeMirror instance.
 */
public class CodeMirrorBase<T extends CodeMirrorOptions> extends BaseInputComponent<String> {
    
    protected final T options;

    private boolean refreshOptions;
    
    protected CodeMirrorBase(T options) {
        this.options = options;
        setMode(options.mode);
    }

    /**
     * Invokes the CodeMirror format method.
     */
    public void format() {
        invoke("format");
    }
    
    /**
     * Returns true if the editor is set to read-only.
     *
     * @return True if read-only.
     */
    @PropertyGetter(value = "readonly", description = "True if read-only.")
    public boolean isReadonly() {
        return BooleanUtils.isTrue(options.readonly);
    }
    
    /**
     * Set the read-only state of the editor.
     *
     * @param readonly The read-only state.
     */
    @PropertySetter(value = "readonly", defaultValue = "false", description = "True if read-only.")
    public void setReadonly(boolean readonly) {
        propertyChange("readonly", options.readonly, options.readonly = readonly ? readonly : null, true);
    }
    
    /**
     * Returns the placeholder message that is displayed when the editor is empty.
     *
     * @return The placeholder message that is displayed when the editor is empty.
     */
    @PropertyGetter(value = "placeholder", description = "The placeholder message that is displayed when the editor is empty.")
    public String getPlaceholder() {
        return options.placeholder;
    }
    
    /**
     * Sets the placeholder value.
     *
     * @param placeholder The placeholder value.
     */
    @PropertySetter(value = "placeholder", description = "The placeholder message that is displayed when the editor is empty.")
    public void setPlaceholder(String placeholder) {
        propertyChange("placeholder", options.placeholder, options.placeholder = nullify(placeholder), true);
    }
    
    /**
     * Returns the CodeMirror mode parameter.
     *
     * @return The CodeMirror mode parameter.
     */
    protected String getMode() {
        return options.mode;
    }
    
    /**
     * Sets the CodeMirror mode parameter.
     *
     * @param mode The CodeMirror mode parameter.
     */
    protected void setMode(String mode) {
        mode = trimify(mode);
        
        if (mode != null) {
            loadModule("codemirror/mode/" + mode + "/" + mode);
        }
        
        options.mode = mode;
        refreshOptions();
    }
    
    /**
     * Returns the CodeMirror lineNumbers parameter.
     *
     * @return The CodeMirror lineNumbers parameter.
     */
    @PropertyGetter(value = "lineNumbers", description = "The CodeMirror lineNumbers parameter.")
    public boolean getLineNumbers() {
        return BooleanUtils.isTrue(options.lineNumbers);
    }
    
    /**
     * Sets the CodeMirror lineNumbers parameter.
     *
     * @param lineNumbers The CodeMirror lineNumbers parameter.
     */
    @PropertySetter(value = "lineNumbers", defaultValue = "false", description = "The CodeMirror lineNumbers parameter.")
    public void setLineNumbers(boolean lineNumbers) {
        propertyChange("lineNumbers", options.lineNumbers, options.lineNumbers = lineNumbers ? lineNumbers : null, true);
    }
    
    @Override
    protected void _initProps(Map<String, Object> props) {
        super._initProps(props);
        props.put("wclazz", "fujion_codemirror");
    }
    
    @Override
    protected String _toValue(String value) {
        return value;
    }
    
    @Override
    protected String _toString(String value) {
        return value;
    }

    public void refreshOptions() {
        if (!refreshOptions && options != null) {
            refreshOptions = true;
            EventUtil.post("refreshOptions", this, null);
        }
    }

    @EventHandler(value = "refreshOptions", mode = "init")
    private void onRefreshOptions() {
        sync("options", options);
        refreshOptions = false;
    }
    
    public T getOptions() {
        return options;
    }
    
}
