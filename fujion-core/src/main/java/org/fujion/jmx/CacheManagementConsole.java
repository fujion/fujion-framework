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
import org.fujion.expression.ExpressionCache;
import org.fujion.jmx.JMXConsole.IJMXConsolePlugin;
import org.fujion.page.PageDefinitionCache;

import com.udojava.jmx.wrapper.JMXBean;
import com.udojava.jmx.wrapper.JMXBeanAttribute;
import com.udojava.jmx.wrapper.JMXBeanOperation;
import com.udojava.jmx.wrapper.JMXBeanOperation.IMPACT_TYPES;

/**
 * JMX console plugin for cache management.
 */
@JMXBean(resourceBundleName = "org.fujion.common.MessageBundle", descriptionKey = "org.fujion.console.cache.description", sorted = true)
public class CacheManagementConsole implements IJMXConsolePlugin {
    
    private final PageDefinitionCache fspCache = PageDefinitionCache.getInstance();
    
    private final ExpressionCache elCache = ExpressionCache.getInstance();
    
    @Override
    public String getType() {
        return "Cache Manager";
    }
    
    /**
     * Returns the size of the page definition cache.
     *
     * @return The size of the cache.
     */
    @JMXBeanAttribute(sortValue = "10", nameKey = "org.fujion.console.cache.fsp.size.name", descriptionKey = "org.fujion.console.cache.fsp.size.description")
    public String getFSPCacheSize() {
        int size = fspCache.size();
        return formatResult("fsp.size", size);
    }
    
    /**
     * Clears the page definition cache.
     *
     * @return The result of the operation.
     */
    @JMXBeanOperation(sortValue = "11", nameKey = "org.fujion.console.cache.fsp.clear.name", impactType = IMPACT_TYPES.ACTION, descriptionKey = "org.fujion.console.cache.fsp.clear.description")
    public String clearFSPCache() {
        int size = fspCache.size();
        fspCache.clear();
        return formatResult("fsp.clear", size);
    }
    
    /**
     * Refreshes the page definition cache.
     *
     * @return The result of the operation.
     */
    @JMXBeanOperation(sortValue = "12", nameKey = "org.fujion.console.cache.fsp.refresh.name", impactType = IMPACT_TYPES.ACTION, descriptionKey = "org.fujion.console.cache.fsp.refresh.description")
    public String refreshFSPCache() {
        int size = fspCache.size();
        fspCache.refresh();
        return formatResult("fsp.refresh", size);
    }

    /**
     * Returns the size of the expression cache.
     *
     * @return The size of the cache.
     */
    @JMXBeanAttribute(sortValue = "20", nameKey = "org.fujion.console.cache.exp.size.name", descriptionKey = "org.fujion.console.cache.exp.size.description")
    public String getELCacheSize() {
        int size = elCache.size();
        return formatResult("exp.size", size);
    }
    
    /**
     * Refreshes the expression cache.
     *
     * @return The result of the operation.
     */
    @JMXBeanOperation(sortValue = "21", nameKey = "org.fujion.console.cache.exp.clear.name", impactType = IMPACT_TYPES.ACTION, descriptionKey = "org.fujion.console.cache.exp.clear.description")
    public String clearELCache() {
        int size = elCache.size();
        elCache.clear();
        return formatResult("exp.clear", size);
    }

    private String formatResult(String id, Object... args) {
        return StrUtil.getLabel("org.fujion.console.cache." + id + ".result", args);
    }
    
}
