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
package org.fujion.canvas.d2;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

/**
 * Configuration options for 2D renderings.
 */
public class ContextOptions2D extends Options {
    
    /**
     * Indicates if the canvas contains an alpha channel. If set to false, the browser now knows
     * that the backdrop is always opaque, which can speed up drawing of transparent content and
     * images.
     */
    @Option
    public Boolean alpha;

}
