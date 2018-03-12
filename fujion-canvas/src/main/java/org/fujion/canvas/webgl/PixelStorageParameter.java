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
 * Types of pixel storage parameters.
 */
public enum PixelStorageParameter implements IEnumWithValue {
    /**
     * Packing of pixel data into memory.
     */
    PACK_ALIGNMENT(3333),
    /**
     * Number of pixels in a row. (WebGL 2 only)
     */
    PACK_ROW_LENGTH(3330),
    /**
     * Number of pixel locations skipped before the first pixel is written into memory. (WebGL 2
     * only)
     */
    PACK_SKIP_PIXELS(3332),
    /**
     * Number of rows of pixel locations skipped before the first pixel is written into memory.
     * (WebGL 2 only)
     */
    PACK_SKIP_ROWS(3331),
    /**
     * Unpacking of pixel data from memory.
     */
    UNPACK_ALIGNMENT(3317),
    /**
     * Default color space conversion or no color space conversion.
     */
    UNPACK_COLORSPACE_CONVERSION_WEBGL(37443),
    /**
     * Flips the source data along its vertical axis if true.
     */
    UNPACK_FLIP_Y_WEBGL(37440),
    /**
     * Image height used for reading pixel data from memory. (WebGL 2 only)
     */
    UNPACK_IMAGE_HEIGHT(32878),
    /**
     * Multiplies the alpha channel into the other color channels.
     */
    UNPACK_PREMULTIPLY_ALPHA_WEBGL(37441),
    /**
     * Number of pixels in a row. (WebGL 2 only)
     */
    UNPACK_ROW_LENGTH(3314),
    /**
     * Number of pixel images skipped before the first pixel is read from memory. (WebGL 2 only)
     */
    UNPACK_SKIP_IMAGES(32877),
    /**
     * Number of pixel images skipped before the first pixel is read from memory. (WebGL 2 only)
     */
    UNPACK_SKIP_PIXELS(3316),
    /**
     * Number of rows of pixel locations skipped before the first pixel is read from memory. (WebGL
     * 2 only)
     */
    UNPACK_SKIP_ROWS(3315);

    private int value;

    PixelStorageParameter(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }

}
