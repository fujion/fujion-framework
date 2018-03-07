/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
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
package org.fujion.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.fujion.ancillary.IResponseCallback;

/**
 * A registry for managing pending callbacks.
 */
public class CallbackRegistry {
    
    private static final CallbackRegistry instance = new CallbackRegistry();
    
    private final Map<Integer, IResponseCallback<?>> callbacks = new ConcurrentHashMap<>();
    
    private final AtomicInteger callbackId = new AtomicInteger();
    
    /**
     * Register a client callback.
     *
     * @param callback Callback to register.
     * @return The callback handle.
     */
    public static int registerCallback(IResponseCallback<?> callback) {
        return instance._registerCallback(callback);
    }
    
    /**
     * Unregister a client callback.
     *
     * @param handle The callback handle.
     * @return The unregistered client callback.
     */
    public static IResponseCallback<?> unregisterCallback(int handle) {
        return instance._unregisterCallback(handle);
    }
    
    public static void invokeCallback(int handle, Object response) {
        instance._invokeCallback(handle, response);
    }

    private CallbackRegistry() {
    }
    
    private int _registerCallback(IResponseCallback<?> callback) {
        int handle = callbackId.incrementAndGet();
        callbacks.put(handle, callback);
        return handle;
    }
    
    private IResponseCallback<?> _unregisterCallback(int handle) {
        return callbacks.remove(handle);
    }

    private void _invokeCallback(int handle, Object response) {
        @SuppressWarnings("unchecked")
        IResponseCallback<Object> callback = (IResponseCallback<Object>) _unregisterCallback(handle);

        if (callback != null) {
            try {
                callback.onComplete(response);
            } catch (Exception e) {

            }
        }
    }
}
