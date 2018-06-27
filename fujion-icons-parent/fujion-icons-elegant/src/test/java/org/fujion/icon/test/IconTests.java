/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2018 Fujion Framework
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
        setLibrary("elegant");
        doTests();
    }
    
    private void doTests() {
        singleton("arrow_left", "32x32");
        singleton("icon_star.png", "32");
        singleton("icon_star.svg", "32x32");
        multiple("arrow*.png", "*", 64);
        multiple("arrow*.svg", "32x32", 64);
    }
    
}
