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
package org.fujion.ancillary;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

/**
 * Manages CSS styles for a component.
 */
public class CssStyles {

    private final Map<String, String> styles = new LinkedHashMap<>();

    /**
     * Parse style settings.
     *
     * @param value Style settings to parse. Follows same format as style attribute in HTML:
     *            multiple settings are separated by ";" with style name and value separated by ":".
     * @param clear If true, first remove any existing styles.
     */
    public void parse(String value, boolean clear) {
        if (clear) {
            styles.clear();
        }

        if (StringUtils.isEmpty(value)) {
            return;
        }

        for (String style : value.split("\\;")) {
            String[] pcs = style.split("\\:", 2);
            String name = pcs[0].trim();
            value = pcs.length == 1 ? "" : pcs[1].trim();

            if (value.isEmpty()) {
                styles.remove(name);
            } else {
                styles.put(name, value);
            }
        }
    }

    /**
     * Returns an immutable map of existing styles.
     *
     * @return Map of existing styles. Never null.
     */
    public Map<String, String> get() {
        return Collections.unmodifiableMap(styles);
    }

    /**
     * Returns the value associated with a style.
     *
     * @param style Style whose value is sought.
     * @return The associated value, or null if not found.
     */
    public String get(String style) {
        return styles.get(style);
    }

    /**
     * Adds a style and associated value. If the value is null, any existing style is removed.
     *
     * @param style The style name.
     * @param value The new value.
     * @return The previous value for the style, if any.
     */
    public String put(String style, String value) {
        return value == null ? remove(style) : styles.put(style, value);
    }

    /**
     * Removes the specified style.
     *
     * @param style The style name.
     * @return The value for the removed style, if any.
     */
    public String remove(String style) {
        return styles.remove(style);
    }

    /**
     * Returns true if no styles are present.
     *
     * @return True if no styles are present.
     */
    public boolean isEmpty() {
        return styles.isEmpty();
    }

    /**
     * Returns the registered styles in the same format as the style attribute in HTML.
     *
     * @return The concatenated list of registered styles.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Entry<String, String> entry : styles.entrySet()) {
            if (entry.getValue() != null) {
                if (sb.length() > 0) {
                    sb.append(";");
                }

                sb.append(entry.getKey()).append(':').append(entry.getValue());
            }
        }

        return sb.toString();
    }
}
