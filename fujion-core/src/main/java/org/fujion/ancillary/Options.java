/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

import org.fujion.ancillary.OptionMap.IOptionMapConverter;
import org.fujion.annotation.JavaScript;

/**
 * Base class for options. Supports interconverting class-based properties to a map and vice-versa.
 */
public abstract class Options implements IOptionMapConverter {

    /**
     * @see org.fujion.ancillary.OptionMap.IOptionMapConverter#toMap()
     */
    @Override
    public OptionMap toMap() {
        OptionMap map = new OptionMap();
        toMap(getClass(), map);
        return map;
    }

    /**
     * Set each of the class' fields into a map. Ignores private and transient fields. Recurses for
     * each superclass until the root Options class is reached.
     *
     * @param clazz Class to examine.
     * @param map Map to receive fields.
     */
    private void toMap(Class<?> clazz, OptionMap map) {
        if (clazz == Options.class) {
            return;
        }

        toMap(clazz.getSuperclass(), map);

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            int modifiers = field.getModifiers();

            if (!Modifier.isTransient(modifiers) && !Modifier.isPrivate(modifiers)) {
                try {
                    String name = field.getName();
                    Object value = field.get(this);

                    if (value == null) {
                        continue;
                    }

                    if (value instanceof Collection && ((Collection<?>) value).isEmpty()) {
                        continue;
                    }
                    
                    if (value instanceof Map && ((Map<?, ?>) value).isEmpty()) {
                        continue;
                    }

                    if (field.isAnnotationPresent(JavaScript.class)) {
                        if (value.getClass().isArray()) {
                            value = ConvertUtil.convertToJS((Object[]) value);
                        } else {
                            value = ConvertUtil.convertToJS(value);
                        }
                    }

                    setValue(name, value, map);
                } catch (Exception e) {}
            }
        }
    }

    /**
     * Sets the name/value pair into the specified map. If the name contains an underscore, the
     * value is stored in a submap using the first part of the name as the top level key and the
     * second part as the subkey. If an underscore is part of the variable name, use two consecutive
     * underscores. If a name contains a "$" character, the name is truncated at that character.
     * This allows representing alternate forms of the same variable. For such variables, only the
     * last non-null instance will be passed.
     *
     * @param name Key name.
     * @param value Value.
     * @param map Map to receive key/value pair.
     */
    private void setValue(String name, Object value, OptionMap map) {
        if (name.contains("_")) {
            String pcs[] = name.split("\\_", 2);
            name = pcs[0];
            String rest = pcs[1];

            if (rest.startsWith("_")) {
                pcs = rest.split("\\_", 3);
                name += pcs[1];
                rest = pcs.length == 2 ? "" : pcs[2];
            }

            OptionMap submap = (OptionMap) map.get(name);

            if (submap == null) {
                map.put(name, submap = new OptionMap());
            }

            if (!rest.isEmpty()) {
                setValue(rest, value, submap);
            }
            
            return;
        }

        name = name.contains("$") ? name.split("\\$", 2)[0] : name;
        map.put(name, value);
    }

    /**
     * Copies this instance to a target of the same class.
     *
     * @param target Target to receive copy.
     */
    public void copyTo(Options target) {
        if (target.getClass() != getClass()) {
            throw new IllegalArgumentException();
        }

        for (Field field : getClass().getFields()) {
            if (field.isAccessible() && !Modifier.isTransient(field.getModifiers())) {
                try {
                    field.set(target, field.get(this));
                } catch (Exception e) {}
            }
        }

    }
}
