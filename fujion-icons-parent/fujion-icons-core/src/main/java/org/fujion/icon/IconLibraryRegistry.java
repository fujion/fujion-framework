/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2020 Fujion Framework
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
package org.fujion.icon;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.fujion.common.Logger;
import org.fujion.common.AbstractRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Registry for icon libraries. Will automatically register icon library beans instantiated in the
 * root container.
 */
public class IconLibraryRegistry extends AbstractRegistry<String, IIconLibrary> implements BeanPostProcessor {

    private static final Logger log = Logger.create(IconLibraryRegistry.class);

    private static final IconLibraryRegistry instance = new IconLibraryRegistry();

    private String defaultLibrary;

    private String defaultDimensions;

    public static IconLibraryRegistry init(String defaultLibrary, String defaultDimensions) {
        instance.defaultLibrary = StringUtils.trimToNull(defaultLibrary);
        instance.defaultDimensions = StringUtils.trimToNull(defaultDimensions);
        return instance;
    }

    public static IconLibraryRegistry getInstance() {
        return instance;
    }

    /**
     * Enforce singleton instance.
     */
    private IconLibraryRegistry() {
        super();
    }

    @Override
    public void register(IIconLibrary library) {
        super.register(library);
        
        if (defaultLibrary == null) {
            defaultLibrary = library.getId();
        }
    }

    @Override
    public IIconLibrary get(String library) {
        return super.get(library == null ? defaultLibrary : library);
    }

    @Override
    protected String getKey(IIconLibrary item) {
        return item.getId();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof IIconLibrary) {
            IIconLibrary lib = (IIconLibrary) bean;
            register(lib);
            log.info(() -> "Registered icon library: " + lib.getId());
        }

        return bean;
    }

    /**
     * Returns the default icon library. If none has been explicitly set, the first registered
     * library is the default.
     *
     * @return The default icon library.
     */
    public String getDefaultLibrary() {
        return defaultLibrary;
    }

    /**
     * Returns the paths to matching icon resources given name, dimensions, and library name, any
     * one of which may contain wildcard characters.
     *
     * @param library Library name containing the icon (e.g., "silk"). If null, all libraries are
     *            searched.
     * @param iconName Name of the requested icon (e.g., "help*.png").
     * @param dimensions Dimensions of the requested icon (e.g., "16x*").
     * @return The icon path.
     */
    public List<String> getMatching(String library, String iconName, String dimensions) {
        dimensions = dimensions == null ? defaultDimensions : dimensions;

        List<String> results = null;

        for (IIconLibrary lib : this) {
            if (library == null || IconUtil.matcher.match(library, lib.getId())) {
                List<String> urls = lib.getMatching(iconName, dimensions);

                if (results == null) {
                    results = urls;
                } else {
                    results.addAll(urls);
                }
            }
        }

        return results == null ? Collections.emptyList() : results;
    }
}
