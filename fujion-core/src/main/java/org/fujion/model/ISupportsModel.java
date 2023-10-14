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

import org.fujion.annotation.Component.PropertyGetter;
import org.fujion.annotation.Component.PropertySetter;
import org.fujion.component.BaseComponent;

/**
 * Interface for components that support an associated model and view. Note that default
 * implementations are provided for all but the {@link #getModelAndView()} method.
 * Therefore, a component should provide an implementation for only that method.
 *
 * @param <T> The type of component rendered from the model.
 */
public interface ISupportsModel<T extends BaseComponent> {

    /**
     * Returns the model and view for this component.
     *
     * @return The model and view for this component.
     */
    IModelAndView<T, ?> getModelAndView();

    /**
     * Returns the model and view for this component. The model is cast to the specified type.
     *
     * @param <M>  The class of the model object.
     * @param type The type of the model object.
     * @return The model and view for this component.
     */
    @SuppressWarnings("unchecked")
    default <M> IModelAndView<T, M> getModelAndView(Class<M> type) {
        return (IModelAndView<T, M>) getModelAndView();
    }

    /**
     * Returns the list model, or null if none set.
     *
     * @return The list model, possibly null.
     */
    @PropertyGetter(value = "model", description = "The model to be associated with the component.")
    default IListModel<?> getModel() {
        return getModelAndView().getModel();
    }

    /**
     * Sets the list model. If not null and a renderer has been set, the model will be re-rendered
     * immediately. If null, any previous rendering will be removed.
     *
     * @param <M>   The class of the model object.
     * @param model The list model, or null to remove an existing one.
     */
    @PropertySetter(value = "model", description = "The model to be associated with the component.")
    @SuppressWarnings({"rawtypes", "unchecked"})
    default <M> void setModel(IListModel<M> model) {
        getModelAndView().setModel((ListModel) model);
    }

    /**
     * Returns the model for this component. The model is cast to the specified type.
     *
     * @param <M>  The class of the model object.
     * @param type The type of the model object.
     * @return The model this component.
     */
    default <M> IListModel<M> getModel(Class<M> type) {
        return getModelAndView(type).getModel();
    }

    /**
     * Returns the renderer, or null if none set.
     *
     * @return The renderer, possibly null.
     */
    @PropertyGetter(value = "renderer", description = "The renderer to be associated with the model.")
    default IComponentRenderer<T, ?> getRenderer() {
        return getModelAndView().getRenderer();
    }

    /**
     * Sets the renderer. If not null and a model has been set, the model will be re-rendered
     * immediately. If null, any previous rendering will be removed.
     *
     * @param <M>      The class of the model object.
     * @param renderer The renderer, or null to remove an existing one.
     */
    @PropertySetter(value = "renderer", description = "The renderer to be associated with the model.")
    @SuppressWarnings({"rawtypes", "unchecked"})
    default <M> void setRenderer(IComponentRenderer<T, M> renderer) {
        getModelAndView().setRenderer((IComponentRenderer) renderer);
    }

    /**
     * Returns the renderer for a specified model type.
     *
     * @param type The type of model object.
     * @param <M>  The class of the model object.
     * @return The renderer, possibly null.
     */
    default <M> IComponentRenderer<T, M> getRenderer(Class<M> type) {
        return getModelAndView(type).getRenderer();
    }

    /**
     * Returns deferred rendering setting. If true, rendering to the client is deferred until all
     * model objects are rendered, then client updates are sent in bulk. This can be more efficient
     * when rendering a large number of items.
     *
     * @return The deferred rendering setting.
     */
    default boolean getDeferredRendering() {
        return getModelAndView().getDeferredRendering();
    }

    /**
     * Sets the deferred rendering setting. If true, rendering to the client is deferred until all
     * model objects are rendered, then client updates are sent in bulk. This can be more efficient
     * when rendering a large number of items.
     *
     * @param value The deferred rendering setting.
     */
    default void setDeferredRendering(boolean value) {
        getModelAndView().setDeferredRendering(value);
    }

    /**
     * Returns the paging controller, if any.
     *
     * @return The paging controller, possibly null.
     */
    default IPaginator getPaginator() {
        return getModelAndView().getPaginator();
    }

}
