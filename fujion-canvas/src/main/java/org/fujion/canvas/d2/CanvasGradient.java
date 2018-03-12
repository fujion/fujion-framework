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

import org.fujion.canvas.BaseCanvasComponent;
import org.fujion.canvas.CanvasResource;

/**
 * A canvas gradient.
 */
public class CanvasGradient extends CanvasResource {
    
    protected CanvasGradient(BaseCanvasComponent<?, ?> canvas, String factory, Object... args) {
        super(canvas, "initResource", factory, args);
    }

    public void addColorStop(double offset, String color) {
        invoke("addColorStop", offset, color);
    }
}
