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
package org.fujion.model;

/**
 * Supports paging within a list model.
 */
public interface IPaginator {

    /**
     * Interface for listeners receiving events when a setting is modified.
     */
    interface IPagingListener {

        void onPagingChange(PagingEventType type, int oldValue, int newValue);
    }

    /**
     * Type of property change.
     */
    enum PagingEventType {
        CURRENT_PAGE, PAGE_SIZE, MAX_PAGE
    }

    /**
     * Returns the number of model objects in a single page. A value of &lt;= 0 means that paging is
     * disabled.
     *
     * @return The page size.
     */
    int getPageSize();

    /**
     * Sets the number of model objects in a single page. A value of &lt;= 0 disabled paging.
     *
     * @param pageSize The page size.
     */
    void setPageSize(int pageSize);

    /**
     * Returns the index of the current page. Will always return 0 if paging is disabled.
     *
     * @return The current page.
     */
    int getCurrentPage();

    /**
     * Sets the index of the current page. Has no effect if paging is disabled.
     *
     * @param currentPage Index of the page to become the current page.
     */
    void setCurrentPage(int currentPage);

    /**
     * Returns the size of the model.
     *
     * @return Size of the model.
     */
    int getModelSize();

    /**
     * Returns the index of the last page. Will always return 0 if paging is disabled.
     *
     * @return Index of the last page.
     */
    int getMaxPage();

    /**
     * Register a listener to receive events when a setting changes.
     *
     * @param listener The listener to add.
     * @return True if the listener was registered. False if already registered.
     */
    boolean addEventListener(IPagingListener listener);

    /**
     * Removes all listeners.
     */
    void removeAllListeners();

    /**
     * Removes a single event listener.
     *
     * @param listener The listener to remove.
     * @return True if the listener was removed. False if it was not registered.
     */
    boolean removeEventListener(IPagingListener listener);

    /**
     * Returns true if paging is disabled.
     *
     * @return True if paging is disabled.
     */
    default boolean isDisabled() {
        return getPageSize() <= 0;
    }
    
    /**
     * Returns true if a previous page exists. Will always return false if paging is disabled.
     *
     * @return True if a previous page exists.
     */
    default boolean hasPrevious() {
        return !isDisabled() && getCurrentPage() > 0;
    }

    /**
     * Returns true if a previous page exists. Will always return false if paging is disabled.
     *
     * @return True if a previous page exists.
     */
    default boolean hasNext() {
        return !isDisabled() && getCurrentPage() < getMaxPage();
    }
}
