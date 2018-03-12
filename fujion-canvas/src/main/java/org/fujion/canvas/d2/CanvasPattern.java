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
 * An opaque object describing a pattern, based on an image, a canvas or a video, created by the
 * {@link RenderingContext2D#createPattern} method.
 */
public class CanvasPattern extends CanvasResource {

    protected CanvasPattern(BaseCanvasComponent<?, ?> canvas, Object image, Repetition repetition) {
        super(canvas, "initPattern", image, repetition);
    }
    
}
