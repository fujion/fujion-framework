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
package org.fujion.testharness;

import org.fujion.annotation.EventHandler;
import org.fujion.annotation.WiredComponent;
import org.fujion.component.BaseScriptComponent;
import org.fujion.component.Combobox;
import org.fujion.component.Comboitem;
import org.fujion.event.Event;

/**
 * Demonstration of supported server-side scripting engines.
 */
public class ScriptsController extends BaseController {
    
    @WiredComponent
    private Combobox cboScript;

    @EventHandler(value = "scriptExecution", target = { "jsembedded", "jsexternal" })
    private void jsExecutionHandler(Event event) {
        log(event.getData().toString());
    }

    @EventHandler(value = "scriptExecution", target = "clojurescript")
    private void clojureExecutionHandler(Event event) {
        log("Clojure script was executed: " + event.getData());
    }
    
    @EventHandler(value = "scriptExecution", target = "groovyscript")
    private void groovyExecutionHandler(Event event) {
        log("Groovy script was executed: " + event.getData());
    }
    
    @EventHandler(value = "scriptExecution", target = "jrubyscript")
    private void jrubyExecutionHandler(Event event) {
        log("JRuby script was executed: " + event.getData());
    }

    @EventHandler(value = "scriptExecution", target = "jythonscript")
    private void jythonExecutionHandler(Event event) {
        log("Jython script was executed: " + event.getData());
    }

    @EventHandler(value = "scriptExecution", target = "kotlinscript")
    private void kotlinExecutionHandler(Event event) {
        log("Kotlin script was executed: " + event.getData());
    }

    @EventHandler(value = "scriptExecution", target = "luascript")
    private void luaExecutionHandler(Event event) {
        log("Lua script was executed: " + event.getData());
    }

    @EventHandler(value = "scriptExecution", target = "rscript")
    private void renjinExecutionHandler(Event event) {
        log("Renjin script was executed: " + event.getData());
    }
    
    @EventHandler(value = "scriptExecution", target = "externalscript")
    private void externalExecutionHandler(Event event) {
        log("External server script was executed: " + event.getData());
    }
    
    @EventHandler(value = "click", target = "btnScript")
    private void btnScriptClickHandler() {
        Comboitem item = cboScript.getSelectedItem();

        if (item != null) {
            BaseScriptComponent script = root.findByName(item.getValue(), BaseScriptComponent.class);
            script.execute();
        }
    }
}
