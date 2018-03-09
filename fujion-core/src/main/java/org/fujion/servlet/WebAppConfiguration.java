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

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.web.WebApplicationInitializer;

/**
 * Perform programmatic configuration of web application. This is used in conjunction with the
 * traditional web.xml configuration.
 */
public class WebAppConfiguration implements WebApplicationInitializer {
    
    private static boolean debugEnabled;
    
    /**
     * Returns true if debug mode is enabled.
     *
     * @return True if debug mode is enabled.
     */
    public static boolean isDebugEnabled() {
        return debugEnabled;
    }
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        String debug = System.getProperty("fujion.debug");
        debug = debug != null ? debug : servletContext.getInitParameter("fujion.debug");
        debugEnabled = debug != null && (debug.isEmpty() || BooleanUtils.toBoolean(debug));
        
        servletContext.addFilter("themeFilter", "org.fujion.theme.ThemeServletFilter").addMappingForUrlPatterns(null, false,
            "/*");
    }

}
