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
package org.fujion.webjar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.fujion.common.JSONUtil;
import org.fujion.common.Logger;
import org.fujion.common.MiscUtil;
import org.fujion.core.WebUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES;
import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES;
import static com.fasterxml.jackson.databind.SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS;

/**
 * Locates all web jars on the class path, merging their import maps into a single import map
 * that will be uploaded to the client.
 */
public class WebJarLocator implements ApplicationContextAware, Iterable<WebJar> {

    private static final Logger log = Logger.create(WebJarLocator.class);

    private static final WebJarLocator instance = new WebJarLocator();

    static ObjectMapper parser = new ObjectMapper()
            .configure(ALLOW_UNQUOTED_FIELD_NAMES, true)
            .configure(ALLOW_SINGLE_QUOTES, true)
            .configure(ORDER_MAP_ENTRIES_BY_KEYS, true);

    private String importMapStr;

    private final Map<String, WebJar> webjars = new HashMap<>();

    /**
     * Returns a singleton instance of the web jar locator.
     *
     * @return Singleton instance of the web jar locator.
     */
    public static WebJarLocator getInstance() {
        return instance;
    }
    
    private WebJarLocator() {
    }
    
    /**
     * Returns the merged import map for SystemJS.
     *
     * @return The merged import map for SystemJS.
     */
    public String getImportMap() {
        return importMapStr;
    }
    
    /**
     * Finds a web jar given its unique name.
     *
     * @param name The web jar name
     * @return The corresponding web jar, or null if not found.
     */
    public WebJar getWebJar(String name) {
        return webjars.get(name);
    }

    /**
     * Locate and process all web jars.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            log.debug(() -> "Searching for web jars in classpath...");
            Resource[] resources = applicationContext.getResources("classpath*:/META-INF/resources/webjars/?*/?*/");
            log.debug(() -> "Found " + resources.length + " web jar(s) in classpath.");
            ObjectNode importMap = parser.createObjectNode();
            importMap.set("imports", parser.createObjectNode());
            importMap.set("scopes", parser.createObjectNode());

            for (Resource resource : resources) {
                try {
                    if (resource.isFile() && !resource.getFile().isDirectory()) {
                        continue;
                    }

                    log.debug(() -> "Discovered web jar: " + resource);
                    WebJar webjar = new WebJar(resource);
                    String name = webjar.getName();

                    if (webjars.containsKey(name)) {
                        log.warn("Duplicate webjar was ignored: " + webjar);
                        continue;
                    }

                    webjars.put(name, webjar);
                    JSONUtil.merge(importMap, webjar.getImportMap(), true);
                } catch (Exception e) {
                    log.error(() -> "Error extracting import map from web jar: " + resource, e);
                }
            }
            
            if (WebUtil.isDebugEnabled()) {
                importMapStr = parser.writerWithDefaultPrettyPrinter().writeValueAsString(importMap);
            } else {
                importMapStr = importMap.toString();
            }
        } catch (IOException e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    @Override
    public Iterator<WebJar> iterator() {
        return webjars.values().iterator();
    }
}
