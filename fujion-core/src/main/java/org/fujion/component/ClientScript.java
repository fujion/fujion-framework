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

import java.util.Map;

import org.fujion.annotation.Component;
import org.fujion.annotation.Component.ContentHandling;

/**
 * Component wrapping JavaScript source code for client-side invocation.
 */
@Component(tag = "cscript", widgetClass = "Script", content = ContentHandling.AS_ATTRIBUTE, parentTag = "*", description = "JavaScript source code for client-side invocation.")
public class ClientScript extends BaseScriptComponent {

    public ClientScript() {
        super(true);
    }

    public ClientScript(String script) {
        super(script, true);
    }
    
    @Override
    protected Object _execute(Map<String, Object> variables) {
        invoke("execute", variables.get(getSelfName()), variables);
        return null;
    }

}
