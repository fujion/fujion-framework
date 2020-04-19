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
package org.fujion.servlet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.fujion.common.MiscUtil;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Wrapper for non-file based resource. This allows the server to expose such resources to the
 * client.
 */
class DynamicResource extends AbstractResource {

    private final File file;
    
    private final String description;

    private final long contentLength;

    public DynamicResource(String filename, Resource resource) {
        try {
            description = resource.getDescription();
            contentLength = resource.contentLength();
            file = File.createTempFile("fujion_", "." + FilenameUtils.getExtension(filename));
            file.deleteOnExit();
            FileUtils.copyInputStreamToFile(resource.getInputStream(), file);
        } catch (IOException e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
    
    @Override
    public boolean isFile() {
        return true;
    }

    @Override
    public File getFile() {
        return file;
    }
    
    @Override
    public String getFilename() {
        return file.getName();
    }
    
    @Override
    public URL getURL() throws MalformedURLException {
        return getURI().toURL();
    }

    @Override
    public URI getURI() {
        return file.toURI();
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    @Override
    public long contentLength() {
        return contentLength;
    }
    
    @Override
    public boolean exists() {
        return true;
    }
    
    @Override
    public void finalize() throws Throwable {
        FileUtils.deleteQuietly(file);
        super.finalize();
    }
}
