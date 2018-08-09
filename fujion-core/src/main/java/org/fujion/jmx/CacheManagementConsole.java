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
package org.fujion.jmx;

import org.fujion.common.StrUtil;
import org.fujion.jmx.JMXConsole.IJMXConsolePlugin;
import org.fujion.page.PageDefinitionCache;

import com.udojava.jmx.wrapper.JMXBean;
import com.udojava.jmx.wrapper.JMXBeanOperation;
import com.udojava.jmx.wrapper.JMXBeanOperation.IMPACT_TYPES;

/**
 * JMX console plugin for cache management.
 */
@JMXBean(resourceBundleName = "org.fujion.common.MessageBundle", descriptionKey = "org.fujion.console.FSPCache.description")
public class CacheManagementConsole implements IJMXConsolePlugin {

    private final PageDefinitionCache cache = PageDefinitionCache.getInstance();

    @Override
    public String getType() {
        return "Cache Manager";
    }

    /**
     * Clears the page definition cache.
     * 
     * @return The result of the operation.
     */
    @JMXBeanOperation(nameKey = "org.fujion.console.FSPCache.clearFSPCache.name", impactType = IMPACT_TYPES.ACTION, descriptionKey = "org.fujion.console.FSPCache.clearFSPCache.description")
    public String clearFSPCache() {
        int size = cache.size();
        cache.clear();
        return StrUtil.getLabel("org.fujion.console.FSPCache.clearFSPCache.result", size);
    }

    /**
     * Refreshes the page definition cache.
     * 
     * @return The result of the operation.
     */
    @JMXBeanOperation(nameKey = "org.fujion.console.FSPCache.refreshFSPCache.name", impactType = IMPACT_TYPES.ACTION, descriptionKey = "org.fujion.console.FSPCache.refreshFSPCache.description")
    public String refreshFSPCache() {
        int size = cache.size();
        cache.refresh();
        return StrUtil.getLabel("org.fujion.console.FSPCache.refreshFSPCache.result", size);
    }
    
}
