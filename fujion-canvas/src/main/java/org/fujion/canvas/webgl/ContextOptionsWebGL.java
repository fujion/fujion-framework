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
package org.fujion.canvas.webgl;

import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;

public class ContextOptionsWebGL extends Options {

    /**
     * Indicates if the canvas contains an alpha buffer.
     */
    @Option
    public Boolean alpha;
    
    /**
     * Indicates that the drawing buffer has a depth buffer of at least 16 bits.
     */
    @Option
    public Boolean depth;
    
    /**
     * Indicates that the drawing buffer has a stencil buffer of at least 8 bits.
     */
    @Option
    public Boolean stencil;
    
    /**
     * Indicates whether or not to perform anti-aliasing.
     */
    @Option
    public Boolean antialias;
    
    /**
     * Indicates that the page compositor will assume the drawing buffer contains colors with
     * pre-multiplied alpha.
     */
    @Option
    public Boolean premultipliedAlpha;
    
    /**
     * If the value is true the buffers will not be cleared and will preserve their values until
     * cleared or overwritten by the author.
     */
    @Option
    public Boolean preserveDrawingBuffer;
    
    /**
     * Indicates if a context will be created if the system performance is low.
     */
    @Option
    public Boolean failIfMajorPerformanceCaveat;
}
