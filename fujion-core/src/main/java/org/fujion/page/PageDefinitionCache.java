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
package org.fujion.page;

import org.apache.commons.io.FilenameUtils;
import org.fujion.common.AbstractCache;
import org.fujion.common.Logger;
import org.fujion.common.StrUtil;
import org.fujion.core.WebUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A cache of all compiled page definitions. If a requested page is not in the cache, it will be
 * automatically compiled and added to the cache.
 */
public class PageDefinitionCache extends AbstractCache<String, PageDefinition> implements ServletContextAware, ApplicationListener<ContextRefreshedEvent> {

    private static final PageDefinitionCache instance = new PageDefinitionCache();

    private static final Logger log = Logger.create(PageDefinitionCache.class);

    private Set<String> precompiled = new LinkedHashSet<>();

    private ServletContext servletContext;

    public static PageDefinitionCache getInstance() {
        return instance;
    }

    private PageDefinitionCache() {
    }

    private String normalizeKey(String key) {
        try {
            return WebUtil.getResource(key, servletContext).getURL().toString();
        } catch (IOException e) {
            return null;
        }
    }

    public void setPrecompiled(String fsps) {
        setPrecompiled(StrUtil.toList(fsps, ","));
    }

    public void setPrecompiled(Collection<String> fsps) {
        precompiled.addAll(fsps);
    }

    @Override
    public PageDefinition get(String key) {
        String nkey = normalizeKey(key);
        return nkey == null ? fetch(key) : super.get(nkey);
    }

    @Override
    public boolean isCached(String key) {
        key = normalizeKey(key);
        return key != null && super.isCached(key);
    }

    @Override
    protected PageDefinition fetch(String url) {
        return PageParser.getInstance().parse(url);
    }

    /**
     * Process FSPs marked for pre-compilation.
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (precompiled != null) {
            Set<String> fsps = precompiled;
            precompiled = null;

            for (String fsp : fsps) {
                String url = fsp.trim();

                if (url.isEmpty()) {
                    continue;
                }

                if (!"fsp".equals(FilenameUtils.getExtension(url))) {
                    url += ".fsp";
                }

                try {
                    log.info(() -> "Precompiling " + fsp);
                    get(url);
                } catch (Exception e) {
                    log.warn(() -> "Error precompiling " + fsp, e);
                }
            }
        }

    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

}
