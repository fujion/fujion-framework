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
package org.fujion.canvas.d3;

import org.fujion.annotation.Component;
import org.fujion.canvas.BaseCanvasComponent;

/**
 * Canvas component for 3D rendering.
 */
@Component(tag = "canvas3d", widgetModule = "fujion-canvas", widgetClass = "Canvas", parentTag = "*", description = "Fujion wrapper for HTML5 canvas element using WebGL rendering.")
public class Canvas3D extends BaseCanvasComponent<RenderingContext3D, ContextOptions3D> {
    
    public Canvas3D() {
        super(new ContextOptions3D());
    }
    
    @Override
    protected RenderingContext3D createRenderingContext() {
        return new RenderingContext3D(this);
    }
}