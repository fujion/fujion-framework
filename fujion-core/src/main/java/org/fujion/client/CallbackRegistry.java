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

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fujion.ancillary.IResponseCallback;

/**
 * A registry for managing pending callbacks. There is timestamp-based eviction logic for removing
 * orphaned entries.
 */
public class CallbackRegistry {

    private static final Log log = LogFactory.getLog(CallbackRegistry.class);

    private static final CallbackRegistry instance = new CallbackRegistry();

    private final Map<Integer, IResponseCallback<?>> callbacks = Collections.synchronizedMap(new HashMap<>());

    private final Map<Integer, Long> timestamps = Collections.synchronizedSortedMap(new TreeMap<>());

    private final AtomicInteger callbackId = new AtomicInteger();

    private final long evictionTime = 1000 * 60 * 30;

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
        timestamps.put(handle, System.currentTimeMillis());
        return handle;
    }

    private IResponseCallback<?> _unregisterCallback(int handle) {
        timestamps.remove(handle);
        IResponseCallback<?> callback = callbacks.remove(handle);
        evict();
        return callback;
    }
    
    private void _invokeCallback(int handle, Object response) {
        @SuppressWarnings("unchecked")
        IResponseCallback<Object> callback = (IResponseCallback<Object>) _unregisterCallback(handle);
        
        if (callback != null) {
            try {
                callback.onComplete(response);
            } catch (Exception e) {
                log.error("Exception on client callback", e);
            }
        }
    }

    /**
     * Evict entries older than the specified eviction time.
     */
    private void evict() {
        long threshold = System.currentTimeMillis() - evictionTime;

        synchronized (timestamps) {
            Iterator<Entry<Integer, Long>> iter = timestamps.entrySet().iterator();

            while (iter.hasNext()) {
                Entry<Integer, Long> entry = iter.next();

                if (entry.getValue() < threshold) {
                    iter.remove();
                    callbacks.remove(entry.getKey());
                } else {
                    break;
                }
            }
        }
    }
}
