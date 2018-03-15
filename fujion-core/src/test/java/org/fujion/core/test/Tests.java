/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2018 Regenstrief Institute, Inc.
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
package org.fujion.core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.fujion.ancillary.ConvertUtil;
import org.fujion.ancillary.CssClasses;
import org.fujion.ancillary.CssStyles;
import org.fujion.ancillary.DeferredInvocation;
import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.MimeContent;
import org.fujion.annotation.ComponentDefinition;
import org.fujion.annotation.ComponentScanner;
import org.fujion.common.MiscUtil;
import org.fujion.component.BaseComponent;
import org.fujion.component.Button;
import org.fujion.component.Combobox;
import org.fujion.component.Comboitem;
import org.fujion.component.Div;
import org.fujion.component.Label;
import org.fujion.component.Page;
import org.fujion.component.Radiobutton;
import org.fujion.component.Radiogroup;
import org.fujion.component.Textbox;
import org.fujion.component.Toolbar;
import org.fujion.component.Treenode;
import org.fujion.component.Treeview;
import org.fujion.core.test.TestBinder.TestModel;
import org.fujion.event.KeyCode;
import org.fujion.page.PageDefinition;
import org.fujion.page.PageElement;
import org.fujion.page.PageParser;
import org.fujion.page.PageSource;
import org.fujion.page.PageUtil;
import org.fujion.theme.Theme;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.Base64Utils;

/**
 * Unit test for core functionality. More units tests are in the fujion-test package to avoid
 * circular dependencies.
 */
public class Tests {
    
    private static final String ATTR_TEST = "ATTR_TEST";

    private static final String ATTR_OBJECT = "ATTR_OBJECT";

    private static final String ATTR_NULL = "ATTR_NULL";

    @BeforeClass
    public static void beforeTests() {
        ComponentScanner.getInstance().scanPackage("org.fujion.component");
    }
    
    @Test
    public void parserTests() {
        PageDefinition pagedef = getPageDefinition("test.fsp");
        PageElement pgele = pagedef.getRootElement().getChildren().iterator().next();
        ComponentDefinition cmpdef = pgele.getDefinition();
        assertEquals("page", cmpdef.getTag());
        assertEquals(Page.class, cmpdef.getComponentClass());
        assertEquals("page", pgele.getAttributes().get("name"));
        
        Page page = new Page();
        List<BaseComponent> roots = PageUtil.createPage(pagedef, page, null);
        assertEquals(1, roots.size());
        assertEquals(page, roots.get(0));
        assertEquals("page", page.getName());
        assertEquals("The Page Title", page.getTitle());
    }
    
    @Test
    public void attributeTests() {
        Div cmpt = new Div();
        cmpt.setAttribute(ATTR_OBJECT, new Object());
        cmpt.setAttribute(ATTR_TEST, 1234);
        assertTrue(1234 == cmpt.getAttribute(ATTR_TEST, 0));
        assertTrue(4321 == cmpt.getAttribute(ATTR_OBJECT, 4321));
        assertTrue(5678 == cmpt.getAttribute(ATTR_NULL, 5678));
        cmpt.setAttribute(ATTR_TEST, true);
        assertTrue(cmpt.getAttribute(ATTR_TEST, Boolean.class));
        cmpt.setAttribute(ATTR_TEST, "TRUE");
        assertTrue(cmpt.getAttribute(ATTR_TEST, Boolean.class));
        cmpt.setAttribute(ATTR_TEST, "ANYTHING BUT TRUE");
        assertNull(cmpt.getAttribute(ATTR_TEST, Boolean.class));
        assertNull(cmpt.getAttribute(ATTR_OBJECT, Boolean.class));
        assertNull(cmpt.getAttribute(ATTR_NULL, Boolean.class));
        cmpt.setAttribute(ATTR_TEST, ATTR_TEST);
        assertEquals(ATTR_TEST, cmpt.getAttribute(ATTR_TEST, String.class));
        assertTrue(cmpt.getAttribute(ATTR_OBJECT, String.class).contains("Object"));
        assertNull(cmpt.getAttribute(ATTR_NULL, String.class));
        List<Boolean> list = new ArrayList<>();
        cmpt.setAttribute(ATTR_TEST, list);
        assertSame(list, cmpt.getAttribute(ATTR_TEST, List.class));
        assertNotNull(cmpt.getAttribute(ATTR_OBJECT));
        assertNull(cmpt.getAttribute(ATTR_NULL));
        cmpt.setAttribute(ATTR_TEST, cmpt);
        assertSame(cmpt, cmpt.getAttribute(ATTR_TEST, Div.class));
        assertNull(cmpt.getAttribute(ATTR_OBJECT, Div.class));
    }

    @Test
    public void namespaceTests() {
        PageDefinition pagedef = getPageDefinition("nstest.fsp");
        BaseComponent root = pagedef.materialize(null).get(0);
        BaseComponent ref = root.findByName("myinner");
        assertNotNull(ref);
        BaseComponent cmp = ref.findByName("mycomp");
        assertTrue(cmp instanceof Button);
        cmp = ref.findByName("^/mycomp");
        assertTrue(cmp instanceof Label);
        cmp = ref.findByName("mycomp2");
        assertTrue(cmp instanceof Toolbar);
        cmp = ref.findByName("^/mycomp2");
        assertNull(cmp);
        cmp = ref.findByName("^/myinner/mycomp2");
        assertTrue(cmp instanceof Toolbar);
        cmp = ref.findByName("^/mycomp/myinner/mycomp");
        assertTrue(cmp instanceof Button);
    }

    private enum TestEnum {
        TEST1, TEST2, TEST3
    }

