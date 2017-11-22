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

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ThemeResolver;

/**
 * Delegates theme resolution to one or more registered theme resolvers.
 */
public class ThemeResolvers implements BeanPostProcessor, ThemeResolver {

    public static final String DEFAULT_THEME = "default";

    private static final String THEME_ATTR = ThemeResolvers.class.getName();
    
    private static final ThemeResolvers instance = new ThemeResolvers();
    
    private final Set<ThemeResolver> themeResolvers = new TreeSet<>((tr1, tr2) -> {
        return getOrder(tr1) - getOrder(tr2);
    });
    
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
        return StringUtils.hasText(themeName) ? themeName : DEFAULT_THEME;
    }

    /**
     * Attempts to retrieve the name of the current theme. Tries the following, in order:
     * <ol>
     * <li>The request object</li>
     * <li>A query parameter ("?theme=xxx")</li>
     * <li>The session object</li>
     * </ol>
     *
     * @param request The servlet request.
     * @return The cached theme name, if any.
     */
    private String getThemeName(HttpServletRequest request) {
        String themeName = (String) request.getAttribute(THEME_ATTR);
        themeName = StringUtils.hasText(themeName) ? themeName : request.getParameter("theme");
        
        if (!StringUtils.hasText(themeName)) {
            HttpSession session = request.getSession(false);
            themeName = session == null ? null : (String) session.getAttribute(THEME_ATTR);
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
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            session.setAttribute(THEME_ATTR, themeName);
        }
    }
    
    @Override
    public void setThemeName(HttpServletRequest request, HttpServletResponse response, String themeName) {
        themeName = StringUtils.hasText(themeName) ? themeName : DEFAULT_THEME;
        
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

}
