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
package org.fujion.icon.test;

import org.junit.Test;

public class IconTests extends IconTestBase {
    
    @Test
    public void test() {
        setLibrary("iconic");
        singleton("action-redo", "16x16");
        singleton("action-redo", "8x8");
        multiple("arrow*.png", "8x*", 12);
        multiple("arrow*.png", "*x32", 12);
        multiple("arrow*.png", "16x16", 12);
        multiple("arrow*.svg", "8x8", 12);
    }
    
}
