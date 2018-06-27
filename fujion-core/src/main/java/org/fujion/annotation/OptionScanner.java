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
package org.fujion.annotation;

import java.util.Collection;
import java.util.Map;

import org.fujion.ancillary.ConvertUtil;
import org.fujion.ancillary.OptionMap;
import org.fujion.ancillary.OptionMap.IOptionMapConverter;
import org.fujion.common.Logger;
import org.fujion.expression.ELEvaluator;

/**
 * Builds an OptionMap from Option annotations.
 */
public class OptionScanner extends AbstractFieldScanner<Object, Option> {
    
    /**
     * Used for EL expressions.
     */
    private static class ValueWrapper {
        
        private final Object value;
        
        ValueWrapper(Object value) {
            this.value = value;
        }
        
        @SuppressWarnings("unused")
        public Object getValue() {
            return value;
        }
    }

    private static final Logger log = Logger.create(OptionScanner.class);

    private static final OptionScanner instance = new OptionScanner();
    
    public static void scan(Object object, OptionMap map) {
        instance.scan(object, (annotation, field) -> {
            try {
                if (annotation.ignore()) {
                    return true;
                }
                
                String name = annotation.value();
                name = name.isEmpty() ? field.getName() : name;
                Object value = field.get(object);
                
                if (value == null) {
                    return true;
                }
                
                if (name.contains("${")) {
                    name = instance.convertWith(value, name).toString();
                }

                if (value instanceof IOptionMapConverter) {
                    value = ((IOptionMapConverter) value).toMap();
                }
                
                if (value instanceof Collection && ((Collection<?>) value).isEmpty()) {
                    return true;
                }
                
                if (value instanceof Map && ((Map<?, ?>) value).isEmpty()) {
                    return true;
                }
                
                if (annotation.convertTo() != Object.class) {
                    value = ConvertUtil.convert(value, annotation.convertTo());
                }
                
                if (!annotation.convertUsing().isEmpty()) {
                    value = instance.convertWith(value, annotation.convertUsing());
                }
                
                instance.setValue(name, value, map);
            } catch (Exception e) {
                log.error("Exception transforming option map.", e);
            }
            
            return true;
        });
    }
    
    private OptionScanner() {
        super(Object.class, Option.class);
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
    private void setValue(String name, Object value, Map<String, Object> map) {
        if (name.contains(".")) {
            String pcs[] = name.split("\\.", 2);
            name = pcs[0];
            String rest = pcs[1];

            if (!rest.isEmpty()) {
                @SuppressWarnings("unchecked")
                Map<String, Object> submap = (Map<String, Object>) map.get(name);
                Map<String, Object> newmap = submap == null ? new OptionMap() : submap;
                setValue(rest, value, newmap);
                
                if (submap == null && !newmap.isEmpty()) {
                    map.put(name, newmap);
                }
            }
            
            return;
        }

        map.put(name, value);
    }
    
    private Object convertWith(Object value, String expression) {
        if (!expression.contains("${")) {
            expression = "${" + expression + "}";
        }
        
        return ELEvaluator.getInstance().evaluate(expression, new ValueWrapper(value));
    }
}