    @Test
    public void conversionTests() {
        assertEquals("true", ConvertUtil.convert(true, String.class));
        assertEquals(Boolean.TRUE, ConvertUtil.convert("true", Boolean.class));
        assertEquals(TestEnum.TEST1, ConvertUtil.convert("test1", TestEnum.class));
        assertEquals("TEST2", ConvertUtil.convert(TestEnum.TEST2, String.class));

        assertEquals("function() {var x=1}", ConvertUtil.convert("var x=1", JavaScript.class).toString());
        assertEquals("function() {var x=1}",
            ConvertUtil.convert("function() {var x=1}", JavaScript.class).toString());
    }
    
    @Test
    public void cssClassesTests() {
        CssClasses classes = new CssClasses();
        assertTrue(!classes.hasChanged());
        classes.parse("flavor:red size:big test");
        assertTrue(classes.hasChanged());
        assertEquals("big red test", classes.toString());
        classes.add("flavor:blue size:small -test");
        assertEquals("blue small", classes.toString());
        classes.remove("flavor:");
        assertEquals("small", classes.toString());
        classes.add("-size:");
        assertTrue(classes.hasChanged());
        assertEquals("", classes.toString(true));
        assertTrue(!classes.hasChanged());
    }
    
    @Test
    public void cssStylesTests() {
        CssStyles styles = new CssStyles();
        assertTrue(styles.isEmpty());
        styles.put("style1", "value1");
        assertTrue(!styles.isEmpty());
        assertEquals("style1:value1", styles.toString());
        assertEquals("value1", styles.get("style1"));
        styles.parse("style2 : value2", false);
        assertEquals("style1:value1;style2:value2", styles.toString());
        assertEquals("value1", styles.get("style1"));
        assertEquals("value2", styles.get("style2"));
        styles.remove("style1");
        assertEquals("style2:value2", styles.toString());
        assertNull(styles.get("style1"));
        styles.parse("style1:value1", true);
        assertEquals("style1:value1", styles.toString());
        styles.put("style1", null);
        assertTrue(styles.isEmpty());
    }
    
    @Test
    public void deferredInvocationTests() throws Exception {
        Method method = Tests.class.getDeclaredMethod("_testMethod", String.class, String.class);
        DeferredInvocation<String> inv1 = new DeferredInvocation<>(this, method);
        assertEquals("test1;test2", inv1.invoke("test1", "test2"));
        DeferredInvocation<String> inv2 = new DeferredInvocation<>(this, method, "test3");
        assertEquals("test3;test4", inv2.invoke("test4"));
        DeferredInvocation<String> inv3 = new DeferredInvocation<>(this, method, "test5", "test6");
        assertEquals("test5;test6", inv3.invoke());
        DeferredInvocation<String> inv4 = new DeferredInvocation<>(this, method, TestEnum.TEST1, TestEnum.TEST2);
        assertEquals("TEST1;TEST2", inv4.invoke());

        try {
            inv4.addArgs(new Object());
            fail("Expected illegal argument exception.");
        } catch (IllegalArgumentException e) {}
    }

    protected String _testMethod(String arg1, String arg2) {
        return arg1 + ";" + arg2;
    }

    @Test
    public void keyCodeTests() {
        assertEquals(KeyCode.VK_BACK_SPACE, KeyCode.fromCode(8));
        assertEquals(KeyCode.VK_ASTERISK, KeyCode.fromString("ASTERISK"));
        assertEquals(KeyCode.normalizeKeyCapture("^A ~F1 ^@~@^$1"), "^#65 ~#112 ^@~$#49");
    }
    
    private static final byte[] TEST_CONTENT = { 'a', 'e', 'i', 'o', 'u' };
    
    @Test
    public void mimeContentTests() {
        MimeContent content = new MimeContent("image/png", TEST_CONTENT);
        String src = content.getSrc();
        assertEquals("data:image/png;base64,YWVpb3U=", src);
        assertEquals("aeiou", new String(Base64Utils.decodeFromString(src.split("\\,", 2)[1])));
    }
    
    private static final String GREEN = "green";
    
    private static final String RED = "red";
    
    private static final String ORANGE = "orange";
    
    @Test
    public void bindingTests() {
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

    @Test
    public void testThemes() {
        Theme theme = new Theme("test-theme");
        theme.addMapping("org/acme/*/test/*/**", "org/fujion/$0/test/$2/$1/$3");
        theme.addMapping("*/test/**/*.css", "$1:$2:$3");
        theme.addMapping("/webjars/bootstrap/**", "/webjars/bootswatch-$0/$1");
        assertEquals("org/fujion/test-theme/test/test2/test1/file.xyz",
            theme.translatePath("org/acme/test1/test/test2/file.xyz"));
        assertEquals("test1:test2.1/test2.2:test3", theme.translatePath("test1/test/test2.1/test2.2/test3.css"));
        assertEquals("/webjars/bootswatch-test-theme/css/bootstrap.css",
            theme.translatePath("/webjars/bootstrap/css/bootstrap.css"));
        assertNull(theme.translatePath("this/should/not/match"));
    }

    private PageDefinition getPageDefinition(String file) {
        try (InputStream is = getClass().getResourceAsStream("/" + file)) {
            Assert.assertNotNull(is);
            PageSource src = new PageSource(is, file);
            return PageParser.getInstance().parse(src);
        } catch (Exception e) {
            throw MiscUtil.toUnchecked(e);
        }
    }
    
    private List<BaseComponent> createPage(String file, BaseComponent parent) {
        PageDefinition pagedef = getPageDefinition(file);
        return PageUtil.createPage(pagedef, parent);
    }
    
}
