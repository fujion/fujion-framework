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
package org.fujion.canvas.d2;

/**
 * Specifies the location of the text baseline.
 */
public enum TextBaseline {
    /**
     * The text baseline is the normal alphabetic baseline.
     */
    ALPHABETIC,
    /**
     * The text baseline is the bottom of the bounding box. This differs from the ideographic
     * baseline in that the ideographic baseline doesn't consider descenders.
     */
    BOTTOM,
    /**
     * The text baseline is the hanging baseline. (Used by Tibetan and other Indic scripts.)
     */
    HANGING,
    /**
     * The text baseline is the ideographic baseline; this is the bottom of the body of the
     * characters, if the main body of characters protrudes beneath the alphabetic baseline. (Used
     * by Chinese, Japanese and Korean scripts.)
     */
    IDEOGRAPHIC,
    /**
     * The text baseline is the middle of the em square.
     */
    MIDDLE,
    /**
     * The text baseline is the top of the em square.
     */
    TOP;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
