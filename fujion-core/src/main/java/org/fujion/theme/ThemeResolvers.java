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
package org.fujion.theme;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.fujion.common.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ThemeResolver;

/**
 * Delegates theme resolution to one or more registered theme resolvers.
 */
public class ThemeResolvers implements BeanPostProcessor, ThemeResolver {
    
    private static final String THEME_ATTR = ThemeResolvers.class.getName();
    
    private static final ThemeResolvers instance = new ThemeResolvers();
    
    private final Set<ThemeResolver> themeResolvers = new TreeSet<>((tr1, tr2) -> {
        return getOrder(tr1) - getOrder(tr2);
    });
    
    private String defaultTheme = "default";
    
    public static ThemeResolvers getInstance() {
        return instance;
    }
    
    private ThemeResolvers() {
    }
    
    private int getOrder(ThemeResolver themeResolver) {
        return themeResolver instanceof Ordered ? ((Ordered) themeResolver).getOrder() : Ordered.LOWEST_PRECEDENCE;
    }
    
    @Override
    public String resolveThemeName(HttpServletRequest request) {
        String themeName = getThemeName(request);
        Iterator<ThemeResolver> iter = themeResolvers.iterator();
        
        while (iter.hasNext() && !StringUtils.hasText(themeName)) {
            themeName = iter.next().resolveThemeName(request);
        }
        
        cacheThemeName(request, themeName);
        return StringUtils.hasText(themeName) ? themeName : defaultTheme;
    }
    
    /**
     * Attempts to retrieve the name of the current theme. Tries the following, in order:
     * <ol>
     * <li>The request object</li>
     * <li>Query parameter from the request ("?theme=xxx")</li>
     * <li>Query parameter from the referer ("?theme=xxx")</li>
     * </ol>
     *
     * @param request The servlet request.
     * @return The cached theme name, if any.
     */
    private String getThemeName(HttpServletRequest request) {
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
        
        return themeName;
    }
    
    /**
     * Caches the theme name for efficiency.
     *
     * @param request The servlet request.
     * @param themeName The theme name to cache.
     */
    private void cacheThemeName(HttpServletRequest request, String themeName) {
        request.setAttribute(THEME_ATTR, themeName);
    }
    
    @Override
    public void setThemeName(HttpServletRequest request, HttpServletResponse response, String themeName) {
        themeName = StringUtils.hasText(themeName) ? themeName : defaultTheme;
        
        for (ThemeResolver themeResolver : themeResolvers) {
            themeResolver.setThemeName(request, response, themeName);
        }
    }
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ThemeResolver) {
            themeResolvers.add((ThemeResolver) bean);
        }
        
        return bean;
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
