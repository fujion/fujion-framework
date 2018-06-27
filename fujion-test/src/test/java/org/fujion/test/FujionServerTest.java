/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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

import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class FujionServerTest extends MockWebTest {
    
    @Test
    public void testServer() throws Exception {
        InputStream result = server.request("web/fujion/test/test.fsp");
        List<String> data = IOUtils.readLines(result, "UTF-8");
        assertTrue(data.get(0).equals("<head>"));
        assertTrue(data.get(data.size() - 1).equals("</body>"));
    }
}
