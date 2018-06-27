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
package org.fujion.canvas.webgl;

import org.fujion.ancillary.IEnumWithValue;

/**
 * Frame buffer status types.
 */
public enum FrameBufferStatus implements IEnumWithValue {
    
    /**
     * The frame buffer is ready to display.
     */
    FRAMEBUFFER_COMPLETE(36053),
    /**
     * The attachment types are mismatched or not all framebuffer attachment points are framebuffer
     * attachment complete.
     */
    FRAMEBUFFER_INCOMPLETE_ATTACHMENT(36054),
    /**
     * Height and width of the attachment are not the same.
     */
    FRAMEBUFFER_INCOMPLETE_DIMENSIONS(36057),
    /**
     * There is no attachment.
     */
    FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT(36055),
    /**
     * The values of {@link RenderBufferParameterType#RENDERBUFFER_SAMPLES} are different among
     * attached render buffers, or are non-zero if the attached images are a mix of render buffers
     * and textures.
     */
    FRAMEBUFFER_INCOMPLETE_MULTISAMPLE(36182),
    /**
     * The format of the attachment is not supported or if depth and stencil attachments are not the
     * same render buffer.
     */
    FRAMEBUFFER_UNSUPPORTED(36061);
    
    private int value;

    FrameBufferStatus(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }

}
