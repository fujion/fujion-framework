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
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.fujion.common.JSONUtil;
import org.fujion.common.Logger;
import org.fujion.common.MiscUtil;
import org.fujion.common.XMLUtil;
import org.fujion.core.WebUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

    private static final String OPEN_TAG = "<importmap>";

    private static final String CLOSE_TAG = "</importmap>";

    private ObjectNode config;

    private String importMap;

    private ApplicationContext applicationContext;

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
        return importMap;
    }
    
    /**
     * Returns a copy of the configuration.
     *
     * @return Copy of the configuration.
     */
    public ObjectNode getConfig() {
        return config.deepCopy();
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
        this.applicationContext = applicationContext;
        
        try {
            Resource[] resources = applicationContext.getResources("classpath*:/**/META-INF/resources/webjars/?*/?*/");
            ObjectMapper parser = new ObjectMapper()
                    .configure(ALLOW_UNQUOTED_FIELD_NAMES, true)
                    .configure(ALLOW_SINGLE_QUOTES, true)
                    .configure(ORDER_MAP_ENTRIES_BY_KEYS, true);
            config = createConfig(parser);

            for (Resource resource : resources) {
                try {
                    if (resource.isFile() && !resource.getFile().isDirectory()) {
                        continue;
                    }

                    log.debug(() -> "Extracting configuration data for web jar: " + resource);
                    WebJar webjar = new WebJar(resource);
                    String name = webjar.getName();
                    
                    if (webjars.containsKey(name)) {
                        log.warn("Duplicate webjar was ignored: " + webjar);
                        continue;
                    }
                    
                    webjars.put(name, webjar);
                    boolean success = extractConfig(webjar, parser);
                    
                    if (success) {
                        JSONUtil.merge(config, webjar.getConfig(), true);
                    } else {
                        log.warn(() -> "No import map found for web jar: " + webjar.getName());
                    }
                } catch (Exception e) {
                    log.error(() -> "Error extracting import map from web jar: " + resource, e);
                }
            }
            
            if (WebUtil.isDebugEnabled()) {
                importMap = parser.writerWithDefaultPrettyPrinter().writeValueAsString(config);
            } else {
                importMap = config.toString();
            }
        } catch (IOException e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * Creates a configuration with empty imports and scopes nodes.
     *
     * @param parser The JSON parser.
     * @return The newly created configuration.
     */
    private ObjectNode createConfig(ObjectMapper parser) {
        ObjectNode config = parser.createObjectNode();
        config.set("imports", parser.createObjectNode());
        config.set("scopes", parser.createObjectNode());
        return config;
    }

    /**
     * Locate the pom.xml within the web jar and pass it to the configuration parser.
     *
     * @param webjar The web jar.
     * @param parser The JSON parser.
     * @return True if the configuration data was successfully parsed.
     */
    private boolean extractConfig(
            WebJar webjar,
            ObjectMapper parser) {
        try {
            String pomPath = webjar.getAbsolutePath();
            int i = pomPath.lastIndexOf("/META-INF/") + 10;
            pomPath = pomPath.substring(0, i) + "maven/**/pom.xml";
            Resource[] poms = applicationContext.getResources(pomPath);
            return poms.length > 0 && parseConfig(poms[0], webjar, parser);
        } catch (Exception e) {
            log.error(() -> "Error processing configuration data from " + webjar, e);
            return false;
        }
    }

    /**
     * Scans the pom.xml for a property declaration called "importmap".  If found, the value of the property
     * is parsed and the result is merged into the global import map that we are building.
     *
     * @param pomResource The pom.xml resource.
     * @param webjar      The web jar.
     * @param parser      The JSON parser.
     * @return True if configuration data was found and successfully parsed.
     * @throws Exception Unspecified exception.
     */
    private boolean parseConfig(
            Resource pomResource,
            WebJar webjar,
            ObjectMapper parser) throws Exception {
        try (InputStream is = pomResource.getInputStream()) {
            Iterator<String> iter = IOUtils.lineIterator(is, StandardCharsets.UTF_8);
            StringBuilder sb = null;

            while (iter.hasNext()) {
                String line = iter.next();

                if (sb == null) {
                    int pos = line.indexOf(OPEN_TAG);

                    if (pos >= 0) {
                        sb = new StringBuilder();
                        line = line.substring(pos);
                    } else {
                        continue;
                    }
                }

                int pos = line.indexOf(CLOSE_TAG);

                if (pos >= 0) {
                    sb.append(line, 0, pos + CLOSE_TAG.length());
                    break;
                }

                sb.append(line);
            }

            String json = sb == null ? null :
                    StringUtils.trimToNull(XMLUtil.parseXMLFromString(sb.toString()).getDocumentElement().getTextContent());

            if (json == null) {
                return false;
            }

            if (!json.startsWith("{")) {
                json = "{" + json + "}";
            }

            webjar.setConfig((ObjectNode) parser.readTree(json));
            return true;
        }
    }

    @Override
    public Iterator<WebJar> iterator() {
        return webjars.values().iterator();
    }
}
