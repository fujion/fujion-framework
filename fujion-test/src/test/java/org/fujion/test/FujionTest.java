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
package org.fujion.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.fujion.annotation.ComponentDefinition;
import org.fujion.client.ExecutionContext;
import org.fujion.component.BaseComponent;
import org.fujion.component.Button;
import org.fujion.component.Combobox;
import org.fujion.component.Comboitem;
import org.fujion.component.Label;
import org.fujion.component.Page;
import org.fujion.component.Radiobutton;
import org.fujion.component.Radiogroup;
import org.fujion.component.Textbox;
import org.fujion.component.Toolbar;
import org.fujion.component.Treenode;
import org.fujion.component.Treeview;
import org.fujion.page.PageDefinition;
import org.fujion.page.PageElement;
import org.fujion.page.PageUtil;
import org.fujion.test.TestBinder.TestModel;
import org.junit.Test;

public class FujionTest extends MockTest {
    
    private PageDefinition getPageDefinition(String file) {
        String path = "file://" + ExecutionContext.getSession().getServletContext().getRealPath(file);
        return PageUtil.getPageDefinition(path);
    }
    
    private List<BaseComponent> createPage(String file, BaseComponent parent) {
        PageDefinition pagedef = getPageDefinition(file);
        return PageUtil.createPage(pagedef, parent);
    }
    
    @Test
    public void testParser() {
        PageDefinition pagedef = getPageDefinition("test.fsp");
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
    }
    
    @Test
    public void testNamespace() {
        PageDefinition pagedef = getPageDefinition("nstest.fsp");
        BaseComponent root = pagedef.materialize(null).get(0);
        BaseComponent ref = root.findByName("myinner");
        assertNotNull(ref);
        BaseComponent cmp = ref.findByName("mycomp");
        assertTrue(cmp instanceof Button);
        cmp = ref.findByName("^.mycomp");
        assertTrue(cmp instanceof Label);
        cmp = ref.findByName("mycomp2");
        assertTrue(cmp instanceof Toolbar);
        cmp = ref.findByName("^.mycomp2");
        assertNull(cmp);
        cmp = ref.findByName("^.myinner.mycomp2");
        assertTrue(cmp instanceof Toolbar);
        cmp = ref.findByName("^.mycomp.myinner.mycomp");
        assertTrue(cmp instanceof Button);
    }

    private static final String GREEN = "green";
    
    private static final String RED = "red";
    
    private static final String ORANGE = "orange";
    
    @Test
    public void testBinding() {
        PageDefinition pagedef = getPageDefinition("binding.fsp");
        TestBinder binder = new TestBinder();
        TestModel model = binder.getModel();
        Map<String, Object> args = Collections.singletonMap("binder", binder);
        BaseComponent root = pagedef.materialize(null, args).get(0);
        testBinding(root, model, GREEN);
        model.setColor(RED);
        testBinding(root, model, RED);
        root.findByName("textbox", Textbox.class).setValue(GREEN);
        testBinding(root, model, GREEN);
        root.findByName("combobox", Combobox.class).setSelectedIndex(1);
        testBinding(root, model, RED);
        root.findByName("rbGreen", Radiobutton.class).setChecked(true);
        testBinding(root, model, GREEN);
        root.findByName("rbRed", Radiobutton.class).setChecked(true);
        testBinding(root, model, RED);
        root.findByName("cbiGreen", Comboitem.class).setSelected(true);
        testBinding(root, model, GREEN);
        root.findByName("cbiRed", Comboitem.class).setSelected(true);
        testBinding(root, model, RED);
        root.findByName("label", Label.class).setLabel(GREEN);
        testBinding(root, model, RED, GREEN, false);
        model.setColor(ORANGE);
        testBinding(root, model, ORANGE, ORANGE, true);
        model.setColor(RED);
        testBinding(root, model, RED);
        root.findByName("textbox", Textbox.class).setValue(null);
        testBinding(root, model, null, null, true);
        model.setColor(GREEN);
        testBinding(root, model, GREEN);
    }

    private void testBinding(BaseComponent root, TestModel model, String value) {
        testBinding(root, model, value, value, false);
    }

    private void testBinding(BaseComponent root, TestModel model, String value, String labelValue, boolean noneSelected) {
        Textbox textbox = root.findByName("textbox", Textbox.class);
        Label label = root.findByName("label", Label.class);
        Combobox combobox = root.findByName("combobox", Combobox.class);
        Comboitem cbi = combobox.getSelectedItem();
        Radiogroup radiogroup = root.findByName("radiogroup", Radiogroup.class);
        Radiobutton rbtn = radiogroup.getSelected();
        assertEquals(value, model.getColor());
        assertEquals(value, textbox.getValue());
        assertEquals(labelValue, label.getLabel());
        
        if (noneSelected) {
            assertNull(cbi);
            assertNull(rbtn);
            assertNull(combobox.getValue());
        } else {
            assertEquals(value, combobox.getValue());
            assertEquals(value, cbi.getLabel());
            assertEquals(value, rbtn.getLabel());
        }
    }

    private final String[] nodes = { "1.1", "2.1", "3.1", "2.2", "1.2", "1.3", "2.1", "3.1", "2.2" };
    
    @Test
    public void testTreeview() {
        Treeview tv = (Treeview) createPage("treeview.fsp", null).get(0);
        
        // Test the node iterator
        int index = 0;
        
        for (Treenode node : tv) {
            assertEquals(nodes[index++], node.getLabel());
        }
        
        assertEquals(nodes.length, index);
    }
}
