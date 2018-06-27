/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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
package org.fujion.taglib;

import org.fujion.common.Logger;
import org.fujion.common.AbstractRegistry;
import org.fujion.common.MiscUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

/**
 * Registry of all tag libraries, indexed by their uri, discovered on the class path.
 */
public class TagLibraryRegistry extends AbstractRegistry<String, TagLibrary> implements ApplicationContextAware {
    
    private static Logger log = Logger.create(TagLibraryRegistry.class);
    
    private static TagLibraryRegistry instance = new TagLibraryRegistry();
    
    public static TagLibraryRegistry getInstance() {
        return instance;
    }
    
    private TagLibraryRegistry() {
    }
    
    @Override
    protected String getKey(TagLibrary item) {
        return item.getUri();
    }
    
    /**
     * Locates, parses, and registers all type libraries (files with the <code>tld</code> extension)
     * discovered on the class path.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            Resource[] resources = applicationContext.getResources("classpath*:**/*.tld");
            
            for (Resource resource : resources) {
                log.info(() -> "Found tag library at " + resource);
                
                try {
                    register(TagLibraryParser.getInstance().parse(resource));
                } catch (Exception e) {
                    log.error("Error parsing tag library", e);
                }
            }
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
    
}
