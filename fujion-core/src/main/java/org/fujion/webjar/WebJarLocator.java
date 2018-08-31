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
package org.fujion.webjar;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES;
import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

/**
 * Locates all web jars on the class path, parses their configuration data (supports classic, NPM,
 * and Bower formats), and generates the necessary initialization code for SystemJS. Note that the
 * classic format has been extended to handle both RequireJS and SystemJS configuration data.
 */
public class WebJarLocator implements ApplicationContextAware {
    
    private static final Logger log = Logger.create(WebJarLocator.class);
    
    private static final WebJarLocator instance = new WebJarLocator();
    
    private static final String[] OPEN_TAGS = { "<requirejs>", "<systemjs>" };
    
    private static final String[] CLOSE_TAGS = { "</requirejs>", "</systemjs>" };
    
    private ObjectNode config;

    private String webjarInit;
    
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
     * Returns the initialization data for the SystemJS config call.
     *
     * @return Initialization data for the SystemJS config call.
     */
    public String getWebJarInit() {
        return webjarInit;
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
     * Returns an immutable map of registered web jars.
     * 
     * @return An immutable map of registered web jars.
     */
    public Map<String, WebJar> getWebJars() {
        return Collections.unmodifiableMap(webjars);
    }
    
    /**
     * Locate and process all web jars.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        
        try {
            Resource[] resources = applicationContext.getResources("classpath*:/**/META-INF/resources/webjars/?*/?*/");
            ObjectMapper parser = new ObjectMapper().configure(ALLOW_UNQUOTED_FIELD_NAMES, true)
                    .configure(ALLOW_SINGLE_QUOTES, true);
            config = createConfig(parser);
            
            for (Resource resource : resources) {
                try {
                    if (resource.isFile() && !resource.getFile().isDirectory()) {
                        continue;
                    }

                    log.debug(() -> "Parsing configuration data for web jar: " + resource);
                    WebJar webjar = new WebJar(resource);
                    String name = webjar.getName();
                    
                    if (webjars.containsKey(name)) {
                        log.warn("Duplicate webjar was ignored: " + webjar);
                        continue;
                    }
                    
                    webjars.put(name, webjar);
                    boolean success = tryPOMFormat(webjar, parser) || tryBowerFormat(webjar, parser)
                            || tryNPMFormat(webjar, parser);
                    
                    if (success) {
                        JSONUtil.merge(config, webjar.getConfig(), true);
                    } else {
                        log.warn(() -> "No configuration information found for web jar: " + webjar.getName());
                    }
                } catch (Exception e) {
                    log.error(() -> "Error extracting configuration information from web jar: " + resource, e);
                }
            }
            
            doConfigOverrides("classpath*:/META-INF/", parser);
            doConfigOverrides("WEB-INF/", parser);
            
            if (WebUtil.isDebugEnabled()) {
                webjarInit = parser.writerWithDefaultPrettyPrinter().writeValueAsString(config);
            } else {
                webjarInit = config.toString();
            }
        } catch (IOException e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * Creates a configuration with empty map, paths, and packages nodes.
     *
     * @param parser The JSON parser.
     * @return The newly created configuration.
     */
    private ObjectNode createConfig(ObjectMapper parser) {
        ObjectNode config = parser.createObjectNode();
        config.set("map", parser.createObjectNode());
        config.set("paths", parser.createObjectNode());
        config.set("packages", parser.createObjectNode());
        return config;
    }
    
    /**
     * Settings in the final configuration may be overridden in systemjs.config.json files.
     *
     * @param path The path to search for configuration override files.
     * @param parser The JSON parser.
     */
    private void doConfigOverrides(String path, ObjectMapper parser) {
        try {
            Resource[] resources = applicationContext.getResources(path + "systemjs.config.json");
            
            for (Resource resource : resources) {
                try (InputStream is = resource.getInputStream()) {
                    JSONUtil.merge(config, parser.readTree(is), true);
                }
            }

        } catch (FileNotFoundException e) {
            // ignore
        } catch (IOException e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
    
    /**
     * Determine if the configuration is embedded in the pom.xml file and process if so. To do this,
     * we have to locate the pom.xml resource and search it for a "requirejs" or "systemjs" property
     * entry. If this is found, the configuration is extracted, parsed, and merged with the
     * configuration that we are building.
     *
     * @param webjar The web jar.
     * @param parser The JSON parser.
     * @return True if successfully processed.
     */
    private boolean tryPOMFormat(WebJar webjar, ObjectMapper parser) {
        try {
            String pomPath = webjar.getAbsolutePath();
            int i = pomPath.lastIndexOf("/META-INF/") + 10;
            pomPath = pomPath.substring(0, i) + "maven/**/pom.xml";
            Resource[] poms = applicationContext.getResources(pomPath);
            return poms.length > 0 && extractConfig(poms[0], webjar, parser);
        } catch (Exception e) {
            log.error(() -> "Error processing configuration data from " + webjar, e);
            return false;
        }
    }
    
    /**
     * Extracts, parses, and merges the "requirejs" or "systemjs" property value from the pom.xml.
     *
     * @param pomResource The pom.xml resource.
     * @param webjar The web jar.
     * @param parser The JSON parser.
     * @return True if a configuration was found.
     * @throws Exception Unspecified exception.
     */
    private boolean extractConfig(Resource pomResource, WebJar webjar, ObjectMapper parser) throws Exception {
        try (InputStream is = pomResource.getInputStream();) {
            Iterator<String> iter = IOUtils.lineIterator(is, StandardCharsets.UTF_8);
            StringBuilder sb = null;
            int tag = -1;
            
            while (iter.hasNext()) {
                String line = iter.next();
                
                if (sb == null) {
                    for (tag = 0; tag < 2; tag++) {
                        int pos = line.indexOf(OPEN_TAGS[tag]);
                        
                        if (pos >= 0) {
                            sb = new StringBuilder();
                            line = line.substring(pos);
                            break;
                        }
                    }
                    
                    if (sb == null) {
                        continue;
                    }
                }
                
                int pos = line.indexOf(CLOSE_TAGS[tag]);
                
                if (pos >= 0) {
                    sb.append(line.substring(0, pos + CLOSE_TAGS[tag].length()));
                    break;
                }
                
                sb.append(line);
            }
            
            String json = sb == null ? null
                    : StringUtils
                    .trimToNull(XMLUtil.parseXMLFromString(sb.toString()).getDocumentElement().getTextContent());

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
    
    /**
     * Determine if packaged as Bower and process if so.
     *
     * @param webjar The web jar.
     * @param parser The JSON parser.
     * @return True if successfully processed.
     */
    private boolean tryBowerFormat(WebJar webjar, ObjectMapper parser) {
        return extractConfig("bower.json", webjar, parser);
    }
    
    /**
     * Determine if packaged as NPM and process if so.
     *
     * @param webjar The web jar.
     * @param parser The JSON parser.
     * @return True if successfully processed.
     */
    private boolean tryNPMFormat(WebJar webjar, ObjectMapper parser) {
        return extractConfig("package.json", webjar, parser);
    }
    
    /**
     * Attempts to locate and parse a package file.
     *
     * @param packageFile The package file name.
     * @param webjar The web jar.
     * @param parser The JSON parser.
     * @return True if the package file was successfully processed.
     */
    private boolean extractConfig(String packageFile, WebJar webjar, ObjectMapper parser) {
        
        try {
            Resource configResource = webjar.createRelative(packageFile);

            if (configResource.exists()) {
                try (InputStream is = configResource.getInputStream();) {
                    JsonNode cfg = parser.readTree(is);
                    String name = cfg.get("name").asText();
                    String main = getMain(cfg.get("main"));

                    if (main != null) {
                        main = StringUtils.removeStart(main, "./");
                        ObjectNode config = createConfig(parser);
                        webjar.setConfig(config);
                        ObjectNode paths = (ObjectNode) config.get("paths");
                        paths.set(name, new TextNode(main));
                    }
                    
                    return main != null;
                }
            }
        } catch (Exception e) {
            // Ignore
        }
        
        return false;
    }
    
    /**
     * Extract the "main" entry.
     *
     * @param node The node corresponding to the "main" or "files" entry. If this is an array, we
     *            consider only the first element.
     * @return The "main" entry.
     */
    private String getMain(JsonNode node) {
        if (node != null) {
            if (node.isArray()) {
                Iterator<JsonNode> iter = node.elements();
                
                if (iter.hasNext()) {
                    return getMain(iter.next());
                }
            } else {
                return node.asText();
            }
        }
        
        return null;
    }
    
}
