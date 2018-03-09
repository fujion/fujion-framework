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

import java.util.Collection;

import org.fujion.common.AbstractRegistry;
import org.fujion.component.Page;
import org.springframework.util.Assert;

/**
 * A registry of all active pages, indexed by their page id.
 */
public class PageRegistry extends AbstractRegistry<String, Page> {
    
    private static final PageRegistry instance = new PageRegistry();
    
    /**
     * Convenience method for registering a page.
     *
     * @param page Page to register.
     */
    public static void registerPage(Page page) {
        instance.register(page);
    }
    
    /**
     * Convenience method for unregistering a page.
     *
     * @param page Page to unregister.
     */
    public static void unregisterPage(Page page) {
        instance.unregister(page);
    }
    
    /**
     * Convenience method for looking up a registered page.
     *
     * @param pid The page id.
     * @return The page with the specified id.
     * @exception IllegalArgumentException Thrown if the page cannot be found.
     */
    public static Page getPage(String pid) {
        Page page = instance.get(pid);
        Assert.notNull(page, "Page not found: " + pid);
        return page;
    }
    
    /**
     * Returns true if the page specified by its id is registered.
     *
     * @param pid The page id.
     * @return True if the page is registered.
     */
    public static boolean hasPage(String pid) {
        return instance.contains(pid);
    }

    /**
     * Convenience method for returning all registered pages.
     *
     * @return A read-only collection of all registered pages.
     */
    public static Collection<Page> getPages() {
        return instance.getAll();
    }
    
    private PageRegistry() {
    }
    
    @Override
    protected String getKey(Page item) {
        return item.getId();
    }
    
}
