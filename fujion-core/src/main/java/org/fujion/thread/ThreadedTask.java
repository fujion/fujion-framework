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
package org.fujion.thread;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.time.StopWatch;
import org.fujion.client.ExecutionContext;
import org.fujion.common.Assert;
import org.fujion.common.Logger;
import org.fujion.component.BaseComponent;
import org.fujion.event.Event;
import org.fujion.event.EventUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Used to run long operations in the background. Uses events to notify the requester of completion.
 * <p>
 * <i>Note: reference {@link #getException()} to evaluate any exception the task may
 * have thrown. You may also invoke {@link #rethrow()}. One of these methods should be referenced to
 * ensure that the task successfully completed.</i>
 * </p>
 */
public class ThreadedTask implements Runnable, ICancellable, Future<ThreadedTask> {

    @FunctionalInterface
    public interface TaskExecutor {

        void execute(Map<String, Object> result);

    }

    private static final Logger log = Logger.create(ThreadedTask.class);

    public static final String DEFAULT_EVENT_NAME = "threadComplete";

    private volatile boolean cancelled;

    private volatile boolean done;

    private volatile boolean running;

    private Throwable exception;

    private final StopWatch stopWatch = new StopWatch();

    private final TaskExecutor task;

    private final Event event;

    private final String pid;

    private final Map<String, Object> result = Collections.synchronizedMap(new HashMap<>());

    /**
     * Creates a runnable for executing a task in the background using the default event name for
     * notification of completion.  The task is guaranteed to be executed in the execution context
     * of the requester.
     *
     * @param task The task to be performed in the background thread..
     * @param requester The component requesting the operation.
     */
    public ThreadedTask(TaskExecutor task, BaseComponent requester) {
        this(task, requester, DEFAULT_EVENT_NAME);
    }

    /**
     * Creates a runnable for executing a task in the background using the specified event name for
     * notification of completion.  The task is guaranteed to be executed in the execution context
     * of the requester.
     *
     * @param task The task to be performed in the background thread..
     * @param requester The component requesting the operation.
     * @param eventName Name of the event used to notify requester of completion. When fired, the
     *            data associated with the event will be a reference to this instance and may be
     *            interrogated to determine the outcome of the operation.
     */
    public ThreadedTask(TaskExecutor task, BaseComponent requester, String eventName) {
        this.task = task;
        this.pid = requester.getPage().getId();
        this.event = new Event(eventName, requester, this);
    }

    /**
     * Executes the task in the execution context of the requester.
     */
    public void run() {
        if (cancelled) {
            return;
        }

        Assert.state(!running, "Task is currently running.");
        Assert.state(!done, "Task has already executed.");
        log.debug(() -> "Executing task [task=" + task.getClass().getName() + "]");

        ExecutionContext.invoke(pid, () -> {
            running = true;
            stopWatch.start();

            try {
                task.execute(result);
            } catch (Exception e) {
                this.exception = e;
            }

            stopWatch.stop();
            running = false;
            done = true;

            if (!cancelled) {
                EventUtil.post(event);
            }
        });
    }
    
    /**
     * Returns the execution time of the thread in milliseconds.
     *
     * @return long elapsed
     */
    public long getElapsed() {
        return stopWatch.getTime();
    }
    
    /**
     * Returns the named attribute from the executed task.
     *
     * @param name Name of the attribute.
     * @return Value of the attribute, or null if not found.
     */
    public Object getAttribute(String name) {
        return result.get(name);
    }
    
    /**
     * Returns the exception thrown by the background thread, or null if there was none.
     *
     * @return Throwable
     */
    public Throwable getException() {
        return exception;
    }
    
    /**
     * Throws the saved exception in the current thread. If there is no saved exception, no action
     * is taken.
     *
     * @throws Throwable when exception was thrown via IRunnable task
     */
    public void rethrow() throws Throwable {
        if (exception != null) {
            throw exception;
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        cancelled = true;
        return true;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public ThreadedTask get() {
        return running ? null : this;
    }

    @Override
    public ThreadedTask get(long timeout, TimeUnit unit) {
        throw new NotImplementedException();
    }

}
