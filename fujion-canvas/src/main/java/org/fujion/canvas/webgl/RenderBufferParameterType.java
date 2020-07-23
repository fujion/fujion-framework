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
 * Types of render buffer parameters.
 */
public enum RenderBufferParameterType implements IEnumWithValue {
    /**
     * The resolution size (in bits) for the alpha component.
     */
    RENDERBUFFER_ALPHA_SIZE(36179),
    /**
     * The resolution size (in bits) for the blue color.
     */
    RENDERBUFFER_BLUE_SIZE(36178),
    /**
     * The resolution size (in bits) for the depth component.
     */
    RENDERBUFFER_DEPTH_SIZE(36180),
    /**
     * The resolution size (in bits) for the green color.
     */
    RENDERBUFFER_GREEN_SIZE(36177),
    /**
     * The height of the image of the currently bound renderbuffer.
     */
    RENDERBUFFER_HEIGHT(36163),
    /**
     * The internal format of the currently bound renderbuffer. Returns RenderBufferInternalFormat
     * type.
     */
    RENDERBUFFER_INTERNAL_FORMAT(36164),
    /**
     * The resolution size (in bits) for the red color.
     */
    RENDERBUFFER_RED_SIZE(36176),
    /**
     * The number of samples of the image of the currently bound renderbuffer. (WebGL 2 only)
     */
    RENDERBUFFER_SAMPLES(36011),
    /**
     * The resolution size (in bits) for the stencil component.
     */
    RENDERBUFFER_STENCIL_SIZE(36181),
    /**
     * The width of the image of the currently bound renderbuffer.
     */
    RENDERBUFFER_WIDTH(36162);

    private final int value;

    RenderBufferParameterType(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }

}
