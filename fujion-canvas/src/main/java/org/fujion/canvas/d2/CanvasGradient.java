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
package org.fujion.canvas.d2;

import org.fujion.canvas.BaseCanvasComponent;
import org.fujion.canvas.CanvasResource;

/**
 * A canvas gradient.
 */
public class CanvasGradient extends CanvasResource {

    protected CanvasGradient(BaseCanvasComponent<?, ?> canvas, String factory, Object... args) {
        super(canvas, "initResource", factory, args);
    }
    
    /**
     * Adds a new stop, defined by an offset and a color, to the gradient. If the offset is not
     * between 0 and 1 an INDEX_SIZE_ERR is raised, if the color can't be parsed as a CSS color, a
     * SYNTAX_ERR is raised.
     *
     * @param offset Offset to begin color.
     * @param color A color.
     */
    public void addColorStop(double offset, String color) {
        invoke("addColorStop", offset, color);
    }
}
