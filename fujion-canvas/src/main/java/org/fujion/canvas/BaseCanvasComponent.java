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

import org.fujion.ancillary.Options;
import org.fujion.component.BaseUIComponent;

/**
 * Base wrapper for HTML5 canvas element.
 *
 * @param <C> Rendering context type.
 * @param <O> Rendering options type.
 */
public abstract class BaseCanvasComponent<C extends RenderingContextBase, O extends Options> extends BaseUIComponent {
    
    private int resourceId;
    
    private C context;
    
    private final O options;

    /**
     * Create canvas with set of available rendering options.
     * 
     * @param options Configuration options for renderer.
     */
    protected BaseCanvasComponent(O options) {
        this.options = options;
    }
    
    /**
     * Returns the current rendering context, creating one if it does not already exist.
     *
     * @return The current rendering context.
     */
    public C getRenderingContext() {
        return context == null ? newRenderingContext() : context;
    }
    
    /**
     * Creates a new rendering context, replacing the current one (if present).
     *
     * @return The newly created rendering context.
     */
    public C newRenderingContext() {
        return context = createRenderingContext();
    }
    
    /**
     * Returns the configuration options for the rendering context.
     *
     * @return The configuration options for the rendering context.
     */
    public O getOptions() {
        return options;
    }

    /**
     * Factory method for creating a new rendering context.
     *
     * @return The new rendering context.
     */
    protected abstract C createRenderingContext();

    /**
     * Returns the next available resource id.
     *
     * @return The next available resource id.
     */
    protected int nextResourceId() {
        return ++resourceId;
    }
}
