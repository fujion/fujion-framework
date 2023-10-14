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

import org.fujion.client.Synchronizer;
import org.fujion.component.BaseComponent;
import org.fujion.component.Page;
import org.fujion.model.IListModel.IListModelListener;
import org.fujion.model.IListModel.ListEventType;
import org.fujion.model.IPaginator.IPagingListener;
import org.fujion.model.IPaginator.PagingEventType;

import java.util.HashMap;
import java.util.Map;

/**
 * Associates a model with a view renderer.
 *
 * @param <T> The component type to be rendered.
 * @param <M> The model type.
 */
public class ModelAndView<T extends BaseComponent, M> implements IListModelListener, IPagingListener, IModelAndView<T, M> {
    
    private BaseComponent parent;
    
    private IComponentRenderer<T, M> renderer;
    
    private IListModel<M> model;
    
    private Map<BaseComponent, ModelAndView<T, M>> linkedViews;
    
    private boolean deferredRendering;

    private final Paginator paginator;
    
    public ModelAndView(BaseComponent parent) {
        this.parent = parent;
        paginator = new Paginator();
        paginator.addEventListener(this);
    }
    
    public ModelAndView(BaseComponent parent, IListModel<M> model, IComponentRenderer<T, M> renderer) {
        this(parent);
        setModel(model);
        setRenderer(renderer);
    }
    
    @Override
    public IComponentRenderer<T, M> getRenderer() {
        return renderer;
    }
    
    @Override
    public void setRenderer(IComponentRenderer<T, M> renderer) {
        if (renderer != this.renderer) {
            this.renderer = renderer;
            rerender();
        }
    }
    
    @Override
    public IListModel<M> getModel() {
        return model;
    }
    
    @Override
    public void setModel(IListModel<M> model) {
        if (this.model != null) {
            this.model.removeEventListener(this);
        }
        
        this.model = model;
        
        if (this.model != null) {
            this.model.addEventListener(this);
        }
        
        paginator.setModelSize(model == null ? 0 : model.size());
        rerender();
    }
    
    private Map<BaseComponent, ModelAndView<T, M>> getLinkedViews() {
        if (linkedViews == null) {
            linkedViews = new HashMap<>();
        }
        
        return linkedViews;
    }
    
    private int getChildIndex(int modelIndex) {
        if (paginator.isDisabled()) {
            return modelIndex;
        } else {
            return modelIndex - paginator.getModelOffset(paginator.getCurrentPage());
        }
    }
    
    @Override
    public void rerender() {
        removeLinkedViews();
        
        if (parent != null) {
            parent.destroyChildren();
        }
        
        if (model != null && parent != null && renderer != null) {
            try {
                onRenderStart();
                int start = adjustIndex(0);
                int end = adjustIndex(model.size() - 1);

                for (int i = start; i <= end; i++) {
                    renderChild(i);
                }
            } finally {
                onRenderStop();
            }
        }
    }
    
    /**
     * If deferred rendering is active, sets the synchronizer to queueing mode.
     */
    protected void onRenderStart() {
        if (deferredRendering) {
            synchronizer(true);
        }
    }
    
    /**
     * If deferred rendering is active, sets the synchronizer to non-queueing mode and flushes its
     * queue.
     */
    protected void onRenderStop() {
        if (deferredRendering) {
            synchronizer(false);
        }
    }
    
    private void synchronizer(boolean pause) {
        Page page = parent == null ? null : parent.getPage();
        Synchronizer synchronizer = page == null ? null : page.getSynchronizer();
        
        if (synchronizer != null) {
            if (pause) {
                synchronizer.startQueueing();
            } else {
                synchronizer.stopQueueing();
            }
        }
    }
    
