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

import org.fujion.annotation.Component;
import org.fujion.canvas.BaseCanvasComponent;

/**
 * Canvas component for 2D rendering.
 */
@Component(tag = "canvas2d", widgetModule = "fujion-canvas", widgetClass = "Canvas", parentTag = "*", description = "Fujion wrapper for HTML5 canvas element, using 2D rendering.")
public class Canvas2D extends BaseCanvasComponent<RenderingContext2D, ContextOptions2D> {

    public Canvas2D() {
        super(new ContextOptions2D());
    }

    @Override
    protected RenderingContext2D createRenderingContext() {
        return new RenderingContext2D(this);
    }
}
