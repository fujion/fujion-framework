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

import org.fujion.component.BaseComponent;

import java.lang.reflect.Method;

/**
 * Represents a binding between a property on a model object and one on a component. Naming
 * conventions are from the perspective of the model object. Therefore, a read binding reads a value
 * from a model object's property and writes that value to a component's property. Similarly, a
 * write binding reads a value from a component's property and writes that value to a model object's
 * property.
 */
public interface IBinding {

    void init(BaseComponent instance, String propertyName, Method getter, Method setter);

    /**
     * Read from model, write to target.
     */
    interface IReadBinding extends IBinding {

        void read();
    }
    
    /**
     * Read from target, write to model.
     */
    interface IWriteBinding extends IBinding {

        void write();
    }
    
}
