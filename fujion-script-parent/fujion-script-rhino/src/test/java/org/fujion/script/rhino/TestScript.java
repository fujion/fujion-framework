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
package org.fujion.script.rhino;

import org.fujion.script.IScriptLanguage;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

/**
 * Test Lua scripts.
 */
public class TestScript {

    @Test
    public void test() {
        IScriptLanguage lang = new RhinoScript();
        Assert.assertEquals(1234, lang.parse("1234").run());
        IScriptLanguage.IParsedScript script = lang.parse("Math.sqrt(x);");
        Assert.assertEquals(10.0, script.run(Collections.singletonMap("x", 100)));
        Assert.assertEquals(9.0, script.run(Collections.singletonMap("x", 81)));
    }

}
