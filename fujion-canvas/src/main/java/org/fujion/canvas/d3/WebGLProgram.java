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

import java.util.HashSet;
import java.util.Set;

import org.fujion.canvas.BaseCanvasComponent;
import org.fujion.canvas.CanvasResource;

public class WebGLProgram extends CanvasResource {

    private final Set<WebGLShader> shaders = new HashSet<>();

    protected WebGLProgram(BaseCanvasComponent<?, ?> canvas) {
        super(canvas, "initResource", "createProgram");
    }
    
    protected Set<WebGLShader> getShaders() {
        return shaders;
    }
}
