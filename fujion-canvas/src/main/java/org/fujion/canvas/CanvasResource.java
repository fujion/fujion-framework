/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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

import org.apache.commons.lang3.ArrayUtils;
import org.fujion.ancillary.IResponseCallback;
import org.fujion.client.IClientTransform;

import java.util.Collections;

/**
 * Base class for canvas-generated resources.
 */
public abstract class CanvasResource implements IClientTransform {

    private final BaseCanvasComponent<?, ?> canvas;
    
    private final int handle;

    protected CanvasResource(BaseCanvasComponent<?, ?> canvas, String factory, Object... args) {
        this(canvas, false, factory, args);
    }
    
    protected CanvasResource(BaseCanvasComponent<?, ?> canvas, boolean requestResponse, String factory, Object... args) {
        this.canvas = canvas;
        handle = canvas.nextResourceId();
        IResponseCallback<?> callback = this::callback;
        initResource(requestResponse ? callback : null, factory, args);
    }
    
    protected void callback(Object response) {
    }

    protected void initResource(IResponseCallback<?> callback, String factory, Object... args) {
        init(callback, factory, args);
    }
    
    protected void init(String function, Object... args) {
        init(null, function, args);
    }
    
    protected void init(IResponseCallback<?> callback, String function, Object... args) {
        args = ArrayUtils.insert(0, args, handle);
        canvas.invoke(function, callback, args);
    }
    
    protected void invoke(String function, Object... args) {
        invoke(null, function, args);
    }
    
    protected void invoke(IResponseCallback<?> callback, String function, Object... args) {
        canvas.invoke("invokeResource", callback, handle, function, args);
    }
    
    protected void config(String property, Object value) {
        config(null, property, value);
    }

    protected void config(IResponseCallback<?> callback, String property, Object value) {
        canvas.invoke("configResource", callback, handle, property, value);
    }

    protected void destroy() {
        canvas.invoke("destroyResource", handle);
    }
    
    @Override
    protected void finalize() throws Throwable {
        destroy();
        super.finalize();
    }
    
    /**
     * Returns the canvas to which this resource belongs.
     *
     * @return The canvas to which this resource belongs.
     */
    public BaseCanvasComponent<?, ?> getCanvas() {
        return canvas;
    }

    /**
     * Special client transform for resources.
     *
     * @see org.fujion.client.IClientTransform#transformForClient()
     */
    @Override
    public Object transformForClient() {
        return Collections.singletonMap("__fujion_res__", handle);
    }
}
