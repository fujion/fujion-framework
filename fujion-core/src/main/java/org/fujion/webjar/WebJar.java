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
package org.fujion.webjar;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.fujion.common.MiscUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

/**
 * Information describing a single web jar resource.
 */
public class WebJar {
    
    private static final String[] EXTENSIONS = { "", ".js", ".css" };
    
    private final Resource resource;
    
    private final String name;

    private final String version;

    private final String absolutePath;
    
    private ObjectNode config;
    
    public WebJar(Resource resource) {
        try {
            this.resource = resource;
            absolutePath = resource.getURL().toString();
            int i = absolutePath.lastIndexOf("/webjars/") + 9;
            int j = absolutePath.indexOf("/", i);
            name = absolutePath.substring(i, j);
            i = absolutePath.indexOf("/", j + 1);
            version = absolutePath.substring(j + 1, i);
        } catch (IOException e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * Returns the configuration for this webjar, after normalization.
     *
     * @return The normalized configuration.
     */
    protected ObjectNode getConfig() {
        if (config != null) {
            normalizePaths();
            normalizePackages();
        }

        return config;
    }

    /**
     * Sets the configuration for this web jar.
     *
     * @param config The root node of the configuration.
     */
    protected void setConfig(ObjectNode config) {
        this.config = config;
    }
    
    /**
     * Returns the absolute path of this web jar.
     *
     * @return The absolute path.
     */
    public String getAbsolutePath() {
        return absolutePath;
    }
    
    /**
     * Returns the relative root path of this web jar.
     *
     * @return The relative root path.
     */
    public String getRootPath() {
        return "webjars/" + name + "/";
    }

    /**
     * Returns the unique name for this web jar.
     *
     * @return The unique name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the version of this web jar.
     *
     * @return The version.
     */
    public String getVersion() {
        return version;
    }
    
    /**
     * Returns a resource given its relative path.
     *
     * @param relativePath The relative path.
     * @return Resource corresponding to the relative path.
     */
    public Resource createRelative(String relativePath) {
        try {
            return resource.createRelative(relativePath);
        } catch (IOException e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * Finds the first web jar resource that matches one of the specified file extensions.
     *
     * @param resourceLoader The resource loader that will perform the search.
     * @param extensions The file extensions to match.
     * @return The first matching resource encountered, or null if none found.
     */
    public Resource findResource(ResourcePatternResolver resourceLoader, String... extensions) {
        try {
            String path = getRootPath();

            for (String extension : extensions) {
                Resource[] resources = resourceLoader.getResources(path + "**/*." + extension);

                if (resources.length > 0) {
                    return resources[0];
                }
            }
        } catch (Exception e) {}

        return null;
    }
    
    @Override
    public String toString() {
        return "webjar:" + name + ":" + version;
    }
    
    /**
     * Add root path to the map and path entries of the parsed requirejs/systemjs config.
     */
    private void normalizePaths() {
        normalizePaths("paths");
        normalizePaths("map");
    }
    
    /**
     * Add root path to the map and path entries of the parsed requirejs/systemjs config.
     *
     * @param node One of: "paths", "map"
     */
    private void normalizePaths(String node) {
        ObjectNode paths = (ObjectNode) config.get(node);
        
        if (paths != null) {
            Iterator<Entry<String, JsonNode>> iter = paths.fields();
            
            while (iter.hasNext()) {
                Entry<String, JsonNode> entry = iter.next();
                JsonNode child = entry.getValue();
                
                if (child.isTextual()) {
                    String value = child.asText();

                    if (!value.startsWith("//") && !value.contains("webjars/")) {
                        entry.setValue(createPathNode(value));
                    }
                }
            }
        }
    }
    
    /**
     * Fix any package entries found in the config.
     */
    private void normalizePackages() {
        JsonNode packages = config.get("packages");
        
        if (packages != null) {
            if (packages.isArray()) {
                config.remove("packages");
                ObjectNode pkgs = config.objectNode();
                config.set("packages", pkgs);

                for (int i = 0; i < packages.size(); i++) {
                    fixPackage(packages.get(i), pkgs);
                }
            }
        }
    }
    
    /**
     * Fix a package entry, if necessary.
     *
     * @param entry The package entry.
     * @param pkgs The packages node to receive the parsed package.
     */
    private void fixPackage(JsonNode entry, ObjectNode pkgs) {
        String name;
        String main = null;
        ObjectNode pkg = pkgs.objectNode();

        if (entry.isTextual()) {
            name = entry.asText();
            main = "main";
        } else {
            JsonNode mainNode = entry.get("main");
            main = mainNode == null ? null : mainNode.asText();
            JsonNode nameNode = entry.get("name");
            name = nameNode == null ? null : nameNode.asText();
        }
        
        if (name != null) {
            pkg.set("main", new TextNode(main == null ? "main" : main));
            pkg.set("defaultExtension", new TextNode("js"));
            pkgs.set(name, pkg);
            getOrCreateMapNode().set(name, createPathNode(""));
        }
    }
    
    /**
     * Returns the map node for the configuration, creating one if it does not exist.
     *
     * @return The map node.
     */
    private ObjectNode getOrCreateMapNode() {
        ObjectNode map = (ObjectNode) config.get("map");

        if (map == null) {
            config.set("map", map = config.objectNode());
        }

        return map;
    }

    /**
     * Creates a path node.
     *
     * @param file The name of the file referenced in this path node.
     * @return The new path node.
     */
    private TextNode createPathNode(String file) {
        for (String ext : EXTENSIONS) {
            Resource resource = createRelative(file + ext);
            
            if (resource.exists() && resource.isReadable()) {
                file += ext;
                break;
            }
        }
        
        return new TextNode(getRootPath() + file);
    }

}
