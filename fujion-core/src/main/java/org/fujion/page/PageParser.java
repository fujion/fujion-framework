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
package org.fujion.page;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.fujion.ancillary.ComponentException;
import org.fujion.ancillary.ComponentRegistry;
import org.fujion.annotation.ComponentDefinition;
import org.fujion.common.RegistryMap;
import org.fujion.common.RegistryMap.DuplicateAction;
import org.fujion.core.WebUtil;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/**
 * Parses a Fujion server page into a page definition.
 */
public class PageParser {
    
    private static final PageParser instance = new PageParser();
    
    private static final String CONTENT_TAG = "#text";
    
    private final RegistryMap<String, PIParserBase> piParsers = new RegistryMap<>(DuplicateAction.ERROR);
    
    private final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    
    public static PageParser getInstance() {
        return instance;
    }
    
    private PageParser() {
        registerPIParser(new PIParserTagLibrary());
        registerPIParser(new PIParserAttribute());
        documentBuilderFactory.setNamespaceAware(true);
    }
    
    /**
     * Parses a Fujion Server Page into a page definition.
     *
     * @param src URL of the FSP.
     * @return The resulting page definition.
     */
    public PageDefinition parse(String src) {
        return parse(WebUtil.getResource(src));
    }
    
    /**
     * Parses a Fujion Server Page into a page definition.
     *
     * @param resource The resource containing the FSP.
     * @return The resulting page definition.
     */
    public PageDefinition parse(Resource resource) {
        String source = resource.getFilename();
        source = source == null ? resource.getDescription() : source;
        
        try {
            return parse(resource.getInputStream(), source);
        } catch (IOException e) {
            throw new ComponentException(e, "Exception reading resource '" + source + "'");
        }
    }
    
    /**
     * Parses a Fujion Server Page into a page definition.
     *
     * @param stream An input stream referencing the FSP.
     * @return The resulting page definition.
     */
    public PageDefinition parse(InputStream stream) {
        return parse(stream, "<unknown>");
    }
    
    /**
     * Parses a Fujion Server Page into a page definition.
     *
     * @param stream An input stream referencing the FSP.
     * @param source Source of the page definition.
     * @return The resulting page definition.
     */
    protected PageDefinition parse(InputStream stream, String source) {
        try {
            Document document = documentBuilderFactory.newDocumentBuilder().parse(stream);
            PageDefinition pageDefinition = new PageDefinition();
            pageDefinition.setSource(source);
            parseNode(document, pageDefinition.getRootElement());
            return pageDefinition;
        } catch (Exception e) {
            throw new ComponentException(e, "Exception parsing resource '" + source + "'");
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }
    
    /**
     * Registers a processing instruction parser.
     *
     * @param piParser A processing instruction parser.
     */
    public void registerPIParser(PIParserBase piParser) {
        piParsers.put(piParser.getTarget(), piParser);
    }
    
    private void parseNode(Node node, PageElement parentElement) {
        ComponentDefinition def;
        ComponentDefinition parentDef = parentElement.getDefinition();
        PageElement childElement;
        
        switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                Element ele = (Element) node;
                String tag = ele.getTagName();
                def = ComponentRegistry.getInstance().get(tag);
                
                if (def == null) {
                    throw new RuntimeException("Unrecognized tag: " + tag);
                }
                
                childElement = new PageElement(def, parentElement);
                NamedNodeMap attributes = ele.getAttributes();
                
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node attr = attributes.item(i);
                    String name = attr.getNodeName();
                    
                    if (!name.startsWith("xml")) {
                        childElement.setAttribute(name, attr.getNodeValue());
                    }
                }
                
                parseChildren(node, childElement);
                childElement.validate();
                break;
            
            case Node.TEXT_NODE:
            case Node.CDATA_SECTION_NODE:
                if (isTextNode(node.getPreviousSibling())) {
                    break;
                }
                
                Text text = (Text) node;
                String value = text.getWholeText();
                
                if (value.trim().isEmpty()) {
                    break;
                }
                
                switch (parentDef.contentHandling()) {
                    case ERROR:
                        throw new RuntimeException("Text content is not allowed for tag " + parentDef.getTag());
                        
                    case IGNORE:
                        break;
                    
                    case AS_ATTRIBUTE:
                        parentElement.setAttribute(CONTENT_TAG, normalizeText(value));
                        break;
                    
                    case AS_CHILD:
                        def = ComponentRegistry.getInstance().get(CONTENT_TAG);
                        childElement = new PageElement(def, parentElement);
                        childElement.setAttribute(CONTENT_TAG, normalizeText(value));
                        break;
                }
                
                break;
            
            case Node.DOCUMENT_NODE:
                parseChildren(node, parentElement);
                break;
            
            case Node.COMMENT_NODE:
                break;
            
            case Node.PROCESSING_INSTRUCTION_NODE:
                ProcessingInstruction pi = (ProcessingInstruction) node;
                PIParserBase piParser = piParsers.get(pi.getTarget());
                
                if (piParser != null) {
                    piParser.parse(pi, parentElement);
                } else {
                    throw new RuntimeException("Unrecognized prosessing instruction: " + pi.getTarget());
                }
                
                break;
            
            default:
                throw new RuntimeException("Unrecognized document content: " + node.getNodeName());
        }
    }
    
    /**
     * Returns true if the node is a text or CDATA node.
     *
     * @param node Node to test.
     * @return True if the node is a text or CDATA node.
     */
    private boolean isTextNode(Node node) {
        return node != null && (node.getNodeType() == Node.TEXT_NODE || node.getNodeType() == Node.CDATA_SECTION_NODE);
    }

    private void parseChildren(Node node, PageElement parentElement) {
        NodeList children = node.getChildNodes();
        int childCount = children.getLength();
        
        for (int i = 0; i < childCount; i++) {
            Node childNode = children.item(i);
            parseNode(childNode, parentElement);
        }
    }
    
    private String normalizeText(String text) {
        int i = text.indexOf('\n');
        
        if (i == -1) {
            return text;
        }
        
        if (text.substring(0, i).trim().isEmpty()) {
            text = text.substring(i + 1);
        }
        
        i = text.lastIndexOf('\n');
        
        if (i >= 0 && text.substring(i).trim().isEmpty()) {
            text = text.substring(0, i);
        }
        
        return text;
    }
    
}
