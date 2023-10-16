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
package org.fujion.core;

import org.fujion.ancillary.ComponentException;
import org.fujion.common.Assert;
import org.fujion.common.MiscUtil;
import org.fujion.convert.ConversionService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Bean manipulation methods.
 */
public class BeanUtil {

    /**
     * Returns the value of the named property.
     *
     * @param bean         The object whose property value is sought.
     * @param propertyName The name of the property.
     * @return The property value.
     */
    public static Object getPropertyValue(Object bean, String propertyName) {
        try {
            if (bean == null) {
                return null;
            }

            PropertyDescriptor dx = getPropertyDescriptor(bean, propertyName);
            Method getter = dx.getReadMethod();
            Assert.notNull(getter, "Property %s.%s cannot be read.", bean.getClass().getName(), propertyName);
            return getter.invoke(bean);
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * Returns the value of the named property.  If the value is not of the expected type, null is returned.
     *
     * @param bean         The object whose property value is sought.
     * @param propertyName The name of the property.
     * @param type         The expected data type.
     * @return The property value.
     */
    public static <T> T getPropertyValue(Object bean, String propertyName, Class<T> type) {
        Object value = getPropertyValue(bean, propertyName);
        return MiscUtil.castTo(value, type);
    }

    /**
     * Sets a property value.  The value is first converted to the type required by the property.
     *
     * @param bean         The object whose property value is to be set.
     * @param propertyName The name of the property.
     * @param value        The property value.
     */
    public static void setPropertyValue(Object bean, String propertyName, Object value) {
        try {
            PropertyDescriptor dx = getPropertyDescriptor(bean, propertyName);
            Class<?> targetType = dx.getPropertyType();
            Method setter = dx.getWriteMethod();
            Assert.notNull(setter, "Property %s.%s cannot be written.", bean.getClass().getName(), propertyName);
            value = ConversionService.getInstance().convert(value, targetType);
            setter.invoke(bean, value);
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * Sets multiple property values from a map of property name/property value pairs.  This method
     * calls {@link #setPropertyValue} to set each property value.
     *
     * @param bean   The object whose properties are to be set.
     * @param values The values to set.  The map's key is the property name.
     */
    public static void setPropertyValues(Object bean, Map<String, ?> values) {
        values.forEach((k, v) -> setPropertyValue(bean, k, v));
    }

    /**
     * Returns the descriptor for the named property.
     *
     * @param bean         The object containing the named property.
     * @param propertyName The name of the property.
     * @return The property descriptor (never null).
     * @throws IllegalArgumentException If the property was not found.
     */

    public static @NonNull PropertyDescriptor getPropertyDescriptor(Object bean, String propertyName) {
        PropertyDescriptor dx = BeanUtils.getPropertyDescriptor(bean.getClass(), propertyName);
        Assert.notNull(dx, "No property named %s in object of type %s", propertyName, bean.getClass().getName());
        return dx;
    }

    /**
     * Invokes a method with the provided value(s), performing type conversion as necessary.
     *
     * @param instance Instance that is the target of the invocation (may be null for static
     *                 methods).
     * @param method   The method to invoke.
     * @param args     Arguments to be passed to the method (may be null if no arguments). Argument
     *                 values will be coerced to the expected type if possible.
     * @return Return value of the method, if any.
     */
    public static Object invokeMethod(Object instance, Method method, Object... args) {
        try {
            method.setAccessible(true);
            args = ConversionService.getInstance().convertArgs(instance, method.getParameterTypes(), args);
            return method.invoke(instance, args);
        } catch (Exception e) {
            throw new ComponentException(e, "Exception invoking method \"%s\" on component \"%s\"", method.getName(),
                    instance.getClass().getName());
        }
    }

    /**
     * Invokes a compatible constructor with the provided value(s), performing type conversion as
     * necessary.
     *
     * @param <T>   The expected return type.
     * @param clazz The class whose constructor is to be invoked.
     * @param args  Arguments to be passed to the constructor (may be null if no arguments). Argument
     *              values will be coerced to the expected type if possible.
     * @return The newly created instance.
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeConstructor(Class<T> clazz, Object... args) {
        int arglen = args == null ? 0 : args.length;

        if (arglen == 0) {
            return MiscUtil.newInstance(clazz);
        }

        RuntimeException lastException = null;

        for (Constructor<?> ctor : clazz.getDeclaredConstructors()) {
            if (ctor.getParameterCount() == arglen) {
                try {
                    Object[] newArgs = ConversionService.getInstance().convertArgs(null, ctor.getParameterTypes(), args);
                    return (T) ctor.newInstance(newArgs);
                } catch (Exception e) {
                    lastException = MiscUtil.toUnchecked(e);
                }
            }
        }

        Assert.notNull(lastException, () -> "No suitable constructor found for class " + clazz);
        throw lastException;
    }

    /**
     * Enforce static class.
     */
    private BeanUtil() {
    }
}
