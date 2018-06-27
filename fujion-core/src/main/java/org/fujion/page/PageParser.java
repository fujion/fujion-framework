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
package org.fujion.page;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.fujion.common.Logger;
import org.fujion.ancillary.ComponentRegistry;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.ComponentDefinition;
import org.fujion.common.RegistryMap;
import org.fujion.common.RegistryMap.DuplicateAction;
import org.fujion.component.Content;
import org.fujion.core.WebUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/**
 * Parses a Fujion server page into a page definition.
 */
public class PageParser implements BeanPostProcessor {

    private static final Logger log = Logger.create(PageParser.class);

    private static final PageParser instance = new PageParser();

    public static final String CONTENT_ATTR = "#text";

    public static final String NS_FSP = "http://www.fujion.org/schema/fsp";

    public static final String NS_ON = NS_FSP + "/on";

    public static final String NS_ATTR = NS_FSP + "/attr";

    private final Map<String, String> attrNSMap = new HashMap<>();

    private final Map<String, String> tagNSMap = new HashMap<>();

    private final RegistryMap<String, PIParserBase> piParsers = new RegistryMap<>(DuplicateAction.ERROR);

    public static PageParser getInstance() {
        return instance;
    }

    private PageParser() {
        attrNSMap.put(NS_ON, "on");
        attrNSMap.put(NS_ATTR, "attr");
        tagNSMap.put(NS_FSP, "");
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
        return parse(new PageSource(resource));
    }

    /**
     * Parses a Fujion Server Page into a page definition.
     *
     * @param stream An input stream referencing the FSP.
     * @return The resulting page definition.
     */
    public PageDefinition parse(InputStream stream) {
        return parse(new PageSource(stream));
    }

    /**
     * Parses a Fujion Server Page into a page definition.
     *
     * @param source Source of the FSP.
     * @return The resulting page definition.
     */
    public PageDefinition parse(PageSource source) {
        PageDefinition pageDefinition = new PageDefinition();
        pageDefinition.setSource(source.getSource());
        parse(source, pageDefinition.getRootElement());
        return pageDefinition;
    }

    /**
     * Parse the FSP document referenced by an input stream.
     *
     * @param source Source of the FSP.
     * @param parentElement The parent element for the parsing operation.
     */
    protected void parse(PageSource source, PageElement parentElement) {
        parseNode(source.getDocument(), parentElement);
    }
    
    private void parseNode(Node node, PageElement parentElement) {
        ComponentDefinition def;
        PageElement childElement;

        switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                String tag = normalizeNodeName(node, tagNSMap);
                
                if (tag == null) {
                    break;
                }

                if (tag.equals("fsp") && node.getParentNode() instanceof Document) {
                    processAttributes(node, (name, value) -> {
                        badAttribute(tag, name);
                    });
                    parseChildren(node, parentElement);
                    return;
                }
                
                def = ComponentRegistry.getInstance().get(tag);

                if (def == null) {
                    throw new ParserException("Unrecognized tag  '<%s>'", tag);
                }

                childElement = new PageElement(def, parentElement);
                processAttributes(node, (name, value) -> {
                    if (!def.validateAttribute(name)) {
                        badAttribute(tag, name);
                    } else {
                        childElement.setAttribute(name, value);
                    }
                });
                parseChildren(node, childElement);
                childElement.validate();
                break;

            case Node.TEXT_NODE:
            case Node.CDATA_SECTION_NODE:
                if (isTextNode(node.getPreviousSibling())) {
                    break;
                }
                
                String value = ((Text) node).getWholeText();

                if (value.trim().isEmpty()) {
                    break;
                }

                ComponentDefinition parentDef = parentElement.getDefinition();

                switch (parentDef == null ? ContentHandling.AS_CHILD : parentDef.contentHandling()) {
                    case ERROR:
                        throw new ParserException("Text content is not allowed for tag '<%s>'", parentDef.getTag());

                    case IGNORE:
                        break;

                    case AS_ATTRIBUTE:
                        parentElement.setAttribute(CONTENT_ATTR, normalizeText(value));
                        break;

                    case AS_CHILD:
                        def = ComponentRegistry.getInstance().get(Content.class);
                        childElement = new PageElement(def, parentElement);
                        childElement.setAttribute(CONTENT_ATTR, normalizeText(value));
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
                    throw new ParserException("Unrecognized processing instruction \"%s\"", pi.getTarget());
                }

                break;

            default:
                throw new ParserException("Unrecognized document content type \"%s\"", node.getNodeName());
        }
    }
    
    /**
     * Throws a bad attribute exception.
     *
     * @param tag Name of tag containing attribute.
     * @param name Name of offending attribute.
     */
    private void badAttribute(String tag, String name) {
        throw new ParserException("Unrecognized attribute \"%s\" on tag \"<%s>\"", name, tag);
    }

    /**
     * Processes a node's attributes for known namespaces.
     *
     * @param node A node.
     * @param consumer Consumer function to process attributes.
     */
    private void processAttributes(Node node, BiConsumer<String, String> consumer) {
        NamedNodeMap attributes = node.getAttributes();

        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = attributes.item(i);
            String name = normalizeNodeName(attr, attrNSMap);

            if (name != null && !"xmlns".equals(name)) {
                consumer.accept(name, attr.getNodeValue());
            }
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

    /**
     * Normalizes a namespace-prefixed node name by mapping the namespace's URL to a standard
     * prefix. If the namespace URL is not known, null is returned. If the node name has no prefix,
     * the original name is returned.
     *
     * @param node Node whose name is to be normalized.
     * @param nsMap Namespace URL to standard prefix mapping.
     * @return The normalized node name. If null, the node name could not be normalized.
     */
    private String normalizeNodeName(Node node, Map<String, String> nsMap) {
        String name = node.getNodeName();
        int i = name.indexOf(":");

        if (i > 0) {
            String pfx = name.substring(0, i);
            name = name.substring(i + 1);
            String uri = node.lookupNamespaceURI(pfx);
            pfx = nsMap.get(uri);
            name = pfx == null ? null : pfx.isEmpty() ? name : pfx + ":" + name;
        }

        return name;
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
    
    /**
     * Registers a processing instruction parser.
     *
     * @param piParser A processing instruction parser.
     */
    private void registerPIParser(PIParserBase piParser) {
        piParsers.put(piParser.getTarget(), piParser);
        log.info(() -> "Registered processing instruction parser for target '" + piParser.getTarget() + "'.");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof PIParserBase) {
            registerPIParser((PIParserBase) bean);
        }

        return bean;
    }

}
