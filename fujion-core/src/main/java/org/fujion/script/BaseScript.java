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
import org.fujion.common.MiscUtil;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Collections;
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

        public ParsedScript(String source) {
            this.source = StringUtils.trimToEmpty(source);
        }

        @Override
        public Object run(Map<String, Object> variables) {
            ScriptEngine engine = SCRIPT_ENGINE_MANAGER.getEngineByName(type);
            MiscUtil.assertState(engine != null, () -> displayName + " scripting engine was not found.");

            if (variables != null) {
                for (Map.Entry<String, Object> entry : variables.entrySet()) {
                    engine.put(entry.getKey(), entry.getValue());
                }
            }

            try {
                return engine.eval(source);
            } catch (ScriptException e) {
                throw MiscUtil.toUnchecked(e);
            }
        }

    }

    public static final ScriptEngineManager SCRIPT_ENGINE_MANAGER = new ScriptEngineManager();

    private final String type;

    private final String displayName;

    protected BaseScript(
            String type,
            String displayName) {
        this.type = type;
        this.displayName = displayName;
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
