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
package org.fujion.core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.fujion.ancillary.ConvertUtil;
import org.fujion.ancillary.CssClasses;
import org.fujion.ancillary.CssStyles;
import org.fujion.ancillary.DeferredInvocation;
import org.fujion.ancillary.MimeContent;
import org.fujion.component.Div;
import org.fujion.event.KeyCode;
import org.junit.Test;
import org.springframework.util.Base64Utils;

public class Tests {
    
    private static final String ATTR_TEST = "ATTR_TEST";

    private static final String ATTR_OBJECT = "ATTR_OBJECT";

    private static final String ATTR_NULL = "ATTR_NULL";

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

    private enum TestEnum {
        TEST1, TEST2, TEST3
    }

    @Test
    public void conversionTests() {
        assertEquals("true", ConvertUtil.convert(true, String.class));
        assertEquals(Boolean.TRUE, ConvertUtil.convert("true", Boolean.class));
        assertEquals(TestEnum.TEST1, ConvertUtil.convert("test1", TestEnum.class));
        assertEquals("TEST2", ConvertUtil.convert(TestEnum.TEST2, String.class));

        assertEquals("function() {var x=1}", ConvertUtil.convertToJS("var x=1"));
        assertEquals("function() {var x=1}", ConvertUtil.convertToJS("function() {var x=1}"));
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
    
}
