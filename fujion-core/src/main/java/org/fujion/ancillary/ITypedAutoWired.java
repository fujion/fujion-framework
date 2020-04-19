/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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

import org.fujion.component.BaseComponent;

/**
 * A typed extension to IAutoWired.
 * 
 * @param <T> The type of the root component.
 */
public interface ITypedAutoWired<T extends BaseComponent> extends IAutoWired {

    /**
     * Called when the controller has been fully initialized.
     *
     * @param root The root component to which this controller is bound.
     */
    default void afterTypedInitialized(T root) {
    }
    
    /**
     * Called before the controller has been fully initialized.
     *
     * @param root The root component to which this controller will be bound.
     */
    default void beforeTypedInitialized(T root) {
    }
    
    @Override
    @SuppressWarnings("unchecked")
    default void afterInitialized(BaseComponent root) {
        afterTypedInitialized((T) root);
    }

    @Override
    @SuppressWarnings("unchecked")
    default void beforeInitialized(BaseComponent root) {
        beforeTypedInitialized((T) root);
    }
    
}
