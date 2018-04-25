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
package org.fujion.page;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fujion.common.AbstractCache;
import org.fujion.common.MiscUtil;
import org.fujion.common.StrUtil;
import org.fujion.core.WebUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.ServletContextAware;

/**
 * A cache of all compiled page definitions. If a requested page is not in the cache, it will be
 * automatically compiled and added to the cache.
 */
public class PageDefinitionCache extends AbstractCache<String, PageDefinition> implements ServletContextAware, ApplicationListener<ContextRefreshedEvent> {
    
    private static PageDefinitionCache instance = new PageDefinitionCache();
    
    private static Log log = LogFactory.getLog(PageDefinitionCache.class);
    
    private Set<String> precompiledFSPs = new LinkedHashSet<>();
    
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
            throw MiscUtil.toUnchecked(e);
        }
    }
    
    public void setPrecompiled(String fsps) {
        setPrecompiled(StrUtil.toList(fsps, ","));
    }
    
    public void setPrecompiled(Collection<String> fsps) {
        precompiledFSPs.addAll(fsps);
    }
    
    @Override
    public PageDefinition get(String key) {
        return super.get(normalizeKey(key));
    }
    
    @Override
    public boolean isCached(String key) {
        return super.isCached(normalizeKey(key));
    }
    
    @Override
    protected PageDefinition fetch(String url) {
        try {
            return PageParser.getInstance().parse(url);
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
    
    /**
     * Process FSPs marked for pre-compilation.
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (precompiledFSPs != null) {
            Set<String> fsps = precompiledFSPs;
            precompiledFSPs = null;
            
            for (String url : fsps) {
                url = url.trim();
                
                if (!"fsp".equals(FilenameUtils.getExtension(url))) {
                    url += ".fsp";
                }
                
                try {
                    if (log.isInfoEnabled()) {
                        log.info("Precompiling " + url);
                    }
                    
                    get(url);
                } catch (Exception e) {
                    log.error("Error precompiling " + url, e);
                }
            }
        }
        
    }
    
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    
}
