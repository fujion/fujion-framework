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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.fujion.common.MiscUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Subclass of DispatcherServlet. Provides the following modifications:
 * <p>
 * <ul>
 * <li>Prevents re-initialization if an exception occurred during initial loading. It does this by
 * caching the exception and re-throwing it if initialization is re-attempted.</li>
 * <li>Manages ETags and cache control.</li>
 * </ul>
 */
public class FujionServlet extends DispatcherServlet {
    
    private static final long serialVersionUID = 1L;

    private static final String ATTR_EXCEPTION = FujionServlet.class.getName() + ".EXCEPTION";

    /**
     * Request wrapper to alter return value for If-Modified-Since header. This is necessary to make
     * ETags work properly.
     */
    private static class RequestWrapper extends HttpServletRequestWrapper {
        
        public RequestWrapper(HttpServletRequest request) {
            super(request);
        }
        
        @Override
        public long getDateHeader(String name) {
            return HttpHeaders.IF_MODIFIED_SINCE.equalsIgnoreCase(name) ? 0 : super.getDateHeader(name);
        }
        
    }
    
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
    
    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String requestEtag = request.getHeader(HttpHeaders.IF_NONE_MATCH);
        String responseEtag = response.getHeader(HttpHeaders.ETAG);
        int status = response.getStatus();
        
        if (responseEtag == null || status < 200 || status > 299) {
            super.doService(request, response);
        } else if (requestEtag != null && (responseEtag.equals(requestEtag) || "*".equals(requestEtag))) {
            response.setStatus(HttpStatus.SC_NOT_MODIFIED);
        } else {
            response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
            super.doService(new RequestWrapper(request), response);
        }
    }
    
}
