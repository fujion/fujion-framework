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
package org.fujion.ancillary;


import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Manages CSS classes for a component. There are two semantic extensions to CSS class names:
 * <ol>
 * <li>A "-" character preceding a class name indicates that it is to be removed.</li>
 * <li>A group name may precede a class name, separated by a ":" character. When a class with a
 * group name is added, any previous class associated with that group will be removed. A group name
 * with no following class name simply removes the class currently associated with that group. This
 * facilitates managing classes that are mutually exclusive.</li>
 * </ol>
 */
public class CssClasses {

    private final Set<String> classes = new TreeSet<>();

    private final Map<String, String> groups = new HashMap<>();

    private boolean changed;

    /**
     * Parses a space-delimited list of class names. Any existing classes and groups are removed.
     *
     * @param value Space-delimited list of class names.
     * @return Returns this instance for chaining.
     */
    public CssClasses parse(String value) {
        classes.clear();
        groups.clear();
        update(false, value);
        return this;
    }
    
    /**
     * Adds multiple class names.
     *
     * @param values Class names to be added.
     * @return True if the state changed as a result of the operation.
     */
    public boolean add(String... values) {
        return update(false, values);
    }

    /**
     * Removes multiple class names.
     *
     * @param values Class names to be removed.
     * @return True if the state changed as a result of the operation.
     */
    public boolean remove(String... values) {
        return update(true, values);
    }

    /**
     * Toggles the presence of two mutually exclusive classes based on a condition.
     *
     * @param yesValue Classes to be added if the condition is true, or removed if false.
     * @param noValue Classes to be added if the condition is false, or removed if true.
     * @param condition The condition value.
     * @return True if the state changed as a result of the operation.
     */
    public boolean toggle(String yesValue, String noValue, boolean condition) {
        return update(condition, noValue) | update(!condition, yesValue);
    }

    /**
     * Add or remove one or more classes.
     *
     * @param remove If true, remove the classes. If false, add them.
     * @param values Classes to be added or removed.
     * @return True if the state changed as a result of the operation.
     */
    private boolean update(boolean remove, String... values) {
        for (String value : values) {
            if (StringUtils.isEmpty(value)) {
                continue;
            }

            for (String cls : value.split(" ")) {
                cls = cls.trim();
                boolean _remove = remove;

                if (cls.startsWith("-")) {
                    cls = cls.substring(1);
                    _remove = true;
                }

                if (cls.contains(":")) {
                    String[] pcs = cls.split(":", 2);
                    cls = pcs[1];
                    String group = pcs[0];
                    String clazz = groups.get(group);
                    changed |= clazz != null && classes.remove(clazz);
                    groups.put(group, _remove ? "" : cls);
                }

                if (!cls.isEmpty()) {
                    if (_remove) {
                        changed |= classes.remove(cls);
                    } else {
                        changed |= classes.add(cls);
                    }
                }
            }
        }

        return changed;
    }

    /**
     * Returns true if the state has changed since last being reset.
     *
     * @return True if the state has changed.
     */
    public boolean hasChanged() {
        return changed;
    }

    /**
     * Returns true if no classes are present.
     *
     * @return True if no classes are present.
     */
    public boolean isEmpty() {
        return classes.isEmpty();
    }

    /**
     * Returns a space-delimited list of classes.
     *
     * @param clearChanged If true, clear the changed flag.
     * @return A space-delimited list of classes.
     */
    public String toString(boolean clearChanged) {
        StringBuilder sb = new StringBuilder();

        for (String cls : classes) {
            sb.append(sb.length() == 0 ? "" : " ").append(cls);
        }

        changed &= !clearChanged;
        return sb.toString();
    }

    @Override
    public String toString() {
        return toString(false);
    }
}
