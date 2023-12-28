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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.fujion.common.MiscUtil;
import org.fujion.common.StrUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Resolves current theme from the HTTP request.
 */
public class ThemeResolver {

    public static final String THEME_ATTR = ThemeResolver.class.getName();

    private static final ThemeResolver instance = new ThemeResolver();

    public static ThemeResolver getInstance() {
        return instance;
    }

    private String defaultTheme = "default";

    private ThemeResolver() {
    }

    /**
     * Attempts to retrieve the name of the current theme from the request. The following strategies are tried, in order:
     * <ol>
     * <li>From a request attribute (the cached value)</li>
     * <li>From a query parameter ("theme=xxx")</li>
     * <li>From a query parameter from the referer ("theme=xxx")</li>
     * <li>From a cookie</li>
     * </ol>
     * If none of these strategies succeed, the default theme is used.  The resulting theme name is cached as a
     * request attribute.
     *
     * @param request The servlet request.
     * @return The theme name (never null).
     */
    public String resolveTheme(HttpServletRequest request) {
        String themeName = getThemeName(
                () -> MiscUtil.castTo(request.getAttribute(THEME_ATTR), String.class),
                () -> request.getParameter("theme"),
                () -> themeNameFromReferrer(request),
                () -> themeNameFromCookie(request)
        );
        request.setAttribute(THEME_ATTR, themeName);
        return themeName;
    }

    /**
     * Tries each strategy until one succeeds in finding a theme name.  If all fail, the default theme name is returned.
     *
     * @param strategies The lookup strategies.
     * @return The theme name.
     */
    @SafeVarargs
    private String getThemeName(Supplier<String>... strategies) {
        return Arrays.stream(strategies)
                .map(Supplier::get)
                .filter(StringUtils::hasText)
                .findFirst()
                .orElse(defaultTheme);
    }

    /**
     * Retrieves theme name from referrer.
     *
     * @param request The servlet request.
     * @return The theme name (possibly null).
     */
    private String themeNameFromReferrer(HttpServletRequest request) {
        String referer = request.getHeader(HttpHeaders.REFERER);
        int i = referer == null ? -1 : referer.indexOf("?");
        return i == -1 ? null : URLEncodedUtils.parse(referer.substring(i + 1), StrUtil.UTF8).stream()
                .filter(nvp -> "theme".equals(nvp.getName()))
                .map(NameValuePair::getValue)
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves theme name from a cookie.
     *
     * @param request The servlet request.
     * @return The theme name (possibly null).
     */
    private String themeNameFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, THEME_ATTR);
        return cookie == null ? null : cookie.getValue();
    }

    /**
     * Returns the name of the default theme. This theme will be applied if no theme is specified.
     *
     * @return The name of the default theme
     */
    public String getDefaultTheme() {
        return defaultTheme;
    }

    /**
     * Sets the name of the default theme. This theme will be applied if no theme is specified.
     *
     * @param defaultTheme The name of the default theme
     */
    public void setDefaultTheme(String defaultTheme) {
        this.defaultTheme = defaultTheme;
    }

}
