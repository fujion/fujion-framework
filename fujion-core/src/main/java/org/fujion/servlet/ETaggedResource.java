/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2019 Fujion Framework
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

import org.fujion.core.WebUtil;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.resource.HttpResource;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * Wraps a resource that has an associated ETag. See {@link #lastModified()} for more information.
 */
public class ETaggedResource implements HttpResource {

    public static class ResourceNotModifiedException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        private final String etag;

        ResourceNotModifiedException(String etag) {
            this.etag = etag;
        }

        public String getETag() {
            return etag;
        }
    }

    private final Resource resource;

    private final HttpHeaders headers;

    private final boolean match;

    public static Resource create(Resource resource, HttpServletRequest request, String responseETag) {
        responseETag = WebUtil.formatETag(responseETag, false);
        boolean match = WebUtil.sameETag(WebUtil.getETag(request), responseETag);
        return new ETaggedResource(resource, responseETag, match);
    }

    private ETaggedResource(Resource resource, String responseETag, boolean match) {
        this.resource = resource;
        this.match = match;
        
        if (resource instanceof HttpResource) {
            headers = ((HttpResource) resource).getResponseHeaders();
        } else {
            headers = new HttpHeaders();
        }
        
        headers.setETag(WebUtil.formatETag(responseETag, false));
    }

    @Override
    public boolean exists() {
        return resource.exists();
    }

    @Override
    public URL getURL() throws IOException {
        return resource.getURL();
    }

    @Override
    public URI getURI() throws IOException {
        return resource.getURI();
    }

    @Override
    public File getFile() throws IOException {
        return resource.getFile();
    }

    @Override
    public long contentLength() throws IOException {
        return resource.contentLength();
    }

    /**
     * If this resource's ETag matched the request's ETag, invoking this method will throw a
     * ResourceNotModifiedException. Otherwise, it will return -1 to suppress setting of the
     * Last-Modified header in the response.
     */
    @Override
    public long lastModified() {
        if (match) {
            throw new ResourceNotModifiedException(headers.getETag());
        }

        return -1;
    }
    
    @Override
    public Resource createRelative(String relativePath) throws IOException {
        return resource.createRelative(relativePath);
    }

    @Override
    public String getFilename() {
        return resource.getFilename();
    }

    @Override
    public String getDescription() {
        return resource.getDescription();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return resource.getInputStream();
    }

    @Override
    public HttpHeaders getResponseHeaders() {
        return headers;
    }

}
