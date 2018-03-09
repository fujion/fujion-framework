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
package org.fujion.event;

import java.util.LinkedList;

import org.fujion.client.ExecutionContext;
import org.fujion.component.Page;
import org.fujion.websocket.Session;

/**
 * A page's queue for posted events. Queued (posted) events are delivered at the end of an execution
 * cycle. If an event is queued outside of the target page's execution context, it will be delivered
 * at the end of the next execution cycle for that page. To ensure timely delivery in such an
 * instance, a ping request is sent to the client in order to trigger an execution cycle for the
 * target page.
 */
public class EventQueue {
    
    private final LinkedList<Event> queue = new LinkedList<>();
    
    private final Page page;
    
    /**
     * Create a dedicated event queue for the page.
     * 
     * @param page A page.
     */
    public EventQueue(Page page) {
        this.page = page;
    }
    
    /**
     * Queue an event.
     *
     * @param event Event to queue.
     */
    public synchronized void queue(Event event) {
        if (event.getPage() != page) {
            throw new RuntimeException("Event does not belong to this queue's page");
        }
        
        queue.add(event);
        
        if (queue.size() == 1 && ExecutionContext.getPage() != page) {
            Session session = page.getSession();
            
            if (session != null) {
                session.ping("flush");
            }
        }
    }
    
    /**
     * Process all queued events.
     */
    public synchronized void processAll() {
        while (!queue.isEmpty()) {
            Event event = queue.removeFirst();
            EventUtil.send(event, event.getTarget() == null ? page : event.getTarget());
        }
    }
    
    /**
     * Clear all queued events.
     */
    public synchronized void clearAll() {
        queue.clear();
    }
    
    /**
     * Returns true if the queue is empty.
     *
     * @return True if the queue is empty.
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
