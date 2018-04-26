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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.http.HttpStatus;
import org.fujion.common.MiscUtil;
import org.fujion.core.WebUtil;
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
     * Response wrapper to suppress inclusion of Last-Modified header when ETag header is present.
     */
    private static class ResponseWrapper extends HttpServletResponseWrapper {
        
        public ResponseWrapper(HttpServletResponse response) {
            super(response);
        }
        
        @Override
        public void setDateHeader(String name, long date) {
            if (HttpHeaders.LAST_MODIFIED.equalsIgnoreCase(name)) {
                setHeader(name, null);
            } else {
                super.setDateHeader(name, date);
            }
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
        int status = response.getStatus();
        
        if (status < 200 || status > 299) {
            super.doService(request, response);
            return;
        }
        
        String requestEtag = request.getHeader(HttpHeaders.IF_NONE_MATCH);
        String responseEtag = response.getHeader(HttpHeaders.ETAG);
        
        if (responseEtag == null) {
            responseEtag = WebUtil.addETag(response, WebUtil.DEFAULT_ETAG, true);
        }
        
        if (WebUtil.matchETag(requestEtag, responseEtag)) {
            response.setStatus(HttpStatus.SC_NOT_MODIFIED);
            return;
        }
        
        super.doService(request, new ResponseWrapper(response));
    }
    
}
