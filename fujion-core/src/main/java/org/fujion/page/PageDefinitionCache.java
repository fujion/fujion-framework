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

import org.fujion.common.AbstractCache;
import org.fujion.common.MiscUtil;
import org.fujion.core.WebUtil;

/**
 * A cache of all compiled page definitions. If a requested page is not in the cache, it will be
 * automatically compiled and added to the cache.
 */
public class PageDefinitionCache extends AbstractCache<String, PageDefinition> {
    
    private static PageDefinitionCache instance = new PageDefinitionCache();
    
    public static PageDefinitionCache getInstance() {
        return instance;
    }
    
    private PageDefinitionCache() {
    }
    
    private String normalizeKey(String key) {
        try {
            return WebUtil.getResource(key).getURL().toString();
        } catch (IOException e) {
            throw MiscUtil.toUnchecked(e);
        }
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
    
}
