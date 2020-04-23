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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.fujion.common.MiscUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Information describing a single web jar resource.
 */
public class WebJar {

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
     * Returns the configuration for this web jar.
     *
     * @return The normalized configuration.
     */
    protected ObjectNode getConfig() {
        return config;
    }

    /**
     * Sets and normalizes the configuration for this web jar.
     *
     * @param config The incoming configuration data.
     */
    protected void setConfig(ObjectNode config) {
        this.config = config.objectNode();
        addNormalizedNode("imports", config);
        addNormalizedNode("scopes", config);
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
        return "./webjars/" + name + "/";
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
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * Extracts the named node from the incoming configuration data, normalizes
     * it by converting relative paths to root-based paths, and adds it to
     * this web jar's configuration.
     *
     * @param name One of: "imports", "scopes"
     * @param config The incoming configuration data.
     */
    private void addNormalizedNode(String name, ObjectNode config) {
        ObjectNode paths = (ObjectNode) config.get(name);

        if (paths == null) {
            return;
        }

        this.config.set(name, paths);
        Iterator<Entry<String, JsonNode>> it = paths.fields();

        while (it.hasNext()) {
            Entry<String, JsonNode> entry = it.next();
            JsonNode child = entry.getValue();

            if (child.isTextual()) {
                String value = child.asText();

                if (!value.startsWith("//") && !value.contains("webjars/")) {
                    entry.setValue(createPathNode(value));
                }
            } else {
                it.remove();
            }
        }
    }

    /**
     * Returns the named node for the configuration, creating one if it does not exist.
     *
     * @param name The node name.
     * @return The named node.
     */
    private ObjectNode getOrCreateNode(String name, ObjectNode config) {
        ObjectNode node = (ObjectNode) config.get(name);

        if (node == null) {
            config.set(name, node = config.objectNode());
        }

        return node;
    }

    /**
     * Creates an absolute path node.
     *
     * @param path A relative path.
     * @return The new path node containing an absolute path.
     */
    private TextNode createPathNode(String path) {
        return new TextNode(getRootPath() + path);
    }

    @Override
    public String toString() {
        return "webjar:" + name + ":" + version;
    }

}
