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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.fujion.core.WebUtil;
import org.fujion.servlet.ETaggedResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.AbstractResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

/**
 * Inserts web jar version into request path. Converts
 * <p>
 * <code>webjars/{webjar-name}/**</code>
 * </p>
 * to
 * <p>
 * <code>webjars/{webjar-name}/{webjar-version}/**</code>
 * </p>
 */
public class WebJarResourceResolver extends AbstractResourceResolver {
    
    public static String getResourcePath(String path) {
        int i = path.indexOf("/");
        String name = path.substring(0, i);
        int j = path.indexOf("/", i + 1);
        String version = j < 0 ? "" : path.substring(i + 1, j);
        WebJar webjar = WebJarLocator.getInstance().getWebjar(name);

        if (webjar != null && !version.equals(webjar.getVersion())) {
            path = name + "/" + webjar.getVersion() + path.substring(i);
        }

        return path;
    }

    @Override
    protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath,
                                               List<? extends Resource> locations, ResourceResolverChain chain) {
        String newPath = getResourcePath(requestPath);
        Resource resource = chain.resolveResource(request, newPath, locations);
        
        if (resource != null) {
            String etag = WebUtil.generateETag(newPath);
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
