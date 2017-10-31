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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.annotation.EventHandler;
import org.fujion.common.MiscUtil;
import org.fujion.core.WebUtil;
import org.fujion.event.Event;
import org.fujion.event.EventUtil;
import org.fujion.script.IScriptLanguage;
import org.fujion.script.IScriptLanguage.IParsedScript;
import org.fujion.script.ScriptRegistry;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

/**
 * A component wrapping script source code for server-side invocation.
 */
@Component(tag = "sscript", widgetClass = "MetaWidget", content = ContentHandling.AS_ATTRIBUTE, parentTag = "*", description = "Script source code for server-side invocation.")
public class ServerScript extends BaseScriptComponent {

    private static final String EVENT_DEFERRED = "deferredExecution";

    public static final String EVENT_EXECUTED = "scriptExecution";

    private IScriptLanguage scriptLanguage;

    private IParsedScript script;

    private String type;

    public ServerScript() {
        super(false);
    }
    
    public ServerScript(String type, String script) {
        super(script, false);
        setType(type);
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
     * Executes the compiled script.
     *
     * @return Value returned by the executed script.
     */
    @Override
    protected Object _execute(Map<String, Object> variables) {
        Object result = getScript().run(variables);
        EventUtil.post(new Event(EVENT_EXECUTED, this, result));
        return result;
    }

    @Override
    public String getSelf() {
        return scriptLanguage == null ? null : scriptLanguage.getSelf();
    }
    
    /**
     * Returns the script text, either from an external source or as embedded content.
     *
     * @return The script text.
     */
    private IParsedScript getScript() {
        if (script == null) {
            Assert.notNull(scriptLanguage, "A script type must be specified");
            String code = getSrc() == null ? getContent() : getExternalScript();
            script = scriptLanguage.parse(code);
        }

        return script;
    }

    /**
     * Return the text of an external script.
     *
     * @return The script text.
     */
    private String getExternalScript() {
        try {
            Resource resource = WebUtil.getResource(getSrc());
            return IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * Returns the type of script.
     *
     * @return The script type.
     */
    @PropertyGetter(value = "type", description = "The script type.")
    public String getType() {
        return type;
    }

    /**
     * Sets the type of script.
     *
     * @param type The script type.
     */
    @PropertySetter(value = "type", description = "The script type.")
    public void setType(String type) {
        type = nullify(type);
        scriptLanguage = type == null ? null : ScriptRegistry.getInstance().get(type);

        if (scriptLanguage == null && type != null) {
            throw new IllegalArgumentException("Unknown script type: " + type);
        }
        
        propertyChange("type", this.type, this.type = type, false);
    }

    /**
     * Performs deferred execution of the script.
     */
    @EventHandler(value = EVENT_DEFERRED, syncToClient = false)
    private void onDeferredExecution() {
        execute();
    }

    /**
     * Force script re-compilation if any property changes.
     */
    @EventHandler("propertychange")
    private void onPropertyChanged() {
        script = null;
    }
}
