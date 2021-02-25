/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2021 Fujion Framework
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
package org.fujion.spring;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.expression.ParseException;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

/**
 * Provides namespace support for scanning for Fujion class annotations.
 */
public abstract class AbstractXmlParser extends AbstractSingleBeanDefinitionParser {

    private static final String[] ATTRIBUTES = { "class", "package" };

    protected abstract void setTargetObject(BeanDefinitionBuilder builder);

    @Override
    protected String getBeanClassName(Element element) {
        return "org.springframework.beans.factory.config.MethodInvokingBean";
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        if (element.getAttributes().getLength() > 1) {
            error("Invalid attributes specified");
        }
        
        Attr attr = (Attr) element.getAttributes().item(0);
        int i = attr == null ? -2 : ArrayUtils.indexOf(ATTRIBUTES, attr.getName());
        
        switch (i) {
            case -2: // Missing attribute
                error("Missing attribute");
                
            case -1: // Unknown attribute
                error("Unrecognized attribute: " + attr.getName());
                
            case 0: // class
                builder.addPropertyValue("targetMethod", "scanClass");
                break;

            case 1: // package
                builder.addPropertyValue("targetMethod", "scanPackage");
                break;

        }

        setTargetObject(builder);
        builder.addPropertyValue("arguments", new Object[] { attr.getValue() });
    }
    
    @Override
    protected boolean shouldGenerateId() {
        return true;
    }
    
    private void error(String message) {
        throw new ParseException(0, message + ".\n" + "Must be one of: " + String.join(", ", ATTRIBUTES));
    }
}
