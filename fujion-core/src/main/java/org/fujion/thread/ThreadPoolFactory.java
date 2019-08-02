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

import org.springframework.beans.factory.annotation.Value;

/**
 * Manages threads on a per session basis.
 */
public class ThreadPoolFactory {

    private static ThreadPoolFactory instance = new ThreadPoolFactory();

    @Value("${org.fujion.thread.sessionMinPoolSize:0}")
    private int sessionMinPoolSize;

    @Value("${org.fujion.thread.sessionMaxPoolSize:5}")
    private int sessionMaxPoolSize;

    @Value("${org.fujion.thread.sessionKeepAlive:3}")
    private long sessionKeepalive;

    @Value("${org.fujion.thread.appMinPoolSize:8}")
    private int appMinPoolSize;

    @Value("${org.fujion.thread.appMaxPoolSize:20}")
    private int appMaxPoolSize;

    @Value("${org.fujion.thread.appKeepAlive:60}")
    private long appKeepalive;

    public static ThreadPoolFactory getInstance() {
        return instance;
    }

    private ThreadPool appThreadPool;

    private ThreadPoolFactory() {
    }

    /**
     * Creates the application's thread pool.
     */
    private void init() {
        appThreadPool = new ThreadPool(appMinPoolSize, appMaxPoolSize, appKeepalive);
    }

    /**
     * Shutdown any background tasks.
     */
    private void destroy() {
        appThreadPool.shutdown();
    }

    /**
     * Creates a session thread pool.
     *
     * @return A new thread pool.
     */
    public ThreadPool createSessionThreadPool() {
        return new ThreadPool(sessionMinPoolSize, sessionMaxPoolSize, sessionKeepalive);
    }

    /**
     * Returns the application's thread pool.
     *
     * @return The application thread pool.
     */
    public ThreadPool getApplicationThreadPool() {
        return appThreadPool;
    }
}
