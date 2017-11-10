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
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.theme.CookieThemeResolver;

/**
 * Subclasses the cookie-based theme resolver by allowing override by query parameter.
 */
public class ThemeResolver extends CookieThemeResolver {

    public ThemeResolver() {
        setDefaultThemeName("default");
    }

    @Override
    public String resolveThemeName(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String themeName = request.getParameter("theme");
        String cookieName = getCookieName();
        themeName = themeName != null ? themeName : session == null ? null : (String) session.getAttribute(cookieName);
        themeName = themeName != null ? themeName : super.resolveThemeName(request);

        if (session != null) {
            session.setAttribute(cookieName, themeName);
        }
        
        return themeName;
    }
    
}
