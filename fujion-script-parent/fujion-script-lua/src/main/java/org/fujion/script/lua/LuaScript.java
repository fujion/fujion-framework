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
package org.fujion.script.lua;

import org.fujion.common.MiscUtil;
import org.fujion.script.IScriptLanguage;

import javax.script.*;
import java.util.Collections;
import java.util.Map;

/**
 * Support for embedding Lua scripts.
 */
public class LuaScript implements IScriptLanguage {

    /**
     * Wrapper for a parsed Lua script
     */
    public static class ParsedScript implements IParsedScript {

        private final CompiledScript script;

        public ParsedScript(String source) {
            try {
                this.script = ((Compilable) engine).compile(source);
            } catch (ScriptException e) {
                throw MiscUtil.toUnchecked(e);
            }
        }

        @Override
        public Object run(Map<String, Object> variables) {
            Bindings bindings = engine.createBindings();
            bindings.putAll(variables == null ? Collections.emptyMap() : variables);

            try {
                return script.eval(bindings);
            } catch (ScriptException e) {
                throw MiscUtil.toUnchecked(e);
            }
        }

    }

    private static ScriptEngineManager sem = new ScriptEngineManager();

    private static ScriptEngine engine = sem.getEngineByName("luaj");

    /**
     * Wrapper for a parsed Groovy script.
     */

    /**
     * @see org.fujion.script.IScriptLanguage#getType()
     */
    @Override
    public String getType() {
        return "lua";
    }

    /**
     * @see org.fujion.script.IScriptLanguage#parse(java.lang.String)
     */
    @Override
    public IParsedScript parse(String source) {
        return new ParsedScript(source);
    }

}
