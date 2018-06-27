/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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
 * Implemented by classes that can supply components for insertion into other components. A
 * composite provides a FSP source for insertion, the anchor in the parent where insertion is to
 * take place, and the position of the insertion relative to the anchor.
 */
public interface IComposite {
    
    /**
     * Describes the insertion point of the composite relative to its anchor.
     */
    enum CompositePosition {
        /**
         * Add composite as sibling before anchor.
         */
        BEFORE,
        /**
         * Add composite as sibling after anchor.
         */
        AFTER,
        /**
         * Add composite as first child of anchor.
         */
        FIRST,
        /**
         * Add composite as last child of anchor.
         */
        LAST,
        /**
         * Add composite as new parent of anchor.
         */
        PARENT,
        /**
         * Replace anchor with composite.
         */
        REPLACE
    }
    
    /**
     * Returns the name of the anchor component within the parent namespace. If null, the anchor is
     * assumed to be the parent component itself.
     *
     * @return The name of the anchor component within the parent namespace.
     */
    String getCompositeAnchor();

    /**
     * Returns the insertion point of the composite relative to its anchor.
     *
     * @return The insertion point of the composite relative to its anchor.
     */
    CompositePosition getCompositePosition();

    /**
     * Returns the root component for the composite.
     *
     * @return The root component for the composite.
     */
    BaseComponent getCompositeRoot();

}
