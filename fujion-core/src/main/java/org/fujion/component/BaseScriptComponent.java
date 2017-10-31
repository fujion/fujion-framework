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

import java.util.HashMap;
import java.util.Map;

import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;

/**
 * Base for components that implement scripting support.
 */
public abstract class BaseScriptComponent extends BaseSourcedComponent {

    /**
     * Controls timing of script execution.
     */
    public enum ExecutionMode {
        /**
         * Execution is immediate.
         */
        IMMEDIATE,
        /**
         * Execution is deferred. The exact timing is implementation dependent.
         */
        DEFER,
        /**
         * Execution only occurs by manual invocation.
         */
        MANUAL
    }

    private ExecutionMode mode = ExecutionMode.IMMEDIATE;

    protected BaseScriptComponent(boolean contentSynced) {
        super(contentSynced);
    }
    
    protected BaseScriptComponent(String content, boolean contentSynced) {
        super(content, contentSynced);
    }
    
    /**
     * Returns the {@link ExecutionMode execution mode}.
     *
     * @return The execution mode.
     */
    @PropertyGetter(value = "mode", description = "The script's execution mode.")
    public ExecutionMode getMode() {
        return mode;
    }

    /**
     * Sets the {@link ExecutionMode execution mode}.
     *
     * @param mode The execution mode.
     */
    @PropertySetter(value = "mode", description = "The script's execution mode.")
    public void setMode(ExecutionMode mode) {
        propertyChange("mode", this.mode, this.mode = defaultify(mode, ExecutionMode.IMMEDIATE), isContentSynced());
    }

    /**
     * Returns the variable name for "this".
     *
     * @return The variable name for "this".
     */
    public String getSelf() {
        return "self";
    }
    
    /**
     * Execute the script with the specified variable values.
     *
     * @param variables A mapped of named variable values.
     * @return Result of the script execution.
     */
    public Object execute(Map<String, Object> variables) {
        Map<String, Object> vars = new HashMap<>();
        vars.put(getSelf(), this);

        if (variables != null) {
            vars.putAll(variables);
        }
        
        return _execute(vars);
    }

    /**
     * Execute the script with the default variable values.
     *
     * @return Result of the script execution.
     */
    public Object execute() {
        return execute(null);
    }

    protected abstract Object _execute(Map<String, Object> variables);
}
