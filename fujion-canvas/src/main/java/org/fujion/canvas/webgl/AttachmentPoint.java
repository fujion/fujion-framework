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
package org.fujion.canvas.webgl;

import org.fujion.ancillary.IEnumWithValue;

/**
 * Buffer attachment points.
 */
public enum AttachmentPoint implements IEnumWithValue {
    // @formatter:off

    COLOR_ATTACHMENT0(36064),
    COLOR_ATTACHMENT1(36065),
    COLOR_ATTACHMENT10(36074),
    COLOR_ATTACHMENT11(36075),
    COLOR_ATTACHMENT12(36076),
    COLOR_ATTACHMENT13(36077),
    COLOR_ATTACHMENT14(36078),
    COLOR_ATTACHMENT15(36079),
    COLOR_ATTACHMENT2(36066),
    COLOR_ATTACHMENT3(36067),
    COLOR_ATTACHMENT4(36068),
    COLOR_ATTACHMENT5(36069),
    COLOR_ATTACHMENT6(36070),
    COLOR_ATTACHMENT7(36071),
    COLOR_ATTACHMENT8(36072),
    COLOR_ATTACHMENT9(36073),
    DEPTH_ATTACHMENT(36096),
    DEPTH_STENCIL_ATTACHMENT(33306),
    STENCIL_ATTACHMENT(36128);

    // @formatter:on

    private int value;

    AttachmentPoint(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }

}
