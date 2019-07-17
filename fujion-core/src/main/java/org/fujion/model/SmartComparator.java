/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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
package org.fujion.model;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.fujion.common.MiscUtil;

import java.util.Comparator;

/**
 * General purpose comparator that will compare two objects using a specified property or field
 * name.
 */
public class SmartComparator implements Comparator<Object> {
    
    private final String name;
    
    private final boolean nullsFirst;
    
    private final boolean caseSensitive;
    
    private final boolean isField;
    
    /**
     * Smart comparator for given property or field name and default settings for nullsFirst (true)
     * and caseSensitive (false).
     * 
     * @param name If prefixed with an "@", is treated as the name of an instance field; otherwise,
     *            is treated as a property name.
     */
    public SmartComparator(String name) {
        this(name, true, false);
    }
    
    /**
     * Smart comparator for given property or field name with explicit settings for nullsFirst and
     * caseSensitive.
     * 
     * @param name If prefixed with an "@", is treated as the name of an instance field; otherwise,
     *            is treated as a property name.
     * @param nullsFirst If true, nulls sort before non-nulls.
     * @param caseSensitive If true, string comparisons are case-sensitive.
     */
    public SmartComparator(String name, boolean nullsFirst, boolean caseSensitive) {
        isField = name.startsWith("@");
        this.name = isField ? name.substring(1) : name;
        this.nullsFirst = nullsFirst;
        this.caseSensitive = caseSensitive;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public int compare(Object bean1, Object bean2) {
        Object value1 = getValue(bean1);
        Object value2 = getValue(bean2);
        
        if (value1 == value2) {
            return 0;
        }
        
        if (value1 == null) {
            return nullsFirst ? -1 : 1;
        }
        
        if (value2 == null) {
            return nullsFirst ? 1 : -1;
        }
        
        if (value1.getClass() != value2.getClass()) {
            return value1.hashCode() - value2.hashCode();
        }
        
        if (value1 instanceof String) {
            String s1 = (String) value1;
            String s2 = (String) value2;
            return caseSensitive ? s1.compareTo(s2) : s1.compareToIgnoreCase(s2);
        }
        
        if (value1 instanceof Comparable) {
            return ((Comparable<Object>) value1).compareTo(value2);
        }
        
        return value1.hashCode() - value2.hashCode();
    }
    
    private Object getValue(Object bean) {
        try {
            return bean == null ? null : isField ? getFieldValue(bean) : getPropertyValue(bean);
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
    
    private Object getPropertyValue(Object bean) throws Exception {
        return PropertyUtils.getProperty(bean, name);
    }
    
    private Object getFieldValue(Object bean) throws Exception {
        return FieldUtils.readField(bean, name, true);
    }
    
}
