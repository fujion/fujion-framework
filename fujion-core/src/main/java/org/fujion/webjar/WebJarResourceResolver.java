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

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.fujion.core.WebUtil;
import org.fujion.servlet.ETaggedResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.AbstractResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import java.util.List;

/**
 * Inserts web jar version into request path and appends a "js" extension
 * if no recognized extension is provided. For example, converts
 * <p>
 * <code>webjars/{webjar-name}/path/xyz</code>
 * </p>
 * to
 * <p>
 * <code>webjars/{webjar-name}/{webjar-version}/path/xyz.js</code>
 * </p>
 */
public class WebJarResourceResolver extends AbstractResourceResolver {

    public static String getResourcePath(String path) {
        int i = path.indexOf("/");
        String name = path.substring(0, i);
        int j = path.indexOf("/", i + 1);
        String version = j < 0 ? "" : path.substring(i + 1, j);
        WebJar webjar = WebJarLocator.getInstance().getWebJar(name);

        if (webjar == null) {
            return path;
        }

        if (!version.equals(webjar.getVersion())) {
            path = name + "/" + webjar.getVersion() + path.substring(i);
        }

        String ext = FilenameUtils.getExtension(path).toLowerCase();

        if (ext.isEmpty()) {
            path += ".js";
        } else if (!ext.equalsIgnoreCase("js")) {
            i = path.indexOf("/");
            i = i < 0 ? i : path.indexOf("/", i + 1);
            String resource = i < 0 ? "" : path.substring(i + 1);

            if (webjar.getResource(resource) == null && webjar.getResource(resource + ".js") != null) {
                path += ".js";
            }
        }

        return path;
    }
    
    @Override
    protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath,
                                               List<? extends Resource> locations, ResourceResolverChain chain) {
        String newPath = getResourcePath(requestPath);
        Resource resource = chain.resolveResource(request, newPath, locations);

        if (resource != null) {
            String etag = WebUtil.generateETag(newPath) + (WebUtil.isDebugEnabled() ? "@debug@" : "");
            resource = ETaggedResource.create(resource, request, etag);
        }

        return resource;
    }

    @Override
    protected String resolveUrlPathInternal(String resourceUrlPath, List<? extends Resource> locations,
                                            ResourceResolverChain chain) {
        String newPath = getResourcePath(resourceUrlPath);
        return chain.resolveUrlPath(newPath, locations);
    }
}
