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
package org.fujion.canvas.d2;

/**
 * Possible settings for text alignment.
 */
public enum TextAlign {
    /**
     * The text is centered.
     */
    CENTER,
    /**
     * The text is aligned at the normal end of the line (right-aligned for left-to-right locales,
     * left-aligned for right-to-left locales).
     */
    END,
    /**
     * The text is left-aligned.
     */
    LEFT,
    /**
     * The text is right-aligned.
     */
    RIGHT,
    /**
     * The text is aligned at the normal start of the line (left-aligned for left-to-right locales,
     * right-aligned for right-to-left locales).
     */
    START;
    
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
