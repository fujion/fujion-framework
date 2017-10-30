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
package org.fujion.annotation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.fujion.ancillary.ComponentRegistry;
import org.fujion.annotation.Component.ContentHandling;
import org.fujion.annotation.ComponentDefinition.Cardinality;
import org.fujion.common.StrUtil;
import org.fujion.common.Version;
import org.fujion.common.Version.VersionPart;
import org.fujion.common.XMLUtil;
import org.fujion.page.PageParser;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Generate an XML schema from annotations.
 */
public class SchemaGenerator {

    private static final String NS_SCHEMA = "http://www.w3.org/2001/XMLSchema";

    private static final String NS_VERSIONING = "http://www.w3.org/2007/XMLSchema-versioning";

    private static final String UNBOUNDED = "unbounded";
    
    private final Document schema;
    
    private final Element root;

    /**
     * Main entry point.
     *
     * @param args The command line arguments.
     * @throws Exception Unspecified exception.
     */
    public static void main(String... args) throws Exception {
        Options options = new Options();
        Option option = new Option("p", "packages", true, "Java package(s) to scan");
        option.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(option);
        option = new Option("c", "classes", true, "Java class(es) to scan");
        option.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(option);
        option = new Option("v", "version", true, "Schema version");
        options.addOption(option);
        option = new Option("t", "title", true, "Title for schema documentation");
        options.addOption(option);
        option = new Option("h", "help", false, "This help message");
        options.addOption(option);
        CommandLine cmd = new DefaultParser().parse(options, args);

        if (cmd.hasOption("h")) {
            new HelpFormatter().printHelp("SchemaGenerator [options] ...", options);
            return;
        }

        String xml = new SchemaGenerator(cmd.getOptionValues("p"), cmd.getOptionValues("c"), cmd.getOptionValue("v"),
                cmd.getOptionValue("t")).toString();
        String output = cmd.getArgs().length == 0 ? null : cmd.getArgs()[0];

        if (output == null) {
            System.out.println(xml);
        } else {
            File file = new File(output);
            FileUtils.forceMkdirParent(file);

            try (OutputStream strm = new FileOutputStream(file)) {
                IOUtils.write(xml, strm, StandardCharsets.UTF_8);
            }
        }
    }

    public SchemaGenerator(String[] packages, String[] classes, String version, String title) throws Exception {
        version = StringUtils.isEmpty(version) ? SchemaGenerator.class.getPackage().getImplementationVersion() : version;

        if (!StringUtils.isEmpty(version)) {
            version = new Version(version).toString(VersionPart.MINOR);
            version = StrUtil.piece(version, ".", 1, 2);
        }

        ComponentRegistry registry = ComponentRegistry.getInstance();
        boolean isRoot = packages == null && classes == null;
        Element ele;

        if (packages != null) {
            for (String pkg : packages) {
                ComponentScanner.getInstance().scanPackage(pkg);
            }
        }
        
        if (classes != null) {
            for (String clazz : classes) {
                ComponentScanner.getInstance().scanClass(clazz);
            }
        }
        
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        schema = docBuilder.newDocument();
        root = schema.createElementNS(NS_SCHEMA, "xs:schema");
        root.setAttribute("targetNamespace", PageParser.NS_FSP);
        root.setAttribute("xmlns:fsp", PageParser.NS_FSP);
        root.setAttributeNS(NS_VERSIONING, "vc:minVersion", "1.1");
        root.setAttribute("elementFormDefault", "qualified");
        schema.appendChild(root);
        ele = createElement("annotation", root);
        createElement("documentation", ele).setTextContent(
            "Fujion Server Page" + formatForTitle(title, ", %s") + formatForTitle(version, ", version %s"));
        
        if (isRoot) {
            ele = createElement("simpleType", root, "name", "el");
            ele = createElement("restriction", ele, "base", "xs:string");
            createElement("pattern", ele, "value", ".*\\$\\{.+\\}.*");
            addExtendedType("boolean");
            addExtendedType("decimal");
            addExtendedType("integer");
            ele = createElement("element", root, "name", "fsp");
            ele = createElement("complexType", ele, "mixed", "true");
            ele = createElement("all", ele);
            ele = createElement("any", ele, "namespace", "##targetNamespace");
            setCardinality(ele, 0, UNBOUNDED);
            ele = createElement("element", root, "name", "anyParent");
            ele.setAttribute("abstract", "true");
            return;
        }
        
        Assert.isTrue(registry.size() > 0, "No component annotations found");
        createElement("include", root, "schemaLocation", PageParser.NS_FSP + "-root.xsd");

        for (ComponentDefinition def : registry) {
            if (def.getTag().startsWith("#")) {
                continue;
            }

            ele = createElement("element", root, "name", def.getTag());
            Element ct = createElement("complexType", ele);

            if (def.getParentTags().contains("*")) {
                ele.setAttribute("substitutionGroup", "fsp:anyParent");
            }

            boolean childrenAllowed = def.childrenAllowed();
            boolean contentAllowed = def.contentHandling() != ContentHandling.ERROR;

            if (!childrenAllowed && contentAllowed) {
                Element sc = createElement("simpleContent", ct);
                ct = createElement("extension", sc);
                ct.setAttribute("base", "xs:string");
            } else if (childrenAllowed) {
                if (contentAllowed) {
                    ct.setAttribute("mixed", "true");
                }

                Element childAnchor = createElement("all", ct);

                for (Entry<String, Cardinality> childTag : def.getChildTags().entrySet()) {
                    String tag = childTag.getKey();
                    Cardinality card = childTag.getValue();

                    if ("*".equals(tag)) {
                        ele = createElement("element", childAnchor, "ref", "fsp:anyParent");
                        setCardinality(ele, 0, UNBOUNDED);
                    } else {
                        addChildElement(childAnchor, registry.get(tag), def, card);
                    }

                }
            }

            processAttributes(def.getSetters(), ct);
            processAttributes(def.getFactoryParameters(), ct);
            createElement("anyAttribute", ct, "namespace", "##other").setAttribute("processContents", "lax");
        }
    }

