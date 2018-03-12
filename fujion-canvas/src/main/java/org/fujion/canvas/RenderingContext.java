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
package org.fujion.canvas;

/**
 * Base context for rendering contexts.
 */
public abstract class RenderingContext extends CanvasResource {
    
    private String type;

    /**
     * Creates a new rendering context for the canvas.
     *
     * @param canvas The canvas.
     * @param types A list of context types. The first one successfully created will be used.
     */
    protected RenderingContext(BaseCanvasComponent<?, ?> canvas, String... types) {
        super(canvas, true, "initContext", types, canvas.getOptions());
    }
    
    /**
     * Callback sets the type of rendering context that was created.
     * 
     * @see org.fujion.canvas.CanvasResource#callback(java.lang.Object)
     */
    @Override
    public void callback(Object type) {
        this.type = (String) type;
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
