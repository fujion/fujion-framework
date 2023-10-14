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
package org.fujion.script.rhino;

import org.apache.commons.lang3.StringUtils;
import org.fujion.script.IScriptLanguage;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.util.Map;

/**
 * Support for embedding Lua scripts.
 */
public class RhinoScript implements IScriptLanguage {

    /**
     * Wrapper for a parsed Rhino script.
     */
    public static class ParsedScript implements IParsedScript {

        private final String source;

        public ParsedScript(String source) {
            this.source = StringUtils.trimToEmpty(source);
        }

        @Override
        public Object run(Map<String, Object> variables) {
            Context context = Context.enter();
            Scriptable scope = context.initStandardObjects();

            if (variables != null) {
                variables.forEach((key, value) -> ScriptableObject.putProperty(scope, key, value));
            }

            try {
                return context.evaluateString(scope, source, null, 1, null);

            } finally {
                Context.exit();
            }
        }

    }

    @Override
    public String getType() {
        return "rhino";
    }

    @Override
    public IParsedScript parse(String source) {
        return new ParsedScript(source);
    }

}
