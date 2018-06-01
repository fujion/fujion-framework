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
package org.fujion.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.fujion.common.Logger;
import org.fujion.ancillary.IResponseCallback;
import org.fujion.component.Page;
import org.fujion.event.CallbackEvent;
import org.fujion.event.ITypedEventListener;

/**
 * A registry for managing pending callbacks. There is timestamp-based eviction logic for removing
 * orphaned entries.
 */
public class CallbackRegistry {
    
    private static final Logger log = Logger.create(CallbackRegistry.class);
    
    private static final long HALF_HOUR = 1000 * 60 * 30;

    private final Map<Integer, IResponseCallback<?>> callbacks = Collections.synchronizedMap(new HashMap<>());
    
    private final Map<Integer, Long> timestamps = Collections.synchronizedSortedMap(new TreeMap<>());
    
    private final AtomicInteger callbackId = new AtomicInteger();
    
    private final long evictionInterval;
    
    private final ITypedEventListener<CallbackEvent> callbackListener = (event) -> {
        invokeCallback(event.getHandle(), event.getData());
    };
    
    /**
     * Creates a callback registry for the specified page using the default eviction interval (30
     * minutes).
     *
     * @param page The owning page.
     */
    public CallbackRegistry(Page page) {
        this(page, HALF_HOUR);
    }
    
    /**
     * Creates a callback registry for the specified page using the specified eviction interval.
     *
     * @param page The owning page.
     * @param evictionInterval The time in milliseconds after which an entry will be evicted.
     */
    public CallbackRegistry(Page page, long evictionInterval) {
        this.evictionInterval = evictionInterval;
        page.addEventListener(CallbackEvent.class, callbackListener);
    }
    
    /**
     * Registers a callback.
     *
     * @param callback Callback to register.
     * @return A unique handle.
     */
    public int registerCallback(IResponseCallback<?> callback) {
        int handle = callbackId.incrementAndGet();
        callbacks.put(handle, callback);
        timestamps.put(handle, System.currentTimeMillis());
        return handle;
    }
    
    /**
     * Unregisters a callback.
     *
     * @param handle The callback's handle.
     * @return The callback that was unregistered (may be null).
     */
    private IResponseCallback<?> unregisterCallback(int handle) {
        timestamps.remove(handle);
        IResponseCallback<?> callback = callbacks.remove(handle);
        evict();
        return callback;
    }

    /**
     * Unregisters and invokes a callback.
     *
     * @param handle The callback's handle.
     * @param response The response to pass to the callback.
     */
    private void invokeCallback(int handle, Object response) {
        @SuppressWarnings("unchecked")
        IResponseCallback<Object> callback = (IResponseCallback<Object>) unregisterCallback(handle);

        if (callback != null) {
            try {
                callback.onComplete(response);
            } catch (Exception e) {
                log.error("Exception on client callback", e);
            }
        }
    }
    
    /**
     * Clears all registered callbacks.
     */
    public void clear() {
        callbacks.clear();
        timestamps.clear();
    }
    
    /**
     * Evict entries older than the specified eviction time.
     */
    private void evict() {
        long threshold = System.currentTimeMillis() - evictionInterval;
        
        synchronized (timestamps) {
            Iterator<Entry<Integer, Long>> iter = timestamps.entrySet().iterator();
            
            while (iter.hasNext()) {
                Entry<Integer, Long> entry = iter.next();
                
                if (entry.getValue() < threshold) {
                    iter.remove();
                    callbacks.remove(entry.getKey());
                    log.warn(() -> "Evicted expired callback #" + entry.getKey());
                } else {
                    break;
                }
            }
        }
    }
}
