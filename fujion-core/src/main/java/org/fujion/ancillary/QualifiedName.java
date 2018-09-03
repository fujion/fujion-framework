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
package org.fujion.ancillary;

/**
 * Represents a name that has an optional qualifier. A qualified name has one of two formats:
 * <p>
 * <ul>
 * <li>&lt;name&gt;</li>
 * <li>&lt;name&gt;:&lt;qualifier&gt;</li>
 * </ul>
 */
public class QualifiedName {

    private final String name;

    private final String qualifier;

    /**
     * Parses a qualified name into 2 components, name and qualifier.
     *
     * @param value Qualified name to parse.
     */
    public QualifiedName(String value) {
        if (!value.contains(":")) {
            name = value;
            qualifier = value;
        } else {
            String[] pcs = value.split("\\:", 2);
            name = pcs[0] + ":";
            qualifier = pcs[1];
        }

    }

    /**
     * Returns the name component. If the name has an associated qualifier, the name will have a ":"
     * appended to it.
     *
     * @return The name component.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the qualifier component. If no qualifier was specified, the qualifier will be the
     * same as the name component.
     *
     * @return The qualifier component.
     */
    public String getQualifier() {
        return qualifier;
    }
}