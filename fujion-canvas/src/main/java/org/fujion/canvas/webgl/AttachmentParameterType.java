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
package org.fujion.canvas.webgl;

import org.fujion.ancillary.IEnumWithValue;

/**
 * Types of attachment parameters.
 */
public enum AttachmentParameterType implements IEnumWithValue {
    // @formatter:off

    FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE(33301),
    FRAMEBUFFER_ATTACHMENT_BLUE_SIZE(33300),
    FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING(33296),
    FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE(33297),
    FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE(33302),
    FRAMEBUFFER_ATTACHMENT_GREEN_SIZE(33299),
    FRAMEBUFFER_ATTACHMENT_OBJECT_NAME(36049),
    FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE(36048),
    FRAMEBUFFER_ATTACHMENT_RED_SIZE(33298),
    FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE(33303),
    FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE(36051),
    FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER(36052),
    FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL(36050);

    // @formatter:on
    
    private final int value;
    
    AttachmentParameterType(int value) {
        this.value = value;
    }
    
    @Override
    public int value() {
        return value;
    }
    
}
