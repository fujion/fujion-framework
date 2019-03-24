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
package org.fujion.page;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.fujion.common.MiscUtil;
import org.fujion.common.XMLUtil;
import org.fujion.core.WebUtil;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;

/**
 * Represents a source for a FSP document.
 */
public class PageSource {
    
    private final String source;

    private InputStream stream;

    private Document document;
    
    /**
     * The source is a web resource.
     *
     * @param src URL of web resource.
     */
    public PageSource(String src) {
        this(WebUtil.getResource(src));
    }

    /**
     * The source is a web resource.
     *
     * @param src URL of web resource.
     * @param ctx The servlet context.
     */
    public PageSource(String src, ServletContext ctx) {
        this(WebUtil.getResource(src, ctx));
    }

    /**
     * The source is a resource.
     *
     * @param resource A resource.
     */
    public PageSource(Resource resource) {
        try {
            String src = resource.getFilename();
            source = src == null ? resource.getDescription() : src;
            stream = resource.getInputStream();
        } catch (IOException e) {
            throw MiscUtil.toUnchecked(e);
        }
    }

    /**
     * The source is an input stream.
     *
     * @param stream An input stream.
     */
    public PageSource(InputStream stream) {
        this(stream, "<unknown>");
    }

    /**
     * The source is an input stream.
     *
     * @param stream An input stream.
     * @param src Source of input stream.
     */
    public PageSource(InputStream stream, String src) {
        this.stream = stream;
        source = src;
    }
    
    /**
     * Returns the FSP as an XML document.
     *
     * @return The FSP as an XML document.
     */
    public Document getDocument() {
        if (document == null) {
            try (InputStream is = stream) {
                stream = null;
                document = XMLUtil.newDocumentBuilder(true).parse(is);
            } catch (Exception e) {
                throw new ParserException(e, "Exception parsing resource \"%s\"", source);
            }
        }
        
        return document;
    }

    /**
     * Returns the source of the FSP.
     *
     * @return The source of the FSP.
     */
    public String getSource() {
        return source;
    }
}
