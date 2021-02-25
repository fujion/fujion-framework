/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;

/**
 * Perform programmatic configuration of web application. This is used in conjunction with the
 * traditional web.xml configuration.
 */
public class WebAppConfiguration implements WebApplicationInitializer {
    
    /**
     * Name of the debug mode parameter. See {@link #isDebugEnabled}
     */
    public static final String DEBUG_PARAM = "fujion.debug";
    
    private static boolean debugEnabled;
    
    /**
     * Returns true if debug mode is enabled. When enabled, debug mode can affect various
     * application behaviors such as disabling javascript minification.
     * <p>
     * The debug mode setting is a Boolean parameter, {@value #DEBUG_PARAM}, that may be specified
     * either as a system property (which takes precedence) or as a context parameter. The latter is
     * specified in the <b>web.xml</b> file; for example:
     *
     * <pre>
     * {@code
     * <context-param>
     *  <param-name>fujion.debug</param-name>
     *  <param-value>true</param-value>
     * </context-param>
     * }
     * </pre>
     *
     * @return True if debug mode is enabled.
     */
    public static boolean isDebugEnabled() {
        return debugEnabled;
    }
    
    @Override
    public void onStartup(ServletContext servletContext) {
        String debug = System.getProperty(DEBUG_PARAM);
        debug = debug != null ? debug : servletContext.getInitParameter(DEBUG_PARAM);
        debugEnabled = debug != null && (debug.isEmpty() || BooleanUtils.toBoolean(debug));
        
        servletContext.addFilter("themeFilter", "org.fujion.theme.ThemeServletFilter").addMappingForUrlPatterns(null, false,
                "/*");
    }

}
