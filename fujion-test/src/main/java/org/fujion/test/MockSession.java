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
package org.fujion.test;

import org.fujion.component.Page;
import org.fujion.websocket.Session;
import org.springframework.context.ApplicationContext;

/**
 * A mock session.
 */
public class MockSession extends Session {
    
    public MockSession(ApplicationContext applicationContext, MockServletContext servletContext,
        MockWebSocketSession socket) {
        super(applicationContext, servletContext, socket);
        Page page = Page._create("mockpage");
        _init(page.getId());
    }
    
    @Override
    protected void destroy() {
        super.destroy();
    }
}
