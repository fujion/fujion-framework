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

import org.fujion.canvas.BaseCanvasComponent;
import org.fujion.canvas.CanvasResource;

/**
 * Base class for array buffers.
 */
public abstract class ArrayBufferBase extends CanvasResource {
    
    private final PixelDataType type;

    private final int size;
    
    protected ArrayBufferBase(BaseCanvasComponent<?, ?> canvas, PixelDataType type, int size, Object data) {
        super(canvas, "initArrayBuffer", type, data != null ? data : size);
        this.type = type;
        this.size = size;
    }
    
    /**
     * The type of pixel data stored in the array buffer view.
     *
     * @return Pixel data type.
     */
    public PixelDataType getType() {
        return type;
    }

    /**
     * Returns the size of the array.
     *
     * @return The size of the array.
     */
    public int getSize() {
        return size;
    }

}
