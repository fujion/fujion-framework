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

import org.fujion.component.BaseComponent;

/**
 * Renders a model object to a component.
 *
 * @param <T> The component type to be rendered.
 * @param <M> The model type.
 */
public interface IComponentRenderer<T extends BaseComponent, M> {
    
    /**
     * Renders the model object as a new component instance.
     * 
     * @param model The model object to be rendered.
     * @return A new component instance (never null).
     */
    T render(M model);
}
