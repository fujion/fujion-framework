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

/**
 * Static thread-related utility methods.
 */
public class ThreadUtil {

    /**
     * Executes a task using the application's thread pool.
     *
     * @param task Task to execute.
     */
    public static void executeApplicationTask(Runnable task) {
        ThreadPoolFactory.getInstance().executeApplicationTask(task);
    }

    /**
     * Executes a task using the session's thread pool.
     *
     * @param task Task to execute.
     */
    public static void executeSessionTask(Runnable task) {
        ExecutionContext.getSession().getThreadPool().execute(task);
    }

    private ThreadUtil() {}
}
