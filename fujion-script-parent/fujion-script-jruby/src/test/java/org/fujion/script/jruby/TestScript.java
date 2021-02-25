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
package org.fujion.script.jruby;

import org.fujion.script.IScriptLanguage;
import org.jruby.RubyFixnum;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

/**
 * Test JRuby scripts.
 */
public class TestScript {
    
    @Test
    public void test() {
        IScriptLanguage lang = new JRubyScript();
        Assert.assertEquals(1234, toInt(lang.parse("1234").run()));
        Assert.assertEquals(101, toInt(lang.parse("10*10+1").run()));
        Assert.assertEquals("TEST123", lang.parse("\"TEST123\"").run().toString());
        Assert.assertEquals(testInt(),
            toInt(lang.parse("$self.testInt").run(Collections.singletonMap(lang.getSelf(), this))));
    }
    
    private int toInt(Object value) {
        return ((RubyFixnum) value).getIntValue();
    }

    public int testInt() {
        return 4321;
    }
}
