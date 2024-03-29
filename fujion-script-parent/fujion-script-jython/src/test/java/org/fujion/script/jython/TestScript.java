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
package org.fujion.script.jython;

import org.fujion.script.IScriptLanguage;
import org.junit.Assert;
import org.junit.Test;
import org.python.core.PyInteger;

import java.util.Collections;

/**
 * Test Jython scripts.
 */
public class TestScript {

    @Test
    public void test() {
        IScriptLanguage lang = new JythonScript();
        Assert.assertEquals(1234, toInt(lang.parse("1234").run()));
        Assert.assertEquals(101, toInt(lang.parse("10*10+1").run()));
        Assert.assertEquals("TEST123", lang.parse("\"TEST123\"").run().toString());
        Assert.assertEquals(testInt(),
            toInt(lang.parse("self.testInt()").run(Collections.singletonMap(lang.getSelf(), this))));
    }

    private int toInt(Object value) {
        return ((PyInteger) value).asInt();
    }
    
    public int testInt() {
        return 4321;
    }
}
