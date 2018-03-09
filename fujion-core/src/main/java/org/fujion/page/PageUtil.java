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

import java.util.List;
import java.util.Map;

import org.fujion.client.ExecutionContext;
import org.fujion.component.BaseComponent;
import org.fujion.component.Page;
import org.springframework.core.io.ByteArrayResource;

/**
 * Convenience methods for creating pages.
 */
public class PageUtil {
    
    /**
     * Given the URL of its source, retrieves the page definition from the cache. If the page
     * definition is not in the cache, it is retrieved, compiled, and placed in the cache.
     *
     * @param url The URL of the page definition.
     * @return The page definition.
     */
    public static PageDefinition getPageDefinition(String url) {
        return PageDefinitionCache.getInstance().get(url);
    }
    
    /**
     * Creates a Fujion Server Page given the URL of its page definition.
     *
     * @param url URL of the page definition.
     * @param parent Component the will become the parent of any top level components. May be null.
     * @return A list of the top level components that were created.
     */
    public static List<BaseComponent> createPage(String url, BaseComponent parent) {
        return createPage(url, parent, null);
    }
    
    /**
     * Creates a Fujion Server Page given the URL of its page definition.
     *
     * @param url URL of the page definition.
     * @param parent Component the will become the parent of any top level components. May be null.
     * @param args A map of arguments that will be copied into the attribute maps of all top level
     *            components. This may be null.
     * @return A list of the top level components that were created.
     */
    public static List<BaseComponent> createPage(String url, BaseComponent parent, Map<String, Object> args) {
        return createPage(getPageDefinition(url), parent, args);
    }
    
    /**
     * Creates a Fujion Server Page from a page definition.
     *
     * @param def The page definition.
     * @param parent Component the will become the parent of any top level components. May be null.
     * @return A list of the top level components that were created.
     */
    public static List<BaseComponent> createPage(PageDefinition def, BaseComponent parent) {
        return createPage(def, parent, null);
    }
    
    /**
     * Creates a Fujion Server Page from a page definition.
     *
     * @param def The page definition.
     * @param parent Component the will become the parent of any top level components. May be null.
     * @param args A map of arguments that will be copied into the attribute maps of all top level
     *            components. This may be null.
     * @return A list of the top level components that were created.
     */
    public static List<BaseComponent> createPage(PageDefinition def, BaseComponent parent, Map<String, Object> args) {
        return def.materialize(parent, args);
    }
    
    /**
     * Creates a Fujion Server Page directly from XML.
     *
     * @param content String containing FSP markup.
     * @param parent Component the will become the parent of any top level components. May be null.
     * @return A list of the top level components that were created.
     */
    public static List<BaseComponent> createPageFromContent(String content, BaseComponent parent) {
        return createPageFromContent(content, parent, null);
    }
    
    /**
     * Creates a Fujion Server Page directly from XML.
     *
     * @param content String containing FSP markup.
     * @param parent Component the will become the parent of any top level components. May be null.
     * @param args A map of arguments that will be copied into the attribute maps of all top level
     *            components. This may be null.
     * @return A list of the top level components that were created.
     */
    public static List<BaseComponent> createPageFromContent(String content, BaseComponent parent, Map<String, Object> args) {
        ByteArrayResource resource = new ByteArrayResource(content.getBytes(), "<content>");
        return createPage(PageParser.getInstance().parse(resource), parent, args);
    }
    
    /**
     * Returns true if the current execution context belongs to the specified page.
     *
     * @param page Page instance.
     * @return True if the current execution context belongs to the specified page.
     */
    public static boolean inExecutionContext(Page page) {
        return page != null && ExecutionContext.getPage() == page;
    }
    
    private PageUtil() {
    }
}
