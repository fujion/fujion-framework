/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Prevents caching of Fujion Server Pages.
 */
class FSPInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.endsWith(".fsp")) {
            applyCacheControl(response);
        }

        return true;
    }

    private void applyCacheControl(HttpServletResponse response) {
        response.setHeader(HttpHeaders.CACHE_CONTROL, CacheControl.noStore().getHeaderValue());
        removeHeader(HttpHeaders.PRAGMA, response);
        removeHeader(HttpHeaders.EXPIRES, response);
    }

    private void removeHeader(String header, HttpServletResponse response) {
        if (response.containsHeader(header)) {
            response.setHeader(header, "");
        }
    }

}
