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
package org.fujion.page;

import org.w3c.dom.ProcessingInstruction;

/**
 * Abstract base class for implementing a parser for a processing instruction of a given target
 * type.
 */
public abstract class PIParserBase {

    private final String target;

    /**
     * Creates the PI parser with the specified target name.
     *
     * @param target This is the name of the target of the processing instruction.
     */
    protected PIParserBase(String target) {
        this.target = target;
    }

    /**
     * Returns the processing instruction's target.
     *
     * @return The processing instruction's target.
     */
    public String getTarget() {
        return target;
    }

    /**
     * Parses a processing instruction.
     *
     * @param pi The processing instruction.
     * @param element The enclosing page element.
     */
    public abstract void parse(ProcessingInstruction pi, PageElement element);

    /**
     * Extracts an attribute from a processing instruction's data where the data is formatted as a
     * space-delimited list of key-value pairs.
     *
     * @param pi The processing instruction.
     * @param name The attribute's key.
     * @param required If true and the attributes is not found, an exception is throw.
     * @return The attribute's value. If the attribute is not found, null will be returned unless
     *         the <code>required</code> parameter is true, in which case an exception is thrown.
     */
    protected String getAttribute(ProcessingInstruction pi, String name, boolean required) {
        String data = pi.getData();
        int i = data.indexOf(name + "=");
        data = i == -1 ? "" : data.substring(i + name.length() + 1);
        String dlm = data.startsWith("\"") || data.startsWith("'") ? data.substring(0, 1) : null;
        i = data.indexOf(dlm == null ? " " : dlm, 1);
        data = data.substring(dlm == null ? 0 : 1, i < 0 ? data.length() : i);
        data = data.isEmpty() ? null : data;

        if (data == null && required) {
            throw new IllegalArgumentException(
                    "Processing instruction \"" + pi.getTarget() + "\" is missing expected attribute \"" + name + "\"");
        }

        return data;
    }

}
