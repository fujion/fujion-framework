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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.fujion.ancillary.OptionMap;
import org.fujion.ancillary.Options;
import org.junit.Test;

/**
 * Tests for Option class functionality.
 */
public class OptionTests {
    
    private static class TestOptions1 extends Options {
        
        // primitive type: should serialize
        @SuppressWarnings("unused")
        public int should0;
        
        // public with value: should serialize
        @SuppressWarnings("unused")
        public Integer should1 = 1;
        
        // private with or without value: should not serialize
        @SuppressWarnings("unused")
        private final Integer shouldnot2 = 2;

        // transient: should not serialize
        @SuppressWarnings("unused")
        public transient Integer shouldnot3 = 3;
        
        // protected with value: should serialize
        @SuppressWarnings("unused")
        protected Integer should4 = 4;
        
        // first alternate form without value: should not serialize
        @SuppressWarnings("unused")
        public String alternate5$shouldnot;
        
        // second alternate form with value: should serialize
        @SuppressWarnings("unused")
        public Integer alternate5$should = 5;
        
        // third alternate form without value: should not serialize
        @SuppressWarnings("unused")
        public String alternate5$shouldnoteither;
        
        // should serialize under submap
        @SuppressWarnings("unused")
        public Integer map1_should6 = 6;
        
        // should not serialize under submap
        @SuppressWarnings("unused")
        public Integer map1_shouldnot7;
        
        // empty map2 should not serialize
        @SuppressWarnings("unused")
        public Integer map2_shouldnot8;
        
        // empty map2 should not serialize
        @SuppressWarnings("unused")
        public Integer map2_shouldnot9;
        
        // non-empty map: should serialize
        @SuppressWarnings("unused")
        public TestOptions3 map3$should10 = new TestOptions3();
        
        // empty map: should not serialize
        @SuppressWarnings("unused")
        public TestOptions4 map4$shouldnot11 = new TestOptions4();

        // test of underscore escape.
        @SuppressWarnings("unused")
        public Integer should__12 = 12;

    }
    
    public static class TestOptions3 extends Options {
        
        public Integer should30 = 30;
        
        public Integer shouldnot31;
    }

    public static class TestOptions4 extends Options {
        
        public Integer shouldnot40;
        
        public Integer shouldnot41;
    }

    @Test
    public void test() {
        TestOptions1 opt = new TestOptions1();
        OptionMap map = opt.toMap();
        assertEquals(0, map.get("should0"));
        assertEquals(1, map.get("should1"));
        assertFalse(map.containsKey("shouldnot2"));
        assertFalse(map.containsKey("shouldnot3"));
        assertEquals(4, map.get("should4"));
        assertEquals(5, map.get("alternate5"));
        assertTrue(map.get("map1") instanceof OptionMap);
        assertFalse(map.containsKey("map2"));
        assertTrue(map.get("map3") instanceof OptionMap);
        assertFalse(map.containsKey("map4"));
        assertEquals(12, map.get("should_12"));
        OptionMap map1 = (OptionMap) map.get("map1");
        assertEquals(6, map1.get("should6"));
        assertFalse(map1.containsKey("shouldnot7"));
        OptionMap map3 = (OptionMap) map.get("map3");
        assertEquals(30, map3.get("should30"));
        assertFalse(map3.containsKey("shouldnot31"));
    }
}
