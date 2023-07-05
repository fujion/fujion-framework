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
package org.fujion.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Base class for tests using embedded Tomcat server. Use this when you need a full web server
 * running for unit tests.
 */
public class MockWebTest {

    private static int initCount;

    protected static MockWebServer server;

    @BeforeClass
    public static void beforeClass() throws Exception {
        if (initCount++ == 0) {
            server = new MockWebServer();
            server.start();
        }
    }

    @AfterClass
    public static void afterClass() throws Exception {
        if (--initCount == 0) {
            server.stop();
            server = null;
        }
    }

}
