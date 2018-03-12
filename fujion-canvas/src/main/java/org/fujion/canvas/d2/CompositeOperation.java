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
package org.fujion.canvas.d2;

/**
 * The type of compositing operation to apply when drawing new shapes.
 */
public enum CompositeOperation {
    /**
     * Displays the source image. The destination image is ignored.
     */
    COPY,
    /**
     * Displays the destination image on top of the source image. The part of the destination image
     * that is outside the source image is not shown.
     */
    DESTINATION_ATOP,
    /**
     * Displays the destination image in to the source image. Only the part of the destination image
     * that is INSIDE the source image is shown, and the source image is transparent.
     */
    DESTINATION_IN,
    /**
     * Displays the destination image out of the source image. Only the part of the destination
     * image that is OUTSIDE the source image is shown, and the source image is transparent.
     */
    DESTINATION_OUT,
    /**
     * Displays the destination image over the source image.
     */
    DESTINATION_OVER,
    /**
     * Displays the source image + the destination image.
     */
    LIGHTER,
    /**
     * Displays the source image on top of the destination image. The part of the source image that
     * is outside the destination image is not shown.
     */
    SOURCE_ATOP,
    /**
     * Displays the source image in to the destination image. Only the part of the source image that
     * is INSIDE the destination image is shown, and the destination image is transparent.
     */
    SOURCE_IN,
    /**
     * Displays the source image out of the destination image. Only the part of the source image
     * that is OUTSIDE the destination image is shown, and the destination image is transparent.
     */
    SOURCE_OUT,
    /**
     * Displays the source image over the destination image.
     */
    SOURCE_OVER,
    /**
     * The source image is combined by using an exclusive OR with the destination image.
     */
    XOR;

    @Override
    public String toString() {
        return name().toLowerCase().replace("_", "-");
    }
}
