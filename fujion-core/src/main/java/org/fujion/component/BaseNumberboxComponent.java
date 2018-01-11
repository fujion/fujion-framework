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
package org.fujion.component;

import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

/**
 * The base class for input box components that support the entry of numeric values.
 *
 * @param <T> The type of numeric value supported.
 */
public abstract class BaseNumberboxComponent<T extends Number> extends BaseInputboxComponent<T> {
    
    private final Class<T> clazz;
    
    protected BaseNumberboxComponent(Class<T> clazz) {
        this.clazz = clazz;
    }
    
    @Override
    @PropertyGetter(value = "synchronized", description = "A true value means that the client will notify the server "
            + "as the value of the input box changes. A false value means that the client will notify "
            + "server of the new value only when the input element loses focus.")
    public boolean getSynchronized() {
        return super.getSynchronized();
    }
    
    @Override
    @PropertySetter(value = "synchronized", defaultValue = "false", description = "A true value means that the client will notify the server "
            + "as the value of the input box changes. A false value means that the client will notify "
            + "server of the new value only when the input element loses focus.")
    public void setSynchronized(boolean synchronize) {
        super.setSynchronized(synchronize);
    }
    
    @Override
    protected String _toString(T value) {
        return value == null ? null : value.toString();
    }
    
    @Override
    protected T _toValue(String value) {
        value = value == null ? "" : StringUtils.trimAllWhitespace(value);
        return value.isEmpty() ? null : NumberUtils.parseNumber(value, clazz);
    }
    
}
