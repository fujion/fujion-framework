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
package org.fujion.model;

import org.fujion.common.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple implementation of a paginator that can notify subscribers when a property value has
 * changed.
 */
public class Paginator implements IPaginator {

    private final List<IPagingListener> listeners = new ArrayList<>();

    private int pageSize;

    private int currentPage;

    private int modelSize;

    public Paginator() {
    }
    
    /**
     * @see org.fujion.model.IPaginator#getPageSize()
     */
    @Override
    public int getPageSize() {
        return pageSize;
    }
    
    /**
     * @see org.fujion.model.IPaginator#setPageSize(int)
     */
    @Override
    public void setPageSize(int pageSize) {
        if (pageSize != this.pageSize) {
            currentPage = 0;
            fireEvent(PagingEventType.PAGE_SIZE, this.pageSize, this.pageSize = pageSize);
        }
    }
    
    /**
     * @see org.fujion.model.IPaginator#getCurrentPage()
     */
    @Override
    public int getCurrentPage() {
        return currentPage;
    }
    
    /**
     * @see org.fujion.model.IPaginator#setCurrentPage(int)
     */
    @Override
    public void setCurrentPage(int pageIndex) {
        if (pageIndex != currentPage) {
            Assert.isTrue(pageIndex >= 0, () -> "Current page may not be less than 0");
            Assert.isTrue(pageIndex <= getMaxPage(), () -> "Current page may not exceed maximum number of pages");
            fireEvent(PagingEventType.CURRENT_PAGE, this.currentPage, this.currentPage = pageIndex);
        }
    }
    
    /**
     * @see org.fujion.model.IPaginator#getModelSize()
     */
    @Override
    public int getModelSize() {
        return modelSize;
    }
    
    /**
     * Sets the number of elements in the underlying model.
     *
     * @param modelSize Number of elements in the underlying model.
     */
    public void setModelSize(int modelSize) {
        if (modelSize != this.modelSize) {
            Assert.isTrue(modelSize >= 0, () -> "Model size must not be less than 0");
            int oldmax = getMaxPage();
            this.modelSize = modelSize;
            fireEvent(PagingEventType.MAX_PAGE, oldmax, getMaxPage());
        }
    }

    /**
     * @see org.fujion.model.IPaginator#getMaxPage()
     */
    @Override
    public int getMaxPage() {
        return isDisabled() || modelSize == 0 ? 0 : (modelSize - 1) / pageSize;
    }

    /**
     * Returns true if the model index falls within the current page. If the paginator is disabled,
     * will always return true.
     *
     * @param modelIndex The model index.
     * @return True if the index falls within the current page.
     */
    public boolean inRange(int modelIndex) {
        return isDisabled() || (modelIndex >= getModelOffset(currentPage) && modelIndex < getModelOffset(currentPage + 1));
    }

    /**
     * Returns the index of the first model element on the specified page. If the paginator is
     * disabled, will always return 0. If the page index exceeds the maximum page index, will return
     * one more than the total number of model elements.
     *
     * @param pageIndex The page index.
     * @return The index of the first model element on the page.
     */
    public int getModelOffset(int pageIndex) {
        return isDisabled() ? 0 : Math.min(pageIndex * pageSize, modelSize);
    }
    
    /**
     * @see org.fujion.model.IPaginator#addEventListener(org.fujion.model.IPaginator.IPagingListener)
     */
    @Override
    public boolean addEventListener(IPagingListener listener) {
        return listeners.add(listener);
    }
    
    /**
     * @see org.fujion.model.IPaginator#removeAllListeners()
     */
    @Override
    public void removeAllListeners() {
        listeners.clear();
    }
    
    /**
     * @see org.fujion.model.IPaginator#removeEventListener(org.fujion.model.IPaginator.IPagingListener)
     */
    @Override
    public boolean removeEventListener(IPagingListener listener) {
        return listeners.remove(listener);
    }

    /**
     * Fires a paging event to all listeners.
     *
     * @param type Type of paging event.
     * @param oldValue The old value of the property that changed.
     * @param newValue The new value of the property that changed.
     */
    private void fireEvent(PagingEventType type, int oldValue, int newValue) {
        for (IPagingListener listener : listeners) {
            listener.onPagingChange(type, oldValue, newValue);
        }
    }
    
}
