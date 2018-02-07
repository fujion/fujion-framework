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
    
    private BaseComponent self = this;

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
     * Returns the script language's variable name corresponding to "this".
     *
     * @return The script language's variable name corresponding to "this".
     */
    public String getSelfName() {
        return "self";
    }

    /**
     * Returns the component referenced by the script language's "self" variable. By default, it is
     * the script component itself. If "self" is explicitly included in the variable map passed to
     * {@link #execute(Map)}, that value will be used instead.
     *
     * @return The component referenced by the script language's "self" variable.
     */
    @PropertyGetter(value = "self", description = "The component to be referenced by the script language's \"self\" variable.")
    public BaseComponent getSelf() {
        return self;
    }
    
    /**
     * Sets the component to be referenced by the script language's "self" variable. If "self" is
     * explicitly included in the variable map passed to {@link #execute(Map)}, that value will be
     * used instead.
     *
     * @param self The component to be referenced by the script language's "self" variable.
     */
    @PropertySetter(value = "self", description = "The component to be referenced by the script language's \"self\" variable.")
    public void setSelf(BaseComponent self) {
        if (self != this.self) {
            swapTrackedComponents(self, this.self);
            propertyChange("self", this.self, this.self = self, false);
        }
    }
    
    /**
     * Remove reference when "self" is destroyed.
     *
     * @see org.fujion.component.BaseComponent#onDestroyTracked(org.fujion.component.BaseComponent)
     */
    @Override
    protected void onDestroyTracked(BaseComponent comp) {
        if (comp == self) {
            setSelf(null);
        }

        super.onDestroyTracked(comp);
    }
    
    /**
     * Execute the script with the specified variable values.
     *
     * @param variables A mapped of named variable values.
     * @return Result of the script execution.
     */
    public Object execute(Map<String, Object> variables) {
        Map<String, Object> vars = new HashMap<>();
        vars.put(getSelfName(), self);
        
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
