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

/**
 * Support for model sorting.
 */
public class Sorting {
    
    /**
     * Ordering specifier for sort operation.
     */
    public enum SortOrder {
        /**
         * Ascending collation order.
         */
        ASCENDING,
        /**
         * Descending collation order.
         */
        DESCENDING,
        /**
         * Native order as determined by the model.
         */
        NATIVE,
        /**
         * Indeterminate order.
         */
        UNSORTED}
    
    /**
     * Type of sort toggle.
     */
    public enum SortToggle {
        /**
         * Toggle between {@link SortOrder#ASCENDING ascending} and {@link SortOrder#DESCENDING
         * descending} sort order.
         */
        BINARY,
        /**
         * Toggle between {@link SortOrder#ASCENDING ascending}, {@link SortOrder#DESCENDING
         * descending} and {@link SortOrder#NATIVE native} sort order.
         */
        TRISTATE}
    
    private Sorting() {
    }
}
