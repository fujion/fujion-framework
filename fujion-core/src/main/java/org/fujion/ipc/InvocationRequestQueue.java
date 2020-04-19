/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
package org.fujion.ipc;

import org.apache.commons.lang.reflect.MethodUtils;
import org.fujion.common.Logger;
import org.fujion.common.MiscUtil;
import org.fujion.component.BaseComponent;
import org.fujion.component.Page;
import org.fujion.event.EventUtil;
import org.fujion.event.IEventListener;

/**
 * This class implements a queue that allows one page to execute methods on a target on another page
 * (the owner of the queue) via invocation requests.
 */
public class InvocationRequestQueue {

    private static final Logger log = Logger.create(InvocationRequestQueue.class);

    private final Object target;

    private final Page page;

    private final String name;

    private final String eventName;

    private final InvocationRequest onClose;

    private boolean closed;

    /**
     * Invokes the method on the target as specified by the event.
     */
    private final IEventListener invocationListener = (event) -> invokeRequest((InvocationRequest) event.getData());

    /**
     * Create an invocation request queue for the specified target.
     *
     * @param name Unique name for this queue.
     * @param target Target of invocation requests sent to the queue.
     * @param onClose Invocation request to send to the target upon queue closure (may be null).
     */
    public InvocationRequestQueue(String name, BaseComponent target, InvocationRequest onClose) {
        this(name, target.getPage(), target, onClose);
    }

    /**
     * Create an invocation request queue for the specified page and target.
     *
     * @param name Unique name for this queue.
     * @param page Page instance that owns the queue.
     * @param target Target upon which invocations will operate.
     * @param onClose Invocation request to send to the target upon queue closure (may be null).
     */
    public InvocationRequestQueue(String name, Page page, Object target, InvocationRequest onClose) {
        super();
        this.name = name;
        this.target = target;
        this.page = page;
        this.onClose = onClose;
        eventName = "invoke_" + name;
        InvocationRequestQueueRegistry.getInstance().register(this);
        page.addEventListener(eventName, invocationListener);
    }

    /**
     * Returns the unique name for this queue.
     *
     * @return The queue's unique name.
     */
    public String getName() {
        return name;
    }

    /**
     * Invoke the request on the target.
     *
     * @param request The request to invoke.
     */
    private void invokeRequest(InvocationRequest request) {
        try {
            MethodUtils.invokeMethod(target, request.getMethodName(), request.getArgs(),
                MiscUtil.getParameterTypes(request.getArgs()));
        } catch (Exception e) {
            log.error("Remote invocation error.", e);
        }
    }

    /**
     * Close the invocation queue.
     */
    public void close() {
        if (!closed) {
            closed = true;
            InvocationRequestQueueRegistry.getInstance().unregister(this);
            page.removeEventListener(eventName, invocationListener);

            if (onClose != null) {
                invokeRequest(onClose);
            }
        }
    }

    /**
     * Queue a request.
     *
     * @param methodName Name of method to invoke on the target.
     * @param args Arguments to pass to the invoked method.
     */
    public void sendRequest(String methodName, Object... args) {
        sendRequest(new InvocationRequest(methodName, args));
    }

    /**
     * Queue a request on the owning page's event queue..
     *
     * @param request The request.
     */
    public void sendRequest(InvocationRequest request) {
        EventUtil.post(page, eventName, page, request);
    }

    /**
     * Returns true if this queue is alive.
     *
     * @return True if this queue is alive.
     */
    public boolean isAlive() {
        if (!closed && page.isDead()) {
            close();
        }

        return !closed;
    }

}
