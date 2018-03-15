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

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 * Converts a string to a JavaScript Snippet
 */
public class JavaScriptConverter extends AbstractConverter {
    
    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
        if (type != getDefaultType() || !(value instanceof String)) {
            throw new ConversionException("Cannot convert object of type " + value.getClass() + " to " + getDefaultType());
        }

        return type.cast(new JavaScript((String) value));
    }
    
    @Override
    protected Class<?> getDefaultType() {
        return JavaScript.class;
    }

}
