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
package org.fujion.script;

import org.apache.commons.lang3.StringUtils;
import org.fujion.common.Assert;
import org.fujion.common.MiscUtil;

import javax.script.*;
import java.util.Map;

/**
 * Base class for JSR-223 compliant scripts.
 */
public abstract class BaseScript implements IScriptLanguage {

    /**
     * Wrapper for a script.
     */
    public class ParsedScript implements IParsedScript {

        private final String source;

        private final ScriptEngine engine;

        private CompiledScript compiledScript;

        public ParsedScript(String source) {
            this.source = StringUtils.trimToEmpty(source);
            this.engine = SCRIPT_ENGINE_MANAGER.getEngineByName(engineName);
            Assert.state(engine != null, "%s scripting engine was not found.", engineName);

            try {
                compiledScript = engine instanceof Compilable ? ((Compilable) engine).compile(source) : null;
            } catch (ScriptException e) {
                compiledScript = null;
            }

        }

        @Override
        public Object run(Map<String, Object> variables) {
            Bindings bindings = null;

            if (variables != null) {
                if (supportsBindings) {
                    bindings = engine.createBindings();
                    bindings.putAll(variables);
                } else {
                    variables.entrySet().stream().forEach(entry -> engine.put(entry.getKey(), entry.getValue()));
                }
            }

            try {
                if (compiledScript != null) {
                    return bindings == null ? compiledScript.eval() : compiledScript.eval(bindings);
                } else {
                    return bindings == null ? engine.eval(source) : engine.eval(source, bindings);
                }
            } catch (ScriptException e) {
                throw MiscUtil.toUnchecked(e);
            }
        }

    }

    public static final ScriptEngineManager SCRIPT_ENGINE_MANAGER = new ScriptEngineManager();

    private final String type;

    private final String engineName;

    private final boolean supportsBindings;

    protected BaseScript(
            String type,
            String engineName,
            boolean supportsBindings) {
        this.type = type;
        this.engineName = engineName;
        this.supportsBindings = supportsBindings;
    }

    /**
     * @see IScriptLanguage#getType()
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * @see IScriptLanguage#parse(String)
     */
    @Override
    public IParsedScript parse(String source) {
        return new ParsedScript(source);
    }

}
