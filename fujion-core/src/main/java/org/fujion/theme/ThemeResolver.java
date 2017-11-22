/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.theme.CookieThemeResolver;

/**
 * Subclasses the cookie-based theme resolver by allowing override by query parameter. Theme name is
 * cached in the session object so that it is available to subsequent requests.
 */
public class ThemeResolver extends CookieThemeResolver implements Ordered {
    
    private static final String SESSION_ATTR = ThemeResolver.class.getName();
    
    public ThemeResolver() {
        setDefaultThemeName(ThemeResolvers.DEFAULT_THEME);
    }
    
    @Override
    public String resolveThemeName(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String themeName = request.getParameter("theme");
        themeName = StringUtils.hasText(themeName) ? themeName
                : session == null ? null : (String) session.getAttribute(SESSION_ATTR);
        themeName = StringUtils.hasText(themeName) ? themeName : super.resolveThemeName(request);
        setThemeName(request, themeName);
        return themeName;
    }

    @Override
    public void setThemeName(HttpServletRequest request, HttpServletResponse response, String themeName) {
        setThemeName(request, themeName);
        super.setThemeName(request, response, themeName);
    }

    /**
     * Caches the theme name in the session object.
     *
     * @param request The servlet request.
     * @param themeName The theme name to cache.
     */
    private void setThemeName(HttpServletRequest request, String themeName) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            if (StringUtils.hasText(themeName)) {
                session.setAttribute(SESSION_ATTR, themeName);
            } else {
                session.removeAttribute(SESSION_ATTR);
            }
        }
    }
    
    /**
     * Forces this to be the default theme resolver if no others are registered.
     * 
     * @see org.springframework.core.Ordered#getOrder()
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
