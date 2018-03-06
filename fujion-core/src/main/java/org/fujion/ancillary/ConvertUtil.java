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

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.fujion.common.DateUtil.Format;
import org.fujion.common.StrUtil;
import org.fujion.component.BaseComponent;
import org.fujion.component.Page;
import org.springframework.util.Assert;

/**
 * Utility methods for interconverting data types.
 */
public class ConvertUtil {
    
    static {
        DateConverter dtc = new DateConverter();
        String[] patterns = new String[Format.values().length];
        int i = 0;
        
        for (Format format : Format.values()) {
            patterns[i++] = format.getPattern();
        }
        
        dtc.setUseLocaleFormat(true);
        dtc.setPatterns(patterns);
        ConvertUtils.register(dtc, Date.class);
    }

    /**
     * Converts an input value to a target type.
     *
     * @param <T> The target type.
     * @param value The value to convert.
     * @param targetType The type to which to convert.
     * @return The converted value.
     */
    public static <T> T convert(Object value, Class<T> targetType) {
        return convert(value, targetType, null);
    }
    
    /**
     * Converts an input value to a target type.
     *
     * @param <T> The target type.
     * @param value The value to convert.
     * @param targetType The type to which to convert.
     * @param instance The object instance whose property value is to be set (necessary when the
     *            target type is a component and the value is the component name or id).
     * @return The converted value.
     */
    @SuppressWarnings("unchecked")
    public static <T> T convert(Object value, Class<T> targetType, Object instance) {
        if (value == null || targetType == null || targetType.isInstance(value)) {
            return (T) value;
        }
        
        if (targetType.isEnum()) {
            return (T) convertToEnum(value, targetType);
        }
        
        if (BaseComponent.class.isAssignableFrom(targetType)) {
            return (T) convertToComponent(value, targetType, instance);
        }
        
        if (targetType == Boolean.class || targetType == boolean.class) {
            String val = value.toString().trim().toLowerCase();
            Boolean result = "true".equals(val) ? Boolean.TRUE : "false".equals(val) ? Boolean.FALSE : null;
            Assert.notNull(result, "Not a valid Boolean value: " + value);
            return (T) result;
        }
        
        return (T) ConvertUtils.convert(value, targetType);
    }
    
    /**
     * Converts the input value to an enumeration member. The input value must resolve to a string
     * which is then matched to an enumeration member by using a case-insensitive lookup.
     *
     * @param value The value to convert.
     * @param enumType The enumeration type.
     * @return The enumeration member corresponding to the input value.
     */
    private static Object convertToEnum(Object value, Class<?> enumType) {
        String val = convert(value, String.class, null);
        
        for (Object e : enumType.getEnumConstants()) {
            if (((Enum<?>) e).name().equalsIgnoreCase(val)) {
                return e;
            }
        }
        
        throw new IllegalArgumentException(
                StrUtil.formatMessage("The value \"%s\" is not a member of the enumeration %s", value, enumType.getName()));
    }
    
    /**
     * Converts the input value to component. The input value must resolve to a string which
     * represents the name or id of the component sought. This name is resolved to a component
     * instance by looking it up in the namespace of the provided component instance.
     *
     * @param value The value to convert.
     * @param componentType The component type.
     * @param instance The component whose namespace will be used for lookup.
     * @return The component whose name matches the input value.
     */
    private static BaseComponent convertToComponent(Object value, Class<?> componentType, Object instance) {
        if (!BaseComponent.class.isInstance(instance)) {
            StrUtil.formatMessage("The property owner is not of the expected type (was %s but expected %s)",
                instance.getClass().getName(), BaseComponent.class.getName());
        }
        
        String name = convert(value, String.class, instance);
        BaseComponent container = (BaseComponent) instance;
        BaseComponent target = name.startsWith(Page.ID_PREFIX) ? container.getPage().findById(name)
                : container.findByName(name);
        
        if (target == null) {
            throw new IllegalArgumentException(
                    StrUtil.formatMessage("A component with name or id \"%s\" was not found", name));
        }
        
        if (!componentType.isInstance(target)) {
            throw new IllegalArgumentException(StrUtil.formatMessage(
                "The component with name or id \"%s\" is not of the expected type (was %s but expected %s)", name,
                target.getClass().getName(), componentType.getName()));
        }
        
        return target;
    }
    
    /**
     * Process a JavaScript code snippet. If the snippet does not have a function wrapper, a
     * no-argument wrapper will be added. Note: this function does no validation of the JavaScript
     * code.
     *
     * @param snippet JS code snippet.
     * @return A JavaScriptSnippet object or null if the input was null.
     */
    public static JavaScriptSnippet convertToJS(Object snippet) {
        return snippet == null ? null : new JavaScriptSnippet(snippet.toString());
    }
    
    /**
     * Process an array of JavaScript code snippets. See {@link #convertToJS(Object)}.
     *
     * @param snippets Array of JS code snippet.
     * @return New array of processed snippets.
     */
    public static JavaScriptSnippet[] convertToJS(Object[] snippets) {
        JavaScriptSnippet[] results = new JavaScriptSnippet[snippets.length];

        for (int i = 0; i < snippets.length; i++) {
            results[i] = convertToJS(snippets[i]);
        }
        
        return results;
    }
    
    /**
     * Invokes a method with the provided value(s), performing type conversion as necessary.
     *
     * @param instance Instance that is the target of the invocation (may be null for static
     *            methods).
     * @param method The method to invoke.
     * @param args Arguments to be passed to method (may be null if no arguments). Argument values
     *            will be coerced to the expected type if possible.
     * @return Return value of the method, if any.
     */
    public static Object invokeMethod(Object instance, Method method, Object... args) {
        try {
            Class<?>[] parameterTypes = method.getParameterTypes();
            args = args == null ? new Object[0] : args;

            if (args.length != parameterTypes.length) {
                throw new IllegalArgumentException(StrUtil.formatMessage(
                    "Attempted to invoke method \"%s\" with the incorrect number of arguments (provided %d but expected %d)",
                    method.getName(), args.length, parameterTypes.length));
            }
            
            for (int i = 0; i < parameterTypes.length; i++) {
                args[i] = convert(args[i], parameterTypes[i], instance);
            }
            
            return method.invoke(instance, args);
        } catch (Exception e) {
            throw new ComponentException(e, "Exception invoking method \"%s\" on component \"%s\"", method.getName(),
                    instance.getClass().getName());
        }
    }
    
    private ConvertUtil() {
    }
}
