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
package org.fujion.component;

import java.util.HashMap;
import java.util.Map;

import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.event.EventUtil;

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

    private static final String EVENT_DEFERRED = "deferredExecution";

    private ExecutionMode mode = ExecutionMode.IMMEDIATE;

    private BaseComponent self = this;
    
    private boolean includeNamedComponents = true;
    
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
        propertyChange("mode", this.mode, this.mode = defaultify(mode, ExecutionMode.IMMEDIATE), false);
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
     * If true, any named components within the namespace occupied by "self" will be passed as
     * arguments to the script.
     *
     * @return True if named components are to be passed as arguments to the script.
     */
    @PropertyGetter(value = "includeNamedComponents", description = "If true, any named components within the namespace occupied by \"self\" will be passed as arguments to the script.")
    public boolean getIncludeNamedComponents() {
        return includeNamedComponents;
    }

    /**
     * If true, any named components within the namespace occupied by "self" will be passed as
     * arguments to the script.
     *
     * @param includeNamedComponents Set to true if named components are to be passed as arguments
     *            to the script.
     */
    @PropertySetter(value = "includeNamedComponents", defaultValue = "true", description = "If true, any named components within the namespace occupied by \"self\" will be passed as arguments to the script.")
    public void setIncludeNamedComponents(boolean includeNamedComponents) {
        this.includeNamedComponents = includeNamedComponents;
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
        
        if (includeNamedComponents && self != null) {
            vars.putAll(self.findAllNamed());
        }
        
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

    /**
     * Triggers script execution. If not deferred, execution is immediate. Otherwise, a
     * {@value #EVENT_DEFERRED} event is posted, deferring script execution until the end of the
     * execution cycle.
     *
     * @see org.fujion.component.BaseComponent#onAttach(org.fujion.component.Page)
     */
    @Override
    protected void onAttach(Page page) {
        super.onAttach(page);

        switch (getMode()) {
            case DEFER:
                EventUtil.post(EVENT_DEFERRED, this, null);
                break;

            case IMMEDIATE:
                execute();
                break;

            case MANUAL:
                break;
        }
    }

    /**
     * Performs deferred execution of the script.
     */
    @EventHandler(value = EVENT_DEFERRED, syncToClient = false)
    private void onDeferredExecution() {
        execute();
    }

    protected abstract Object _execute(Map<String, Object> variables);
}
