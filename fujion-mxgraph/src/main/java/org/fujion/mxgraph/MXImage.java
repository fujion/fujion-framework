/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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
package org.fujion.mxgraph;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Specifies an image for a cell overlay.
 */
public class MXImage extends Options {

    @Option
    public final String src;

    @Option
    public final int width;

    @Option
    public final int height;

    public MXImage(String src, int width, int height) {
        this.src = src;
        this.width = width;
        this.height = height;
    }
}
