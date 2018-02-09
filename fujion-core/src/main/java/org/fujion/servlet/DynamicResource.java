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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.fujion.common.MiscUtil;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;

/**
 * Wrapper for non-file based resource.
 */
class DynamicResource extends AbstractResource {
    
    private final Resource resource;
    
    private final String filename;
    
    private final File file;

    public DynamicResource(String filename, Resource resource) {
        this.filename = filename;
        this.resource = resource;
        this.file = createDummyFile();
    }

    private File createDummyFile() {
        try {
            return File.createTempFile("fujion", filename);
        } catch (IOException e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
    
    @Override
    public File getFile() {
        return file;
    }
    
    @Override
    public String getFilename() {
        return filename;
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
    public long contentLength() throws IOException {
        return resource.contentLength();
    }

    @Override
    protected void finalize() throws Throwable {
        FileUtils.deleteQuietly(file);
        super.finalize();
    }
}
