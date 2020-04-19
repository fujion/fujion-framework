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
package org.fujion.canvas;

/**
 * Base context for rendering contexts.
 */
public abstract class RenderingContextBase extends CanvasResource {
    
    private final String type;

    /**
     * Creates a new rendering context for the canvas.
     *
     * @param canvas The canvas.
     * @param type The context type to be created.
     */
    protected RenderingContextBase(BaseCanvasComponent<?, ?> canvas, String type) {
        super(canvas, false, "initContext", type, canvas.getOptions());
        this.type = type;
    }
    
    /**
     * Returns the type of this rendering context.
     *
     * @return The type of rendering context.d
     */
    public String getType() {
        return type;
    }
}
