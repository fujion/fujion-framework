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
package org.fujion.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.fujion.annotation.ComponentDefinition;
import org.fujion.client.ExecutionContext;
import org.fujion.component.BaseComponent;
import org.fujion.component.Page;
import org.fujion.page.PageDefinition;
import org.fujion.page.PageElement;
import org.fujion.page.PageUtil;
import org.junit.Test;

public class FujionTest extends MockTest {

    @Test
    public void parserTests() {
        PageDefinition pagedef = getPageDefinition("web/fujion/test/test.fsp");
        PageElement pgele = pagedef.getRootElement().getChildren().iterator().next();
        Page page = ExecutionContext.getPage();
        ComponentDefinition cmpdef = pgele.getDefinition();
        assertEquals("page", cmpdef.getTag());
        assertEquals(Page.class, cmpdef.getComponentClass());
        assertEquals("page", pgele.getAttributes().get("name"));
        
        List<BaseComponent> roots = PageUtil.createPage(pagedef, page, null);
        assertEquals(1, roots.size());
        BaseComponent root = roots.get(0);
        assertSame(page, root);
        assertEquals("page", page.getName());
        assertEquals("The Page Title", page.getTitle());
        // EL expressions
        BaseComponent el_expressions = page.findByName("el_expressions");
        assertNotNull(el_expressions);
        checkEL(el_expressions, 0, "org.fujion.expression.ELEvaluator");
        checkEL(el_expressions, 1, "This is a test.");
        checkEL(el_expressions, 2, 20);
        // Conditional blocks
        BaseComponent conditional_blocks = page.findByName("conditional_blocks");
        assertNotNull(conditional_blocks);
        assertNotNull(page.findByName("if_include"));
        assertNotNull(page.findByName("unless_include"));
        assertNull(page.findByName("if_exclude"));
        assertNull(page.findByName("unless_exclude"));
        // Namespaces
        BaseComponent namespaces = page.findByName("namespaces");
        assertNotNull(namespaces);
        checkNS(namespaces, "ns1_1", true);
        checkNS(namespaces, "ns2_1", false);
        checkNS(namespaces, "ns3_1", false);
        checkNS(namespaces, "ns2/ns1_1", false);
        checkNS(namespaces, "ns2/ns2_1", true);
        checkNS(namespaces, "ns2/ns3_1", false);
        checkNS(namespaces, "ns3/ns1_1", false);
        checkNS(namespaces, "ns3/ns2_1", false);
        checkNS(namespaces, "ns3/ns3_1", false);
        checkNS(namespaces, "ns2/ns3/ns1_1", false);
        checkNS(namespaces, "ns2/ns3/ns2_1", false);
        checkNS(namespaces, "ns2/ns3/ns3_1", true);
        // Labels
        BaseComponent labels = page.findByName("labels");
        assertNotNull(labels);
        checkLabel(labels, 0, "This is a test.");
        checkLabel(labels, 1, "This contains a label reference: This is a test.");
        checkLabel(labels, 2, "This contains a recursive reference: This contains a label reference: This is a test.");
        checkLabel(labels, 3, "This introduces an infinite recursion: Infinite recursion: test3");
        checkLabel(labels, 4, "Demonstration of a dotted name.");
        // Tag libraries
        BaseComponent tag_libraries = page.findByName("tag_libraries");
        assertNotNull(tag_libraries);
        assertEquals("This is a test.", tag_libraries.getFirstChild().getData());
    }

    private void checkEL(BaseComponent parent, int childIndex, Object expectedValue) {
        BaseComponent comp = parent.getChildAt(childIndex);
        assertEquals(expectedValue, comp.getAttribute("el"));
    }
    
    private void checkNS(BaseComponent ref, String path, boolean notNull) {
        BaseComponent comp = ref.findByName(path);
        assertTrue(notNull ? comp != null : comp == null);
    }

    private void checkLabel(BaseComponent parent, int childIndex, String expectedValue) {
        BaseComponent comp = parent.getChildAt(childIndex);
        assertEquals(expectedValue, comp.getData());
    }
}
