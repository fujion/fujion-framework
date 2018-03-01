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
package org.fujion.script;

import java.util.Map;

/**
 * Every script language plugin must implement this interface.
 */
public interface IScriptLanguage {

    /**
     * Interface for executing a parsed script.
     */
    public interface IParsedScript {

        /**
         * Executes the compiled script with optional variables.
         *
         * @param variables Optional variable assignments (may be null).
         * @return The result of the script evaluation, if any.
         */
        Object run(Map<String, Object> variables);

        /**
         * Executes the compiled script.
         *
         * @return The result of the script evaluation, if any.
         */
        default Object run() {
            return run(null);
        }

        /**
         * Destroys the compiled script. Not all script implementations require this.
         */
        default void destroy() {
        }
    }

    /**
     * The language type of the script (e.g., "groovy"). Must be unique.
     *
     * @return The language type.
     */
    String getType();

    /**
     * Compiles the script source.
     *
     * @param source The script source.
     * @return The compiled script.
     */
    IParsedScript parse(String source);

    /**
     * Returns script variable that will represent the calling context.
     *
     * @return Name of script variable for calling context.
     */
    default String getSelf() {
        return "self";
    }

}
