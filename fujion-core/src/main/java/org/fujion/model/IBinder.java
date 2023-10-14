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
package org.fujion.model;

import java.util.function.Function;

/**
 * Interface for implementing model bindings. The following binding types are supported:
 * <ul>
 * <li>Read - A property change is read from the model and written to the target.</li>
 * <li>Write - A property change is read from the target and written to the model.</li>
 * <li>Dual - Property changes are synchronized between the model and the target.</li>
 * </ul>
 *
 * @param <M> The type of model object.
 */
public interface IBinder<M> {

    /**
     * Supports use of format strings as type converters.
     */
    class TemplateConverter implements Function<Object, Object> {

        private final String template;
        
        TemplateConverter(String template) {
            this.template = template;
        }
        
        @Override
        public String apply(Object value) {
            return template == null || value == null ? null : String.format(template, value);
        }

    }

    /**
     * A converter may return this value to ignore a binding change.
     */
    enum Value {
        NONE
    }

    /**
     * Shorthand for Value.NONE.
     */
    Value NOVALUE = Value.NONE;
    
    /**
     * Returns the bound model.
     *
     * @return The bound model.
     */
    M getModel();
    
    /**
     * Sets the model to bind.
     *
     * @param model The model to bind.
     */
    void setModel(M model);
    
    /**
     * Establishes a read binding for the given model property.
     *
     * @param modelProperty The name of the model property.
     * @return The new binding.
     */
    default IBinding read(String modelProperty) {
        return read(modelProperty, (Function<?, ?>) null);
    }
    
    /**
     * Establishes a read binding for the given model property.
     *
     * @param modelProperty The name of the model property.
     * @param template Template for property conversion.
     * @return The new binding.
     */
    default IBinding read(String modelProperty, String template) {
        return read(modelProperty, new TemplateConverter(template));
    }
    
    /**
     * Establishes a read binding for the given model property.
     *
     * @param modelProperty The name of the model property.
     * @param converter Property converter.
     * @return The new binding.
     */
    IBinding read(String modelProperty, Function<?, ?> converter);
    
    /**
     * Establishes a write binding for the given model property.
     *
     * @param modelProperty The name of the model property.
     * @return The new binding.
     */
    default IBinding write(String modelProperty) {
        return write(modelProperty, (Function<?, ?>) null);
    }
    
    /**
     * Establishes a write binding for the given model property.
     *
     * @param modelProperty The name of the model property.
     * @param template Template for property conversion.
     * @return The new binding.
     */
    default IBinding write(String modelProperty, String template) {
        return write(modelProperty, new TemplateConverter(template));
    }
    
    /**
     * Establishes a write binding for the given model property.
     *
     * @param modelProperty The name of the model property.
     * @param converter Property converter.
     * @return The new binding.
     */
    IBinding write(String modelProperty, Function<?, ?> converter);
    
    /**
     * Establishes a read and write binding for the given model property.
     *
     * @param modelProperty The name of the model property.
     * @return The new binding.
     */
    default IBinding dual(String modelProperty) {
        return dual(modelProperty, (Function<?, ?>) null, null);
    }
    
    /**
     * Establishes a read and write binding for the given model property.
     *
     * @param modelProperty The name of the model property.
     * @param readTemplate Template for property conversion.
     * @param writeTemplate Template for property conversion.
     * @return The new binding.
     */
    default IBinding dual(String modelProperty, String readTemplate, String writeTemplate) {
        return dual(modelProperty, new TemplateConverter(readTemplate), new TemplateConverter(writeTemplate));
    }
    
    /**
     * Establishes a read and write binding for the given model property.
     *
     * @param modelProperty The name of the model property.
     * @param readConverter Property converter.
     * @param writeConverter Property converter.
     * @return The new binding.
     */
    IBinding dual(String modelProperty, Function<?, ?> readConverter, Function<?, ?> writeConverter);
}
