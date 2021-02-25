/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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
package org.fujion.script.clojure;

import clojure.lang.IFn;
import clojure.lang.RT;
import clojure.lang.Var;
import org.apache.commons.lang3.StringUtils;
import org.fujion.script.IScriptLanguage;

import java.util.Map;

/**
 * Support for embedding Clojure scripts.
 */
public class ClojureScript implements IScriptLanguage {
    
    private static volatile Var compiler;
    
    /**
     * Wrapper for a parsed Clojure script
     */
    public static class ParsedScript implements IParsedScript {
        
        private final IFn script;
        
        public ParsedScript(String source) {
            source = StringUtils.trimToEmpty(source);
            this.script = (IFn) getCompiler().invoke("(fn [args] " + source + ")");
        }
        
        @Override
        public Object run(Map<String, Object> variables) {
            return script.invoke(variables);
        }
    }
    
    private static synchronized Var getCompiler() {
        if (compiler == null) {
            compiler = RT.var("clojure.core", "load-string");
        }
        
        return compiler;
    }
    
    /**
     * @see org.fujion.script.IScriptLanguage#getType()
     */
    @Override
    public String getType() {
        return "clojure";
    }
    
    /**
     * @see org.fujion.script.IScriptLanguage#parse(java.lang.String)
     */
    @Override
    public IParsedScript parse(String source) {
        return new ParsedScript(source);
    }
    
}
