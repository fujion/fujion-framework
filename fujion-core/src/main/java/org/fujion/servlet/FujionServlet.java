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
package org.fujion.servlet;

import org.fujion.common.MiscUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Subclass DispatcherServlet to prevent re-initialization if an exception occurred during initial
 * loading. It does this by caching the exception and re-throwing it if re-initialization is
 * attempted.
 */
public class FujionServlet extends DispatcherServlet {
    
    private static final long serialVersionUID = 1L;

    private static final String ATTR_EXCEPTION = FujionServlet.class.getName() + ".EXCEPTION";

    @Override
    protected WebApplicationContext initWebApplicationContext() {
        Object exc = getServletContext().getAttribute(ATTR_EXCEPTION);

        try {
            if (exc instanceof Throwable) {
                throw (Throwable) exc;
            }

            return super.initWebApplicationContext();
        } catch (Throwable t) {
            if (exc == null) {
                getServletContext().setAttribute(ATTR_EXCEPTION, t);
            }

            throw MiscUtil.toUnchecked(t);
        }
    }
}
