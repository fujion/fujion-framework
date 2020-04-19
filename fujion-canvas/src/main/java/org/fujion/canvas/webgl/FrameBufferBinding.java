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
package org.fujion.canvas.webgl;

import org.fujion.ancillary.IEnumWithValue;

/**
 * Possible binding points for a frame buffer.
 */
public enum FrameBufferBinding implements IEnumWithValue {
    /**
     * Equivalent to FRAMEBUFFER. Used as a destination for drawing, rendering, clearing, and
     * writing operations. (WebGL 2 only)
     */
    DRAW_FRAMEBUFFER(36009),
    /**
     * Collection buffer data storage of color, alpha, depth and stencil buffers used to render an
     * image.
     */
    FRAMEBUFFER(36160),
    /**
     * Used as a source for reading operations. (WebGL 2 only)
     */
    READ_FRAMEBUFFER(36008);

    private int value;
    
    FrameBufferBinding(int value) {
        this.value = value;
    }
    
    @Override
    public int value() {
        return value;
    }
}
