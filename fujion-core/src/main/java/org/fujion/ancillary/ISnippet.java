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

/**
 * Implemented by components that act as snippets for insertion into other components.
 */
public interface ISnippet {

    /**
     * Describes the insertion point of the snippet relative to its anchor.
     */
    enum SnippetPosition {
        /**
         * Add snippet as sibling before anchor.
         */
        BEFORE,
        /**
         * Add snippet as sibling after anchor.
         */
        AFTER,
        /**
         * Add snippet as first child of anchor.
         */
        FIRST,
        /**
         * Add snippet as last child of anchor.
         */
        LAST,
        /**
         * Add snippet as new parent of anchor.
         */
        PARENT,
        /**
         * Replace anchor with snippet.
         */
        REPLACE
    }

    /**
     * Returns the name of the anchor component within a template.
     *
     * @return The name of the anchor component within a template.
     */
    String getSnippetAnchor();
    
    /**
     * Returns the insertion point of the snippet relative to its anchor.
     *
     * @return The insertion point of the snippet relative to its anchor.
     */
    SnippetPosition getSnippetPosition();
    
    /**
     * Returns the URL of the source FSP for this snippet.
     *
     * @return The URL of the source FSP for this snippet.
     */
    String getSnippetSource();
    
}