    /**
     * Renders a child.
     *
     * @param modelIndex The index of the model object to be rendered.
     * @return The rendered child component, or null if no renderer has been registered or
     *         pagination is active and the child falls outside the current page.
     */
    protected T renderChild(int modelIndex) {
        if (renderer != null && paginator.inRange(modelIndex)) {
            M mdl = model.get(modelIndex);
            T child = renderer.render(mdl);
            parent.addChild(child, getChildIndex(modelIndex));
            
            if (model instanceof INestedModel) {
                getLinkedViews().put(child, new ModelAndView<>(child, ((INestedModel<M>) model).getChildren(mdl), renderer));
            }
            
            return child;
        }
        
        return null;
    }
    
    /**
     * Destroys the child component corresponding to the model object at the specified index.
     *
     * @param modelIndex The index of the model object.
     */
    protected void destroyChild(int modelIndex) {
        if (paginator.inRange(modelIndex)) {
            BaseComponent child = parent.getChildAt(getChildIndex(modelIndex));
            ModelAndView<T, M> linkedView = linkedViews == null ? null : linkedViews.get(child);

            if (linkedView != null) {
                linkedViews.remove(child);
                linkedView.destroy();
            }
            child.destroy();
        }
    }
    
    private void removeLinkedViews() {
        if (linkedViews != null) {
            for (ModelAndView<T, M> linkedView : linkedViews.values()) {
                linkedView.destroy();
            }
            
            linkedViews.clear();
        }
    }
    
    /**
     * Clean up all resources.
     */
    public void destroy() {
        if (model != null) {
            model.removeEventListener(this);
        }

        paginator.removeAllListeners();
        removeLinkedViews();
        linkedViews = null;
        model = null;
        renderer = null;
        parent = null;
    }
    
    /**
     * Dynamically updates the rendering when the model changes.
     *
     * @see org.fujion.model.IListModel.IListModelListener#onListChange(org.fujion.model.IListModel.ListEventType,
     *      int, int)
     */
    @Override
    public void onListChange(ListEventType type, int startIndex, int endIndex) {
        paginator.setModelSize(model.size());
        
        switch (type) {
            case ADD:
                startIndex = adjustIndex(startIndex);
                endIndex = adjustIndex(endIndex);

                for (int i = startIndex; i <= endIndex; i++) {
                    renderChild(i);
                }

                break;

            case DELETE:
                startIndex = adjustIndex(startIndex);
                endIndex = adjustIndex(endIndex);

                for (int i = endIndex; i >= startIndex; i--) {
                    destroyChild(i);
                }

                break;

            case CHANGE:
                rerender();
                break;

            case REPLACE:
                onListChange(ListEventType.DELETE, startIndex, endIndex);
                onListChange(ListEventType.ADD, startIndex, endIndex);
                break;

            case SWAP:
                if (paginator.isDisabled()) {
                    parent.swapChildren(startIndex, endIndex);
                }

                break;
            
            case SORT:
                if (!paginator.isDisabled()) {
                    rerender();
                }

                break;
        }
    }
    
    /**
     * Force the model index to be within the current page range.
     *
     * @param modelIndex Model index.
     * @return Adjusted index.
     */
    private int adjustIndex(int modelIndex) {
        if (modelIndex < 0 || paginator.isDisabled()) {
            return modelIndex;
        }
        
        int page = paginator.getCurrentPage();
        int min = paginator.getModelOffset(page);
        int max = paginator.getModelOffset(page + 1) - 1;
        return modelIndex < min ? min : modelIndex > max ? max : modelIndex;
    }
    
    @Override
    public T rerender(M object) {
        return rerender(model.indexOf(object));
    }
    
    @Override
    public T rerender(int modelIndex) {
        if (paginator.inRange(modelIndex)) {
            destroyChild(modelIndex);
            return renderChild(modelIndex);
        } else {
            return null;
        }
    }
    
    @Override
    public boolean getDeferredRendering() {
        return deferredRendering;
    }
    
    @Override
    public void setDeferredRendering(boolean value) {
        deferredRendering = value;
    }
    
    @Override
    public IPaginator getPaginator() {
        return paginator;
    }

    @Override
    public void onPagingChange(PagingEventType type, int oldValue, int newValue) {
        if (type != PagingEventType.MAX_PAGE) {
            rerender();
        }
    }
    
}
