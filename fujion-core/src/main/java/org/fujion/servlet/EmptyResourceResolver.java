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
package org.fujion.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.fujion.core.WebUtil;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.resource.AbstractResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

/**
 * Resolves to an empty resource.
 */
public class EmptyResourceResolver extends AbstractResourceResolver {

    /**
     * Simulates an empty file resource.
     */
    private static class EmptyResource implements Resource {

        private final String path;

        EmptyResource(String path) {
            this.path = path;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return StreamUtils.emptyInput();
        }

        @Override
        public boolean exists() {
            return true;
        }

        @Override
        public URL getURL() throws IOException {
            return null;
        }

        @Override
        public URI getURI() throws IOException {
            return null;
        }

        @Override
        public File getFile() throws IOException {
            return new File(path);
        }

        @Override
        public long contentLength() throws IOException {
            return 0;
        }

        @Override
        public long lastModified() throws IOException {
            return -1;
        }

        @Override
        public Resource createRelative(String relativePath) throws IOException {
            return null;
        }

        @Override
        public String getFilename() {
            return path;
        }

        @Override
        public String getDescription() {
            return null;
        }

    }

    private static final String ETAG = WebUtil.generateETag("empty");

    @Override
    protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath,
                                               List<? extends Resource> locations, ResourceResolverChain chain) {
        return ETaggedResource.create(new EmptyResource(requestPath), request, ETAG);
    }

    @Override
    protected String resolveUrlPathInternal(String resourceUrlPath, List<? extends Resource> locations,
                                            ResourceResolverChain chain) {
        return chain.resolveUrlPath(resourceUrlPath, locations);
    }

}
