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

import jakarta.servlet.http.HttpServletRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.fujion.common.StrUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

/**
 * Delegates theme resolution to one or more registered theme resolvers.
 */
public class ThemeResolver {

    private static final String THEME_ATTR = ThemeResolver.class.getName();

    private static final ThemeResolver instance = new ThemeResolver();

    public static ThemeResolver getInstance() {
        return instance;
    }

    private String defaultTheme = "default";

    private ThemeResolver() {
    }

    /**
     * Attempts to retrieve the current theme from the request. Tries the following, in order:
     * <ol>
     * <li>The request object</li>
     * <li>Query parameter from the request ("?theme=xxx")</li>
     * <li>Query parameter from the referer ("?theme=xxx")</li>
     * </ol>
     *
     * @param request The servlet request.
     * @return The theme name, if any.
     */
    public Theme resolveTheme(HttpServletRequest request) {
        String themeName = (String) request.getAttribute(THEME_ATTR);
        themeName = StringUtils.hasText(themeName) ? themeName : request.getParameter("theme");

        if (!StringUtils.hasText(themeName)) {
            String referer = request.getHeader(HttpHeaders.REFERER);
            int i = referer == null ? -1 : referer.indexOf("?");

            if (i > -1) {
                for (NameValuePair nvp : URLEncodedUtils.parse(referer.substring(i + 1), StrUtil.UTF8)) {
                    if ("theme".equals(nvp.getName())) {
                        themeName = nvp.getValue();
                        break;
                    }
                }
            }
        }

        themeName = StringUtils.hasText(themeName) ? themeName : defaultTheme;
        request.setAttribute(THEME_ATTR, themeName);
        return ThemeRegistry.getInstance().get(themeName);
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
