/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2023 Fujion Framework
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

import org.apache.commons.io.FileUtils;
import org.fujion.ancillary.JavaScript;
import org.fujion.ancillary.OptionMap;
import org.fujion.ancillary.Options;
import org.fujion.annotation.Option;
import org.fujion.common.JSONUtil;
import org.fujion.common.StrUtil;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Tests for Option class functionality.
 */
public class OptionTests {

    private static class TestOptions1 extends Options {

        // primitive type: should serialize
        @Option
        public int should0;

        // with value: should serialize
        @Option
        public Integer should1 = 1;

        // no annotation: should not serialize
        @SuppressWarnings("unused")
        public final Integer shouldnot2 = 2;
        
        // set to ignore: should not serialize
        @Option(ignore = true)
        private final Integer shouldnot3 = 3;

        // with value: should serialize
        @Option
        private final Integer should4 = 4;

        // first alternate form without value: should not serialize
        @Option("alternate5")
        public String alternate5$shouldnot;

        // second alternate form with value: should serialize
        @Option("alternate5")
        public Integer alternate5$should = 5;

        // third alternate form without value: should not serialize
        @Option("alternate5")
        public String alternate5$shouldnoteither;

        // should serialize under submap
        @Option("map1.should6")
        public Integer map1_should6 = 6;

        // should not serialize under submap
        @Option("map1.shouldnot7")
        public Integer map1_shouldnot7;

        // empty map2 should not serialize
        @Option("map2.shouldnot8")
        public Integer map2_shouldnot8;

        // empty map2 should not serialize
        @Option("map2.shouldnot9")
        public Integer map2_shouldnot9;

        // non-empty map: should serialize
        @Option("map3")
        public TestOptions3 map3$should10 = new TestOptions3();

        // empty map: should not serialize
        @Option("map4")
        public TestOptions4 map4$shouldnot11 = new TestOptions4();
        
        // should serialize as JavaScript instance
        @Option(convertTo = JavaScript.class)
        protected String should12 = "x=1;";

        // should serialize as numeric value
        @Option(convertUsing = "${value ? 1 : -1}")
        private final Boolean should13 = true;

    }
    
    public static class TestOptions3 extends Options {

        @Option
        public Integer should30 = 30;

        @Option
        public Integer shouldnot31;
    }
    
    public static class TestOptions4 extends Options {

        @Option
        public Integer shouldnot40;

        @Option
        public Integer shouldnot41;
    }
    
    @Test
    public void test() throws Exception {
        TestOptions1 opt = new TestOptions1();
        OptionMap map = opt.toMap();
        String json = FileUtils.readFileToString(ResourceUtils.getFile("classpath:options.json"), StrUtil.UTF8);
        @SuppressWarnings("unchecked")
        Map<String, Object> expected = (Map<String, Object>) JSONUtil.deserialize(json);
        expected.put("should12", new JavaScript((String) expected.get("should12")));
        assertTrue(expected.equals(map));
    }
}
