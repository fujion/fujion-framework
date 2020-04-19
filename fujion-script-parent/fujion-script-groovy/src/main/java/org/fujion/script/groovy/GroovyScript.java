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
package org.fujion.script.groovy;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.fujion.script.IScriptLanguage;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

/**
 * Support for embedding Groovy scripts.
 */
public class GroovyScript implements IScriptLanguage {

    private static volatile GroovyShell shell;

    /**
     * Utility method for accessing the Groovy shell, instantiating it on first invocation.
     *
     * @return The Groovy shell,
     */
    public static synchronized GroovyShell getGroovyShell() {
        if (shell == null) {
            shell = new GroovyShell();
        }

        return shell;
    }

    /**
     * Wrapper for a parsed Groovy script.
     */
    public static class ParsedScript implements IParsedScript {

        private final Script script;

        public ParsedScript(String source) {
            source = StringUtils.trimToEmpty(source);
            script = getGroovyShell().parse(source);
        }

        @Override
        public Object run(Map<String, Object> variables) {
            script.setBinding(new Binding(variables));
            return script.run();
        }

    }

    /**
     * @see org.fujion.script.IScriptLanguage#getType()
     */
    @Override
    public String getType() {
        return "groovy";
    }

    /**
     * @see org.fujion.script.IScriptLanguage#parse(java.lang.String)
     */
    @Override
    public IParsedScript parse(String source) {
        return new ParsedScript(source);
    }

}