    private String formatForTitle(String value, String format) {
        return StringUtils.isEmpty(value) ? "" : String.format(format, value);
    }

    private void processAttributes(Map<String, Method> setters, Element ct) {
        for (Entry<String, Method> setter : setters.entrySet()) {
            String key = setter.getKey();

            if (key.startsWith("#") || key.endsWith(":")) {
                continue;
            }

            Element attr = createElement("attribute", ct, "name", setter.getKey());
            Class<?> javaType = setter.getValue().getParameterTypes()[0];

            if (javaType.isEnum()) {
                processEnum(attr, javaType);
            } else {
                attr.setAttribute("type", getType(javaType));
            }
        }
    }

    private void addChildElement(Element seq, ComponentDefinition childDef, ComponentDefinition parentDef,
                                 Cardinality card) {

        if (childDef.getTag().startsWith("#")) {
            return;
        }

        if (childDef != null && !childDef.isParentTag(parentDef.getTag())) {
            return;
        }

        Element child = createElement("element", seq, "ref", "fsp:" + childDef.getTag());
        setCardinality(child, card.getMinimum(), card.hasMaximum() ? card.getMaximum() : UNBOUNDED);
    }

    private Element addExtendedType(String type) {
        Element ele = createElement("simpleType", root, "name", type);
        createElement("union", ele, "memberTypes", "xs:" + type + " fsp:el");
        return ele;
    }

    private Element setCardinality(Element ele, Object minOccurs, Object maxOccurs) {
        ele.setAttribute("minOccurs", minOccurs.toString());
        ele.setAttribute("maxOccurs", maxOccurs.toString());
        return ele;
    }
    
    private void processEnum(Element attr, Class<?> javaType) {
        String name = findElement("element", attr).getAttribute("name") + "_" + attr.getAttribute("name");
        Element root = attr.getOwnerDocument().getDocumentElement();
        attr.setAttribute("type", "fsp:" + name);
        Element st = createElement("simpleType", root, "name", name);
        Element union = createElement("union", st, "memberTypes", "fsp:el");
        st = createElement("simpleType", union);
        Element res = createElement("restriction", st);
        res.setAttribute("base", "xs:string");

        for (Object val : javaType.getEnumConstants()) {
            createElement("enumeration", res, "value", val.toString().toLowerCase());
        }
    }

    private String getType(Class<?> javaType) {
        String type = null;
        type = type != null ? type : getType(javaType, "fsp:boolean", boolean.class, Boolean.class);
        type = type != null ? type : getType(javaType, "fsp:integer", int.class, Integer.class);
        type = type != null ? type : getType(javaType, "fsp:decimal", float.class, Float.class, double.class, Double.class);
        return type != null ? type : "xs:string";
    }

    private String getType(Class<?> javaType, String type, Class<?>... classes) {
        for (Class<?> clazz : classes) {
            if (clazz.isAssignableFrom(javaType)) {
                return type;
            }
        }

        return null;
    }

    private Element findElement(String tag, Element ele) {
        tag = "xs:" + tag;
        
        while (ele != null) {
            if (ele.getTagName().equals(tag)) {
                return ele;
            }

            ele = (Element) ele.getParentNode();
        }

        return null;
    }

    private Element createElement(String tag) {
        return schema.createElement("xs:" + tag);
    }
    
    private Element createElement(String tag, Element parent) {
        return createElement(tag, parent, null, null);
    }

    private Element createElement(String tag, Element parent, String keyName, String keyValue) {
        Element element = createElement(tag);
        Element ref = null;
        
        if (keyName != null) {
            element.setAttribute(keyName, keyValue);
        }

        NodeList nodes = parent.getChildNodes();

        for (int i = 0, j = nodes.getLength(); i < j; i++) {
            Element sib = (Element) nodes.item(i);

            if (!sib.getTagName().endsWith(tag)) {
                continue;
            }

            String val = keyName == null ? null : sib.getAttribute(keyName);

            if (val != null && val.compareToIgnoreCase(keyValue) >= 0) {
                ref = sib;
                break;
            } else {
                ref = (Element) sib.getNextSibling();
            }
        }

        parent.insertBefore(element, ref);
        return element;
    }
    
    /**
     * Returns the text representation of the generated schema.
     */
    @Override
    public String toString() {
        return XMLUtil.toString(schema);
    }

}
