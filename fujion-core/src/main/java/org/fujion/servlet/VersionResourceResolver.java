/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
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
package org.fujion.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.AbstractResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

/**
 * Strips version from resource.
 * <p>
 * <code>/_v_XXX/** &rarr; /**</code>
 * </p>
 */
public class VersionResourceResolver extends AbstractResourceResolver {

    public static final String PREFIX = "_v_";
    
    public boolean root;
    
    /**
     * Create version resource resolver.
     *
     * @param root If true, we are resolving a root-level URL. When false, we need to strip off an
     *            additional path level.
     */
    public VersionResourceResolver(boolean root) {
        this.root = root;
    }
    
    @Override
    protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath,
                                               List<? extends Resource> locations, ResourceResolverChain chain) {
        return chain.resolveResource(request, stripVersion(requestPath), locations);
    }

    @Override
    protected String resolveUrlPathInternal(String resourceUrlPath, List<? extends Resource> locations,
                                            ResourceResolverChain chain) {
        return chain.resolveUrlPath(stripVersion(resourceUrlPath), locations);
    }

    private String stripVersion(String path) {
        if (path.startsWith(PREFIX)) {
            int i = path.indexOf("/");
            i = root || i == -1 ? i : path.indexOf("/", i + 1);
            path = i == -1 ? path : path.substring(i + 1);
        }
        
        return path;
    }
    
}
