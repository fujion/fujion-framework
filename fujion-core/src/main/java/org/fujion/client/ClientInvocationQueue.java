/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2023 Fujion Framework
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of a queue for client invocation requests. This queue has the special property
 * that if a client invocation with a matching key already exists, that entry will be replaced by
 * the client invocation being queued while maintaining its original position in the queue. This
 * minimizes the traffic between the server and the client for cases where processing the preceding
 * client invocations would be unnecessary.
 */
public class ClientInvocationQueue {
    
    private final Map<String, ClientInvocation> queue = new LinkedHashMap<>();
    
    public ClientInvocationQueue() {
    }
    
    /**
     * Queue a client invocation request.
     * 
     * @param invocation A client invocation request.
     */
    public void queue(ClientInvocation invocation) {
        synchronized (queue) {
            queue.put(invocation.getKey(), invocation);
        }
    }
    
    /**
     * Queue multiple client invocation requests.
     * 
     * @param invocations The requests to queue.
     */
    public void queue(Iterable<ClientInvocation> invocations) {
        synchronized (queue) {
            for (ClientInvocation invocation : invocations) {
                queue.put(invocation.getKey(), invocation);
            }
        }
    }
    
    /**
     * Flush the queue, returning all dequeued requests.
     * 
     * @return The dequeued requests.
     */
    public List<ClientInvocation> flush() {
        List<ClientInvocation> invocations;
        
        synchronized (queue) {
            invocations = new ArrayList<>(queue.values());
            queue.clear();
        }
        
        return invocations;
    }
    
    /**
     * Clear the queue of all queued requests without processing them.
     */
    public void clear() {
        synchronized (queue) {
            queue.clear();
        }
    }
}
