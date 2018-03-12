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
package org.fujion.canvas.webgl;

import java.util.Map;

import org.fujion.canvas.CanvasResource;
import org.fujion.canvas.webgl.RenderingContextWebGL.IWebGLInfoCallback;

public class WebGLActiveInfo extends CanvasResource {
    
    private String name;
    
    private int size;
    
    private Object type;
    
    private IWebGLInfoCallback callback;
    
    protected WebGLActiveInfo(String factory, WebGLProgram program, int index, IWebGLInfoCallback callback) {
        super(program.getCanvas(), "initResource", true, factory, program, index);
        this.callback = callback;
    }
    
    @Override
    protected void callback(Object response) {
        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) response;
        name = (String) map.get("name");
        size = (Integer) map.get("size");
        type = map.get("type");
        callback.onComplete(this);
        callback = null;
    }
    
    /**
     * Returns the name of the requested variable.
     *
     * @return The name of the requested variable.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the size of the requested variable.
     *
     * @return The size of the requested variable.
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Returns the type of the requested variable.
     *
     * @return The type of the requested variable.
     */
    public Object getType() {
        return type;
    }

}
