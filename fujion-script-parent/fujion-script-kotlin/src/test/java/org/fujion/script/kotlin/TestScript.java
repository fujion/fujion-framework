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
package org.fujion.script.kotlin;

import org.fujion.script.IScriptLanguage;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collections;

/**
 * Test Kotlin scripts.
 */
public class TestScript {

    @Test
    @Ignore  // Tests succeed when run by IntelliJ, but fail when run independently.
    public void test() {
        IScriptLanguage lang = new KotlinScript();
        Assert.assertEquals(123, lang.parse("var x=120+3\nx").run());
        Assert.assertEquals(456, lang.parse("val self=bindings[\"self\"] as org.fujion.script.kotlin.TestScript\nself.testFcn()").run(Collections.singletonMap("self", this)));
        IScriptLanguage.IParsedScript script = lang.parse("val x=bindings[\"x\"] as Double\nkotlin.math.sqrt(x)");
        Assert.assertEquals(10.0, script.run(Collections.singletonMap("x", 100.0)));
        Assert.assertEquals(9.0, script.run(Collections.singletonMap("x", 81.0)));
    }

    public int testFcn() {
        return 456;
    }

}
