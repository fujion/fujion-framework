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
package org.fujion.icon.test;

import org.junit.Test;

public class IconTests extends IconTestBase {

    @Test
    public void test() {
        setLibrary("material");
        doTests();
    }
    
    private void doTests() {
        singleton("filled/backup", "24x24");
        singleton("outlined/public.svg", "24");
        singleton("save.svg", "24");
        multiple("round/access*.svg", "*", 8);
        multiple("round/access*.svg", "24x24", 8);
    }
    
}
