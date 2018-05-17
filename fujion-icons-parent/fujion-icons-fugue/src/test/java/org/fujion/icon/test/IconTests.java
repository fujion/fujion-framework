/* #%L
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
package org.fujion.icon.test;

import org.junit.Test;

public class IconTests extends IconTestBase {

    @Test
    public void test() {
        setLibrary("fugue");
        doTests();
        setLibrary("fugue-shadowless");
        doTests();
    }
    
    private void doTests() {
        singleton("acorn", "16x16");
        singleton("application.png", "24");
        singleton("counter.gif", "16x16");
        multiple("arrow*.png", "*", 155);
        multiple("arrow*.png", "32x32", 4);
        multiple("counter*.gif", null, 3);
    }
    
}
