/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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

import org.fujion.client.ExecutionContext;
import org.fujion.websocket.Session;
import org.springframework.util.Assert;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Static thread-related utility methods.
 */
public class ThreadUtil {

    /**
     * Returns the thread pool for the application.
     *
     * @return The application's thread pool.
     */
    public static ThreadPool getApplicationThreadPool() {
        return ThreadPoolFactory.getInstance().getApplicationThreadPool();
    }

    /**
     * Returns the thread pool for the active session.
     *
     * @return The session's thread pool.
     */
    private static ThreadPool getSessionThreadPool() {
        Session session = ExecutionContext.getSession();
        Assert.notNull(session, "Cannot access thread service outside execution context.");
        return session.getThreadPool();
    }

    /**
     * Executes a task using the session's thread pool.
     *
     * @param task Task to execute.
     * @return Future for monitoring progress of the task.
     */
    public static Future<?> execute(Runnable task) {
        return getSessionThreadPool().submit(task);
    }

    /**
     * Schedule a task to run after the given delay.
     *
     * @param task Task to execute.
     * @param delay Delay before the task is executed.
     * @param unit Time unit for the delay.
     * @return Future for monitoring progress of the task.
     */
    public static ScheduledFuture<?> schedule(Runnable task, long delay, TimeUnit unit) {
        return getSessionThreadPool().schedule(task, delay, unit);
    }

    /**
     * Schedule a task to run after the given delay.
     *
     * @param <V> The result type returned.
     * @param task Task to execute.
     * @param delay Delay before the task is executed.
     * @param unit Time unit for the delay.
     * @return Future for monitoring progress of the task.
     */
    public static <V> ScheduledFuture<V> schedule(Callable<V> task, long delay, TimeUnit unit) {
        return getSessionThreadPool().schedule(task, delay, unit);
    }

    /**
     * Schedule a task to run repeatedly after the an initial delay.
     *
     * @param task Task to execute.
     * @param initialDelay Delay before the task is first executed.
     * @param period Interval between initiation of tasks.
     * @param unit Time unit for initial delay and period.
     * @return Future for monitoring progress of the task.
     */
    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit) {
        return getSessionThreadPool().scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    /**
     * Schedule a task to run repeatedly after an initial delay.
     *
     * @param task Task to execute.
     * @param initialDelay Delay before the task is first executed.
     * @param delay Delay between completion of a task and initiation of its successor.
     * @param unit Time unit for initial delay and delay.
     * @return Future for monitoring progress of the task.
     */
    public static ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, long initialDelay, long delay, TimeUnit unit) {
        return getSessionThreadPool().scheduleWithFixedDelay(task, initialDelay, delay, unit);
    }

    private ThreadUtil() {}
}
