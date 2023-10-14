/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2023 Fujion Framework
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
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.fujion.common.Logger;
import org.fujion.common.MiscUtil;
import org.fujion.common.Version;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;

import static org.fujion.webjar.WebJarLocator.IMPORT_MAP_FILE;

/**
 * Information describing a single web jar resource.
 */
public class WebJar {

    private static final Logger log = Logger.create(WebJar.class);

    private final Resource resource;

    private final String name;

    private final Version version;

    private final String versionStr;

    private final String absolutePath;

    private ObjectNode importMap;

    public WebJar(Resource resource) {
        try {
            this.resource = resource;
            absolutePath = resource.getURL().toString();
            int i = absolutePath.lastIndexOf("/webjars/") + 9;
            int j = absolutePath.indexOf("/", i);
            this.name = absolutePath.substring(i, j);
            i = absolutePath.indexOf("/", j + 1);
            this.versionStr = absolutePath.substring(j + 1, i);
            this.version = new Version(versionStr);
            parseImportMap();
        } catch (IOException e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * Returns the import map for this web jar.
     *
     * @return The import map with all paths normalized..
     */
    protected ObjectNode getImportMap() {
        return importMap;
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
     * Returns the canonical (parsed) version of this web jar.
     *
     * @return The canonical version of this web jar.
     */
    public Version getCanonicalVersion() {
        return version;
    }

    /**
     * Returns the version of this web jar.
     *
     * @return The version as a string.
     */
    public String getVersion() {
        return versionStr;
    }

    /**
     * Normalizes the named node from import map by converting relative paths to root-based paths.
     *
     * @param name One of: "imports", "paths", "map"
     * @return The root node that was normalized.
     */
    private ObjectNode normalizeNode(String name) {
        ObjectNode node = (ObjectNode) importMap.get(name);

        if (node == null) {
            return null;
        }

        Iterator<Entry<String, JsonNode>> it = node.fields();

        while (it.hasNext()) {
            Entry<String, JsonNode> entry = it.next();
            JsonNode child = entry.getValue();

            if (child.isTextual()) {
                String value = child.asText();

                if (!value.startsWith("//") && !value.contains("webjars/")) {
                    entry.setValue(createPathNode(value));
                }
            }
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

    /**
     * Retrieves and parses the import map if one is present.
     */
    private void parseImportMap() {
        Resource importMapResource = getResource(IMPORT_MAP_FILE);

        if (importMapResource != null) {
            try (InputStream is = importMapResource.getInputStream()) {
                importMap = (ObjectNode) WebJarLocator.parser.readTree(is);
                ObjectNode imports = normalizeNode("imports");
                ObjectNode paths = normalizeNode("paths");
                ObjectNode map = normalizeNode("map");

                if (imports != null) {
                    return;
                } else if (paths != null) {
                    imports = paths;
                    importMap.remove("paths");
                } else if (map != null) {
                    imports = map;
                    importMap.remove("map");
                }

                importMap.removeAll();
                importMap.set("imports", imports);
            } catch (Exception e) {
                log.error("Error processing web jar import map", e);
            }
        }
    }

    /**
     * Return a resource within this web jar.
     *
     * @param path The relative path to the resource.
     * @return The requested resource, or null if one does not exist.
     */
    public Resource getResource(String path) {
        try {
            Resource res = resource.createRelative(path);
            return res.exists() ? res : null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "webjar:" + name + "@" + versionStr;
    }

}
