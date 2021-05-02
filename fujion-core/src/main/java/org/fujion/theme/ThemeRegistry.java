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
package org.fujion.theme;

import org.fujion.common.AbstractRegistry;
import org.fujion.common.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * A registry of all themes discovered during application startup.
 */
public class ThemeRegistry extends AbstractRegistry<String, Theme> implements BeanPostProcessor {

    private static final Logger log = Logger.create(ThemeRegistry.class);

    private static final ThemeRegistry instance = new ThemeRegistry();
    
    /**
     * Returns a singleton instance of the theme registry.
     *
     * @return Singleton instance of the theme registry.
     */
    public static ThemeRegistry getInstance() {
        return instance;
    }
    
    @Override
    protected String getKey(Theme theme) {
        return theme.getName();
    }
    
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Theme) {
            Theme newTheme = (Theme) bean;
            String themeName = newTheme.getName();
            Theme oldTheme = get(themeName);

            if (oldTheme != null) {
                oldTheme.merge(newTheme);
                return oldTheme;
            }
            
            register(newTheme);
            log.info(() -> "Registered theme: " + themeName);
        }

        return bean;
    }
    
}
