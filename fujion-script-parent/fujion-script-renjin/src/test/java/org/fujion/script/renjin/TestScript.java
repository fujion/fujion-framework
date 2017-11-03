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
package org.fujion.script.renjin;

import java.util.Collections;
import java.util.Map;

import org.fujion.script.IScriptLanguage;
import org.junit.Assert;
import org.junit.Test;
import org.renjin.sexp.Vector;

/**
 * Test Renjin scripts.
 */
public class TestScript {
    
    @Test
    public void test() {
        IScriptLanguage lang = new RScript();
        Assert.assertEquals(1234, test(lang, "as.integer(1234)", null));
        Assert.assertEquals(101.0, test(lang, "10*10+1", null));
        Assert.assertEquals("TEST123", test(lang, "\"TEST123\"", null));
        Assert.assertEquals(testInt(), test(lang, "self$testInt()", Collections.singletonMap(lang.getSelf(), this)));
    }
    
    private Object test(IScriptLanguage lang, String expression, Map<String, Object> args) {
        Vector result = (Vector) lang.parse(expression).run(args);
        return result.getElementAsObject(0);
    }
    
    public int testInt() {
        return 4321;
    }
    
}
