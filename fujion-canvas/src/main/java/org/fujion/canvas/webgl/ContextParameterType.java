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
 * Types of context parameters.
 */
public enum ContextParameterType implements IEnumWithValue {
    // @formatter:off

    ACTIVE_TEXTURE(34016),
    ALIASED_LINE_WIDTH_RANGE(33902),
    ALIASED_POINT_SIZE_RANGE(33901),
    ALPHA_BITS(3413),
    ARRAY_BUFFER_BINDING(34964),
    BLEND(3042),
    BLEND_COLOR(32773),
    BLEND_DST_ALPHA(32970),
    BLEND_DST_RGB(32968),
    BLEND_EQUATION(32777),
    BLEND_EQUATION_ALPHA(34877),
    BLEND_EQUATION_RGB(32777),
    BLEND_SRC_ALPHA(32971),
    BLEND_SRC_RGB(32969),
    BLUE_BITS(3412),
    COLOR_CLEAR_VALUE(3106),
    COLOR_WRITEMASK(3107),
    COMPRESSED_TEXTURE_FORMATS(34467),
    CULL_FACE(2884),
    CULL_FACE_MODE(2885),
    CURRENT_PROGRAM(35725),
    DEPTH_BITS(3414),
    DEPTH_CLEAR_VALUE(2931),
    DEPTH_FUNC(2932),
    DEPTH_RANGE(2928),
    DEPTH_TEST(2929),
    DEPTH_WRITEMASK(2930),
    DITHER(3024),
    ELEMENT_ARRAY_BUFFER_BINDING(34965),
    FRAMEBUFFER_BINDING(36006),
    FRONT_FACE(2886),
    GENERATE_MIPMAP_HINT(33170),
    GREEN_BITS(3411),
    IMPLEMENTATION_COLOR_READ_FORMAT(35739),
    IMPLEMENTATION_COLOR_READ_TYPE(35738),
    LINE_WIDTH(2849),
    MAX_COMBINED_TEXTURE_IMAGE_UNITS(35661),
    MAX_CUBE_MAP_TEXTURE_SIZE(34076),
    MAX_FRAGMENT_UNIFORM_VECTORS(36349),
    MAX_RENDERBUFFER_SIZE(34024),
    MAX_TEXTURE_IMAGE_UNITS(34930),
    MAX_TEXTURE_SIZE(3379),
    MAX_VARYING_VECTORS(36348),
    MAX_VERTEX_ATTRIBS(34921),
    MAX_VERTEX_TEXTURE_IMAGE_UNITS(35660),
    MAX_VERTEX_UNIFORM_VECTORS(36347),
    MAX_VIEWPORT_DIMS(3386),
    PACK_ALIGNMENT(3333),
    POLYGON_OFFSET_FACTOR(32824),
    POLYGON_OFFSET_FILL(32823),
    POLYGON_OFFSET_UNITS(10752),
    RED_BITS(3410),
    RENDERBUFFER_BINDING(36007),
    RENDERER(7937),
    SAMPLE_BUFFERS(32936),
    SAMPLE_COVERAGE_INVERT(32939),
    SAMPLE_COVERAGE_VALUE(32938),
    SAMPLES(32937),
    SCISSOR_BOX(3088),
    SCISSOR_TEST(3089),
    SHADING_LANGUAGE_VERSION(35724),
    STENCIL_BACK_FAIL(34817),
    STENCIL_BACK_FUNC(34816),
    STENCIL_BACK_PASS_DEPTH_FAIL(34818),
    STENCIL_BACK_PASS_DEPTH_PASS(34819),
    STENCIL_BACK_REF(36003),
    STENCIL_BACK_VALUE_MASK(36004),
    STENCIL_BACK_WRITEMASK(36005),
    STENCIL_BITS(3415),
    STENCIL_CLEAR_VALUE(2961),
    STENCIL_FAIL(2964),
    STENCIL_FUNC(2962),
    STENCIL_PASS_DEPTH_FAIL(2965),
    STENCIL_PASS_DEPTH_PASS(2966),
    STENCIL_REF(2967),
    STENCIL_TEST(2960),
    STENCIL_VALUE_MASK(2963),
    STENCIL_WRITEMASK(2968),
    SUBPIXEL_BITS(3408),
    TEXTURE_BINDING_2D(32873),
    TEXTURE_BINDING_CUBE_MAP(34068),
    UNPACK_ALIGNMENT(3317),
    UNPACK_COLORSPACE_CONVERSION_WEBGL(37443),
    UNPACK_FLIP_Y_WEBGL(37440),
    UNPACK_PREMULTIPLY_ALPHA_WEBGL(37441),
    VENDOR(7936),
    VERSION(7938),
    VIEWPORT(2978);

    // @formatter:on

    private final int value;

    ContextParameterType(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }

}
