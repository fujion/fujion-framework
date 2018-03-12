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
package org.fujion.canvas.webgl;

import org.fujion.ancillary.IEnumWithValue;

/**
 * WebGL capabilities.
 */
public enum Capability implements IEnumWithValue {
    
    /**
     * Blending of the computed fragment color values.
     */
    BLEND(3042),
    /**
     * Culling of polygons.
     */
    CULL_FACE(2884),
    /**
     * Depth comparisons and updates to the depth buffer
     */
    DEPTH_TEST(2929),
    /**
     * Dithering of color components before they get written to the color buffer.
     */
    DITHER(3024),
    /**
     * Adding an offset to depth values of polygon's fragments.
     */
    POLYGON_OFFSET_FILL(32823),
    /**
     * Primitives are discarded immediately before the rasterization stage, but after the optional
     * transform feedback stage. (WebGL 2 only)
     */
    RASTERIZER_DISCARD(35977),
    /**
     * The computation of a temporary coverage value determined by the alpha value.
     */
    SAMPLE_ALPHA_TO_COVERAGE(32926),
    /**
     * ANDing the fragment's coverage with the temporary coverage value.
     */
    SAMPLE_COVERAGE(32928),
    /**
     * The scissor test that discards fragments that are outside of the scissor rectangle.
     */
    SCISSOR_TEST(3089),
    /**
     * Stencil testing and updates to the stencil buffer.
     */
    STENCIL_TEST(2960);
    
    private int value;
    
    Capability(int value) {
        this.value = value;
    }
    
    @Override
    public int value() {
        return value;
    }
    
}
