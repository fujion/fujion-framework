/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2023 Fujion Framework
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
package org.fujion.theme;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ThemeResolver;

import java.io.IOException;

/**
 * Performs URL rewrites for theme-based resources.
 */
public class ThemeServletFilter implements Filter {

    private final ThemeResolver themeResolver = ThemeResolvers.getInstance();

    public ThemeServletFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
    ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String themeName = themeResolver.resolveThemeName(httpRequest);
        Theme theme = ThemeRegistry.getInstance().get(themeName);

        if (theme != null) {
            String originalPath = httpRequest.getPathInfo();
            String requestPath = originalPath == null ? null : theme.translatePath(originalPath);

            if (requestPath != null) {
                requestPath = requestPath.isEmpty() ? "empty/" + originalPath : requestPath;
                httpRequest.getRequestDispatcher(requestPath).forward(httpRequest, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
