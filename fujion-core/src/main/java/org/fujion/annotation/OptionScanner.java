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
package org.fujion.annotation;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fujion.ancillary.ConvertUtil;
import org.fujion.ancillary.OptionMap;
import org.fujion.ancillary.OptionMap.IOptionMapConverter;
import org.fujion.common.MiscUtil;

/**
 * Builds an OptionMap from Option annotations.
 */
public class OptionScanner extends AbstractFieldScanner<Object, Option> {
    
    protected interface NOPConverter extends Function<Object, Object> {};

    private static final Log log = LogFactory.getLog(OptionScanner.class);

    private static final OptionScanner instance = new OptionScanner();
    
    private final Map<Class<?>, Function<Object, Object>> converters = new HashMap<>();

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
                
                if (annotation.convertWith() != NOPConverter.class) {
                    value = instance.convertWith(value, annotation.convertWith());
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
    private void setValue(String name, Object value, OptionMap map) {
        if (name.contains(".")) {
            String pcs[] = name.split("\\.", 2);
            name = pcs[0];
            String rest = pcs[1];

            if (!rest.isEmpty()) {
                OptionMap submap = (OptionMap) map.get(name);
                OptionMap newmap = submap == null ? new OptionMap() : submap;
                setValue(rest, value, newmap);
                
                if (submap == null && !newmap.isEmpty()) {
                    map.put(name, newmap);
                }
            }
            
            return;
        }

        map.put(name, value);
    }
    
    @SuppressWarnings("unchecked")
    private Object convertWith(Object value, Class<? extends Function<?, ?>> convertWith) {
        Function<Object, Object> converter = converters.get(convertWith);
        
        if (converter == null) {
            try {
                Constructor<?> ctor = convertWith.getDeclaredConstructor();
                ctor.setAccessible(true);
                converter = (Function<Object, Object>) ctor.newInstance();
                converters.put(convertWith, converter);
            } catch (Exception e) {
                throw MiscUtil.toUnchecked(e);
            }
        }
        
        return converter.apply(value);
    }

}